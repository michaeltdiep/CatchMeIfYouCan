package edu.calpoly.catchmeifyoucan;

import java.util.ArrayList;
import java.util.List;

import edu.calpoly.catchmeifyoucan.MapsItemizedOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.SmsMessage;
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
	
	BroadcastReceiver localTextReceiver;
	IntentFilter filter;
	
	// Typeface
	Typeface thin;
	TextView timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seeker_map);
		extractMapView();
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		drawable = this.getResources().getDrawable(R.drawable.map_marker);
        itemizedoverlay = new MapsItemizedOverlay(drawable, this);
		mapOverlays = mapView.getOverlays();
        mapOverlays.add(myLocationOverlay);
        markerCounter = 0;
        mapView.postInvalidate();
        
     // Typeface
        thin = Typeface.createFromAsset(getAssets(), "roboto_thin.ttf");
        timer = (TextView)findViewById(R.id.seeker_timer);
        timer.setTypeface(thin);
        
        CmiycJavaRes.activityState = CmiycJavaRes.SEEKERMAP;
        
        localTextReceiver = new BroadcastReceiver(){

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				Bundle bundle = intent.getExtras();

				if (bundle != null) {
				        Object[] pdusObj = (Object[]) bundle.get("pdus");
				        SmsMessage[] messages = new SmsMessage[pdusObj.length];
				        
				        // getting SMS information from Pdu.
				        for (int i = 0; i < pdusObj.length; i++) {
				                messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
				        }

				        for (SmsMessage currentMessage : messages) {
				        	if(CmiycJavaRes.activityState == CmiycJavaRes.SEEKERMAP){
				        		if(currentMessage.getDisplayMessageBody().contains("@!#gp:")){
				        			String geoStringTemp = currentMessage.getDisplayMessageBody().replace("@!#gp:", "");
				        			GeoPoint geoPointTemp = CmiycJavaRes.stringToGeoPoint(geoStringTemp); 	//add textview to display
				        			extractMapView(); 														//this string to test where
				        			addMarker(geoPointTemp); 												//where the error is occuring
				        			this.abortBroadcast();
				        		}
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
    	this.unregisterReceiver(this.localTextReceiver);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		// when our activity resumes, we want to register for location updates
    	myLocationOverlay.enableMyLocation();
        CmiycJavaRes.activityState = CmiycJavaRes.SEEKERMAP;
        this.registerReceiver(this.localTextReceiver, filter);

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
	
	
	
	public void addMarker(GeoPoint geoPointTemp){
		markerCounter++;
		extractMapView();
		itemizedoverlay.addOverlay(new OverlayItem(geoPointTemp, "Point " + markerCounter, null));
		mapView.postInvalidate();
	}
}

