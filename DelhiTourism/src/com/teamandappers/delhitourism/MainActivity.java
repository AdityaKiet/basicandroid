package com.teamandappers.delhitourism;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Thread t = new Thread() {
			public void run() {
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent i = new Intent(
							"com.teamandappers.delhitourism.Mainlist");
					startActivity(i);
				}

			}
		};
		t.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	@Override
	public void onBackPressed() {

	}
}
