package dk.itu.info.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InfoatITUAppActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // welcome message
        // signin request
        
        // turn on gps
        // start tracking service
        
        /** 
         * User notification 
         * Use http://developer.android.com/guide/topics/ui/notifiers/notifications.html
         * 1z
         */
        
    }
    
    public void goToAccountListActivity(View view) {
    	startActivity(new Intent(this, AccountList.class));
    }
}