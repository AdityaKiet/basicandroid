package com.andappers.lovecalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Result extends Activity {
	TextView title, resultView;
	String name1, name2;
	Button share;
	int result;
	String titleString, resultString;
	int percentage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		Bundle bundle = getIntent().getExtras();
		name1 = bundle.getString("name1");
		name2 = bundle.getString("name2");
		share = (Button) findViewById(R.id.share);
		title = (TextView) findViewById(R.id.textTitle);
		resultView = (TextView) findViewById(R.id.textResult);
		calculateResult();
		showresult();
		share.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent(android.content.Intent.ACTION_SEND);
				i.setType("text/plain");
				i.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Love Calculator Result");
				i.putExtra(android.content.Intent.EXTRA_TEXT, "Hello " + name1
						+ " and " + name2 + "\n Your love is " + titleString
						+ "\ndiscription is\n" + resultString);
				startActivity(Intent.createChooser(i, "Share via"));
			}
		});
	}

	private void calculateResult() {
		result = name1.compareTo(name2);
		// result += 25;
		titleString = StringText.title[result];
		resultString = StringText.text[result];
		percentage = StringText.percentage[result];
	}

	private void showresult() {

		title.setText(titleString + result);
		resultView.setText(resultString + percentage);
	}

	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent("com.andappers.lovecalculator.MainActivity");
		startActivity(intent);
		this.finish();
	}

}
