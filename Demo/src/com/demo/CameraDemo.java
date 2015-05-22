package com.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import com.example.demo.R;

public class CameraDemo extends Activity {
	Button buttonClick;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		buttonClick = (Button) findViewById(R.id.buttonClick);
		final AlertDialog.Builder dialog = new AlertDialog.Builder(this)
				.setTitle("Leaving launcher").setMessage(
						"Are you sure you want to leave the launcher?");
		dialog.setPositiveButton("Confirm",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						Toast.makeText(getApplicationContext(), "hi",
								Toast.LENGTH_LONG).show();
					}
				});
		final AlertDialog alert = dialog.create();
		alert.show();

		final Handler handler = new Handler();
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if (alert.isShowing()) {
					alert.dismiss();
					finish();
				}
			}
		};

		alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				handler.removeCallbacks(runnable);
			}
		});

		handler.postDelayed(runnable, 10000);
	}
}