package edu.calpoly.catchmeifyoucan;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
//import android.widget.TextView;
//import android.widget.ProgressBar;

public class SeekerWaitingPage extends Activity {
	
//	private TextView waitingForSnitch;
//	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seeker_waiting);
		
//		waitingForSnitch = (TextView)findViewById(R.id.waiting_for_snitch);
//		progressBar = (ProgressBar)findViewById(R.id.seeker_progress_bar);
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
