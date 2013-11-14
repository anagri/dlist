package com.dlistproject.app;

import com.dropbox.sync.android.DbxAccountManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class AuthActivity extends Activity {
	private DbxAccountManager mDbxAcctMgr;
	private static final String APP_KEY = "vyc3fsqjpems5dl";
	private static final String APP_SECRET = "ozgej4db0tyyc0f";
	static final int REQUEST_LINK_TO_DBX = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);
//		startActivity(new Intent(AuthActivity.this,LandingActivity.class));
		mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(), APP_KEY, APP_SECRET);
		mDbxAcctMgr.startLink((Activity)this, REQUEST_LINK_TO_DBX);
    	Log.i("TAG","oncreate");
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_LINK_TO_DBX) {
	        if (resultCode == Activity.RESULT_OK) {
	        	Log.i("TAG","worked");
	            Toast.makeText(getApplicationContext(), "worked", Toast.LENGTH_SHORT).show();
	        } else {
	        	Log.i("TAG","failed");
	            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
	        }
	    } else {
	        super.onActivityResult(requestCode, resultCode, data);
	    }
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.auth, menu);
		return true;
	}

}
