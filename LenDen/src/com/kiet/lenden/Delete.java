package com.kiet.lenden;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.kei.lenden.R;
import com.kiet.dto.CustomAdapter;
import com.kiet.dto.PersonDTO;

public class Delete extends Activity {
	String[] array;
	List<PersonDTO> list = new ArrayList<PersonDTO>();
	ListView listView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete);
		listView = (ListView) findViewById(R.id.listdelete);
		setList();
	}

	private void setList() {

		DBActivity db = new DBActivity(this);
		db.open();
		list = db.getArrayList();
		db.close();
		int length = list.size();
		array = new String[length];
		for (int i = 0; i < length; i++) {

			array[i] = list.get(i).getName();
		}

		CustomAdapter adapter = new CustomAdapter(Delete.this, array);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				DBActivity db = new DBActivity(Delete.this);
				db.open();
				list = db.getArrayList();
				db.close();
				final PersonDTO obj = list.get(position);

				String[] temp = { "Name :  " + obj.getName(),
						"Id   :  " + Integer.toString(obj.getId()),
						"Give :  " + Integer.toString(obj.getGiveAmount()),
						"Take :  " + Integer.toString(obj.getTakeAmount()) };
				new AlertDialog.Builder(Delete.this)
						.setTitle(obj.getName())
						.setPositiveButton("Delete",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0,
											int arg1) {
										DBActivity db = new DBActivity(
												Delete.this);
										db.open();
										db.deleteEntry(obj.getName());

										db.close();
										Toast.makeText(getApplicationContext(),
												obj.getName() + "deleted",
												Toast.LENGTH_LONG).show();
										Delete.this.finish();
										Intent intent = new Intent(
												"com.kiet.lenden.Delete");
										startActivity(intent);
									}
								}).setNegativeButton("Cancel", null)
						.setItems(temp, new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
							}
						}).show();
			}
		});

	}

}
