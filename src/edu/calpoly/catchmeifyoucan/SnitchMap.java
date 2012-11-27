package edu.calpoly.catchmeifyoucan;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import edu.calpoly.catchmeifyoucan.MapsItemizedOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.graphics.Typeface;

public class SnitchMap extends MapActivity implements OnClickListener {
	
	ArrayList<GeoPoint> geoPoints = new ArrayList<GeoPoint>(); // used to dynamically store geopoints
	
	MapView mapView;                              // declaring these variables here (but not initializing them!)
	MyLocationOverlay myLocationOverlay;          // allows them to be referenced in multiple methods
	List<Overlay> mapOverlays;
	MapsItemizedOverlay itemizedoverlay;
	Drawable drawable;
	TextView snitchTimer;
	SmsManager sm = SmsManager.getDefault();
	ArrayList<String> seekerNumbers;
	Timer timer;
	int timerInterval;
	int secondCounter;
	RelativeLayout buttonSnitchTagged;
	
	// Typeface
	Typeface thin, light;
	TextView textTagged;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_snitch_map);
		extractMapView();
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		mapOverlays = mapView.getOverlays();
        mapOverlays.add(myLocationOverlay);
        snitchTimer = (TextView)findViewById(R.id.snitch_timer);
        seekerNumbers = this.getIntent().getExtras().getStringArrayList(CmiycJavaRes.seekerNumbersKey);
        secondCounter = 0;
        timerInterval = this.getIntent().getExtras().getInt(CmiycJavaRes.timerIntervalKey);
        timer = new Timer();
        timer.schedule(new SnitchTimerTask(), 0, 1000);
        CmiycJavaRes.activityState = CmiycJavaRes.SNITCHMAP;
        buttonSnitchTagged = (RelativeLayout)findViewById(R.id.button_snitch_tagged);
        buttonSnitchTagged.setOnClickListener(this);
        
        // Typeface
        thin = Typeface.createFromAsset(getAssets(), "roboto_thin.ttf");
        snitchTimer.setTypeface(thin);
        light = Typeface.createFromAsset(getAssets(), "roboto_light.ttf");
        textTagged = (TextView)findViewById(R.id.text_snitch_tagged);
        textTagged.setTypeface(light);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_snitch_map, menu);
		return true;
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		// when our activity pauses, we want to remove listening for location updates
    	//myLocationOverlay.disableMyLocation();
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		//myLocationOverlay.disableMyLocation();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		// when our activity resumes, we want to register for location updates
    	myLocationOverlay.enableMyLocation();
    	CmiycJavaRes.activityState = CmiycJavaRes.SNITCHMAP;
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void onClick(View buttonClicked) {
		if(buttonClicked == buttonSnitchTagged){
			/*AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("End Game?");
			alertDialog.setIcon(R.drawable.ic_launcher);
			alertDialog.setMessage("Do you want to end the game?");
			alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
	              public void onClick(DialogInterface dialog, int which) {
	            	  Intent i = new Intent(idk, GameOverPage.class);
	            	  startActivity(i);
	                  finish();
	                return;
	            } }); 
	            alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
	              public void onClick(DialogInterface dialog, int which) {
	                  dialog.cancel();
	                return;
	            }}); 
	              alertDialog.show();*/
			for(int j =0;j<seekerNumbers.size();j++){
				String textContent = "@!#gameOver";
				sm.sendTextMessage(seekerNumbers.get(j), null, textContent, null, null);
			}
			myLocationOverlay.disableMyLocation();
			timer.cancel();
			Intent i = new Intent(this, GameOverPage.class);
			startActivity(i);
			finish();
		}
	}
	
	public void extractMapView(){                           // extracts mapview from layout in order to add overlays
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);               // enables zoom
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	     if (keyCode == KeyEvent.KEYCODE_BACK) {

	         AlertDialog alertDialog = new AlertDialog.Builder(this).create();
	            alertDialog.setTitle("Exit Game?");
	            alertDialog.setIcon(R.drawable.ic_launcher);

	            alertDialog.setMessage("Do you really want to go back? This will remove you from the game!");
	            alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
	              public void onClick(DialogInterface dialog, int which) {
	            	  myLocationOverlay.disableMyLocation();
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
	     return super.onKeyDown(keyCode, event);
	 }
	
	class SnitchTimerTask extends TimerTask {

        @Override
        public void run() {
            SnitchMap.this.runOnUiThread(new Runnable() {

                public void run() {
                	if(secondCounter>=timerInterval){
                		//put in texting
                		if(seekerNumbers!=null){
                			for(int j =0;j<seekerNumbers.size();j++){
                				String textContent = "@!#gp:" + myLocationOverlay.getMyLocation().toString();
                				sm.sendTextMessage(seekerNumbers.get(j), null, textContent, null, null);
                			}
                		}
                		
                		secondCounter = 0;
                	}
                	int countdownSeconds = timerInterval - secondCounter;
            		int displayMinutes = countdownSeconds / 60;
            		int displaySeconds = countdownSeconds % 60;
            		snitchTimer.setText(String.format("%d:%02d", displayMinutes, displaySeconds));
            		secondCounter++;
                }
            });
        }
   }

}
