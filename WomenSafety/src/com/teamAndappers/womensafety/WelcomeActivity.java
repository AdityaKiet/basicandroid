package com.teamAndappers.womensafety;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeActivity extends Activity {
	EditText et1, et2;
	Button btn;
	SharedPreferences sp = null;
	SharedPreferences.Editor editor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		et1 = (EditText) findViewById(R.id.etname);
		et2 = (EditText) findViewById(R.id.etaddress);
		sp = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		editor = sp.edit();
		btn = (Button) findViewById(R.id.btsave);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String name = et1.getText().toString();
				String address = et2.getText().toString();
				if (name.trim().length() != 0 && address.trim().length() != 0) {
					editor.putString("keyname", name).commit();
					editor.putString("keyaddress", address).commit();
					Toast.makeText(getApplicationContext(), "Info Saved",
							Toast.LENGTH_LONG).show();
					Bundle bundle = new Bundle();
					bundle.putString("keyname", name);
					Intent intent = new Intent(WelcomeActivity.this,
							GuideLinesActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
					finish();

				} else {
					Toast.makeText(getApplicationContext(),
							"Enter  Your Info to Continue", Toast.LENGTH_LONG)
							.show();
				}

			}
		});
	}

}
