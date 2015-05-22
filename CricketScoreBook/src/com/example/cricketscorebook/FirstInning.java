package com.example.cricketscorebook;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class FirstInning extends Activity {
	
	String overs ;
	Spinner spinner1;
	Button bfstart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstinningovers);

		spinner1 = (Spinner) findViewById(R.id.spinner1);
		bfstart = (Button) findViewById(R.id.bfstart);
		
		bfstart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				overs = spinner1.getSelectedItem().toString();
				
				try {
					Class c = Class.forName("com.example.cricketscorebook.FirstInningscorebook");
					Intent inning1start = new Intent(FirstInning.this ,c);
					inning1start.putExtra("overs", overs);
					startActivity(inning1start);
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
