package edu.calpoly.catchmeifyoucan;

import java.util.ArrayList;

import android.content.Context;
import android.widget.RelativeLayout;



public class Seeker {
	
	static ArrayList<Seeker> seekerArray = new ArrayList<Seeker>();
	
	String mName;
	String mNumber;
	boolean mEntered;
	
	public Seeker(String number, String name) {
		mName = name;
		mNumber = number;
		mEntered = true;
	}
	
	public void deleteSeeker() {
		mEntered = false;
	}
	
	public void createSeekerLayout(Context context) {
		RelativeLayout seekerLayout = new RelativeLayout(context);
		
	}
	
	public static void createSeeker(String number, String name, Context context) {
		seekerArray.add(new Seeker(number, name));
		seekerArray.get(seekerArray.size() - 1).createSeekerLayout(context);
		
	}

}
