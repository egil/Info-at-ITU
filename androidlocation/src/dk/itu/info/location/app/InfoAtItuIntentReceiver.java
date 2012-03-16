package dk.itu.info.location.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

public class InfoAtItuIntentReceiver extends BroadcastReceiver {

	public InfoAtItuIntentReceiver() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("InfoAtItulocationActivity", "Starter broadcast");
		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		boolean entering = intent.getBooleanExtra(key, false);
		Log.i("InfoAtItulocationActivity", "entering har følgende værdi " + entering);
      Intent i = new Intent(context, InfoAtItulocationActivity.class);
      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
      i.putExtra("entering", entering);
//	i.getBooleanExtra("entering", false);
      context.startActivity(i);

	}

}
