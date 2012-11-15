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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;  

public class SeekerMainPage extends Activity implements OnClickListener{

	private Button snitchContactPicker;
	private Button startButton;
	private EditText box;
	private static final int CONTACT_PICKER_RESULT = 1001; 
	String num = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_main_page);
        
        //creates the contact picker that accesses numbers on the phone
        snitchContactPicker = (Button)findViewById(R.id.snitch_contact_picker);
        snitchContactPicker.setOnClickListener(this);   
        startButton = (Button)findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        box = (EditText)findViewById(R.id.snitch_num);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_seeker_main_page, menu);
        return true;
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

    public String checkIfRealNumber(String x) {
    	int stringLength = x.length();
    	String defaultNumber = "";
    	if(stringLength == 11) {
    		defaultNumber = x.substring(1,11);
    		return defaultNumber;
    	} else if(stringLength == 12) {
    		defaultNumber = x.substring(2,12);
    		return defaultNumber;
    	} else if(stringLength == 10) {
    		defaultNumber = x;
    		return defaultNumber;
    	} else {
    		// TODO edit this so that it displays that it is not a full number if it doesnt work.
    		return "REJECTED!";
    	}
    }
    
	public void onClick(View v) {
		if (v.equals(findViewById(R.id.snitch_contact_picker))) {
			Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
			startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
		}
		else if(v.equals(findViewById(R.id.start_button))) {
			num = box.getText().toString();
			startButton.setText(checkIfRealNumber(num));
		}
	}
}
        
