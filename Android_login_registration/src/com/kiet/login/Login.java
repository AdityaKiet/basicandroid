package com.kiet.login;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kiet.android_login_registration.R;

public class Login extends Activity implements OnClickListener {
	EditText id, password;
	Button button;
	InputStream is;
	HttpEntity entity;
	TextView tv;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		id = (EditText) findViewById(R.id.etLoginId);
		password = (EditText) findViewById(R.id.etLoginPassword);
		button = (Button) findViewById(R.id.btnLogin);
		tv = (TextView) findViewById(R.id.txtDisplay);
		button.setOnClickListener(this);
	}

	class Task extends AsyncTask<String, String, Void> {
		private ProgressDialog progressDialog = new ProgressDialog(Login.this);
		String result = "";

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
			List<NameValuePair> list = new ArrayList<NameValuePair>(1);
			list.add(new BasicNameValuePair("id", id_string));
			list.add(new BasicNameValuePair("password", pass_string));
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://10.0.2.2/androidproject/login.php");
				httpPost.setEntity(new UrlEncodedFormEntity(list));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				entity = httpResponse.getEntity();
				is = entity.getContent();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Exception raised " + e.getMessage(), Toast.LENGTH_LONG)
						.show();
			} catch (java.lang.RuntimeException e) {
				Toast.makeText(getApplicationContext(),
						"Database not found. " + e.getMessage(),
						Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Exception raised " + e.getMessage(), Toast.LENGTH_LONG)
						.show();
			}
			try {
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
			Log.d("log", result);
			if (result.equals("false")) {
				tv.setText("Wrong User Id and Password..");
			}
			
			else {
				try {
					JSONArray jsonArray = new JSONArray(result);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = null;
						jsonObject = jsonArray.getJSONObject(i);
						String id = jsonObject.getString("id");
						String password = jsonObject.getString("password");
						String name = jsonObject.getString("Name");
						tv.setText("Id is " + id + " and Password is "
								+ password + "Name is " + name);
					}
				} catch (JSONException e) {
					Toast.makeText(getApplicationContext(),
							"Exception caused is " + e.getMessage(),
							Toast.LENGTH_LONG).show();
				}
			}

			super.onPostExecute(v);
		}

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnLogin) {
			if (isNetworkAvailable()) {
				new Task().execute();
			} else {
				Toast.makeText(getApplicationContext(),
						"Internet not available", Toast.LENGTH_LONG).show();
			}
		} else if (v.getId() == R.id.registerScreen) {
			Intent intent = new Intent("com.kiet.login.MainActivity");
			startActivity(intent);
			finish();
		}
	}

}
