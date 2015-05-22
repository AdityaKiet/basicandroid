package com.teamAndappers.womensafety;



import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GuideLinesActivity extends Activity{
  TextView text1,text2;
  String recieved;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guidelines);
		text1 =(TextView)findViewById(R.id.txtwelcm);
		text2 =(TextView)findViewById(R.id.txtguide);
		Bundle bundle =getIntent().getExtras();
		recieved = bundle.getString("keyname");
		text1.setText("Welcome"+ recieved);
		
	}
	

}
