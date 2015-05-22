package com.kiet.lenden;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.kei.lenden.R;
import com.kiet.dto.CustomAdapter;
import com.kiet.dto.PersonDTO;

public class ViewData extends Activity implements OnClickListener {
	EditText name;
	String[] array;
	Button search;
	PersonDTO personDTO = new PersonDTO();
	ListView listView;
	List<PersonDTO> list = new ArrayList<PersonDTO>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view);
		listView = (ListView) findViewById(R.id.listview);

		name = (EditText) findViewById(R.id.searchByName);
		search = (Button) findViewById(R.id.viewByName);
		search.setOnClickListener(this);
		setList();

	}

	private void setList() {
		DBActivity db = new DBActivity(this);
		db.open();
		list = db.getArrayList();
		db.close();
		// List<String> nameslist = new ArrayList<>();

		int length = list.size();
		array = new String[length];
		for (int i = 0; i < length; i++) {

			array[i] = list.get(i).getName();
		}
		// array = new String[nameslist.size()];

		/*
		 * ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, array);
		 */
		CustomAdapter adapter = new CustomAdapter(ViewData.this, array);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				PersonDTO obj = list.get(position);
				String[] temp = { "Name :  " + obj.getName(),
						"Id   :  " + Integer.toString(obj.getId()),
						"Give :  " + Integer.toString(obj.getGiveAmount()),
						"Take :  " + Integer.toString(obj.getTakeAmount()) };
				new AlertDialog.Builder(ViewData.this).setTitle(obj.getName())
						.setItems(temp, new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
							}
						}).show();
			}
		});

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.viewByName:
			DBActivity db = new DBActivity(ViewData.this);
			db.open();
			if (name.getText().toString() != null
					|| !name.getText().toString().trim().equals("")) {
				personDTO = db.getPersonData(name.getText().toString());

			} else {
				new AlertDialog.Builder(this).setTitle("Not Found!!")
						.setMessage("Entered Name Could Not Be found..")
						.setCancelable(true).setPositiveButton("OK !!", null)
						.show();

			}

			if (personDTO != null) {
				String[] temp = {
						"Name :  " + personDTO.getName(),
						"Id   :  " + Integer.toString(personDTO.getId()),
						"Give :  "
								+ Integer.toString(personDTO.getGiveAmount()),
						"Take :  "
								+ Integer.toString(personDTO.getTakeAmount()) };
				new AlertDialog.Builder(this).setTitle(personDTO.getName())
						.setItems(temp, new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface arg0, int arg1) {

							}
						}).setPositiveButton("OK !!", null).show();
			} else {
				new AlertDialog.Builder(this).setTitle("Not Found!!")
						.setMessage("Entered Name Could Not Be found..")
						.setCancelable(false).setPositiveButton("OK !!", null)
						.show();
			}
			break;
		}

	}
}
