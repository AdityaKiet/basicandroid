package com.teamandappers.delhitourism;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Mainlist extends Activity {
	ListView listview;
	String[] web = { "Monuments", "Parks", "Museums", "Pubs & Discotheques",
			"Eating Joints" };
	Integer[] ids = { R.drawable.monumnt, R.drawable.parkss, R.drawable.museum,
			R.drawable.pubs, R.drawable.eating };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listmain);
		listview = (ListView) findViewById(R.id.listView1);
		AdapterC adapter = new AdapterC(Mainlist.this, web, ids);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 0) {
					Intent i = new Intent(
							"com.teamandappers.delhitourism.Monumentlist");
					startActivity(i);
				} else if (arg2 == 1) {
					Intent i = new Intent("com.teamandappers.delhitourism.Parkslist");
					startActivity(i);
				} else if (arg2 == 2) {
					Intent i = new Intent("com.teamandappers.delhitourism.MuseumList");
					startActivity(i);
				} else if (arg2 == 3) {
					Intent i = new Intent("com.teamandappers.delhitourism.PubsDisc");
					startActivity(i);
				} else {
					Intent i = new Intent(
							"com.teamandappers.delhitourism.Eatingjoints");
					startActivity(i);
				}

			}
		});

	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setMessage("Are you sure you want to exit?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
							}
						}).setNegativeButton("No", null).show();
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
