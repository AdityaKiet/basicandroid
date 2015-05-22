package ainna.acup.slidingmenu;

import ainna.acup.client.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash);

		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent intent = new Intent(
							"ainna.acup.tabsswipe.MainActivity");
					startActivity(intent);
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

}
