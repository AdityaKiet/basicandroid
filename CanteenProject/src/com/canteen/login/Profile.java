package com.canteen.login;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.andappers.canteenproject.R;

public class Profile extends Activity {
	Bundle basket;
	String name, account_number, roll_no, balance, last_recharge;
	TextView tv;
	SharedPreferences sp;
	SharedPreferences.Editor edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		tv = (TextView) findViewById(R.id.test);
		sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		edit = sp.edit();
		basket = getIntent().getExtras();
		if (basket != null) {
			name = basket.getString("name");
			account_number = basket.getString("account_number");
			roll_no = basket.getString("roll_no");
			balance = basket.getString("balance");
		} else {
			name = sp.getString("name", null);
			account_number = sp.getString("account_number", null);
			roll_no = sp.getString("roll_no", null);
			balance = sp.getString("balance", null);
		}

		tv.setText(name + account_number + roll_no + balance);
	}

}
