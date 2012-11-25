package edu.calpoly.catchmeifyoucan;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.ProgressBar;
import android.widget.TextView;

public class SeekerWaitingPage extends Activity {
	
//	private TextView waitingForSnitch;
//	private ProgressBar progressBar;
	static TextView defaultTextView;
	static ProgressBar progressBar;
	static RelativeLayout seekerMapButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seeker_waiting);
		
		CmiycJavaRes.activityState = CmiycJavaRes.SEEKERWAITING;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_seeker_waiting, menu);
		return true;
	}
	
	@Override
    public void onResume(){
    	super.onResume();
    	CmiycJavaRes.activityState = CmiycJavaRes.SEEKERWAITING;
    	
    }

}
