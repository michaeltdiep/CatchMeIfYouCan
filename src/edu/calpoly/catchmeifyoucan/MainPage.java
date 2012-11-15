package edu.calpoly.catchmeifyoucan;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class MainPage extends Activity implements OnClickListener{
	
	RelativeLayout buttonMainSnitch;
	RelativeLayout buttonMainSeeker;
	RelativeLayout buttonMainConfused;
	
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
			i =null;
		}
		this.startActivity(i);
		
	}
}