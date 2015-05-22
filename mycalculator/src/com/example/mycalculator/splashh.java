package com.example.mycalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;




public class splashh extends Activity {
	@Override
protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Thread thread=new Thread()
		{
			public void run()
			{
				try{
					sleep(5000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				finally{
					Intent intent=new Intent("com.example.mycalculator.MainActivity");
					startActivity(intent);
				
				}}
			};
			thread.start();
		
	}
	@Override
	protected void onPause()
	{
		super.onPause();
		finish();
	}
	
	@Override
	public void onBackPressed() {
		
	}
		
	}

	


