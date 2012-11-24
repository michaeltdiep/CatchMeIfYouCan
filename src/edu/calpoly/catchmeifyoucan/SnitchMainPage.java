package edu.calpoly.catchmeifyoucan;

import android.os.Bundle;
import android.app.Activity;
import android.telephony.SmsManager;
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
	int timerInterval;
	
	// Typeface
	Typeface light;
	
	SmsManager sm = SmsManager.getDefault();
	
	static TextView title, startText, seekerName1, seekerName2, seekerName3, seekerName4, seekerName5;
	
	static Boolean seekerEntered1, seekerEntered2, seekerEntered3, seekerEntered4, seekerEntered5;
	
	static RelativeLayout seeker1, seeker2, seeker3, seeker4, seeker5, deleteSeeker1, deleteSeeker2, deleteSeeker3, deleteSeeker4, deleteSeeker5;

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
        seeker1 = (RelativeLayout)findViewById(R.id.seeker_1);
        seeker2 = (RelativeLayout)findViewById(R.id.seeker_2);
        seeker3 = (RelativeLayout)findViewById(R.id.seeker_3);
        seeker4 = (RelativeLayout)findViewById(R.id.seeker_4);
        seeker5 = (RelativeLayout)findViewById(R.id.seeker_5);
        deleteSeeker1 = (RelativeLayout)findViewById(R.id.delete_1);
        deleteSeeker2 = (RelativeLayout)findViewById(R.id.delete_2);
        deleteSeeker3 = (RelativeLayout)findViewById(R.id.delete_3);
        deleteSeeker4 = (RelativeLayout)findViewById(R.id.delete_4);
        deleteSeeker5 = (RelativeLayout)findViewById(R.id.delete_5);
        seekerEntered1 = false;
        seekerEntered2 = false;
        seekerEntered3 = false;
        seekerEntered4 = false;
        seekerEntered5 = false;
        
        seeker1.setVisibility(View.INVISIBLE);
        seeker2.setVisibility(View.INVISIBLE);
        seeker3.setVisibility(View.INVISIBLE);
        seeker4.setVisibility(View.INVISIBLE);
        seeker5.setVisibility(View.INVISIBLE);
        
        deleteSeeker1.setOnClickListener(this);
        deleteSeeker2.setOnClickListener(this);
        deleteSeeker3.setOnClickListener(this);
        deleteSeeker4.setOnClickListener(this);
        deleteSeeker5.setOnClickListener(this);
        
        timerInterval = 30;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_snitch_main_page, menu);
        return true;
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	CmiycJavaRes.activityState = CmiycJavaRes.SNITCHMAIN;
    	
    }
    
    public void onClick(View v){
    	Intent i;
    	if(v.equals(findViewById(R.id.snitch_start_button))) {
            //TODO start button doesn't actually lead to SnitchMap.class
    		if(seekerEntered1){
    			sm.sendTextMessage((String)seekerName1.getText(), null, "@!#seekerConfirm", null, null);
    		}
    		if(seekerEntered2){
    			sm.sendTextMessage((String)seekerName2.getText(), null, "@!#seekerConfirm", null, null);
    		}
    		if(seekerEntered3){
    			sm.sendTextMessage((String)seekerName3.getText(), null, "@!#seekerConfirm", null, null);
    		}
    		if(seekerEntered4){
    			sm.sendTextMessage((String)seekerName4.getText(), null, "@!#seekerConfirm", null, null);
    		}
    		if(seekerEntered5){
    			sm.sendTextMessage((String)seekerName5.getText(), null, "@!#seekerConfirm", null, null);
    		}
    		CmiycJavaRes.activityState = CmiycJavaRes.SNITCHMAP;
    		i = new Intent(this, SnitchMap.class);
    		this.startActivity(i);
    	} else if(v.equals(deleteSeeker1)){
			SnitchMainPage.seekerName1.setText("Waiting...");
			SnitchMainPage.seekerEntered1 = false;
			SnitchMainPage.seeker1.setVisibility(View.INVISIBLE);
    	} else if(v.equals(deleteSeeker2)){
    		SnitchMainPage.seekerName2.setText("Waiting...");
			SnitchMainPage.seekerEntered2 = false;
			SnitchMainPage.seeker2.setVisibility(View.INVISIBLE);
    	} else if(v.equals(deleteSeeker3)){
    		SnitchMainPage.seekerName3.setText("Waiting...");
			SnitchMainPage.seekerEntered3 = false;
			SnitchMainPage.seeker3.setVisibility(View.INVISIBLE);
    	} else if(v.equals(deleteSeeker4)){
    		SnitchMainPage.seekerName4.setText("Waiting...");
			SnitchMainPage.seekerEntered4 = false;
			SnitchMainPage.seeker4.setVisibility(View.INVISIBLE);
    	} else if(v.equals(deleteSeeker1)){
    		SnitchMainPage.seekerName5.setText("Waiting...");
			SnitchMainPage.seekerEntered5 = false;
			SnitchMainPage.seeker5.setVisibility(View.INVISIBLE);
    	}
    }
}
