package com.kiet.lenden;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kei.lenden.R;
import com.kiet.dto.PersonDTO;

public class UpdateInsert extends Activity implements OnClickListener {
	EditText name, giveamount, takeamount;
	Button submit, cancel;
	String title = "Sucess!", message = "New Person Added";
	String nametext = null;
	TextView id;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updateinsert);
		variables();
		Bundle basket = getIntent().getExtras();
		nametext = basket.getString("key");
		DBActivity db = new DBActivity(this);
		db.open();
		PersonDTO obj = new PersonDTO();
		obj = db.getPersonData(nametext);
		db.close();
		name.setText(nametext);
		id.setText("Contact Id : " + Integer.toString(obj.getId()));
		giveamount.setText(Integer.toString(obj.getGiveAmount()));
		takeamount.setText(Integer.toString(obj.getTakeAmount()));
		submit.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	private void variables() {
		id = (TextView) findViewById(R.id.idDisplay);
		name = (EditText) findViewById(R.id.nameupdate);
		giveamount = (EditText) findViewById(R.id.givemoneyupdate);
		takeamount = (EditText) findViewById(R.id.takemoneyupdate);
		submit = (Button) findViewById(R.id.submitupdate);
		cancel = (Button) findViewById(R.id.cancelupdate);
	}

	@SuppressWarnings("null")
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.submitupdate:
			DBActivity db = new DBActivity(this);
			db.open();
			String give = giveamount.getText().toString();
			String take = takeamount.getText().toString();
			if ((give == null || give.trim().equals(""))
					&& (take == null || take.trim().equals(""))) {
				db.updateData(nametext, "0", "0");

			} else if ((give != null || !give.trim().equals(""))
					&& (take == null || take.trim().equals(""))) {
				db.updateData(nametext, give, "0");

			} else if ((give == null || give.trim().equals(""))
					&& (take != null || !take.trim().equals(""))) {
				db.updateData(nametext, "0", take);

			} else {
				db.updateData(nametext, give, take);
			}
			this.finish();

			db.close();
			break;
		case R.id.cancelupdate:
			this.finish();
			break;
		}
	}

}
