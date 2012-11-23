package edu.calpoly.catchmeifyoucan;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.graphics.Typeface;

public class SnitchMainPage extends Activity implements OnClickListener{
	
	RelativeLayout start;
	RelativeLayout settings;
	
	// Typeface
	Typeface light;
	
	static TextView title, startText, seekerName1, seekerName2, seekerName3, seekerName4, seekerName5;
	
	static Boolean seekerVisible1, seekerVisible2, seekerVisible3, seekerVisible4, seekerVisible5;

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
        
        //seeker name textviews
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
        
        //seeker name relative layout (to be made visible/invisible appropriately
        seekerVisible1 = false;
        seekerVisible2 = false;
        seekerVisible3 = false;
        seekerVisible4 = false;
        seekerVisible5 = false;
        
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
