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
import org.apache.http.message.BasicNameValuePair;
import android.annotation.SuppressLint;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.andappers.canteenproject.R;

public class FeedbackFragment extends Fragment implements
		OnItemSelectedListener, OnClickListener {
	InputStream is;
	HttpEntity entity;
	private LinearLayout linearLayout;
	public Bundle basket;
	String name, account_number, roll_no, balance;
	TextView tv;
	private String type;
	SharedPreferences sp;
	SharedPreferences.Editor edit;
	Spinner spinner;
	EditText message;
	Button submitReport, reset;

	public FeedbackFragment(Bundle basket) {
		this.basket = basket;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		linearLayout = (LinearLayout) inflater.inflate(R.layout.feedback,
				container, false);
		sp = PreferenceManager.getDefaultSharedPreferences(getActivity()
				.getBaseContext());
		edit = sp.edit();
		spinner = (Spinner) linearLayout.findViewById(R.id.spnReport);
		submitReport = (Button) linearLayout.findViewById(R.id.btnReport);
		message = (EditText) linearLayout.findViewById(R.id.etReport);
		reset = (Button) linearLayout.findViewById(R.id.btnReset);
		submitReport.setOnClickListener(this);
		reset.setOnClickListener(this);

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
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.report_feedback,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
		return linearLayout;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		type = (String) spinner.getItemAtPosition(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnReport) {
			if (message.getText().toString().trim().equals("")) {
				new AlertDialog.Builder(getActivity())
						.setMessage("Please Enter a message.!")
						.setTitle("Alert !!").setCancelable(true)
						.setIcon(R.drawable.ic_launcher)
						.setNegativeButton("Okay", null).show();
			} else
				new Task().execute();
		} else if (v.getId() == R.id.btnReset) {
			message.setText("");

		}
	}

	@SuppressLint("NewApi")
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
			String message_text = message.getText().toString().trim();
			List<NameValuePair> list = new ArrayList<NameValuePair>(1);
			list.add(new BasicNameValuePair("message", message_text));
			list.add(new BasicNameValuePair("category", type));
			list.add(new BasicNameValuePair("name", name));
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://192.168.51.103:80/canteen/feedback.php");
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
			if (result.equals("true")) {
				message.setText("");
				new AlertDialog.Builder(getActivity())
						.setMessage("Your message has been sent")
						.setTitle("Alert !!").setCancelable(true)
						.setIcon(R.drawable.ic_launcher)
						.setNegativeButton("Okay", null).show();
			} else {
				new AlertDialog.Builder(getActivity())
						.setMessage(
								"Your message has not been sent due to network problem")
						.setTitle("Alert !!").setCancelable(true)
						.setIcon(R.drawable.ic_launcher)
						.setNegativeButton("Okay", null).show();
			}

			super.onPostExecute(v);
		}

	}
}
