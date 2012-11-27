package edu.calpoly.catchmeifyoucan;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.EditText;
import android.widget.Button;
// Typeface
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
// Contact Picker
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;  

public class SeekerMainPage extends Activity implements OnClickListener{

	private Button snitchContactPicker;
	private RelativeLayout startButton;
	private EditText box;
	private static final int CONTACT_PICKER_RESULT = 1001; 
	String num = "";
	String defaultNumber = "";
	SmsManager sm = SmsManager.getDefault();
	Drawable drawable;
	
	// Typeface only
	Typeface light;
	private TextView textTitle;
	private TextView textOr;
	private TextView textStartButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_main_page);
        this.getResources().getDrawable(R.drawable.ic_launcher);
        
        //creates the contact picker that accesses numbers on the phone
        snitchContactPicker = (Button)findViewById(R.id.snitch_contact_picker);
        snitchContactPicker.setOnClickListener(this);
        startButton = (RelativeLayout)findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        box = (EditText)findViewById(R.id.snitch_num);
        
        
        // Typeface Stuff
        light = Typeface.createFromAsset(getAssets(), "roboto_light.ttf");
        textTitle = (TextView)findViewById(R.id.text_seeker_title);
        textTitle.setTypeface(light);
        textOr = (TextView)findViewById(R.id.text_seeker_or);
        textOr.setTypeface(light);
        textStartButton = (TextView)findViewById(R.id.text_seeker_continue);
        textStartButton.setTypeface(light);
        snitchContactPicker.setTypeface(light);
        box.setTypeface(light);
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_seeker_main_page, menu);
        return true;
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	CmiycJavaRes.activityState = CmiycJavaRes.SEEKERMAIN;
    	
    }
   
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (resultCode == RESULT_OK) {  
            switch (requestCode) {  
            case CONTACT_PICKER_RESULT:
                final EditText phoneInput = (EditText)findViewById(R.id.snitch_num);
                Cursor cursor = null;  
                String phoneNumber = "";
                List<String> allNumbers = new ArrayList<String>();
                int phoneIdx = 0;
                try {  
                    Uri result = data.getData();  
                    String id = result.getLastPathSegment();  
                    cursor = getContentResolver().query(Phone.CONTENT_URI, null, Phone.CONTACT_ID + "=?", new String[] { id }, null);  
                    phoneIdx = cursor.getColumnIndex(Phone.DATA);
                    if (cursor.moveToFirst()) {
                        while (cursor.isAfterLast() == false) {
                            phoneNumber = cursor.getString(phoneIdx);
                            allNumbers.add(phoneNumber);
                            cursor.moveToNext();
                        }
                    } else {
                        //no results actions
                    }  
                } catch (Exception e) {  
                   //error actions
                } finally {  
                    if (cursor != null) {  
                        cursor.close();
                    }
                    final CharSequence[] items = allNumbers.toArray(new String[allNumbers.size()]);
                    AlertDialog.Builder builder = new AlertDialog.Builder(SeekerMainPage.this);
                    builder.setTitle("Choose a number");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            String selectedNumber = items[item].toString();
                            selectedNumber = selectedNumber.replace("-", "");
                            //num = selectedNumber;
                            phoneInput.setText(selectedNumber);
                        }
                    });
                    AlertDialog alert = builder.create();
                    if(allNumbers.size() > 1) {
                        alert.show();
                    } else {
                        String selectedNumber = phoneNumber.toString();
                        selectedNumber = selectedNumber.replace("-", "");
                        //num = selectedNumber;
                        phoneInput.setText(selectedNumber);
                    }

                    if (phoneNumber.length() == 0) {  
                        //no numbers found actions  
                    }  
                }  
                break;  
            }  
        } else {
           //activity result error actions
        }  
    }

    public boolean checkIfRealNumber(String x) {
    	defaultNumber = x.replace("(", "");
    	defaultNumber = defaultNumber.replace(")", "").replace(" ", "").replace("-", "").replace("+", "");
		if(defaultNumber.length() == 11) {
		    return true;
		} else if(defaultNumber.length() == 12) {
		    return true;
		} else if(defaultNumber.length() == 10) {
		    return true;
		} else if(defaultNumber.length() == 7) {
		    return true;
		} else {
		    return false;
		}
    }
    
	private void numberDoesntWork(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Invalid Number");
        alertDialog.setIcon(R.drawable.ic_launcher);
        alertDialog.setMessage("The number that you have entered is not a valid number. Please enter a valid number.");
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
              //Safety Net (Phew)
           }
        });
        alertDialog.show();
    }
    
	public void onClick(View v) {
		Intent seekerWaitIntent = new Intent(this, SeekerWaitingPage.class);
		if (v.equals(findViewById(R.id.snitch_contact_picker))) {
			Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
			startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
		} else if(v.equals(findViewById(R.id.start_button))) {
			num = box.getText().toString();
			if(checkIfRealNumber(num) == true) {
				//defaultNumber stores the phone number to text this is where you send out something to the snitch
				seekerWaitIntent.putExtra("snitchNumber", defaultNumber);
				sm.sendTextMessage(defaultNumber, null, "@!#seekerJoin", null, null);
				this.startActivity(seekerWaitIntent);
				finish();
			} else {
				numberDoesntWork();
			}

		}
	}
}
        
