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
import com.google.android.maps.OverlayItem;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

//Typeface
import android.graphics.Typeface;
import android.widget.TextView;

public class SeekerMap extends MapActivity implements OnClickListener {
	
	ArrayList<GeoPoint> geoPoints = new ArrayList<GeoPoint>(); // used to dynamically store geopoints
	
	MapView mapView;                              // declaring these variables here (but not initializing them!)
	MyLocationOverlay myLocationOverlay;          // allows them to be referenced in multiple methods
	List<Overlay> mapOverlays;
	MapsItemizedOverlay itemizedoverlay;
	Drawable drawable;
	int markerCounter;
	int timerInterval;
	int secondCounter;
	Timer timer;
	
	BroadcastReceiver localTextReceiver;
	IntentFilter filter;
	
	// Typeface
	Typeface thin;
	TextView seekerTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seeker_map);
		mapView = (MapView) findViewById(R.id.mapview_seeker);
		mapView.setBuiltInZoomControls(true); 
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		drawable = this.getResources().getDrawable(R.drawable.map_marker);
        itemizedoverlay = new MapsItemizedOverlay(drawable, this);
		mapOverlays = mapView.getOverlays();
        mapOverlays.add(myLocationOverlay);
        markerCounter = 0;
        seekerTimer = (TextView)findViewById(R.id.seeker_timer);
        mapView.postInvalidate();
        timer = new Timer();
        timer.schedule(new SeekerTimerTask(), 0, 1000);
        
     // Typeface
        thin = Typeface.createFromAsset(getAssets(), "roboto_thin.ttf");
        seekerTimer.setTypeface(thin);
        
        CmiycJavaRes.activityState = CmiycJavaRes.SEEKERMAP;
        
        timerInterval = this.getIntent().getExtras().getInt(CmiycJavaRes.timerIntervalKey);
        secondCounter = 0;
        localTextReceiver = new BroadcastReceiver(){

			@Override
			public void onReceive(Context context, Intent intent) {
				Bundle bundle = intent.getExtras();

				if (bundle != null) {
				        Object[] pdusObj = (Object[]) bundle.get("pdus");
				        SmsMessage[] messages = new SmsMessage[pdusObj.length];
				        
				        // getting SMS information from Pdu.
				        for (int i = 0; i < pdusObj.length; i++) {
				                messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
				        }

				        for (SmsMessage currentMessage : messages) {
				        	if(currentMessage.getDisplayMessageBody().contains("@!#gp:")){
				        		String geoStringTemp = currentMessage.getDisplayMessageBody().replace("@!#gp:", "");
				        		GeoPoint geoPointTemp = CmiycJavaRes.stringToGeoPoint(geoStringTemp); 	//add textview to display								
				        		addMarker(geoPointTemp); 												
				        		this.abortBroadcast();
				        	}  else if(currentMessage.getDisplayMessageBody().contains("@!#gameOver")){
			        			Intent i = new Intent(context, MainPage.class);
			        			startActivity(i);
			        			finish();
			        		}
				               //currentMessage.getDisplayOriginatingAddress();		// has sender's phone number
				               //currentMessage.getDisplayMessageBody();				// has the actual message
				        }
				}
				
			}
        	
        };
        filter = new IntentFilter();
        filter.addAction(CmiycJavaRes.ACTION);
        this.registerReceiver(this.localTextReceiver, filter);

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
    	//this.unregisterReceiver(this.localTextReceiver);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		// when our activity resumes, we want to register for location updates
    	myLocationOverlay.enableMyLocation();
        CmiycJavaRes.activityState = CmiycJavaRes.SEEKERMAP;
        //this.registerReceiver(this.localTextReceiver, filter);

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	     if (keyCode == KeyEvent.KEYCODE_BACK) {

	         AlertDialog alertDialog = new AlertDialog.Builder(this).create();
	            alertDialog.setTitle("Exit Game?");
	            alertDialog.setIcon(R.drawable.ic_launcher);

	            alertDialog.setMessage("Do you really want to go back? This will remove you from the game!");
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
	
	public void addMarker(GeoPoint geoPointTemp){
		markerCounter++;
		//mapView.setBuiltInZoomControls(true); 
		itemizedoverlay.addOverlay(new OverlayItem(geoPointTemp, "Point " + markerCounter, null));
		mapOverlays.add(itemizedoverlay);
		mapView.postInvalidate();
	}
	
	class SeekerTimerTask extends TimerTask {

        @Override
        public void run() {
            SeekerMap.this.runOnUiThread(new Runnable() {

                public void run() {
                	if(secondCounter>=timerInterval){
                		secondCounter = 0;
                	}
                	int countdownSeconds = timerInterval - secondCounter;
            		int displayMinutes = countdownSeconds / 60;
            		int displaySeconds = countdownSeconds % 60;
            		seekerTimer.setText(String.format("%d:%02d", displayMinutes, displaySeconds));
            		secondCounter++;
                }
            });
        }
   }
}

