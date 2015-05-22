package com.example.mycalculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnsum,
			btnmul, btndiv, btnsub, btnmod, btnreset, btnequal, pt;
	TextView tv;
	String strn = "", str = "";
	float num, num1, num2;
	char op = 'a';
	boolean flag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		btn4 = (Button) findViewById(R.id.button4);
		btn5 = (Button) findViewById(R.id.button5);
		btn6 = (Button) findViewById(R.id.button6);
		btn7 = (Button) findViewById(R.id.button7);
		btn8 = (Button) findViewById(R.id.button8);
		btn9 = (Button) findViewById(R.id.button9);
		btn0 = (Button) findViewById(R.id.button0);
		btnsum = (Button) findViewById(R.id.buttonsm);
		btnsub = (Button) findViewById(R.id.buttonsb);
		btnmul = (Button) findViewById(R.id.buttonml);
		btndiv = (Button) findViewById(R.id.buttondv);
		btnmod = (Button) findViewById(R.id.buttonmd);
		btnreset = (Button) findViewById(R.id.buttonrs);
		btnequal = (Button) findViewById(R.id.equal);
		pt = (Button) findViewById(R.id.btnpt);

		tv = (TextView) findViewById(R.id.textView1);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		btn9.setOnClickListener(this);
		btn0.setOnClickListener(this);
		btnsum.setOnClickListener(this);
		btnsub.setOnClickListener(this);
		btnmul.setOnClickListener(this);
		btndiv.setOnClickListener(this);
		btnmod.setOnClickListener(this);
		btnreset.setOnClickListener(this);
		btnequal.setOnClickListener(this);
		pt.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button0:
			Log.d("log", "working");
			insert(0);
			break;
		case R.id.button1:
			insert(1);
			break;
		case R.id.button2:
			insert(2);
			break;
		case R.id.button3:
			insert(3);
			break;
		case R.id.button4:
			insert(4);
			break;
		case R.id.button5:
			insert(5);
			break;
		case R.id.button6:
			insert(6);
			break;
		case R.id.button7:
			insert(7);
			break;
		case R.id.button8:
			insert(8);
			break;
		case R.id.button9:
			insert(9);
			break;
		case R.id.buttonsm:
			perform();
			op = '+';
			break;
		case R.id.buttonsb:
			perform();
			op = '-';
			break;
		case R.id.buttonml:
			perform();
			op = '*';
			break;
		case R.id.buttondv:
			perform();
			op = '/';
			break;
		case R.id.buttonmd:
			perform();
			op = '%';
			break;
		case R.id.equal:
			try {
				if (op == '+')
					eqs();
				else if (op == '-')
					eqsb();
				else if (op == '*')
					eqm();
				else if (op == '/')
					eqd();
				else if (op == '%')
					eqdv();
			} catch (Exception e) {
				tv.setText("enter only integer number");
				return;
			}

			break;

		case R.id.buttonrs:
			reset();
			break;
		case R.id.btnpt:
			reset();
		default:
			break;

		}

	}

	private void reset() {
		str = "";
		tv.setText("");
	}

	private void perform() {

		if (op == '+') {

			tv.setText("+");
		} else if (op == '-') {
			tv.setText("-");
			num = 0;
		} else if (op == '*') {
			tv.setText("*");
			num = 0;
		} else if (op == '/') {
			tv.setText("/");
			num = 0;
		} else if (op == '%') {
			tv.setText("%");
			num = 0;
		}
	}

	private void eqs() {

		try {
			if (!flag) {
				flag = !flag;
				num1 = Integer.parseInt(tv.getText().toString());
				Log.d("num1", "" + num1);
			} else {
				num2 = Integer.parseInt(tv.getText().toString());
				Log.d("num2", "" + num2);
			}
		} catch (Exception e) {
			tv.setText("enter number");
		}
	}

	private void eqsb() {
		try {
			num = num - num1;
			tv.setText("SUBTRACTION=" + num);
		} catch (Exception e) {
			tv.setText("plz enter a number");
		}
	}

	private void eqm() {
		try {
			num = num * num1;
			tv.setText("MULTIPLICATION=" + num);
		} catch (Exception e) {
			tv.setText("enter number");
		}
	}

	private void eqd() {
		try {
			num = num / num1;
			tv.setText("DIVISION=" + num);
		} catch (Exception e) {
			tv.setText("enter number");
		}
	}

	private void eqdv() {
		try {
			num = num % num1;
			tv.setText("MODULUS=" + num);
		} catch (Exception e) {
			tv.setText("enter number");
		}
	}

	private void insert(int j) {

		strn = tv.getText().toString();
		strn = strn + j;
		if (strn != null) {
			tv.setText(strn);
		}
	}

}
