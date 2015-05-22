package com.example.phonegapappliaction;

import org.apache.cordova.DroidGap;

import android.os.Bundle;

public class MainActivity extends DroidGap {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.loadUrl("http://10.0.2.2:8080/Online_Exam/");
		super.setIntegerProperty("splashscreen", R.drawable.ic_launcher);
	}

}
