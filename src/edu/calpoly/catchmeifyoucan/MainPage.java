package edu.calpoly.catchmeifyoucan;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Button;
//Typeface
import android.widget.TextView;
import android.graphics.Typeface;


public class MainPage extends Activity implements OnClickListener{
	
	RelativeLayout buttonMainSnitch;
	RelativeLayout buttonMainSeeker;
	RelativeLayout buttonMainConfused;
	TextView textMainIAm;
	TextView textMainSeeker;
	TextView textMainSnitch;
	TextView textMainConfused;
	Typeface light;
	String name;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        buttonMainSnitch = (RelativeLayout)findViewById(R.id.button_main_snitch);
        buttonMainSnitch.setOnClickListener(this);
        buttonMainSeeker = (RelativeLayout)findViewById(R.id.button_main_seeker);
        buttonMainSeeker.setOnClickListener(this);
        buttonMainConfused = (RelativeLayout)findViewById(R.id.button_main_confused);
        buttonMainConfused.setOnClickListener(this);
        
        textMainIAm = (TextView)findViewById(R.id.text_main_i_am);
        textMainSeeker = (TextView)findViewById(R.id.text_main_seeker);
        textMainSnitch = (TextView)findViewById(R.id.text_main_snitch);
        textMainConfused = (TextView)findViewById(R.id.text_main_confused);
        
        light = Typeface.createFromAsset(getAssets(), "roboto_light.ttf");
    	textMainIAm.setTypeface(light);
    	textMainSeeker.setTypeface(light);
    	textMainSnitch.setTypeface(light);
    	textMainConfused.setTypeface(light);
    	
    	CmiycJavaRes.activityState = CmiycJavaRes.MAIN;
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_page, menu);
        return true;
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	CmiycJavaRes.activityState = CmiycJavaRes.MAIN;
    	
    }
    
	public void onClick(View buttonChosen) {
		Intent i;
		
		// I couldn't get this to work...
		/*switch (buttonChosen.getId()) {
		case R.id.button_main_seeker:
			i = new Intent(this, SeekerMainPage.class);
		case R.id.button_main_snitch:
			i = new Intent(this, SnitchMainPage.class);
		case R.id.button_main_confused:
			i = new Intent(this, Confused.class);
		default:
			i = null;
		} */
		
		if(buttonChosen==buttonMainSnitch){
			i = new Intent(this, SnitchMainPage.class);
			//CmiycJavaRes.activityState = CmiycJavaRes.SNITCHMAIN;
		}else if(buttonChosen==buttonMainSeeker){
			/*saveName();
			if(saveName() == true){
				i = new Intent(this, SeekerMainPage.class);
			}*/
			i = new Intent(this, SeekerMainPage.class);
			//CmiycJavaRes.activityState = CmiycJavaRes.SEEKERMAIN;
		}else if(buttonChosen==buttonMainConfused){
			i = new Intent(this, Confused.class);
			//CmiycJavaRes.activityState = CmiycJavaRes.CONFUSED;
		}else{
			i = null;
		}
		
		this.startActivity(i);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*switch (item.getItemId()) {
			//case R.id.menu_seeker_map:
				//Intent i = new Intent(this, SeekerMap.class);
				//startActivity(i);
			case R.id.menu_snitch:
				Intent i = new Intent(this, SnitchTimer.class);
				startActivity(i);
			}*/
		return true;
	}
	
 /*   private boolean saveName(){
    	final boolean yes;
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("What is your name?");
        alertDialog.setMessage("Please enter your name.");
        alertDialog.setButton(1, "Ok", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
           }
        });
        alertDialog.show();
    }*/
}