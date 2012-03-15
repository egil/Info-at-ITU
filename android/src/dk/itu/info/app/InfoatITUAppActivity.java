package dk.itu.info.app;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoatITUAppActivity extends Activity {
	private Boolean signedIn = false;
	private String btMACAddr = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
//		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();		
//		if (bluetoothAdapter == null) {	
//			// TODO: Add pretty warning and exit, bluetooth not supported by phone :( 			
//		}
//		btMACAddr = bluetoothAdapter.getAddress();
	}

	@Override
	protected void onResume() {
		super.onResume();

		// welcome message
		// signin request

		// turn on gps
		// start tracking service

		/**
		 * User notification Use
		 * http://developer.android.com/guide/topics/ui/notifiers
		 * /notifications.html 1z
		 */
	}

	public void onSignInOut(View view) {
		if (this.signedIn) {
			Intent intent = new Intent(this, TrackingService.class);
			startService(intent);
		} else {
			// sing in
		}
		this.signedIn = !this.signedIn;
		Button signInOutButton = (Button) findViewById(R.id.signInOutButton);
		TextView messageText = (TextView) findViewById(R.id.messageText);
		signInOutButton.setText((signedIn ? R.string.sign_out : R.string.sign_in));
		messageText.setText((signedIn ? R.string.tracking_you_msg : R.string.welcome_msg));
	}

}