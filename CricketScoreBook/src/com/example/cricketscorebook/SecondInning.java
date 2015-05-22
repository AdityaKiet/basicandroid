package com.example.cricketscorebook;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SecondInning extends Activity {

	String overs ;
	String chasescore;
	Spinner spinner1;
	Button bfstart;
	EditText edittext1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondinningovers);
		
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		bfstart = (Button) findViewById(R.id.bsstart);
		edittext1 = (EditText) findViewById(R.id.editText1);		
		
		bfstart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				overs = spinner1.getSelectedItem().toString();
				chasescore = edittext1.getText().toString();
				
				
				try {
					Class c = Class.forName("com.example.cricketscorebook.SecondInningscorebook");
					Intent inning2start = new Intent(SecondInning.this ,c);
					inning2start.putExtra("overs", overs);
					inning2start.putExtra("chasescore", chasescore);					
					startActivity(inning2start);
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
