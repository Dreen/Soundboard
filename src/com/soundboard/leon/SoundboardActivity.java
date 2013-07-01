package com.soundboard.leon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class SoundboardActivity extends Activity {

	int[] drawables = {R.drawable.leonbg1, R.drawable.leonbg2, R.drawable.leonbg3, R.drawable.leonbg4, R.drawable.leonbg5, 
			R.drawable.leonbg6, R.drawable.leonbg7,R.drawable.leonbg8,R.drawable.leonbg9, R.drawable.leonbg10};
	LinearLayout master;
	LinearLayout bg;
	int imageNumber;
	int screensHeight;
	int screensWidth;
	
 	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        int[] sounds = {
        R.raw.what, R.raw.anothermotherfucker, R.raw.brain, R.raw.basiccable, R.raw.obama, 
		R.raw.cumnotcum, R.raw.cooking, R.raw.ejaculate, R.raw.fuckasshole, R.raw.fucklarryup, R.raw.gravy,
		R.raw.haha,	R.raw.hititquitit, R.raw.juice, R.raw.loretta, R.raw.longassballs, R.raw.leaveopen, 
		R.raw.longballslarry, R.raw.mobydick, R.raw.nope, R.raw.notmycum, R.raw.peanut, R.raw.spraypaintcan,
		R.raw.presidentofass, R.raw.rukus, R.raw.smartassmouth, R.raw.fuckedup, R.raw.stainonblanket, R.raw.topsyturvy,
		R.raw.visuals, R.raw.whatkindofcum, R.raw.whatkindofstain, R.raw.whatupal, R.raw.feelme};
		
		String[] labels = {
		"A what?", "Another mother", "Discombobulated", "Basic cable", "Barack Obama!",
		"Cum is not cum", "Cooking", "Ejaculate?!", "Fuck asshole", "Fuck Larry up", "Gravy",
		"Haha", "Hit it & quit it", "Juice? Syrup?", "Loretta!", "Long ass balls", "Leave ass open",
		"Long balls", "Mopy Dick", "Nope", "Not my cum", "Peanut boy!", "Spray paint",
		"President of Ass", "Ruckas", "Smart ass mouth", "Somebody fucked", "Stain on blanket", "Topsy turvy",
		"Visuals", "What cum?", "What kinda stain??", "Word up Al!", "You feel me?"};
		
		getScreenResolution();
		
	 	bg = (LinearLayout) this.findViewById(R.id.bg);
		imageNumber = 0;
		declareButtons(sounds, labels);
		setBackground();
		
		AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
		    audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
		    AudioManager.FLAG_SHOW_UI);
    }
   
    private void getScreenResolution() {
    	
    	    DisplayMetrics displaymetrics = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
	        screensHeight = displaymetrics.heightPixels;
	        screensWidth = displaymetrics.widthPixels; 		
	}

	private void declareButtons(int[] sounds, String[] labels) {
    	master = (LinearLayout) this.findViewById(R.id.master);
    	LinearLayout row = null;
        Button cell;
		int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        
		for (int i=0; i<sounds.length; i++) {
			
			cell = new Button(this);
			cell.setText(labels[i]);
			cell.setOnClickListener(this.getStartListener(sounds[i]));
			cell.setBackgroundDrawable(getResources().getDrawable(R.drawable.soundbutton));
			cell.setTextColor(Color.WHITE);
			cell.setTextSize(13);
		
			LinearLayout.LayoutParams cellParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 1);

			cell.setMinHeight(120);
			
			int totalImageSize = 480;
			
			//push to right by margin
			if (screensWidth > totalImageSize) {
				cellParams.leftMargin = margin + ((screensWidth - totalImageSize) /6);
				cellParams.rightMargin = margin + ((screensWidth - totalImageSize) /6);
			} else {
				cellParams.leftMargin = margin;
				cellParams.rightMargin = margin;
			}
			
						
			if (i%2==0) {
				// left column
				row = new LinearLayout(this);
				row.setOrientation(LinearLayout.HORIZONTAL);
				row.addView(cell);
			} else {
				// right column
				row.addView(cell);
			//	row.setPadding(0, 20, 0, 20);
//				LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, (float) 1);
//				row.setLayoutParams(rowParams);
				row.setPadding(0, 20, 0, 20);
		        master.addView(row);
			}	
			
			cell.setLayoutParams(cellParams);
		}
				
		// handle uneven amount of buttons
		if (sounds.length%2 > 0) {
			master.addView(row);
		}
	}

	public OnClickListener getStartListener(final int sound) {
	 return new View.OnClickListener() {
		 public void onClick(View v) {
			 playMedia(sound);			
		 }	
        };
	 }
	
	private void playMedia (int sound) {
		
		 MediaPlayer mp = MediaPlayer.create(SoundboardActivity.this, sound);
		 if(mp != null) {
			 mp.start();
		 } else {
			 playMedia(sound);
		 }
	}   
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	//handle options click
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.options_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
	        case R.id.about: {
	            showAbout();  
	        }
	        case R.id.background: {
	        	imageNumber++;
	            setBackground();
	        }
	        default:
	            return super.onOptionsItemSelected(item);
        }
    }
    
	private void setBackground() {
		//change background				
		if(imageNumber == drawables.length)
			imageNumber=0;
		
		bg.setBackgroundDrawable(getResources().getDrawable(drawables[imageNumber]));
	}

	private void showAbout() {
		final AlertDialog.Builder ab = new AlertDialog.Builder(this);
	   	 ab.setTitle("About");
	   	 ab.setMessage("Created by Ben Taliadoros" + "\n" + "and Greg Balaga");
	   	 ab.setNegativeButton("Back", new DialogInterface.OnClickListener() {
	   	     public void onClick(DialogInterface arg0, int arg1) {}})
	   	 .show();
	}
}