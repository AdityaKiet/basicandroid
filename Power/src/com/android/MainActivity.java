package com.android;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.power.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		KeyguardManager kgMgr = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		boolean showing = kgMgr.inKeyguardRestrictedInputMode();
		if (showing) {
			Log.d("hi", "locked");
		} else {
			Log.d("hi", "not");
		}
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
						| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
						| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG)
				.show();
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

}
