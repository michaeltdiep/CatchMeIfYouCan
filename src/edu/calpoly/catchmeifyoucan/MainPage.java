package edu.calpoly.catchmeifyoucan;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
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
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_page, menu);
        return true;
    }
    
	public void onClick(View buttonChosen) {
		Intent i;
		if(buttonChosen==buttonMainSnitch){
			i = new Intent(this, SnitchMainPage.class);
		}else if(buttonChosen==buttonMainSeeker){
			i = new Intent(this, SeekerMainPage.class);
		}else if(buttonChosen==buttonMainConfused){
			i = new Intent(this, Confused.class);
		}else{
			i = null;
		}
		this.startActivity(i);
		
	}
}