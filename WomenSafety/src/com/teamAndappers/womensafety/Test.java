package com.teamAndappers.womensafety;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Test extends Activity{
	TextView txt1,txt2,txt3;
	String phone1,phone2,phone3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		txt1 =(TextView)findViewById(R.id.editText1);
		txt2 =(TextView)findViewById(R.id.editText2);
		txt3 =(TextView)findViewById(R.id.editText3);
/*
		phone1=sp.getString("key1",null);
		phone2 =sp.getString("key2", null);
		txt1.setText(phone1);
		txt2.setText(phone2);*/
			}

	
}
