package com.dlistproject.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class LandingActivity extends Activity {
	ListView listCardsViewObj;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing);
		listCardsViewObj = (ListView) findViewById(R.id.listCardsView);
		
		
	}
	public void getPojo() {
	}

}

