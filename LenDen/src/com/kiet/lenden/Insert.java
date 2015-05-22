package com.kiet.lenden;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.kei.lenden.R;

public class Insert extends Activity implements OnClickListener {
	EditText name, giveamount, takeamount;
	Button submit, reset;
	String title = "Sucess!", message = "New Person Added";
	boolean isexists;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insert);
		variables();
		submit.setOnClickListener(this);
		reset.setOnClickListener(this);
	}

	private void variables() {
		name = (EditText) findViewById(R.id.name);
		giveamount = (EditText) findViewById(R.id.givemoney);
		takeamount = (EditText) findViewById(R.id.takemoney);
		submit = (Button) findViewById(R.id.submit);
		reset = (Button) findViewById(R.id.reset);
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.submit:
			addToDataBase();
			break;
		case R.id.reset:
			name.setText(null);
			giveamount.setText(null);
			takeamount.setText(null);
			break;
		}
	}

	private void addToDataBase() {
		try {
			DBActivity obj = new DBActivity(Insert.this);
			obj.open();
			if (name.getText().toString() != null
					&& !name.getText().toString().trim().equals("")) {
				if ((giveamount.getText().toString() == null || giveamount
						.getText().toString().trim().equals(""))
						&& (takeamount.getText().toString() == null || takeamount
								.getText().toString().trim().equals(""))) {
					isexists = obj
							.createEntery(name.getText().toString(), 0, 0);
					if (isexists) {
						title = "Failure";
						message = "Name Already Exists";
					} else {
						name.setText("");
						giveamount.setText("");
						takeamount.setText("");
						title = "Success";
						message = name.getText().toString()
								+ " Added to Database";
					}
				} else if (takeamount.getText().toString() == null
						|| takeamount.getText().toString().trim().equals("")) {
					isexists = obj.createEntery(name.getText().toString(),
							Integer.parseInt(giveamount.getText().toString()),
							0);
					if (isexists) {
						title = "Failure";
						message = "Name Already Exists";
					} else {
						name.setText("");
						giveamount.setText("");
						takeamount.setText("");
						title = "Success";
						message = name.getText().toString()
								+ " Added to Database";
					}
				} else if (giveamount.getText().toString() == null
						|| giveamount.getText().toString().trim().equals("")) {
					isexists = obj.createEntery(name.getText().toString(), 0,
							Integer.parseInt(takeamount.getText().toString()));
					if (isexists) {
						title = "Failure";
						message = "Name Already Exists";
					} else {
						name.setText("");
						giveamount.setText("");
						takeamount.setText("");
						title = "Success";
						message = name.getText().toString()
								+ " Added to Database";
					}
				} else {

					isexists = obj.createEntery(name.getText().toString(),
							Integer.parseInt(giveamount.getText().toString()),
							Integer.parseInt(takeamount.getText().toString()));
					if (isexists) {
						title = "Failure";
						message = "Name Already Exists";
					} else {
						name.setText("");
						giveamount.setText("");
						takeamount.setText("");
						title = "Success";
						message = name.getText().toString() + " Person Added";
					}
				}

			} else {
				title = "No data found !!";
				message = "Please give name atleast";
			}

		} catch (Exception e) {
			title = "Failure";
			message = "Not added...Please try later..!!!!";
		} finally {
			new AlertDialog.Builder(this).setTitle(title).setMessage(message)
					.setCancelable(false).setPositiveButton("OK !!", null)
					.show();

		}
	}
}
