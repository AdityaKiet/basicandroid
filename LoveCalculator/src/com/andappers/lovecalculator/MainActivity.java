package com.andappers.lovecalculator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class MainActivity extends Activity {
	Button button;
	EditText et1, et2;
	Vibrator vibrator;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		button = (Button) findViewById(R.id.button);
		et1 = (EditText) findViewById(R.id.etname1);
		et2 = (EditText) findViewById(R.id.etname2);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				vibrator.vibrate(500);
				if (et1.getText().toString() == null
						|| et2.getText().toString() == null
						|| et1.getText().toString().trim().equals("")
						|| et2.getText().toString().trim().equals("")) {

				} else if (!(et1.getText().toString().trim()
						.matches("[a-zA-Z ]+"))
						|| !(et2.getText().toString().trim()
								.matches("[a-zA-Z ]+"))) {
					Toast.makeText(getApplicationContext(), "Invalid Names",
							Toast.LENGTH_SHORT).show();
				} else {

					Bundle bundle = new Bundle();
					bundle.putString("name1", et1.getText().toString().trim()
							.toLowerCase());
					bundle.putString("name2", et2.getText().toString().trim()
							.toLowerCase());
					Intent intent = new Intent(
							"com.andappers.lovecalculator.Result");
					intent.putExtras(bundle);
					startActivity(intent);
					finish();
				}

			}

		});
	}

	public void onBackPressed() {
		new AlertDialog.Builder(this)
		.setMessage("Are you sure you want to exit?")
		.setCancelable(false)
		.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int id) {
						finish();
					}
				}).setNegativeButton("No", null).show();
	}

}
