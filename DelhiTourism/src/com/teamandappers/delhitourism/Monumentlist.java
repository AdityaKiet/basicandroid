package com.teamandappers.delhitourism;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Monumentlist extends Activity {
	ListView listview;
	String[] web = { "Akshardham Temple", "Bangla Sahib Gurudwara",
			"Chattarpurmandir", "Humayun's Tomb", "India Gate",
			"Isckon Temple", "Jantar Mantar", "Jama Masjid", "Lotus Temple",
			"LaxmiNarayan Temple", "Old Fort(Purana Quila)",
			"Parliament House", "Qutub Minar", "Red Fort", "Rashtrapati Bhavan" };
	Integer[] ids = { R.drawable.akshar, R.drawable.bangla1,
			R.drawable.chattar2, R.drawable.images, R.drawable.india1,
			R.drawable.isckon1, R.drawable.jantar1, R.drawable.jama1,
			R.drawable.lotuss, R.drawable.birla2, R.drawable.purana2,
			R.drawable.parlia2, R.drawable.qutub1, R.drawable.redfort1,
			R.drawable.rashtrapati2 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.monumentlist);
		listview = (ListView) findViewById(R.id.listView2);
		AdapterC adapter = new AdapterC(Monumentlist.this, web, ids);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 0) {
					Intent i = new Intent(
							"com.teamandappers.delhitourism.Akshardham");
					startActivity(i);
				} else if (arg2 == 1) {
					Intent i = new Intent(
							"com.teamandappers.delhitourism.Bangla");
					startActivity(i);
				} else if (arg2 == 2) {
					Intent i = new Intent(
							"com.teamandappers.delhitourism.Chattar");
					startActivity(i);
				} else if (arg2 == 3) {
					Intent i = new Intent(
							"com.teamandappers.delhitourism.Humayun");
					startActivity(i);
				} else if (arg2 == 4) {
					Intent i = new Intent(
							"com.teamandappers.delhitourism.Indiagate");
					startActivity(i);
				} else if (arg2 == 5) {
					Intent i = new Intent(
							"com.teamandappers.delhitourism.Isckon");
					startActivity(i);
				} else if (arg2 == 6) {
					Intent i = new Intent(
							"com.teamandappers.delhitourism.Jantar");
					startActivity(i);
				} else if (arg2 == 7) {
					Intent i = new Intent("com.teamandappers.delhitourism.Jama");
					startActivity(i);
				} else if (arg2 == 8) {
					Intent i = new Intent(
							"com.teamandappers.delhitourism.Lotus");
					startActivity(i);
				} else if (arg2 == 9) {
					Intent i = new Intent(
							"com.teamandappers.delhitourism.Laxmi");
					startActivity(i);
				} else if (arg2 == 10) {
					Intent i = new Intent(
							"com.teamandappers.delhitourism.Oldfort");
					startActivity(i);
				} else if (arg2 == 11) {
					Intent i = new Intent(
							"com.teamandappers.delhitourism.Parlia");
					startActivity(i);
				} else if (arg2 == 12) {
					Intent i = new Intent(
							"com.teamandappers.delhitourism.Qutub");
					startActivity(i);
				} else if (arg2 == 13) {
					Intent i = new Intent("com.teamandappers.delhitourism.Red");
					startActivity(i);
				} else {
					Intent i = new Intent(
							"com.teamandappers.delhitourism.Rashtrapati");
					startActivity(i);
				}

			}
		});

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
