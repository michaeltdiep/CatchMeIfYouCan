package edu.calpoly.catchmeifyoucan;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class SettingsPage extends Activity implements OnClickListener {
	
	RelativeLayout returnButton;
	Intent returnIntent;
	Boolean settingsChanged;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_page);
		returnButton = (RelativeLayout)findViewById(R.id.button_settings_return);
		returnButton.setOnClickListener(this);
		returnIntent = new Intent();
		settingsChanged = false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_settings_page, menu);
		return true;
	}

	public void onClick(View btnClicked) {
		if(btnClicked == returnButton){
			setResult(RESULT_OK, returnIntent);     
			finish();
		}
		
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	     if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	 if(settingsChanged){
	    		 AlertDialog alertDialog = new AlertDialog.Builder(this).create();
	    		 	alertDialog.setTitle("Abandon Changes?");
	    		 	alertDialog.setIcon(R.drawable.ic_launcher);

	    		 	alertDialog.setMessage("Settings were changed. Would you like to go back without saving?");
	    		 	alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
	    		 		public void onClick(DialogInterface dialog, int which) {
	    		 			setResult(RESULT_CANCELED, returnIntent);
	    		 			finish();
	    		 			return;
	    		 		} }); 
	    		 	alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
	    		 		public void onClick(DialogInterface dialog, int which) {
	    		 			dialog.cancel();
	    		 			return;
	    		 		}}); 
	    		 	alertDialog.show();

	    		 	return true;
	    	 }
	     }
	     return super.onKeyDown(keyCode, event);
	 }

}
