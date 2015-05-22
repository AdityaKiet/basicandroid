package com.andappers.liedetector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kiet.liedetector.R;
import com.purplebrain.adbuddiz.sdk.AdBuddiz;

public class FingerPrint extends Activity {
	int counter = 0;
	Button conti;
	private Handler handler = new Handler();
	ProgressBar progress;
	int prostatus = 0;
	ImageButton imgbutton;
	TextView txtview, txtview1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AdBuddiz.setPublisherKey("c4a75052-fa05-4572-810a-4b7ca2233e39");
		AdBuddiz.cacheAds(this);
		setContentView(R.layout.finger);
		conti = (Button) findViewById(R.id.cont);
		imgbutton = (ImageButton) findViewById(R.id.imageButton1);
		txtview = (TextView) findViewById(R.id.textview1);
		txtview1 = (TextView) findViewById(R.id.textview2);
		progress = (ProgressBar) findViewById(R.id.progressBar1);
		conti.setEnabled(false);
		imgbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				conti.setEnabled(false);
				new Thread(new Runnable() {
					public void run() {
						while (prostatus < 100) {

							prostatus += 10;
							counter++;
							handler.post(new Runnable() {
								public void run() {
									progress.setProgress(prostatus);
									txtview1.setText(prostatus + "/" + 100);

									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} finally {
										txtview.setText("Analysing......");

									}
									if (progress.getProgress() == 100) {
										conti.setEnabled(true);
										txtview.setText("Done..Please press continue..");
									}
								}
							});
							try {

								Thread.sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}

					}

				}).start();

			}

		});

		conti.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (counter > 0) {
					Intent intent = new Intent(
							"com.andappers.liedetector.Master");
					startActivity(intent);

				} else {
					txtview.setText("finger print required");
				}

			}
		});

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
