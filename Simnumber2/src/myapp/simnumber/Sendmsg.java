package myapp.simnumber;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Sendmsg extends Activity implements OnClickListener {

	EditText p1 = null, p2 = null, p3 = null;
	String ph1, ph2, ph3;
	Button submit;
	TextView show;
	SharedPreferences sp = null;
	SharedPreferences.Editor edit = null;
	String temp1 = null;
	String temp2 = null;
	String temp3 = null;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.dataentry);
		variables();
		sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		edit = sp.edit();
		temp1 = sp.getString("sim1", null);
		temp2 = sp.getString("sim2", null);
		temp3 = sp.getString("sim3", null);

		if ((temp1 != null && !temp1.equals(""))
				|| (temp2 != null && !temp2.equals(""))
				|| (temp3 != null && !temp3.equals(""))) {
			show.setText("Current Numbers are : " + sp.getString("sim1", null)
					+ "\t" + sp.getString("sim2", null) + "\t"
					+ sp.getString("sim3", null));
			p1.setText(temp1);
			p2.setText(temp2);
			p3.setText(temp3);
		} else
			show.setText("Not Any Number Selected");

		submit.setOnClickListener(this);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.Reset:
			resetItems();
			break;
		case R.id.Exit:
			finish();
			break;
		}
		return false;
	}

	private void resetItems() {
		p1.setText(null);
		p2.setText(null);
		p3.setText(null);
		temp1 = null;
		temp2 = null;
		temp3 = null;
		ph1 = null;
		ph2 = null;
		ph3 = null;
		edit.putString("sim1", null).commit();
		edit.putString("sim2", null).commit();
		edit.putString("sim3", null).commit();
		show.setText("Not Any Number Selected");

	}

	private void variables() {
		p1 = (EditText) findViewById(R.id.phone1);
		p2 = (EditText) findViewById(R.id.phone2);
		p3 = (EditText) findViewById(R.id.phone3);
		submit = (Button) findViewById(R.id.submit);
		show = (TextView) findViewById(R.id.show);
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.submit:
			doAct();
			break;

		}
	}

	private void doAct() {

		ph1 = p1.getText().toString();
		ph2 = p2.getText().toString();
		ph3 = p3.getText().toString();
		edit.putString("sim1", null).commit();
		edit.putString("sim2", null).commit();
		edit.putString("sim3", null).commit();
		if ((ph1 != null && !ph1.equals(""))
				|| (ph2 != null && !ph2.equals(""))
				|| (ph3 != null && !ph3.equals(""))) {
			show.setText("Number Have been saved !!!");
			edit.putString("sim1", ph1).commit();
			edit.putString("sim2", ph2).commit();
			edit.putString("sim3", ph3).commit();
		}

	}

}
