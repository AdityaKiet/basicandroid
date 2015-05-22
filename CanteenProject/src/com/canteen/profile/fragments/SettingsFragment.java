package com.canteen.profile.fragments;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andappers.canteenproject.R;

public class SettingsFragment extends Fragment {

	private LinearLayout linearLayout;
	Button btnlogOut, changePassword, btnchangeEmail, btnchangePhoneNumber;
	public Bundle basket;
	SharedPreferences sp;
	String name, account_number, roll_no, balance, last_recharge;
	SharedPreferences.Editor edit;
	EditText oldPassword, newPassword, confirmnewPassword, phoneno, etEmail;
	InputStream is;
	HttpEntity entity;
	String new_Password, phonenumber, email;
	TextView tv;
	private String logOut = null;

	public SettingsFragment(Bundle basket) {
		this.basket = basket;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		linearLayout = (LinearLayout) inflater.inflate(
				R.layout.settings_fragment, container, false);
		changePassword = (Button) linearLayout
				.findViewById(R.id.btnChangePassword);
		btnchangeEmail = (Button) linearLayout
				.findViewById(R.id.btnChangeEmail);
		btnchangePhoneNumber = (Button) linearLayout
				.findViewById(R.id.btnChangePhoneno);
		btnlogOut = (Button) linearLayout.findViewById(R.id.btnLogout);
		tv = (TextView) linearLayout.findViewById(R.id.txtSettings);
		sp = PreferenceManager.getDefaultSharedPreferences(getActivity()
				.getBaseContext());
		edit = sp.edit();
		tv.setText("Welcome " + sp.getString("name", null));

		if (basket != null) {
			name = basket.getString("name");
			account_number = basket.getString("account_number");
			roll_no = basket.getString("roll_no");
			balance = basket.getString("balance");
			last_recharge = basket.getString("last_recharge");
		} else {
			name = sp.getString("name", null);
			account_number = sp.getString("account_number", null);
			roll_no = sp.getString("roll_no", null);
			balance = sp.getString("balance", null);
			last_recharge = sp.getString("last_recharge", null);
		}

		changePassword.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				openPopupForPassword();
			}
		});
		btnlogOut.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new AlertDialog.Builder(getActivity())
						.setMessage("Are you sure that you want to logout ??")
						.setTitle("Warning !!")
						.setCancelable(true)
						.setIcon(R.drawable.ic_launcher)
						.setNegativeButton("Cancel", null)
						.setPositiveButton("LogOut",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										new Logout().execute();
										while (logOut == null) {

										}
										if (logOut != null
												&& logOut.charAt(0) == '1') {
											Toast.makeText(
													getActivity()
															.getApplicationContext(),
													"Logout Successfully !!! ",
													Toast.LENGTH_LONG).show();
											edit.putBoolean("isLogin", false)
													.commit();
											Intent intent = new Intent(
													"com.canteen.login.MainActivity");
											startActivity(intent);
											getActivity().finish();
										} else {
											new AlertDialog.Builder(
													getActivity())
													.setMessage(

													"Logout Unsuccessful  Please try again later....")
													.setTitle("Warning !!")
													.setCancelable(true)
													.setIcon(
															R.drawable.ic_launcher)
													.setNegativeButton("Okay!",
															null).show();
										}
									}
								}).show();
			}
		});
		btnchangeEmail.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				openPopupForEmail();
			}
		});
		btnchangePhoneNumber.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				openPopupForPhoneno();
			}
		});
		return linearLayout;
	}

	private void openPopupForPassword() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
		alertDialog.setTitle("Change Password");
		oldPassword = new EditText(getActivity());
		newPassword = new EditText(getActivity());
		confirmnewPassword = new EditText(getActivity());
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);
		oldPassword.setHint("Old Password");
		oldPassword.setTransformationMethod(PasswordTransformationMethod
				.getInstance());
		layout.addView(oldPassword);
		newPassword.setHint("New Password");
		newPassword.setTransformationMethod(PasswordTransformationMethod
				.getInstance());
		layout.addView(newPassword);
		confirmnewPassword.setHint("Confirm Password");
		confirmnewPassword.setTransformationMethod(PasswordTransformationMethod
				.getInstance());
		layout.addView(confirmnewPassword);
		alertDialog.setView(layout);
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.setPositiveButton("Confirm",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (newPassword.getText().toString().length() <= 3) {
							new AlertDialog.Builder(getActivity())
									.setTitle("Alert")
									.setPositiveButton("Okay", null)
									.setMessage(
											"Length of password can't be less than 4 characters..")
									.setIcon(R.drawable.ic_launcher).show();
						} else {
							if (!newPassword
									.getText()
									.toString()
									.equals(confirmnewPassword.getText()
											.toString())) {
								new AlertDialog.Builder(getActivity())
										.setMessage("Password Not Matched")
										.setTitle("Warning !!")
										.setCancelable(true)
										.setIcon(R.drawable.ic_launcher)
										.setNegativeButton("Okay", null).show();
							} else {
								new_Password = newPassword.getText().toString()
										.trim();
								new Task().execute();
							}
						}
					}
				});
		alertDialog.setNegativeButton("Cancel", null);
		alertDialog.show();
	}

	private void openPopupForPhoneno() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
		alertDialog.setTitle("Change Phone no.");
		oldPassword = new EditText(getActivity());
		phoneno = new EditText(getActivity());
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);
		oldPassword.setHint("Password");
		oldPassword.setTransformationMethod(PasswordTransformationMethod
				.getInstance());
		layout.addView(oldPassword);
		phoneno.setHint("New Phone Number");
		phoneno.setInputType(InputType.TYPE_CLASS_PHONE);
		layout.addView(phoneno);
		alertDialog.setView(layout);
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.setPositiveButton("Confirm",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						phonenumber = phoneno.getText().toString().trim();
						if (phonenumber.length() != 10) {
							new AlertDialog.Builder(getActivity())
									.setTitle("Warning !!!")
									.setMessage(
											"Your phone number should contain exactly 10 digits.")
									.setPositiveButton("Okay", null)
									.setIcon(R.drawable.ic_launcher).show();

						} else {
							new PhonenoTask().execute();
						}
					}
				});
		alertDialog.setNegativeButton("Cancel", null);
		alertDialog.show();
	}

	private void openPopupForEmail() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
		alertDialog.setTitle("Change E-mail address");
		oldPassword = new EditText(getActivity());
		etEmail = new EditText(getActivity());
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);
		oldPassword.setHint("Password");
		oldPassword.setTransformationMethod(PasswordTransformationMethod
				.getInstance());
		layout.addView(oldPassword);
		etEmail.setHint("New Email");
		layout.addView(etEmail);
		alertDialog.setView(layout);
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.setPositiveButton("Confirm",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						email = etEmail.getText().toString().trim();
						if (!isEmailValid(email)) {
							new AlertDialog.Builder(getActivity())
									.setTitle("Warning !!!")
									.setMessage(
											"Please enter a valid e-mail address.")
									.setPositiveButton("Okay", null)
									.setIcon(R.drawable.ic_launcher).show();

						} else {
							new EmailTask().execute();
						}
					}
				});
		alertDialog.setNegativeButton("Cancel", null);
		alertDialog.show();
	}

	public boolean isEmailValid(String email) {
		String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
				+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
				+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
				+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
				+ "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches())
			return true;
		else
			return false;
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
			String oldPass = oldPassword.getText().toString().trim();
			String newPass = newPassword.getText().toString().trim();
			List<NameValuePair> list = new ArrayList<NameValuePair>(1);
			list.add(new BasicNameValuePair("old_password", oldPass));
			list.add(new BasicNameValuePair("new_password", newPass));
			list.add(new BasicNameValuePair("account_number", account_number));
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://192.168.51.103:80/canteen/updatepassword.php");
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
			if (result.charAt(0) == '1') {
				Toast.makeText(getActivity().getApplicationContext(),
						"Your password has been updated successfully",
						Toast.LENGTH_LONG).show();
				edit.putString("password", new_Password).commit();
			} else if (result.charAt(0) == '0')
				Toast.makeText(getActivity().getApplicationContext(),
						"Please Enter Correct Password", Toast.LENGTH_LONG)
						.show();
			else
				Toast.makeText(
						getActivity().getApplicationContext(),
						"Updation not completed due to network error...try again later.. ",
						Toast.LENGTH_LONG).show();
			super.onPostExecute(v);
		}

	}

	class Logout extends AsyncTask<String, String, Void> {
		private ProgressDialog progressDialog = new ProgressDialog(
				getActivity());
		String result1 = "";

		protected void onPreExecute() {
			progressDialog.setTitle("Loading.....");
			progressDialog.setMessage("Loading.....");
			progressDialog.show();
			progressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface arg0) {
					Logout.this.cancel(true);
				}
			});
			super.onPreExecute();
		}

		protected Void doInBackground(String... params) {

			List<NameValuePair> list = new ArrayList<NameValuePair>(1);
			list.add(new BasicNameValuePair("password", sp
					.getString("password", null).toString().trim()));
			list.add(new BasicNameValuePair("account_number", sp
					.getString("account_number", null).toString().trim()));
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://192.168.51.103:80/canteen/updateLogout.php");
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
				result1 = stringBuilder.toString();
				Log.d("checl", result1);
				logOut = result1;
			} catch (Exception e) {
				Toast.makeText(getActivity().getApplicationContext(),
						"error 2", Toast.LENGTH_LONG).show();
			}
			return null;
		}

		protected void onPostExecute(Void v) {

			progressDialog.dismiss();
			Log.d("post", result1);
			super.onPostExecute(v);
		}

	}

	class PhonenoTask extends AsyncTask<String, String, Void> {

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
					PhonenoTask.this.cancel(true);
				}
			});
			super.onPreExecute();
		}

		protected Void doInBackground(String... params) {
			String oldPass = oldPassword.getText().toString().trim();
			List<NameValuePair> list = new ArrayList<NameValuePair>(1);
			list.add(new BasicNameValuePair("phoneno", phonenumber));
			list.add(new BasicNameValuePair("old_password", oldPass));
			list.add(new BasicNameValuePair("account_number", account_number));
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://192.168.51.103:80/canteen/updatephoneno.php");
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
			if (result.charAt(0) == '1') {
				Toast.makeText(getActivity().getApplicationContext(),
						"Your phone number has been updated successfully",
						Toast.LENGTH_LONG).show();
				edit.putString("password", new_Password).commit();
			} else if (result.charAt(0) == '0')
				Toast.makeText(
						getActivity().getApplicationContext(),
						"Please check your password.. or don't repeat same phone number again..",
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(
						getActivity().getApplicationContext(),
						"Updation not completed due to network error...try again later.. ",
						Toast.LENGTH_LONG).show();
			super.onPostExecute(v);
		}

	}

	class EmailTask extends AsyncTask<String, String, Void> {
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
					EmailTask.this.cancel(true);
				}
			});
			super.onPreExecute();
		}

		protected Void doInBackground(String... params) {
			String oldPass = oldPassword.getText().toString().trim();
			List<NameValuePair> list = new ArrayList<NameValuePair>(1);
			list.add(new BasicNameValuePair("email", email));
			list.add(new BasicNameValuePair("old_password", oldPass));
			list.add(new BasicNameValuePair("account_number", account_number));
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://192.168.51.103:80/canteen/updateemail.php");
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
			if (result.charAt(0) == '1') {
				Toast.makeText(getActivity().getApplicationContext(),
						"Your e-mail address has been updated successfully",
						Toast.LENGTH_LONG).show();
				edit.putString("password", new_Password).commit();
			} else if (result.charAt(0) == '0')
				Toast.makeText(
						getActivity().getApplicationContext(),
						"Please check your password.. or don't repeat same e-mail address again..",
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(
						getActivity().getApplicationContext(),
						"Updation not completed due to network error...try again later.. ",
						Toast.LENGTH_LONG).show();
			super.onPostExecute(v);
		}

	}
}
