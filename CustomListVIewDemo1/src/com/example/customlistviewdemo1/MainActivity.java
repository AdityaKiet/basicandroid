package com.example.customlistviewdemo1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ListView listview;
	String[] web = { "Facebook", "Twitter", "Google Plus", "Bing" };
	Integer[] ids = { R.drawable.clocl, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listview = (ListView) findViewById(R.id.listView1);
		CustomAdapter adapter = new CustomAdapter(MainActivity.this, web, ids);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(MainActivity.this, web[arg2],
						Toast.LENGTH_LONG).show();

			}
		});
	}

}
