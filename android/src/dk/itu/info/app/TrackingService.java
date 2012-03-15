package dk.itu.info.app;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

public class TrackingService extends IntentService {

	public TrackingService() {
		super("TrackingService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "Tracking Service starting", Toast.LENGTH_SHORT).show();
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public int onStopCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "Tracking Service starting", Toast.LENGTH_SHORT).show();
		return super.onStartCommand(intent, flags, startId);
	}
}
