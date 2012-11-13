package edu.calpoly.catchmeifyoucan;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;  

public class SeekerMainPage extends Activity {

	Button snitchContactPicker;
	private static final int CONTACT_PICKER_RESULT = 1001; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_main_page);
        
        //creates the contact picker that accesses numbers on the phone
        snitchContactPicker = (Button)findViewById(R.id.snitch_contact_picker);
        snitchContactPicker.setOnClickListener((OnClickListener)this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_seeker_main_page, menu);
        return true;
    }
    
    public void doLaunchContactPicker(View view) {  
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);  
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);  
    }
    
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (resultCode == RESULT_OK) {  
            switch (requestCode) {  
            case CONTACT_PICKER_RESULT:  
                Cursor cursor = null;  
                String phoneNum = "";  
                try {  
                    Uri result = data.getData();
                    // get the contact id from the Uri  
                    String id = result.getLastPathSegment();  
                    // query for everything in the phone
                    cursor = getContentResolver().query(Phone.CONTENT_URI, null, Phone.CONTACT_ID + "=?", new String[] { id }, null);  
                    int emailIdx = cursor.getColumnIndex(Phone.DATA);  
                    // let's just get the first number 
                    if (cursor.moveToFirst()) {  
                        phoneNum = cursor.getString(emailIdx);  
                        finally {  
                    if (cursor != null) {  
                        cursor.close();  
                    }  
                    EditText phoneEntry = (EditText) findViewById(R.id.snitchs_number);  
                    emailEntry.setText(email);  
                    if (email.length() == 0) {  
                        Toast.makeText(this, "No email found for contact.",  
                        Toast.LENGTH_LONG).show();  
                    }  
                }  
                break;  
            }  
        } else {  
            Log.w(DEBUG_TAG, "Warning: activity result not ok");  
        }  
    }  
}
