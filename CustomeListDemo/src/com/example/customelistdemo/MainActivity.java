package com.example.customelistdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ListView list;
	String[] web = { "Google Plus", "Twitter", "Windows", "Bing", "Itunes",
			"Wordpress"

	};
	Integer[] imageId = { R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		CustomList adapter = new CustomList(MainActivity.this, web, imageId);
		list = (ListView) findViewById(R.id.listView1);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					Toast.makeText(MainActivity.this,
							"You Clicked at Google Plus", Toast.LENGTH_SHORT)
							.show();
				} else
					Toast.makeText(MainActivity.this,
							"You Clicked at " + web[+position],
							Toast.LENGTH_SHORT).show();
			}
		});
	}
}