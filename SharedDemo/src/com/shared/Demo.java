package com.shared;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shareddemo.R;

public class Demo extends Activity {
	Button b;
	EditText et;
	TextView tv;
	SharedPreferences sp = null;
	SharedPreferences.Editor edit = null;
	String oftextview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo);
		b = (Button) findViewById(R.id.button1);
		et = (EditText) findViewById(R.id.editText1);
		tv = (TextView) findViewById(R.id.textView1);
		sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		edit = sp.edit();
		oftextview = sp.getString("key", null);
		if (oftextview != null) {
			tv.setText(oftextview);
		}
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String temp = et.getText().toString().trim();
				tv.setText(temp);
				edit.putString("key", temp).commit();
				Bundle bundle = new Bundle();
				bundle.putString("key", temp);
				Intent intent = new Intent(Demo.this, Demo1.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

}
