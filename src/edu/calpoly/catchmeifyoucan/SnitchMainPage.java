package edu.calpoly.catchmeifyoucan;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
	
	TextView title, startText, seekerName1, seekerName2, seekerName3, seekerName4, seekerName5;
	
	Boolean seekerEntered1, seekerEntered2, seekerEntered3, seekerEntered4, seekerEntered5;
	
	RelativeLayout seeker1, seeker2, seeker3, seeker4, seeker5;
	RelativeLayout deleteSeeker1, deleteSeeker2, deleteSeeker3, deleteSeeker4, deleteSeeker5;
	RelativeLayout snitchSettingsButton;
	
	ArrayList<String> seekerNumbers;
	
	BroadcastReceiver localTextReceiver;
	IntentFilter filter;
	
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
        
        snitchSettingsButton = (RelativeLayout)findViewById(R.id.snitch_settings_button);
        snitchSettingsButton.setOnClickListener(this);
        
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
        
        seekerNumbers = new ArrayList<String>();
        CmiycJavaRes.activityState = CmiycJavaRes.SNITCHMAIN;
        
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
				 //       	if(CmiycJavaRes.activityState == CmiycJavaRes.SNITCHMAIN){
				        		if(currentMessage.getDisplayMessageBody().equals("@!#seekerJoin")){ //.equals remains untested
				        			if(!seekerEntered1){
				        				seeker1.setVisibility(View.VISIBLE);
				       					seekerName1.setText(currentMessage.getDisplayOriginatingAddress());
				       					seekerEntered1 = true;
				       					this.abortBroadcast();
				       				} else if(!seekerEntered2){
				       					seeker2.setVisibility(View.VISIBLE);
				       					seekerName2.setText(currentMessage.getDisplayOriginatingAddress());
				       					seekerEntered2 = true;
				       					this.abortBroadcast();
				       				} else if(!seekerEntered3){
				       					seeker3.setVisibility(View.VISIBLE);
			        					seekerName3.setText(currentMessage.getDisplayOriginatingAddress());
			        					seekerEntered3 = true;
			        					this.abortBroadcast();
			        				} else if(!seekerEntered4){
			        					seeker4.setVisibility(View.VISIBLE);
			        					seekerName4.setText(currentMessage.getDisplayOriginatingAddress());
				        				seekerEntered4 = true;
				        				this.abortBroadcast();
				        			} else if(!seekerEntered5){
				        				seeker5.setVisibility(View.VISIBLE);
				        				seekerName5.setText(currentMessage.getDisplayOriginatingAddress());
				       					seekerEntered5 = true;
				       					this.abortBroadcast();
				       				}
				       			} 
//				        	}
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
        getMenuInflater().inflate(R.menu.activity_snitch_main_page, menu);
        return true;
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	this.unregisterReceiver(this.localTextReceiver);
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	CmiycJavaRes.activityState = CmiycJavaRes.SNITCHMAIN;
    	this.registerReceiver(this.localTextReceiver, filter);
    }
    
    public void onClick(View v){
    	Intent i;
    	if(v.equals(findViewById(R.id.snitch_start_button))) {
    		i = new Intent(this, SnitchMap.class);
    		String textContent = "@!#seekerConfirm;int:" + timerInterval;
    		if(seekerEntered1){
    			sm.sendTextMessage((String)seekerName1.getText(), null, textContent, null, null);
    			seekerNumbers.add((String)seekerName1.getText());
    		}
    		if(seekerEntered2){
    			sm.sendTextMessage((String)seekerName2.getText(), null, textContent, null, null);
    			seekerNumbers.add((String)seekerName2.getText());
    		}
    		if(seekerEntered3){
    			sm.sendTextMessage((String)seekerName3.getText(), null, textContent, null, null);
    			seekerNumbers.add((String)seekerName3.getText());
    		}
    		if(seekerEntered4){
    			sm.sendTextMessage((String)seekerName4.getText(), null, textContent, null, null);
    			seekerNumbers.add((String)seekerName4.getText());
    		}
    		if(seekerEntered5){
    			sm.sendTextMessage((String)seekerName5.getText(), null, textContent, null, null);
    			seekerNumbers.add((String)seekerName5.getText());
    		}
    		CmiycJavaRes.activityState = CmiycJavaRes.SNITCHMAP;
    		i.putStringArrayListExtra(CmiycJavaRes.seekerNumbersKey, seekerNumbers);
    		i.putExtra(CmiycJavaRes.timerIntervalKey, timerInterval);
    		this.startActivity(i);
    	} else if(v.equals(deleteSeeker1)){
			seekerName1.setText("Waiting...");
			seekerEntered1 = false;
			seeker1.setVisibility(View.INVISIBLE);
    	} else if(v.equals(deleteSeeker2)){
    		seekerName2.setText("Waiting...");
			seekerEntered2 = false;
			seeker2.setVisibility(View.INVISIBLE);
    	} else if(v.equals(deleteSeeker3)){
    		seekerName3.setText("Waiting...");
			seekerEntered3 = false;
			seeker3.setVisibility(View.INVISIBLE);
    	} else if(v.equals(deleteSeeker4)){
    		seekerName4.setText("Waiting...");
			seekerEntered4 = false;
			seeker4.setVisibility(View.INVISIBLE);
    	} else if(v.equals(deleteSeeker1)){
    		seekerName5.setText("Waiting...");
    		seekerEntered5 = false;
			seeker5.setVisibility(View.INVISIBLE);
    	} else if(v.equals(snitchSettingsButton)){
    		i = new Intent(this, SettingsPage.class);
    		startActivityForResult(i, CmiycJavaRes.SETTINGS_PAGE_RESULT_CODE);
    	}
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	if (requestCode == CmiycJavaRes.SETTINGS_PAGE_RESULT_CODE) {

    	     if(resultCode == RESULT_OK){
    	    	 
    	     } else if(resultCode == RESULT_CANCELED) {
    	    	 //don't wanna do jack
    	     }
    	}
    }
}
