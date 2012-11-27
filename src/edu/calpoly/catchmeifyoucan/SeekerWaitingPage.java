package edu.calpoly.catchmeifyoucan;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.ProgressBar;
import android.widget.TextView;

public class SeekerWaitingPage extends Activity {
	
//	private TextView waitingForSnitch;
//	private ProgressBar progressBar;
	static TextView defaultTextView;
	static ProgressBar progressBar;
	static RelativeLayout seekerMapButton;
	
	BroadcastReceiver localTextReceiver;
	IntentFilter filter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seeker_waiting);
		
		CmiycJavaRes.activityState = CmiycJavaRes.SEEKERWAITING;
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
				        //	if(CmiycJavaRes.activityState == CmiycJavaRes.SEEKERWAITING){
				        		if(currentMessage.getDisplayMessageBody().contains("@!#seekerConfirm")){
				        			Intent i = new Intent(context, SeekerMap.class);
				        			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				        			if(currentMessage.getDisplayMessageBody().contains("int:")){
				        				String timerIntervalString = currentMessage.getDisplayMessageBody().replace("@!#seekerConfirm;int:", "");
				        				int timerIntervalInt = Integer.parseInt(timerIntervalString);
				        				i.putExtra(CmiycJavaRes.timerIntervalKey, timerIntervalInt);
				        			}
				        			
				        			this.abortBroadcast();
				        			context.startActivity(i);
				        			finish();
				        		}
				        //	}
				        	
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
		getMenuInflater().inflate(R.menu.activity_seeker_waiting, menu);
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
    	this.registerReceiver(this.localTextReceiver, filter);
    	CmiycJavaRes.activityState = CmiycJavaRes.SEEKERWAITING;
    	
    }

}
