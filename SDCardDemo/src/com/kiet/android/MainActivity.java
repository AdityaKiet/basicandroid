package com.kiet.android;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.sdcarddemo.R;

public class MainActivity extends Activity {
	Button btn;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("KIET")
						.setMessage("This is KIET")
						.setIcon(R.drawable.ic_launcher)
						.setCancelable(true)
						.setPositiveButton("Exit",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										promptSpeechInput();
									}
								}).setNegativeButton("Cancel", null).show();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.exitt) {
			MainActivity.this.finish();
		}
		return super.onOptionsItemSelected(item);
	}

	private void promptSpeechInput() {
		/*Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				getString(R.string.speech_prompt));
		try {
			startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
		} catch (ActivityNotFoundException a) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.speech_not_supported),
					Toast.LENGTH_SHORT).show();
		}*/
	}
}
