package dk.itu.info.app;

import java.io.IOException;
import java.util.List;

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
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class AppInfo extends Activity {
	private DefaultHttpClient http_client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_info);
		this.http_client = InfoatITUAppActivity.http_client;
	}

	@Override
	protected void onResume() {
		super.onResume();
		Intent intent = getIntent();
		AccountManager accountManager = AccountManager.get(getApplicationContext());		
		Account account = (Account)intent.getExtras().get("account");
		accountManager.getAuthToken(account, "ah", false, new GetAuthTokenCallback(), null);
	}

	private class GetAuthTokenCallback implements AccountManagerCallback<Bundle> {
		public void run(AccountManagerFuture<Bundle> result) {
			Bundle bundle;
			try {
				bundle = result.getResult();
				Intent intent = (Intent)bundle.get(AccountManager.KEY_INTENT);
				if(intent != null) {
					// User input required
					startActivity(intent);
				} else {
					onGetAuthToken(bundle);
				}
			} catch (OperationCanceledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AuthenticatorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	protected void onGetAuthToken(Bundle bundle) {
		// invalidate auth token
//		AccountManager accountManager = AccountManager.get(getApplicationContext());
//		accountManager.invalidateAuthToken(AccountManager.KEY_ACCOUNT_TYPE, authToken)
				
		String auth_token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
		new GetCookieTask().execute(auth_token);
	}

	private class GetCookieTask extends AsyncTask<String, Void, Boolean> {
		protected Boolean doInBackground(String... tokens) {
			try {
				// Don't follow redirects
				http_client.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, false);
				
				HttpGet http_get = new HttpGet("https://info-at-itu-proxy.appspot.com/_ah/login?continue=http://localhost/&auth=" + tokens[0]);
				HttpResponse response;
				response = http_client.execute(http_get);
				if(response.getStatusLine().getStatusCode() != 302)
					// Response should be a redirect
					return false;
				List<Cookie> cookies = http_client.getCookieStore().getCookies();
				for(Cookie cookie : cookies) {
					if(cookie.getName().equals("ACSID") || cookie.getName().equals("SACSID"))
						return true;
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				http_client.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, true);
			}
			return false;
		}
		
		protected void onPostExecute(Boolean result) {
			Intent res= new Intent();
			res.putExtra("isAuthenticated", result);
			setResult(1, res);
			finish();
		}
	}

}