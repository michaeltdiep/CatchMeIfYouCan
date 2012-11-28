package edu.calpoly.catchmeifyoucan;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

// Typeface
import android.graphics.Typeface;
import android.widget.TextView;

public class Confused extends Activity {

	Typeface thin, light;
	TextView title, objectiveTitle, startTitle, gameTitle, endTitle;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confused);
        
        // Typeface
        thin = Typeface.createFromAsset(getAssets(), "roboto_thin.ttf");
        title = (TextView)findViewById(R.id.text_confused_title);
        title.setTypeface(thin);
        
        light = Typeface.createFromAsset(getAssets(), "roboto_light.ttf");
        objectiveTitle = (TextView)findViewById(R.id.text_objective_title);
        objectiveTitle.setTypeface(light);
        startTitle = (TextView)findViewById(R.id.text_start_title);
        startTitle.setTypeface(light);
        gameTitle = (TextView)findViewById(R.id.text_game_title);
        gameTitle.setTypeface(light);
        endTitle = (TextView)findViewById(R.id.text_end_title);
        endTitle.setTypeface(light);
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_confused, menu);
        return true;
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	CmiycJavaRes.activityState = CmiycJavaRes.CONFUSED;
    	
    }
}
//test push without bin