package com.canteen.login;

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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.andappers.canteenproject.R;
import com.canteen.dto.ItemDTO;
import com.canteen.global.AllClassItems;

public class Splash extends Activity {
	SharedPreferences sp;
	SharedPreferences.Editor edit;
	boolean isLogin;
	InputStream is;
	HttpEntity entity;

	@SuppressLint("CommitPrefEdits")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		ArrayList<ItemDTO> boughtItems = new ArrayList<>();
		((AllClassItems) getApplicationContext()).setItemList(boughtItems);
		ArrayList<Bitmap> images = new ArrayList<>();
		((AllClassItems) getApplicationContext()).setImages(images);
		sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		edit = sp.edit();
		isLogin = sp.getBoolean("isLogin", false);
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {

					if (!isLogin) {
						Intent intent = new Intent(
								"com.canteen.login.MainActivity");
						startActivity(intent);
					} else {
						new GetData().execute();
						Intent intent = new Intent(
								"com.canteen.profile.MainActivity");
						startActivity(intent);
					}

				}
			}
		};
		if (isNetworkAvailable()) {
			timer.start();
		} else {
			new AlertDialog.Builder(Splash.this)
					.setTitle("Alert !!!")
					.setMessage("Network not available")
					.setPositiveButton("Retry!",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent(
											Intent.ACTION_MAIN);
									intent.setClassName("com.android.phone",
											"com.android.phone.NetworkSetting");
									startActivity(intent);
									finish();
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Splash.this.finish();
								}
							}).show();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		finish();
	}

	@Override
	public void onBackPressed() {

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	class GetData extends AsyncTask<String, String, Void> {
		// private ProgressDialog progressDialog = new
		// ProgressDialog(Splash.this);
		String result = "";

		protected void onPreExecute() {
			/*
			 * progressDialog.setTitle("Loading.....");
			 * progressDialog.setMessage("Loading....."); progressDialog.show();
			 * progressDialog.setOnCancelListener(new OnCancelListener() {
			 * 
			 * @Override public void onCancel(DialogInterface arg0) {
			 * GetData.this.cancel(true); } });
			 */
			super.onPreExecute();
		}

		protected Void doInBackground(String... params) {
			List<NameValuePair> list = new ArrayList<NameValuePair>(1);
			list.add(new BasicNameValuePair("account_number", sp.getString(
					"account_number", null)));
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://192.168.51.103:80/canteen/profile.php");
				httpPost.setEntity(new UrlEncodedFormEntity(list));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				Log.d("log", httpResponse.toString());
				entity = httpResponse.getEntity();
				Log.d("log", entity.toString());
				is = entity.getContent();
				Log.d("log", is.toString());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Exception raised " + e.getMessage(), Toast.LENGTH_LONG)
						.show();
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
			Log.d("testrerslut", result);

			try {
				JSONArray jsonArray = new JSONArray(result);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = null;
					jsonObject = jsonArray.getJSONObject(i);
					// progressDialog.dismiss();
					edit.putString("email", jsonObject.getString("email"));
					edit.putString("password", jsonObject.getString("password"))
							.commit();
					edit.putString("name", jsonObject.getString("name").trim())
							.commit();
					edit.putString("account_number",
							jsonObject.getString("account_number")).commit();
					edit.putString("balance", jsonObject.getString("balance"))
							.commit();
					edit.putString("roll_no", jsonObject.getString("roll_no"))
							.commit();
					edit.putString("phoneno", jsonObject.getString("phoneno"))
							.commit();

				}
			} catch (JSONException e) {
				// progressDialog.dismiss();
				Toast.makeText(getApplicationContext(),
						"Exception caused is " + e.getMessage(),
						Toast.LENGTH_LONG).show();
			}
			Log.d("done", "done");
			super.onPostExecute(v);
		}

	}
}