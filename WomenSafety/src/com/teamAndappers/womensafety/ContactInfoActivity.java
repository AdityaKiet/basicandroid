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

public class ContactInfoActivity extends Activity {
	EditText contact1, contact2, contact3;
	Button btsub;

	SharedPreferences sp1 = null;

	SharedPreferences.Editor edit = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_info);
		contact1 = (EditText) findViewById(R.id.editText1);
		contact2 = (EditText) findViewById(R.id.editText2);
		contact3 = (EditText) findViewById(R.id.editText3);

		sp1 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

		edit = sp1.edit();

		btsub = (Button) findViewById(R.id.btsubmit);

		btsub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String con1 = contact1.getText().toString();
				String con2 = contact2.getText().toString();
				String con3 = contact3.getText().toString();
				if (con1.trim().length() == 0 && con2.trim().length() == 0
						&& con3.trim().length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Enter Atleast One Contact", Toast.LENGTH_LONG)
							.show();

				}

				else {
					edit.putString("key1", "+91" + con1).commit();
					edit.putString("key2", "+91" + con2).commit();
					edit.putString("key3", "+91" + con3).commit();
					Toast.makeText(getApplicationContext(),
							"Information Saved Succesfully ", Toast.LENGTH_LONG)
							.show();
					Intent intent = new Intent(
							"com.teamAndappers.womensafety.WelcomeActivity");
					startActivity(intent);
					finish();
				}
			}
		});
	}

}
