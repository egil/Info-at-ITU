package dk.itu.info.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ProxyService extends IntentService {
	private static long PING_DELAY = 1200000;

	private String btmacaddr;
	private Account account;
	private DefaultHttpClient http_client;
	private volatile Boolean isRunning = false;

	public ProxyService() {
		super("TrackingService");
		this.http_client = InfoatITUAppActivity.http_client;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("info@itu", "Starting proxy service");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		isRunning = false;
		// The service is no longer used and is being destroyed
		Log.i("info@itu", "Stopping proxy service");

		// call /leaving on proxy service
		new AuthenticatedRequestTask().doInBackground("http://info-at-itu-proxy.appspot.com/leaving?" + btmacaddr);

		super.onDestroy();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		btmacaddr = (String) intent.getExtras().get("btmacaddr");
		account = (Account) intent.getExtras().get("account");
		// call /entering on proxy service
		new AuthenticatedRequestTask().doInBackground("http://info-at-itu-proxy.appspot.com/entering?" + btmacaddr + "&" + account.name);

		// every 20 minutes, call /ping on proxy service
		isRunning = true;
		while (isRunning) {
			synchronized (this) {
				try {
					wait(PING_DELAY);
					if(isRunning) {
						new AuthenticatedRequestTask().doInBackground("http://info-at-itu-proxy.appspot.com/ping?" + btmacaddr);
					}
				} catch (Exception e) {
					Log.e("info@itu", "Crashed while waiting.", e);
					stopSelf();
				}
			}
		}
	}

	private class AuthenticatedRequestTask extends AsyncTask<String, Void, HttpResponse> {
		@Override
		protected HttpResponse doInBackground(String... urls) {
			try {
				Log.i("info@itu", "Proxy call: " + urls[0]);
				HttpGet http_get = new HttpGet(urls[0]);

				return http_client.execute(http_get);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				Log.e("info@itu", "Crashed while accss", e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
}
