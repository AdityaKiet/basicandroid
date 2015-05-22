package com.teamandappers.delhitourism;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class Oldfort extends Activity {
	WebView webview;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webvieww);
		webview = (WebView) findViewById(R.id.webView1);
		webview.setVerticalScrollBarEnabled(false);
		webview.setHorizontalScrollBarEnabled(false);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setLoadWithOverviewMode(true);
		WebSettings webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webview.setBackgroundColor(0);
		webview.getSettings().setBuiltInZoomControls(true);
		webview.loadUrl("file:///android_asset/oldfort.html");

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			webview.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_settings) {
			Intent i = new Intent(android.content.Intent.ACTION_SEND);
			i.setType("text/plain");
			i.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Share The Delhi Tourism Application");
			i.putExtra(
					android.content.Intent.EXTRA_TEXT,
					"Hi ! friends I am using this wonderful application. Please try this from following link......  http://goo.gl/BsQ73h");
			startActivity(Intent.createChooser(i, "Share via"));
		}
		return super.onOptionsItemSelected(item);
	}

}
