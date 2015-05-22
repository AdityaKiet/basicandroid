package com.eon.porject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sharma.R;

public class MainActivity extends Activity implements OnClickListener {
	Button button1, button2, button3, button4, button5, button6;
	EditText editText;
	TextView textView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button6 = (Button) findViewById(R.id.button6);
		editText = (EditText) findViewById(R.id.editText1);
		textView = (TextView) findViewById(R.id.textView1);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);
		button6.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			button1Method();
			break;
		case R.id.button2:
			button2Method();
			break;
		case R.id.button3:
			button3Method();
			break;
		case R.id.button4:
			button4Method();
			break;
		case R.id.button5:
			button5Method();
			break;
		case R.id.button6:
			button6Method();
			break;
		}

	}

	private void button6Method() {
		// TODO Auto-generated method stub

	}

	private void button5Method() {
		// TODO Auto-generated method stub

	}

	private void button4Method() {
		// TODO Auto-generated method stub

	}

	private void button3Method() {
		// TODO Auto-generated method stub

	}

	private void button2Method() {
		// TODO Auto-generated method stub

	}

	private void button1Method() {
		// TODO Auto-generated method stub
	}

}
