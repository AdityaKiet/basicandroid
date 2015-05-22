package com.example.cricketscorebook;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button ins1 = (Button) findViewById(R.id.ins1);
		Button ins2 = (Button) findViewById(R.id.ins2);
		
		ins1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				try {
					Class c = Class.forName("com.example.cricketscorebook.FirstInning");
					Intent inning1 = new Intent(MainActivity.this ,c);
					startActivity(inning1);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		ins2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				try {
					Class c = Class.forName("com.example.cricketscorebook.SecondInning");
					Intent inning2 = new Intent(MainActivity.this ,c);
					startActivity(inning2);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
