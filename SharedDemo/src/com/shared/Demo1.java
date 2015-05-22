package com.shared;

import com.example.shareddemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Demo1 extends Activity {
	TextView tv;
	String temp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deom1);
		tv = (TextView) findViewById(R.id.textView12);
		Bundle basket = getIntent().getExtras();
		temp = basket.getString("key");
		tv.setText(temp);
	}

}
