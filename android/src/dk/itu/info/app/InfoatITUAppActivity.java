package dk.itu.info.app;

import org.apache.http.impl.client.DefaultHttpClient;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoatITUAppActivity extends Activity {
	private static final int GET_USER_ID = 1;
	private static final int ENABLE_DISCOVERABLE_MODE = 2;
	private static final int AUTHENTICATE_AGAINST_PROXY = 3;
	private LocationManager locationManager;

// jonas hjem 55.70982, 12.57196
//	private final double longitude = 12.57196;
//	private final double latitude = 55.70982;

//	ITU
	private final double longitude = 12.59103;
	private final double latitude = 55.65959;
	
	private static final int radius = 1000;
	private static final String proxi_alert_intent = "dk.itu.info.location";
	private String enteringInfo = null;
	private boolean isInarea = false;
	private boolean isGPS;

	private enum AppState {
		SIGNED_OUT, SIGNED_IN, ENTERING_ITU, LEAVING_ITU, ENABLED_BT, OUTSIDE_ITU, INSIDE_ITU, AUTHENTICATED,
	};

	private AppState currentState = AppState.SIGNED_OUT;
	private String btmacaddr = null;
	private Intent proxyService;
	private Account account;

	protected static DefaultHttpClient http_client = new DefaultHttpClient();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	}

	public void onSignInOut(View view) {
		if (this.currentState != AppState.SIGNED_OUT) {
			this.currentState = AppState.SIGNED_OUT;
			gotoNext();
		} else {
			// Let user pick an account
			if (account == null) {
				Intent signInIntent = new Intent(this, PickAccountList.class);
				startActivityForResult(signInIntent, GET_USER_ID);
			} else {
				onSignedIn(this.account);
			}
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		enteringInfo = intent.getStringExtra("entering");
		Log.d("info@itu", "onNewIntent is called!" + enteringInfo);
		if (enteringInfo.equals("enter")&&!isInarea) {
			this.currentState = AppState.ENTERING_ITU;
			isInarea = true;
			this.gotoNext();
		} else if (enteringInfo.equals("leaving")&&isInarea) {
			this.currentState = AppState.LEAVING_ITU;
			isInarea = false;
			this.gotoNext();
		}
	}

	@Override
	protected void onResume() {
		Log.i("info@itu", "onresum køre");
		Log.i("info@itu", "Gps" + isGPS + " "+ enteringInfo);
		if (enteringInfo == null) {
			Log.i("info@itu", "Entering " + this.getIntent().getStringExtra("entering"));
			//
			if (this.getIntent().getStringExtra("entering") != null) {
				enteringInfo = this.getIntent().getStringExtra("entering");
				if (enteringInfo.equals("enter")&&!isInarea) {
					this.currentState = AppState.ENTERING_ITU;
					isInarea = true;
					this.gotoNext();
				} else if (enteringInfo.equals("leaving")&&isInarea) {
					this.currentState = AppState.LEAVING_ITU;
					isInarea = false;
					this.gotoNext();
				}
				Log.d("info@itu", "entering er true");
			}

		}
		enteringInfo = null;
		super.onResume();
	}
	
	private void startGps(){
		isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!isGPS) {
			startActivityForResult(new Intent(
					android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
					0);

			isGPS = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
		}

	}

	private void addProximityAlert() {
		Intent intent = new Intent(proxi_alert_intent);
		PendingIntent proximityIntent = PendingIntent.getBroadcast(this, 0,
				intent, 0);
		// 55,65970, 12,59103
		locationManager.addProximityAlert(this.latitude, this.longitude,
				radius, -1, proximityIntent);
		// IntentFilter filter = new IntentFilter(proxi_alert_intent);
		// registerReceiver(new ProximityIntentReceiver(), filter);
	}

	private void onSignedIn(Account account) {
		this.account = account;
		this.currentState = AppState.SIGNED_IN;
		gotoNext();
	}

	private void onBluetoothInDiscoverableMode(String address) {
		this.btmacaddr = address.replace(":", "");
		this.currentState = AppState.ENABLED_BT;
		gotoNext();
	}

	private void doEnableBluetoothDiscoverableMode() {
		BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();

		// if bluetooth adapter is not in discoverable mode, we request it.
		if (bta.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(
					BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivityForResult(discoverableIntent, ENABLE_DISCOVERABLE_MODE);
		} else {
			onBluetoothInDiscoverableMode(bta.getAddress());
		}
	}

	private void doDisableBluetoothDiscoverableMode() {
		BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
		bta.disable();
		currentState = AppState.OUTSIDE_ITU;
		gotoNext();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// sign in
		if (requestCode == GET_USER_ID) {
			onSignedIn((Account) data.getExtras().get("account"));
		} else if (requestCode == ENABLE_DISCOVERABLE_MODE) {
			BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
			onBluetoothInDiscoverableMode(bta.getAddress());
		} else if (requestCode == AUTHENTICATE_AGAINST_PROXY) {
			currentState = AppState.AUTHENTICATED;
			gotoNext();
		}
	}

	/**
	 * Updates the UI with text message and button text
	 */
	private void setTextInUI() {
		Button signInOutButton = (Button) findViewById(R.id.signInOutButton);
		TextView messageText = (TextView) findViewById(R.id.messageText);

		if (currentState == AppState.SIGNED_IN) {
			String trackingYouMsg = String.format(
					getString(R.string.tracking_you_msg), account.name);
			signInOutButton.setText(R.string.sign_out);
			messageText.setText(trackingYouMsg);
		} else {
			signInOutButton.setText(R.string.sign_in);
			messageText.setText(R.string.welcome_msg);
		}
	}

	/**
	 * 
	 */
	private void gotoNext() {
		switch (currentState) {
		case SIGNED_OUT:
			Log.i("info@itu", "State: Signed out");
			// stop everything
			stopService(proxyService);
			setTextInUI();
			break;
		case SIGNED_IN:
			Log.i("info@itu", "State: Signed in");
			setTextInUI();
			// start GPS tracker activity
			this.startGps();
			this.addProximityAlert();
			// TEMP: skipping to entering
//			currentState = AppState.ENTERING_ITU;
//			gotoNext();
			break;
		case ENTERING_ITU:
			Log.i("info@itu", "State: Entering ITU");
			// notify user we are entering

			// start bluetooth, put in discoverable mode
			doEnableBluetoothDiscoverableMode();
			break;
		case LEAVING_ITU:
			Log.i("info@itu", "State: Leaving ITU");
			// say bye bye to proxy
			// disable bt
			doDisableBluetoothDiscoverableMode();
			break;
		case INSIDE_ITU:
			Log.i("info@itu", "State: Inside ITU");
			break;
		case OUTSIDE_ITU:
			Log.i("info@itu", "State: Outside ITU");
			//
			break;
		case ENABLED_BT:
			Log.i("info@itu", "State: Enabled BT");

			// authenticate
//			Intent intent = new Intent(this, AppInfo.class);
//			intent.putExtra("account", account);
//			startActivityForResult(intent, AUTHENTICATE_AGAINST_PROXY);
			currentState = AppState.AUTHENTICATED;
			gotoNext();

			break;
		case AUTHENTICATED:
			Log.i("info@itu", "State: Authenticated");

			// start tracking service
			proxyService = new Intent(this, ProxyService.class);
			proxyService.putExtra("btmacaddr", btmacaddr);
			proxyService.putExtra("account", account);
			startService(proxyService);
			currentState = AppState.INSIDE_ITU;

			break;
		}
	}

}