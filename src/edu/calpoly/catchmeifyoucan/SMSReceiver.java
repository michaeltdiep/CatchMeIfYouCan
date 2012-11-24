package edu.calpoly.catchmeifyoucan;

import com.google.android.maps.GeoPoint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.View;

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
		        		if(!CmiycJavaRes.isSeeker){
		        			if(currentMessage.getDisplayMessageBody().contains("seekerJoin")){
		        				if(CmiycJavaRes.activityState == CmiycJavaRes.SNITCHMAIN){
		        					SnitchMainPage.seeker1.setVisibility(View.VISIBLE);
		        					SnitchMainPage.seekerName1.setText(currentMessage.getDisplayOriginatingAddress());
		        					SnitchMainPage.seekerEntered1 = true;
		        				} else if(!SnitchMainPage.seekerEntered2){
		        					SnitchMainPage.seeker2.setVisibility(View.VISIBLE);
		        					SnitchMainPage.seekerName2.setText(currentMessage.getDisplayOriginatingAddress());
		        					SnitchMainPage.seekerEntered2 = true;
		        				} else if(!SnitchMainPage.seekerEntered3){
		        					SnitchMainPage.seeker3.setVisibility(View.VISIBLE);
		        					SnitchMainPage.seekerName3.setText(currentMessage.getDisplayOriginatingAddress());
		        					SnitchMainPage.seekerEntered3 = true;
		        				} else if(!SnitchMainPage.seekerEntered4){
		        					SnitchMainPage.seeker4.setVisibility(View.VISIBLE);
		        					SnitchMainPage.seekerName4.setText(currentMessage.getDisplayOriginatingAddress());
		        					SnitchMainPage.seekerEntered4 = true;
		        				} else if(!SnitchMainPage.seekerEntered5){
		        					SnitchMainPage.seeker5.setVisibility(View.VISIBLE);
		        					SnitchMainPage.seekerName5.setText(currentMessage.getDisplayOriginatingAddress());
		        					SnitchMainPage.seekerEntered5 = true;
		        				}
		        			}
		        		} else if(CmiycJavaRes.activityState == CmiycJavaRes.SEEKERMAP){
		        			if(currentMessage.getDisplayMessageBody().contains("gp:")){
		        				String geoStringTemp = currentMessage.getDisplayMessageBody().replace("@!#gp:", "");
		        				GeoPoint geoPointTemp = CmiycJavaRes.stringToGeoPoint(geoStringTemp);
		        				SeekerMap.addMarker(geoPointTemp);
		        			}
		        		} else if(CmiycJavaRes.activityState == CmiycJavaRes.SEEKERWAITING){
		        			if (currentMessage.getDisplayMessageBody().contains("seekerConfirm")){
		        				
		        			}
		        		} else if(CmiycJavaRes.activityState == CmiycJavaRes.SEEKERMAIN){
		        			
		        		}
		        	}
		        	
		                //currentMessage.getDisplayOriginatingAddress();		// has sender's phone number
		                //currentMessage.getDisplayMessageBody();				// has the actual message
		        }
		}
		
	}

}
