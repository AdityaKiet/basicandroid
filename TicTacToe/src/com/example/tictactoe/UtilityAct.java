package com.example.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class UtilityAct extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.utility);
		Intent intent = new Intent("com.example.tictactoe.MainActivity");
		startActivity(intent);
		finish();
	}

}
