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

import com.kei.lenden.R;
import com.kiet.dto.CustomAdapter;
import com.kiet.dto.PersonDTO;

public class Update extends Activity {
	String[] array;
	List<PersonDTO> list = new ArrayList<PersonDTO>();
	ListView listView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		listView = (ListView) findViewById(R.id.list);
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

		/*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, array);*/
		CustomAdapter adapter = new CustomAdapter(Update.this, array);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				DBActivity db = new DBActivity(Update.this);
				db.open();
				list = db.getArrayList();
				db.close();
				final PersonDTO obj = list.get(position);

				String[] temp = { "Name :  " + obj.getName(),
						"Id   :  " + Integer.toString(obj.getId()),
						"Give :  " + Integer.toString(obj.getGiveAmount()),
						"Take :  " + Integer.toString(obj.getTakeAmount()) };
				new AlertDialog.Builder(Update.this)
						.setTitle(obj.getName())
						.setPositiveButton("Update",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										Bundle basket = new Bundle();
										basket.putString("key", obj.getName());
										Intent intent = new Intent(Update.this,
												UpdateInsert.class);
										intent.putExtras(basket);
										startActivity(intent);
									}
								}).setNegativeButton("Cancel", null)
						.setItems(temp, new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
								System.out.println("");
							}
						}).show();
			}
		});

	}
}
