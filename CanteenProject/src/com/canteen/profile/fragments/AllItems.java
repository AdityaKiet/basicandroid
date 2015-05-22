package com.canteen.profile.fragments;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andappers.canteenproject.R;
import com.canteen.util.CustomList;

public class AllItems extends Fragment {
	InputStream is;
	HttpEntity entity;
	private LinearLayout linearLayout;
	public Bundle basket;
	String name, account_number, roll_no, balance;
	SharedPreferences sp;
	SharedPreferences.Editor edit;
	TextView tv;
	private ArrayList<String> itemNames;
	private ListView itemsList;
	private ArrayList<Integer> prices;
	Integer[] listImages = { R.drawable.i0, R.drawable.i1, R.drawable.i2,
			R.drawable.i3, R.drawable.i4 };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		linearLayout = (LinearLayout) inflater.inflate(R.layout.allitems,
				container, false);
		tv = (TextView) linearLayout.findViewById(R.id.testText);
		sp = PreferenceManager.getDefaultSharedPreferences(getActivity()
				.getBaseContext());
		edit = sp.edit();
		itemsList = (ListView) linearLayout.findViewById(R.id.listAllItems);
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
		new Task().execute();
		return linearLayout;
	}

	class Task extends AsyncTask<String, String, Void> {
		private ProgressDialog progressDialog = new ProgressDialog(
				getActivity());
		String result = "";

		protected void onPreExecute() {

			progressDialog.setTitle("Loading.....");
			progressDialog.setMessage("Please Wait.....");
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
			List<NameValuePair> list = new ArrayList<NameValuePair>(1);
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://192.168.51.103:80/canteen/allitems.php");
				httpPost.setEntity(new UrlEncodedFormEntity(list));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				Log.d("log", httpResponse.toString());
				entity = httpResponse.getEntity();
				Log.d("log", entity.toString());
				is = entity.getContent();
				Log.d("log", is.toString());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				Toast.makeText(getActivity().getApplicationContext(),
						"Exception raised " + e.getMessage(), Toast.LENGTH_LONG)
						.show();
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getActivity().getApplicationContext(),
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
				Toast.makeText(getActivity().getApplicationContext(),
						"error 2", Toast.LENGTH_LONG).show();
			}
			return null;
		}

		protected void onPostExecute(Void v) {
			progressDialog.dismiss();
			Log.d("aditya", result);
			if (result.equals("false")) {
				new AlertDialog.Builder(getActivity())
						.setMessage("Some Problem occures try again later..")
						.setTitle("Alert !!").setCancelable(true)
						.setIcon(R.drawable.ic_launcher)
						.setNegativeButton("Okay", null).show();
			} else {
				itemNames = new ArrayList<>();
				prices = new ArrayList<>();
				try {

					JSONArray jsonArray = new JSONArray(result);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = null;
						jsonObject = jsonArray.getJSONObject(i);
						String id = jsonObject.getString("name");
						itemNames.add(id);
						prices.add(jsonObject.getInt("price"));
					}
					String items[] = itemNames.toArray(new String[itemNames
							.size()]);

					CustomList adapter = new CustomList(getActivity(), items,
							listImages);
					itemsList.setAdapter(adapter);
					itemsList
							.setOnItemClickListener(new AdapterView.OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									Bundle bundle = new Bundle();
									bundle.putInt("image", listImages[position]);
									bundle.putString("item",
											itemNames.get(position));
									bundle.putInt("price", prices.get(position));
									Intent intent = new Intent(
											"com.canteen.order.ViewItem");
									intent.putExtras(bundle);
									getActivity().startActivity(intent);
								}
							});
				} catch (JSONException e) {
					Toast.makeText(getActivity(),
							"Exception caused is " + e.getMessage(),
							Toast.LENGTH_LONG).show();
				}
				Log.d("log", result);
			}

			super.onPostExecute(v);
		}

	}
}
