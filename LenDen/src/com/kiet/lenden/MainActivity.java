package com.kiet.lenden;

import com.kei.lenden.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	Button insert, update, view, delete;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		variables();
		insert.setOnClickListener(this);
		update.setOnClickListener(this);
		view.setOnClickListener(this);
		delete.setOnClickListener(this);
	}

	private void variables() {
		insert = (Button) findViewById(R.id.insert);
		update = (Button) findViewById(R.id.update);
		view = (Button) findViewById(R.id.view);
		delete = (Button) findViewById(R.id.delete);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.delete:
			Intent intent3 = new Intent("com.kiet.lenden.Delete");
			startActivity(intent3);
			break;
		case R.id.insert:
			Intent intent = new Intent("com.kiet.lenden.Insert");
			startActivity(intent);
			break;
		case R.id.update:
			Intent intent2 = new Intent("com.kiet.lenden.Update");
			startActivity(intent2);
			break;
		case R.id.view:
			Intent intent1 = new Intent("com.kiet.lenden.ViewData");
			startActivity(intent1);
			break;
		}
	}

}
