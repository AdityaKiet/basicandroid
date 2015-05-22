package com.example.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent openSplash2 = new Intent(
							"com.example.tictactoe.MainActivity");
					startActivity(openSplash2);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		finish();
	}

	@Override
	public void onBackPressed() {

	}

}