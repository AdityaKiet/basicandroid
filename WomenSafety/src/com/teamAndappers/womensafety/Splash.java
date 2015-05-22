package com.teamAndappers.womensafety;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class Splash extends Activity {
	SharedPreferences sp = null;
	SharedPreferences.Editor edit = null;
	String num1, num2, num3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		KeyguardManager kgMgr = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		boolean showing = kgMgr.inKeyguardRestrictedInputMode();
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
						| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
						| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		edit = sp.edit();
		num1 = sp.getString("key1", null);
		num2 = sp.getString("key2", null);
		num3 = sp.getString("key3", null);
		final Thread timer = new Thread() {
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					if (num1 == null && num2 == null && num3 == null) {
						Intent intent = new Intent(
								"com.teamAndappers.womensafety.ContactInfoActivity");
						startActivity(intent);
					} else {
						Intent intent = new Intent(
								"com.teamAndappers.womensafety.ControllerActivity");
						startActivity(intent);
					}
				}
			}
		};
		final AlertDialog.Builder dialog = new AlertDialog.Builder(this)
				.setTitle("Alert").setMessage(
						"Are you sure you want to launch the application?");
		dialog.setPositiveButton("Confirm",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {

						if (isNetworkAvailable()) {
							timer.start();
						} else {
							if (sp.getString("key1", null) == null) {
								new AlertDialog.Builder(Splash.this)
										.setTitle("Warning")
										.setCancelable(false)
										.setMessage(
												"You don't have any settings for security..")
										.setPositiveButton(
												"Exit",
												new DialogInterface.OnClickListener() {

													public void onClick(
															DialogInterface dialog,
															int which) {
														Splash.this.finish();
													}
												}).show();
							} else {
								autoCall();
								Splash.this.finish();
							}
						}
					}
				});
		dialog.setNegativeButton("No !!",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Splash.this.finish();

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
					if (isNetworkAvailable()) {
						timer.start();
					} else {
						if (sp.getString("key1", null) == null) {
							new AlertDialog.Builder(Splash.this)
									.setTitle("Warning")
									.setCancelable(false)
									.setMessage(
											"You don't have any settings for security..")
									.setPositiveButton(
											"Exit",
											new DialogInterface.OnClickListener() {

												public void onClick(
														DialogInterface dialog,
														int which) {
													Splash.this.finish();
												}
											}).show();
						} else {
							autoCall();
							Splash.this.finish();
						}
					}
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

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	@Override
	public void onBackPressed() {

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (Intent.ACTION_MAIN.equals(intent.getAction())) {
			Log.i("MyLauncher", "onNewIntent: HOME Key");
			Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG)
					.show();
		}
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public void autoCall() {
		Uri uri = Uri.fromParts("tel", sp.getString("key1", null), null);
		Intent callIntent = new Intent(Intent.ACTION_CALL, uri);
		callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(callIntent);
	}
}
