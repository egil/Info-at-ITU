package dk.itu.info.location.app;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class InfoAtItulocationActivity extends Activity {
	private LocationManager locationManager;
	// 55.70982, 12.57196
	// 55.65970, 12.59103
	private final double longitude = 55.70982;
	private final double latitude = 12.57196;
	private static final int radius = 100;
	private static final String proxi_alert_intent = "dk.itu.info.location";
	private boolean isInarea = false;
	private SharedPreferences mPreferences;
	private boolean isBroadcastset = false;
	private boolean isBroadcastSee = false;
	private boolean isGPS;
	private boolean isPaused = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mPreferences = getSharedPreferences("broadcastset", MODE_PRIVATE);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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

	@Override
	protected void onNewIntent(Intent intent) {
		Log.d("InfoAtItulocationActivity", "onNewIntent is called!");
		isInarea = intent.getBooleanExtra("entering", false);
		Log.d("InfoAtItulocationActivity", "onNewIntent is called!" + isInarea);
		isBroadcastSee = true;
		super.onNewIntent(intent);
	}

	@Override
	protected void onResume() {
		Log.i("InfoAtItulocationActivity", "onresum køre");
		isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!isGPS) {
			startActivityForResult(new Intent(
					android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
					0);

			isGPS = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
		}
		Log.i("InfoAtItulocationActivity", "Gps" + isGPS);
		if (mPreferences.contains("isSet")) {
			if (!isBroadcastSee) {
				if (this.getIntent().getBooleanExtra("entering", false)) {
					isInarea = true;
					Log.d("InfoAtItulocationActivity", "entering er true");
				} else {
					isInarea = false;
					Log.d("InfoAtItulocationActivity",
							"The intent that started YourActivity did not have an extra string value");
				}
			}else{
				isBroadcastSee = false;
			}
		} else {
			this.addProximityAlert();
			isBroadcastset = true;
			SharedPreferences.Editor editor=mPreferences.edit();
			editor.putBoolean("isSet", true);
			editor.commit();
		}
		super.onResume();
	}

	@Override
	protected void onStop() {
		SharedPreferences.Editor editor=mPreferences.edit();
		editor.remove("isSet");
		editor.commit();
		super.onStop();
	}

	

}