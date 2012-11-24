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

public class SeekerWaitingPage extends Activity implements OnClickListener {
	
//	private TextView waitingForSnitch;
//	private ProgressBar progressBar;
	static TextView defaultTextView;
	static ProgressBar progressBar;
	static RelativeLayout seekerMapButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seeker_waiting);
		
		defaultTextView = (TextView)findViewById(R.id.waiting_for_snitch);
		progressBar = (ProgressBar)findViewById(R.id.seeker_progress_bar);
		seekerMapButton = (RelativeLayout)findViewById(R.id.button_launch_seeker_map);
		
		seekerMapButton.setOnClickListener(this);
		
		defaultTextView.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.VISIBLE);
		seekerMapButton.setVisibility(View.INVISIBLE);
		
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

	public void onClick(View buttonClicked) {
			Intent i = new Intent(this, SeekerMap.class);
			startActivity(i);	
	}

}
