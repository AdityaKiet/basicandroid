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

public class Parkslist extends Activity {
	ListView listview;
	String[] web = { "Buddha Jayanti Park", "Central Park", "Deer Park",
			"Garden Of Five Senses", "Humayun Tomb Park",
			"Jahanpanah City Forest", "Kalindi Kunj", "Kalkaji District Park",
			"Lodhi Park", "Millenium Park", "Mughal Park", "Nehru Park",
			"Sultanpur Bird Sanctuary", "Zoological Garden" };
	Integer[] ids = { R.drawable.budha1, R.drawable.centre3, R.drawable.deer5,
			R.drawable.pic0, R.drawable.hum3, R.drawable.jaha4,
			R.drawable.kalindi3, R.drawable.kalka4, R.drawable.lodhi1,
			R.drawable.mill1, R.drawable.mughal4, R.drawable.nehru4,
			R.drawable.sultan5, R.drawable.zoo1 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parklist);

		listview = (ListView) findViewById(R.id.listView1);
		AdapterC adapter = new AdapterC(Parkslist.this, web, ids);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 0) {
					Intent i = new Intent("com.teamandappers.delhitourism.Budhha");
					startActivity(i);
				} else if (arg2 == 1) {
					Intent i = new Intent("com.teamandappers.delhitourism.Central");
					startActivity(i);
				} else if (arg2 == 2) {
					Intent i = new Intent("com.teamandappers.delhitourism.Deer");
					startActivity(i);
				} else if (arg2 == 3) {
					Intent i = new Intent("com.teamandappers.delhitourism.Fivesense");
					startActivity(i);
				} else if (arg2 == 4) {
					Intent i = new Intent("com.teamandappers.delhitourism.Hum");
					startActivity(i);
				}

				else if (arg2 == 5) {
					Intent i = new Intent("com.teamandappers.delhitourism.Jaha");
					startActivity(i);
				} else if (arg2 == 6) {
					Intent i = new Intent("com.teamandappers.delhitourism.Kalindi");
					startActivity(i);
				} else if (arg2 == 7) {
					Intent i = new Intent("com.teamandappers.delhitourism.Kalka");
					startActivity(i);
				} else if (arg2 == 8) {
					Intent i = new Intent("com.teamandappers.delhitourism.Lodhi");
					startActivity(i);
				} else if (arg2 == 9) {
					Intent i = new Intent("com.teamandappers.delhitourism.Mill");
					startActivity(i);
				} else if (arg2 == 10) {
					Intent i = new Intent("com.teamandappers.delhitourism.Mughal");
					startActivity(i);
				} else if (arg2 == 11) {
					Intent i = new Intent("com.teamandappers.delhitourism.Nehru");
					startActivity(i);
				} else if (arg2 == 12) {
					Intent i = new Intent("com.teamandappers.delhitourism.Sultan");
					startActivity(i);
				} else {
					Intent i = new Intent("com.teamandappers.delhitourism.Zoo");
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
