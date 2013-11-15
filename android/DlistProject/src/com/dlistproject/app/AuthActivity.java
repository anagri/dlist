package com.dlistproject.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.dlistproject.app.csv.CsvHelper;
import com.dlistproject.app.data.TodoTask;
import com.dlistproject.app.data.TodoTasksArray;
import com.dropbox.sync.android.*;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxFile.Listener;

public class AuthActivity extends Activity {
	private DbxAccountManager mDbxAcctMgr;
	TodoTasksArray todoTaskArray;
	DbxFileSystem dbxFs;
	private static final String APP_KEY = "4ul0p4hmwzmk9ah";
	private static final String APP_SECRET = "po67tpsyy9mv3lh";
	static final int REQUEST_LINK_TO_DBX = 0;
	ListView listCardsViewObj;
	private CsvHelper csvHelper = new CsvHelper();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);
//		startActivity(new Intent(AuthActivity.this,LandingActivity.class));
		listCardsViewObj = (ListView) findViewById(R.id.listCardsView);
		mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(), APP_KEY, APP_SECRET);
		if(!mDbxAcctMgr.hasLinkedAccount()) {
			mDbxAcctMgr.startLink((Activity)AuthActivity.this, REQUEST_LINK_TO_DBX);
		}else {
        	try {
				dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
			} catch (Unauthorized e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			setup_file();
		}
		Log.i("TAG","oncreate");
        
		
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_LINK_TO_DBX) {
	        if (resultCode == Activity.RESULT_OK) {
	        	Log.i("TAG","worked");
	        } else {
	        	Log.i("TAG","failed");
	            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
	        }
	    } else {
	        super.onActivityResult(requestCode, resultCode, data);
	    }
	}
	public boolean setup_file() {
        try {
        	if(dbxFs.exists(new DbxPath("todo_items.csv"))) {
        		DbxFile dbxFile = dbxFs.open(new DbxPath("todo_items.csv"));
        		dbxFile.addListener(new Listener() {
					@Override
					public void onFileChange(DbxFile arg0) {
						Log.i("TAG","onfilechange");
						
					}
				});
        		Toast.makeText(getApplicationContext(), "file exists",Toast.LENGTH_SHORT).show();
        	    todoTaskArray = csvHelper.getTodoList(dbxFile);
        	    CustomAdapter adapter = new CustomAdapter(getApplicationContext(), todoTaskArray);
        	    Log.i("TAG","setting adapter");
        	    listCardsViewObj.setAdapter(adapter);
        	    dbxFile.close();
        	}else {
        		DbxFile testFile = dbxFs.create(new DbxPath("todo_items.csv"));
        		Toast.makeText(getApplicationContext(), "file created", Toast.LENGTH_SHORT).show();
        	}
        }catch(Exception e) { 
        	Log.i("TAG","exception in DBXfile sysytem "+e.toString());
        	return false;
        }
        return true;

	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			dbxFs.syncNowAndWait();
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbxFs.shutDown();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_add) {
			showCustomDialog();
		}
		return super.onOptionsItemSelected(item);
	}
	protected void showCustomDialog() {
        final Dialog dialog = new Dialog(AuthActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_new_task);
        
        final EditText editText = (EditText)dialog.findViewById(R.id.newTastContentView);
        Button button = (Button)dialog.findViewById(R.id.setTaskButtonView);    
        button.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
            	String data = editText.getText().toString().trim();
            	if(!data.equals("")){
            		TodoTask todoTask =  new TodoTask();
	            	todoTask.setTitle(data);
	            	todoTaskArray.insert(todoTask) ;
					try {
		            	if(dbxFs.exists(new DbxPath("todo_items.csv"))) {
		            		DbxFile dbxFile = dbxFs.open(new DbxPath("todo_items.csv"));
		            		csvHelper.writeCsv(dbxFile, todoTaskArray);
		            		dbxFile.close();
		            		Log.i("TAG","task added to the file");
		            	}
					} catch (Exception e) {
						e.printStackTrace();
					}
            	}else {
            		Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
            	}
                dialog.dismiss();
                
            }
        });
                
        dialog.show();
    }	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.auth, menu);
		return true;
	}

}
