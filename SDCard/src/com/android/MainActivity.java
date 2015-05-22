package com.android;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sdcard.R;

public class MainActivity extends Activity implements OnClickListener {
	Button write, clear, read, close;
	EditText et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		write = (Button) findViewById(R.id.btnWriteSDFile);
		clear = (Button) findViewById(R.id.btnClearScreen);
		read = (Button) findViewById(R.id.btnReadSDFile);
		close = (Button) findViewById(R.id.btnClose);
		et = (EditText) findViewById(R.id.txtData);
		write.setOnClickListener(this);
		clear.setOnClickListener(this);
		read.setOnClickListener(this);
		close.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnWriteSDFile:
			try {
				File file = new File("/sdcard/myfile.txt");
				file.createNewFile();
				FileOutputStream stream = new FileOutputStream(file);
				OutputStreamWriter writer = new OutputStreamWriter(stream);
				writer.append(et.getText().toString());
				writer.close();
				stream.close();
				Toast.makeText(getBaseContext(),
						"Done writing SD 'mysdfile.txt'", Toast.LENGTH_SHORT)
						.show();
			} catch (Exception e) {
				Toast.makeText(getBaseContext(), e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btnReadSDFile:
			try {
				File file = new File("/sdcard/myfile.txt");
				FileInputStream stream = new FileInputStream(file);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(stream));
				String row = "";
				String buffer = "";
				while ((row = reader.readLine()) != null) {
					buffer += row;
				}
				et.setText(buffer);
				reader.close();
				Toast.makeText(getBaseContext(),
						"Done reading SD 'mysdfile.txt'", Toast.LENGTH_SHORT)
						.show();
			} catch (Exception e) {
				Toast.makeText(getBaseContext(), e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btnClearScreen:
			et.setText("");
			break;
		case R.id.btnClose:
			finish();
			break;
		}
	}
}
