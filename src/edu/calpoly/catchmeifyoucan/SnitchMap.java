package edu.calpoly.catchmeifyoucan;

import java.util.ArrayList;
import java.util.List;

import edu.calpoly.catchmeifyoucan.MapsItemizedOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class SnitchMap extends MapActivity implements OnClickListener {
	
	ArrayList<GeoPoint> geoPoints = new ArrayList<GeoPoint>(); // used to dynamically store geopoints
	
	MapView mapView;                              // declaring these variables here (but not initializing them!)
	MyLocationOverlay myLocationOverlay;          // allows them to be referenced in multiple methods
	List<Overlay> mapOverlays;
	MapsItemizedOverlay itemizedoverlay;
	Drawable drawable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_snitch_map);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_snitch_map, menu);
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
