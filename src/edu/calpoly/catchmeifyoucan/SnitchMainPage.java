package edu.calpoly.catchmeifyoucan;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
import android.content.Intent;
//import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.graphics.Typeface;

public class SnitchMainPage extends Activity implements OnClickListener{
	
	RelativeLayout start;
	RelativeLayout settings;
	
	// Typeface
	TextView title;
	TextView startText;
	TextView seekerName1;
	TextView seekerName2;
	TextView seekerName3;
	TextView seekerName4;
	TextView seekerName5;
	Typeface light;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snitch_main_page);
        
        //Set up start and settings button
        //TODO start button doesn't actually lead to SnitchMap.class
        start = (RelativeLayout)findViewById(R.id.snitch_start_button);
        start.setOnClickListener(this);
        //TODO Settings button will just change layout?
        //      ^ To keep texting activity running
        settings = (RelativeLayout)findViewById(R.id.snitch_settings_button);
        settings.setOnClickListener(this);
        
        // Typeface
        light = Typeface.createFromAsset(getAssets(), "roboto_light.ttf");
        title = (TextView)findViewById(R.id.text_snitch_title);
        title.setTypeface(light);
        startText = (TextView)findViewById(R.id.text_snitch_start);
        startText.setTypeface(light);
        seekerName1 = (TextView)findViewById(R.id.seeker_name_1);
        seekerName1.setTypeface(light);
        seekerName2 = (TextView)findViewById(R.id.seeker_name_2);
        seekerName2.setTypeface(light);
        seekerName3 = (TextView)findViewById(R.id.seeker_name_3);
        seekerName3.setTypeface(light);
        seekerName4 = (TextView)findViewById(R.id.seeker_name_4);
        seekerName4.setTypeface(light);
        seekerName5 = (TextView)findViewById(R.id.seeker_name_5);
        seekerName5.setTypeface(light);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_snitch_main_page, menu);
        return true;
    }
    
    public void onClick(View v){
    	Intent i;
    	if(v.equals(findViewById(R.id.snitch_start_button))) {
            //TODO start button doesn't actually lead to SnitchMap.class
    		i = new Intent(this, SnitchMap.class);
    		this.startActivity(i);
    	}
    }
}
