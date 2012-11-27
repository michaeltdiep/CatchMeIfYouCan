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
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

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
	long starttime = 0;
	int timerInterval;
	int secondCounter;

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
        starttime = System.currentTimeMillis();
        timer.schedule(new SnitchTimerTask(), 0, 1000);
        CmiycJavaRes.activityState = CmiycJavaRes.SNITCHMAP;
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
    	myLocationOverlay.disableMyLocation();
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
		// TODO Auto-generated method stub
		return false;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
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

	            alertDialog.setMessage("Do you really want to go back? This will end the game!");
	            alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
	              public void onClick(DialogInterface dialog, int which) {
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
