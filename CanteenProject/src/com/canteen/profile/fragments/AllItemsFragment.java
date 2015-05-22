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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andappers.canteenproject.R;
import com.canteen.util.CustomListAllItems;

public class AllItemsFragment extends Fragment {
	private LinearLayout linearLayout;
	public Bundle basket;
	TextView tv;
	private ListView itemsList;
	String name, account_number, roll_no, balance;
	SharedPreferences sp;
	SharedPreferences.Editor edit;
	InputStream is;
	// private ArrayList<Bitmap> imagesList;
	HttpEntity entity;
	boolean isDone = false;
	// String imageIds[];
	// private ArrayList<String> imagaIds;
	private ArrayList<String> itemNames;
	private ArrayList<Integer> prices;
	TextView textView;

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
		textView = (TextView) linearLayout.findViewById(R.id.loading);
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
				// imagaIds = new ArrayList<>();
				try {

					JSONArray jsonArray = new JSONArray(result);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = null;
						jsonObject = jsonArray.getJSONObject(i);
						itemNames.add(jsonObject.getString("name"));
						prices.add(jsonObject.getInt("price"));
						// imagaIds.add(jsonObject.getString("id"));
					}
					final String items[] = itemNames
							.toArray(new String[itemNames.size()]);
					/*
					 * imageIds = imagaIds.toArray(new String[imagaIds.size()]);
					 * new LoadImageTask().execute(); while (!isDone) { }
					 */textView.setText("");
					/*
					 * final Bitmap[] imagesArray = imagesList .toArray(new
					 * Bitmap[imagesList.size()]);
					 */
					CustomListAllItems adapter = new CustomListAllItems(
							getActivity(), items);
					itemsList.setAdapter(adapter);
					itemsList.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							LinearLayout layout = new LinearLayout(
									getActivity());
							layout.setOrientation(LinearLayout.VERTICAL);
							ImageView image = new ImageView(getActivity());
							image.setImageResource(R.drawable.kiet);
							layout.addView(image);
							new AlertDialog.Builder(getActivity())
									.setTitle(
											items[position] + " - "
													+ prices.get(position)
													+ " Rs.")
									.setCancelable(true).setView(layout)
									.setPositiveButton("Okay !!!", null).show();

						}
					});
				}

				catch (JSONException e) {
					Toast.makeText(getActivity(),
							"Exception caused is " + e.getMessage(),
							Toast.LENGTH_LONG).show();
				}
				Log.d("log", result);
			}

			super.onPostExecute(v);
		}
	}

	/*
	 * class LoadImageTask extends AsyncTask<String, String, Void> { private
	 * ProgressDialog progressDialog = new ProgressDialog( getActivity());
	 * String result = "";
	 * 
	 * protected void onPreExecute() {
	 * 
	 * progressDialog.setTitle("Loading.....");
	 * progressDialog.setMessage("Please Wait....."); progressDialog.show();
	 * progressDialog.setOnCancelListener(new OnCancelListener() {
	 * 
	 * @Override public void onCancel(DialogInterface arg0) {
	 * LoadImageTask.this.cancel(true); } }); super.onPreExecute(); }
	 * 
	 * protected Void doInBackground(String... params) { imagesList = new
	 * ArrayList<>(); try { for (String index : imagaIds) { String imagesUrl =
	 * "http://canteenproject.netau.net/photo/i" + (Integer.parseInt(index) - 1)
	 * + ".jpg"; URL url = new URL(imagesUrl); Bitmap bmp =
	 * BitmapFactory.decodeStream(url .openConnection().getInputStream());
	 * imagesList.add(bmp); } isDone = true; ((AllClassItems)
	 * getActivity().getApplicationContext()) .setImages(imagesList);
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * Toast.makeText(getActivity().getApplicationContext(), "Exception raised "
	 * + e.getMessage(), Toast.LENGTH_LONG) .show(); }
	 * 
	 * return null; }
	 * 
	 * protected void onPostExecute(Void v) { progressDialog.dismiss();
	 * super.onPostExecute(v); }
	 * 
	 * }
	 */
}
