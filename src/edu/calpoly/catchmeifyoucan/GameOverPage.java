package edu.calpoly.catchmeifyoucan;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.graphics.Typeface;

public class GameOverPage extends Activity implements OnClickListener {
	
	RelativeLayout buttonGameOver;
	
	// Typeface
	TextView textPlayAgain, textGame, textOver;
	Typeface thin, light;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over_page);
		buttonGameOver = (RelativeLayout)findViewById(R.id.button_play_again);
		buttonGameOver.setOnClickListener(this);
		
		// Typeface
		light = Typeface.createFromAsset(getAssets(), "roboto_light.ttf");
		textPlayAgain = (TextView)findViewById(R.id.text_play_again);
		textPlayAgain.setTypeface(light);
		thin = Typeface.createFromAsset(getAssets(), "roboto_thin.ttf");
		textGame = (TextView)findViewById(R.id.text_game);
		textGame.setTypeface(thin);
		textOver = (TextView)findViewById(R.id.text_over);
		textOver.setTypeface(thin);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_game_over_page, menu);
		return true;
	}

	public void onClick(View btnClicked) {
		if(btnClicked == buttonGameOver){
			finish();
		}
	}

}
