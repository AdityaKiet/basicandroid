package com.andappers.liedetector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kiet.liedetector.R;
import com.purplebrain.adbuddiz.sdk.AdBuddiz;

public class Master extends Activity implements OnClickListener {
	Button settruthlie, couldnotdetermine, clear;
	ImageButton imgbutton;
	TextView resulttxt;
	Vibrator vib;
	int flag1 = 1;
	int flag2 = 0;
	int visibility = 1;
	MediaPlayer media;
	SharedPreferences sp;
	SharedPreferences.Editor edit;

	// Boolean first = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		AdBuddiz.setPublisherKey("c4a75052-fa05-4572-810a-4b7ca2233e39");
		AdBuddiz.cacheAds(this);
		setContentView(R.layout.master);
		media = MediaPlayer.create(Master.this, R.drawable.sb48);
		settruthlie = (Button) findViewById(R.id.setlietruth);
		couldnotdetermine = (Button) findViewById(R.id.notdetermined);
		clear = (Button) findViewById(R.id.clear);
		imgbutton = (ImageButton) findViewById(R.id.imageButton);

		resulttxt = (TextView) findViewById(R.id.resulttxt);
		vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		settruthlie.setOnClickListener(this);
		couldnotdetermine.setOnClickListener(this);
		clear.setOnClickListener(this);
		imgbutton.setOnClickListener(this);
		sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		edit = sp.edit();
		String message = "\n 1. This application is just for fun.. you can know the secrets of your friends using this.\n\n 2. There are two invisible buttons on both corners.. you just have to click them to change the text displayed\n\n 3. Keep Enjoying and rate us..";
		Boolean first = sp.getBoolean("first", true);
		if (first) {
			edit.putBoolean("first", false).commit();
			new AlertDialog.Builder(this).setTitle("Tips....")
					.setPositiveButton("Ok", null).setMessage(message)
					.setCancelable(false).show();
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.clear:
			resulttxt.setText("");

			break;
		case R.id.imageButton:

			vib.vibrate(200);

			if (flag2 == 1) {
				resulttxt.setTextColor(Color.YELLOW);
				resulttxt.setText("Not determined");
			} else if (flag2 == 0) {
				if (flag1 == 0) {
					resulttxt.setTextColor(Color.RED);
					resulttxt.setText("You are a lier....!!!");
					vib.vibrate(400);

					media.start();
				} else {
					resulttxt.setTextColor(Color.GREEN);
					resulttxt.setText("that's true buddy..");

				}
			}
			break;
		case R.id.notdetermined:
			vib.vibrate(200);
			if (flag2 == 0) {
				flag2 = 1;
			}
			break;
		case R.id.setlietruth:
			vib.vibrate(200);
			flag2 = 0;
			if (flag1 == 1) {
				flag1 = 0;
			} else if (flag1 == 0) {
				flag1 = 1;
			}
			break;

		}

	}

	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.showhide:
			if (visibility == 0) {
				visibility = 1;
				settruthlie.setBackgroundColor(R.drawable.button);
				couldnotdetermine.setBackgroundColor(R.drawable.button);
			} else {
				visibility = 0;
				settruthlie.setBackgroundResource(R.drawable.grey);
				couldnotdetermine.setBackgroundResource(R.drawable.grey);
			}
			break;
		case R.id.exitt:
			finish();
		}
		return false;
	}

}
