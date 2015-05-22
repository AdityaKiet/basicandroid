package com.canteen.profile.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andappers.canteenproject.R;

public class ProfileFragment extends Fragment {
	private LinearLayout linearLayout;
	public Bundle basket;
	String name, account_number, roll_no, balance, email, phoneno;
	SharedPreferences sp;
	SharedPreferences.Editor edit;
	TextView txtName, txtAccount, txtBalance, txtEmail, txtPhone, txtRollno;

	public ProfileFragment(Bundle basket) {
		this.basket = basket;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		linearLayout = (LinearLayout) inflater.inflate(R.layout.profile,
				container, false);

		txtAccount = (TextView) linearLayout.findViewById(R.id.txtAccountNo);
		txtBalance = (TextView) linearLayout.findViewById(R.id.txtBalance);
		txtEmail = (TextView) linearLayout.findViewById(R.id.txtEmail);
		txtPhone = (TextView) linearLayout.findViewById(R.id.txtPhone);
		txtRollno = (TextView) linearLayout.findViewById(R.id.txtRollno);
		txtName = (TextView) linearLayout.findViewById(R.id.txtName);
		sp = PreferenceManager.getDefaultSharedPreferences(getActivity()
				.getBaseContext());
		edit = sp.edit();
		if (basket != null) {
			name = basket.getString("name");
			account_number = basket.getString("account_number");
			roll_no = basket.getString("roll_no");
			balance = basket.getString("balance");
			email = basket.getString("email");
			phoneno = basket.getString("phoneno");
		} else {
			name = sp.getString("name", null);
			account_number = sp.getString("account_number", null);
			roll_no = sp.getString("roll_no", null);
			balance = sp.getString("balance", null);
			email = sp.getString("email", null);
			phoneno = sp.getString("phoneno", null);

		}
		txtAccount.setText(account_number);
		txtBalance.setText(balance);
		txtEmail.setText(email);
		txtName.setText(name);
		txtPhone.setText(phoneno);
		txtRollno.setText(roll_no);
		return linearLayout;
	}

}
