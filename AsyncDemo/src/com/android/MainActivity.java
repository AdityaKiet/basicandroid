package com.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.asyncdemo.R;

public class MainActivity extends Activity {
	WebView webview;
	Button button;
	ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mProgressDialog = new ProgressDialog(MainActivity.this);
		mProgressDialog.setMessage("A message");
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setCancelable(true);

		setContentView(R.layout.activity_main);
		webview = (WebView) findViewById(R.id.webView1);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new Download().execute("https://www.google.com");
			}
		});
	}

	private class Download extends AsyncTask<String, Integer, Long> {

		@Override
		protected Long doInBackground(String... urls) {
			webview.loadUrl(urls[0]);
			return null;

		}

		@Override
		protected void onPreExecute() {
			Toast.makeText(getApplicationContext(), "Loading....",
					Toast.LENGTH_LONG).show();
			mProgressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Long result) {
			mProgressDialog.dismiss();
			super.onPostExecute(result);
		}

	}
}
