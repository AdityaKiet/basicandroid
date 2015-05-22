package com.kiet.db;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dbdemo.R;

public class MainActivity extends Activity {
	Button del, insert, view;
	EditText ins, delete;
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		del = (Button) findViewById(R.id.btndel);
		insert = (Button) findViewById(R.id.btnIns);
		view = (Button) findViewById(R.id.show);
		ins = (EditText) findViewById(R.id.insert);
		delete = (EditText) findViewById(R.id.del);
		tv = (TextView) findViewById(R.id.textView1);
		del.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DBActivity db = new DBActivity(MainActivity.this);
				db.open();
				db.deleteDB(delete.getText().toString());
				db.close();
			}
		});
		insert.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DBActivity db = new DBActivity(MainActivity.this);
				db.open();
				db.insertDB(ins.getText().toString());
				db.close();
			}
		});
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DBActivity db = new DBActivity(MainActivity.this);
				db.open();
				ArrayList<String> array = new ArrayList<>();
				array = db.getData();
				tv.setText(array.toString());
				db.close();
			}
		});
	}

}
