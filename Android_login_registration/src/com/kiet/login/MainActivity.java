package com.kiet.login;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kiet.android_login_registration.R;

public class MainActivity extends Activity implements OnClickListener {
	EditText id, password, name;
	Button button, buttonLoginScreen;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		id = (EditText) findViewById(R.id.etId);
		password = (EditText) findViewById(R.id.etPass);
		name = (EditText) findViewById(R.id.etName);
		button = (Button) findViewById(R.id.btn);
		buttonLoginScreen = (Button) findViewById(R.id.btnLoginScreen);
		button.setOnClickListener(this);
		buttonLoginScreen.setOnClickListener(this);
	}

	class Task extends AsyncTask<String, String, Void> {
		private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
		String result = "";
		InputStream is;
		HttpEntity entity;
		
		protected void onPreExecute() {
			progressDialog.setTitle("Loading.....");
			progressDialog.setMessage("Loading.....");
			progressDialog.show();
			progressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface arg0) {
					Task.this.cancel(true);
				}
			});
			super.onPreExecute();
		}

		protected Void doInBackground(String... params) {
			String id_string = id.getText().toString().trim();
			String pass_string = password.getText().toString().trim();
			String name_string = name.getText().toString().trim();
			
			List<NameValuePair> list = new ArrayList<NameValuePair>(1);
			
			list.add(new BasicNameValuePair("id", id_string));
			list.add(new BasicNameValuePair("password", pass_string));
			list.add(new BasicNameValuePair("name", name_string));
			
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost("http://10.0.2.2/androidproject/android.php");
				httpPost.setEntity(new UrlEncodedFormEntity(list));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				entity = httpResponse.getEntity();
				is = entity.getContent();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(is));
				StringBuilder stringBuilder = new StringBuilder();
				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line);
				}
				is.close();
				result = stringBuilder.toString();

			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "error 2",
						Toast.LENGTH_LONG).show();
			}
			return null;
		}

		protected void onPostExecute(Void v) {
			progressDialog.dismiss();
			if (result.equals("true"))
				Toast.makeText(getApplicationContext(),"You Have Been Registered Successfully",Toast.LENGTH_LONG).show();
			else if (result.equals("false"))
				Toast.makeText(getApplicationContext(),"Id has already being used.. Please use another Id.",Toast.LENGTH_LONG).show();
			else
				Toast.makeText(getApplicationContext(),"Registration not completed due to network error...try again later.. ",Toast.LENGTH_LONG).show();
			super.onPostExecute(v);
		}

	}



	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn) {
			Task task = new Task();
			task.execute();
		} else if (v.getId() == R.id.btnLoginScreen) {
			Intent intent = new Intent("com.kiet.login.Login");
			startActivity(intent);
			finish();
		}
	}
}
