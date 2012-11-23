package edu.calpoly.catchmeifyoucan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

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
		        	
		        	if(currentMessage.getDisplayMessageBody().contains("@!#")){
		        		if(currentMessage.getDisplayMessageBody().contains("seekerJoin")){
		        			if(!SnitchMainPage.seekerVisible1){
		        				SnitchMainPage.seekerName1.setText(currentMessage.getDisplayOriginatingAddress());
		        				SnitchMainPage.seekerVisible1 = true;
		        			} else if(!SnitchMainPage.seekerVisible2){
		        				SnitchMainPage.seekerName2.setText(currentMessage.getDisplayOriginatingAddress());
		        				SnitchMainPage.seekerVisible2 = true;
		        			} else if(!SnitchMainPage.seekerVisible3){
		        				SnitchMainPage.seekerName3.setText(currentMessage.getDisplayOriginatingAddress());
		        				SnitchMainPage.seekerVisible3 = true;
		        			} else if(!SnitchMainPage.seekerVisible4){
		        				SnitchMainPage.seekerName4.setText(currentMessage.getDisplayOriginatingAddress());
		        				SnitchMainPage.seekerVisible4 = true;
		        			} else if(!SnitchMainPage.seekerVisible5){
		        				SnitchMainPage.seekerName5.setText(currentMessage.getDisplayOriginatingAddress());
		        				SnitchMainPage.seekerVisible5 = true;
		        			}
		        		}
		        	}
		        	
		        	//MainActivity.setTextViewContent1(currentMessage.getDisplayOriginatingAddress());
		        	//MainActivity.setTextViewContent2(currentMessage.getDisplayMessageBody());
		                //currentMessage.getDisplayOriginatingAddress();		// has sender's phone number
		                //currentMessage.getDisplayMessageBody();				// has the actual message
		        }
		}
		
	}

}
