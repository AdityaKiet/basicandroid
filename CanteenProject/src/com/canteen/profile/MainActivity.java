package com.canteen.profile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.andappers.canteenproject.R;
import com.canteen.dto.ItemDTO;
import com.canteen.dto.OrderDTO;
import com.canteen.global.AllClassItems;
import com.canteen.profile.adapter.NavDrawerListAdapter;
import com.canteen.profile.fragments.AllItemsFragment;
import com.canteen.profile.fragments.FeedbackFragment;
import com.canteen.profile.fragments.PlaceOrderFragment;
import com.canteen.profile.fragments.ProfileFragment;
import com.canteen.profile.fragments.SettingsFragment;
import com.canteen.profile.model.NavDrawerItem;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private Bundle basket;
	private CharSequence mTitle;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	private SharedPreferences sp;
	InputStream is;
	private boolean isLoaded = false;
	boolean isDone = false;
	String token;
	SharedPreferences.Editor edit;
	private String logOut = null;
	HttpEntity entity;
	ArrayList<ItemDTO> boughtItemList;
	int totalAmount = 0;
	StringBuilder items = new StringBuilder();
	String balance;
	String expectedTime = "0";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_slider_layout);
		sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		edit = sp.edit();
		basket = getIntent().getExtras();
		mTitle = mDrawerTitle = getTitle();
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		navDrawerItems = new ArrayList<NavDrawerItem>();
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));
		navMenuIcons.recycle();
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			displayView(0);
		}
	}

	@Override
	public void onBackPressed() {
		boughtItemList = ((AllClassItems) getApplicationContext())
				.getItemList();
		if (boughtItemList.size() == 0
				&& ((AllClassItems) getApplicationContext()).getOrder() == null) {
			new AlertDialog.Builder(MainActivity.this)
					.setMessage("Are you sure.. you want to exit ??")
					.setTitle("Alert !!")
					.setCancelable(true)
					.setIcon(R.drawable.ic_launcher)
					.setNegativeButton("Cancel", null)
					.setPositiveButton("Confirm !!",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									MainActivity.super.onBackPressed();
								}
							}).show();
		} else {
			new AlertDialog.Builder(MainActivity.this)
					.setMessage(
							"You have some pending items which will be lost on exit..\n Do you still want to exit ??")
					.setTitle("Alert !!")
					.setCancelable(true)
					.setIcon(R.drawable.ic_launcher)
					.setNegativeButton("Cancel", null)
					.setPositiveButton("Confirm !!",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									MainActivity.super.onBackPressed();
								}
							}).show();
		}

	}

	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		case R.id.placed_order:
			if (((AllClassItems) getApplicationContext()).getOrder() != null) {
				String[] placedorders = { ((AllClassItems) getApplicationContext())
						.getOrder().getId()
						+ " - "
						+ ((AllClassItems) getApplicationContext()).getOrder()
								.getOrder() };
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("Placed Orders")
						.setItems(placedorders, new OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								new OrderStatus().execute();
							}
						}).setIcon(R.drawable.ic_launcher)
						.setPositiveButton("okay", null).show();
			} else {
				new AlertDialog.Builder(MainActivity.this)
						.setIcon(R.drawable.ic_launcher)
						.setTitle("Placed Orders")
						.setMessage("None order found so far")
						.setPositiveButton("okay", null).show();
			}
			return true;
		case R.id.action_cart:

			boughtItemList = ((AllClassItems) getApplicationContext())
					.getItemList();
			items = new StringBuilder();
			totalAmount = 0;
			for (ItemDTO itemDTO : boughtItemList) {
				items.append(itemDTO.getName() + " - " + itemDTO.getQuantity()
						+ "\n");
				totalAmount += (itemDTO.getPrice() * itemDTO.getQuantity());
			}
			if (boughtItemList.size() == 0) {
				new AlertDialog.Builder(MainActivity.this).setTitle("Warning")
						.setCancelable(true).setIcon(R.drawable.ic_launcher)
						.setMessage("Your Cart is empty")
						.setPositiveButton("Okay", null).show();
			} else {
				Log.d("previous", sp.getString("balance", null));
				Log.d("bal", totalAmount + "");
				if (totalAmount > Integer
						.parseInt(sp.getString("balance", "0"))) {
					new AlertDialog.Builder(MainActivity.this)
							.setTitle("Sorry ")
							.setCancelable(true)
							.setIcon(R.drawable.ic_launcher)
							.setMessage(
									"You have insufficient balance in your account")
							.setPositiveButton("Okay", null)
							.setNegativeButton("Reset Cart",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											ArrayList<ItemDTO> list = new ArrayList<>();
											((AllClassItems) getApplicationContext())
													.setItemList(list);
											Toast.makeText(
													getApplicationContext(),
													"Cart has been reset",
													Toast.LENGTH_LONG).show();
										}
									}).show();
				} else {
					new AlertDialog.Builder(MainActivity.this)
							.setTitle("Your Cart is... ")
							.setCancelable(true)
							.setIcon(R.drawable.ic_launcher)
							.setMessage(
									items + "\nTotal Amount is " + totalAmount)
							.setPositiveButton("Confirm !!!",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											new AlertDialog.Builder(
													MainActivity.this)
													.setTitle("Alert !!")
													.setCancelable(true)
													.setIcon(
															R.drawable.ic_launcher)
													.setMessage(
															"Are you sure you want to place this order ?")
													.setPositiveButton(
															"Confirm !!!",
															new DialogInterface.OnClickListener() {

																@Override
																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	if (((AllClassItems) getApplicationContext())
																			.getOrder() == null) {
																		new PlaceOrder()
																				.execute();
																	} else {
																		new AlertDialog.Builder(
																				MainActivity.this)
																				.setIcon(
																						R.drawable.ic_launcher)
																				.setTitle(
																						"Alert")
																				.setPositiveButton(
																						"Okay",
																						null)
																				.setMessage(
																						"You already have a pending order..")
																				.show();
																	}

																}
															}

													)
													.setNegativeButton(
															"Cancel", null)
													.show();

										}
									})
							.setNegativeButton("Reset",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											new AlertDialog.Builder(
													MainActivity.this)
													.setTitle("Alert !!")
													.setCancelable(true)
													.setIcon(
															R.drawable.ic_launcher)
													.setMessage(
															"Are you sure you want to reset this cart ?")
													.setPositiveButton(
															"Confirm !!!",
															new DialogInterface.OnClickListener() {
																public void onClick(
																		DialogInterface dialog,
																		int which) {
																	ArrayList<ItemDTO> list = new ArrayList<>();
																	((AllClassItems) getApplicationContext())
																			.setItemList(list);
																	Toast.makeText(
																			getApplicationContext(),
																			"Cart has been reset",
																			Toast.LENGTH_LONG)
																			.show();
																}
															})
													.setNegativeButton(
															"Cancel", null)
													.show();

										}
									})
							.setNeutralButton("Cancel",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				}
			}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	private void displayView(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			new GetData().execute();
			if (basket != null) {
				isLoaded = false;

				fragment = new ProfileFragment(basket);
			}

			else {
				isLoaded = false;

				fragment = new ProfileFragment(null);
			}
			break;
		case 1:
			new GetData().execute();
			fragment = new PlaceOrderFragment();
			break;
		case 2:
			new GetData().execute();
			fragment = new AllItemsFragment();
			break;
		case 3:
			new GetData().execute();

			if (basket != null)
				fragment = new FeedbackFragment(basket);
			else
				fragment = new FeedbackFragment(null);
			break;
		case 4:
			new GetData().execute();

			if (basket != null)
				fragment = new SettingsFragment(basket);
			else
				fragment = new SettingsFragment(null);
			break;
		case 5:
			new AlertDialog.Builder(MainActivity.this)
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
									new UpdateTask().execute();
									while (logOut == null) {

									}
									Log.d("main", logOut);
									if (logOut != null
											&& logOut.charAt(0) == '1') {
										Toast.makeText(getApplicationContext(),
												"Logout Successfully !!! ",
												Toast.LENGTH_LONG).show();
										edit.putBoolean("isLogin", false)
												.commit();
										Intent intent = new Intent(
												"com.canteen.login.MainActivity");
										startActivity(intent);
										finish();
									} else {
										new AlertDialog.Builder(
												MainActivity.this)
												.setMessage(
														"Logout Unsuccessful  Please try again later....")
												.setTitle("Warning !!")
												.setCancelable(true)
												.setIcon(R.drawable.ic_launcher)
												.setNegativeButton("Okay!",
														null).show();
									}
								}
							}).show();

			mDrawerLayout.closeDrawer(mDrawerList);

			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	class UpdateTask extends AsyncTask<String, String, Void> {
		private ProgressDialog progressDialog = new ProgressDialog(
				MainActivity.this);
		String result1 = "";

		protected void onPreExecute() {
			progressDialog.setTitle("Loading.....");
			progressDialog.setMessage("Loading.....");
			progressDialog.show();
			progressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface arg0) {
					UpdateTask.this.cancel(true);
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
				result1 = stringBuilder.toString();
				Log.d("checl", result1);
				logOut = result1;
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "error 2",
						Toast.LENGTH_LONG).show();
			}
			return null;
		}

		protected void onPostExecute(Void v) {

			progressDialog.dismiss();
			Log.d("post", result1);
			super.onPostExecute(v);
		}

	}

	class PlaceOrder extends AsyncTask<String, String, Void> {
		private ProgressDialog progressDialog = new ProgressDialog(
				MainActivity.this);
		String result = "";

		protected void onPreExecute() {
			progressDialog.setTitle("Loading.....");
			progressDialog.setMessage("Loading.....");
			progressDialog.show();
			progressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface arg0) {
					PlaceOrder.this.cancel(true);
				}
			});
			super.onPreExecute();
		}

		protected Void doInBackground(String... params) {

			List<NameValuePair> list = new ArrayList<NameValuePair>(1);
			list.add(new BasicNameValuePair("items", items.toString()));
			list.add(new BasicNameValuePair("name", sp.getString("name", null)
					.toString().trim()));
			list.add(new BasicNameValuePair("amount", "" + totalAmount));
			list.add(new BasicNameValuePair("account_number", sp.getString(
					"account_number", null)));
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://192.168.51.103:80/canteen/placeorder.php");
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
				Log.d("checl", result);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "error 2",
						Toast.LENGTH_LONG).show();
			}
			return null;
		}

		protected void onPostExecute(Void v) {

			progressDialog.dismiss();
			Log.d("log", result);
			splitString(result);
			if (isDone) {
				boughtItemList = new ArrayList<>();
				((AllClassItems) getApplicationContext())
						.setItemList(boughtItemList);
				totalAmount = 0;
				OrderDTO order = new OrderDTO();
				order.setId(token);
				order.setOrder("Order with Id " + token + "has been placed");
				((AllClassItems) getApplicationContext()).setOrder(order);
				edit.putString("balance", balance).commit();
				new AlertDialog.Builder(MainActivity.this)
						.setMessage(
								"Your order has been placed ... Kindly wait for your order to be confirmed by admin.. You can also cancel it..")
						.setTitle("Success !!").setCancelable(true)
						.setIcon(R.drawable.ic_launcher)
						.setNegativeButton("okay", null).show();
			} else {
				new AlertDialog.Builder(MainActivity.this)
						.setMessage(
								"Something went wrong .. Please try again..")
						.setTitle("Success !!").setCancelable(true)
						.setIcon(R.drawable.ic_launcher)
						.setNegativeButton("okay", null).show();
			}
			super.onPostExecute(v);
		}
	}

	public void splitString(String s) {
		String a[] = s.split(",");
		String b[] = a[0].split(" ");
		String c[] = a[1].split(" ");
		String d[] = a[2].split(" ");
		String e[] = d[1].split("\"");
		if (b[1].equals("1"))
			isDone = true;
		else
			isDone = false;
		balance = c[1].trim();
		token = e[0].trim();
	}

	class GetData extends AsyncTask<String, String, Void> {
		private ProgressDialog progressDialog = new ProgressDialog(
				MainActivity.this);
		String result = "";

		protected void onPreExecute() {
			progressDialog.setTitle("Loading.....");
			progressDialog.setMessage("Loading.....");
			progressDialog.show();
			progressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface arg0) {
					GetData.this.cancel(true);
				}
			});
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
					progressDialog.dismiss();
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
				isLoaded = true;
			} catch (JSONException e) {
				progressDialog.dismiss();
				Toast.makeText(getApplicationContext(),
						"Exception caused is " + e.getMessage(),
						Toast.LENGTH_LONG).show();
				isLoaded = true;
			}
			Log.d("isLoaded", isLoaded + "");
			Log.d("done", "done");
			super.onPostExecute(v);
		}

	}

	class OrderStatus extends AsyncTask<String, String, Void> {
		private ProgressDialog progressDialog = new ProgressDialog(
				MainActivity.this);
		String result2 = "";

		protected void onPreExecute() {
			Log.d("clicked", "yes");
			progressDialog.setTitle("Loading.....");
			progressDialog.setMessage("Loading.....");
			progressDialog.show();
			progressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface arg0) {
					OrderStatus.this.cancel(true);
				}
			});
			super.onPreExecute();
		}

		protected Void doInBackground(String... params) {

			List<NameValuePair> list = new ArrayList<NameValuePair>(1);
			list.add(new BasicNameValuePair("id",
					((AllClassItems) getApplicationContext()).getOrder()
							.getId()));
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://192.168.51.103:80/canteen/orderstatus.php");
				httpPost.setEntity(new UrlEncodedFormEntity(list));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				entity = httpResponse.getEntity();
				is = entity.getContent();
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
				result2 = stringBuilder.toString();
				Log.d("one", "!" + result2);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "error 2",
						Toast.LENGTH_LONG).show();
			}
			return null;
		}

		protected void onPostExecute(Void v) {
			super.onPostExecute(v);
			progressDialog.dismiss();
			Log.d("post", result2);
			if (result2.equals("1")) {
				new ExpectedTime().execute();
				new AlertDialog.Builder(MainActivity.this)
						.setIcon(R.drawable.ic_launcher)
						.setTitle("Alert")
						.setPositiveButton("Get Token",
								new DialogInterface.OnClickListener() {

									@SuppressLint("SdCardPath")
									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										try {

											File file = new File(
													"/sdcard/token.txt");
											file.createNewFile();
											FileOutputStream stream = new FileOutputStream(
													file);
											OutputStreamWriter writer = new OutputStreamWriter(
													stream);
											writer.append("KIET Canteen \n");
											writer.append("Hello "
													+ sp.getString("name", null)
													+ " . \nYour account number is "
													+ sp.getString(
															"account_number",
															null)
													+ " \n Token Number is :"
													+ ((AllClassItems) getApplicationContext())
															.getOrder().getId()
													+ "\n Expected waiting time is "
													+ expectedTime + " minutes");

											writer.close();
											stream.close();
											((AllClassItems) getApplicationContext())
													.setOrder(null);
										} catch (Exception e) {
											Toast.makeText(
													getApplicationContext(),
													"exception" + e.toString(),
													Toast.LENGTH_SHORT).show();
										}

									}
								})
						.setMessage(
								"Your order has been placed..\n Your token number is "
										+ ((AllClassItems) getApplicationContext())
												.getOrder().getId()).show();

			} else {
				Log.d("done", "cancel it");
				new AlertDialog.Builder(MainActivity.this)
						.setIcon(R.drawable.ic_launcher)
						.setTitle("Alert")
						.setMessage(
								"Your order is still pending.. Do you want to cancel it ??")
						.setPositiveButton("Yes! ",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										new DeleteOrder().execute();

									}
								}).setNegativeButton("No !", null).show();
			}
		}
	}

	class DeleteOrder extends AsyncTask<String, String, Void> {
		private ProgressDialog progressDialog = new ProgressDialog(
				MainActivity.this);
		String result3 = "";

		protected void onPreExecute() {
			progressDialog.setTitle("Loading.....");
			progressDialog.setMessage("Loading.....");
			progressDialog.show();
			progressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface arg0) {
					DeleteOrder.this.cancel(true);
				}
			});
			super.onPreExecute();
		}

		protected Void doInBackground(String... params) {

			List<NameValuePair> list = new ArrayList<NameValuePair>(1);
			list.add(new BasicNameValuePair("id",
					((AllClassItems) getApplicationContext()).getOrder()
							.getId()));
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://192.168.51.103:80/canteen/deleteorder.php");
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
				result3 = stringBuilder.toString();
				Log.d("checl", result3);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "error 2",
						Toast.LENGTH_LONG).show();
			}
			return null;
		}

		protected void onPostExecute(Void v) {

			progressDialog.dismiss();
			Log.d("post", result3);
			if (result3.equals("1")) {
				Toast.makeText(getApplicationContext(),
						"Order has been cancelled", Toast.LENGTH_LONG).show();
				((AllClassItems) getApplicationContext()).setOrder(null);
			} else {
				Toast.makeText(getApplicationContext(),
						"Order has not been cancelled.. Please try again..",
						Toast.LENGTH_LONG).show();

			}
			super.onPostExecute(v);
		}

	}

	class ExpectedTime extends AsyncTask<String, String, Void> {
		private ProgressDialog progressDialog = new ProgressDialog(
				MainActivity.this);
		String result3 = "";

		protected void onPreExecute() {
			progressDialog.setTitle("Loading.....");
			progressDialog.setMessage("Loading.....");
			progressDialog.show();
			progressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface arg0) {
					ExpectedTime.this.cancel(true);
				}
			});
			super.onPreExecute();
		}

		protected Void doInBackground(String... params) {

			List<NameValuePair> list = new ArrayList<NameValuePair>(1);
			list.add(new BasicNameValuePair("id",
					((AllClassItems) getApplicationContext()).getOrder()
							.getId()));
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://192.168.51.103:80/canteen/gettime.php");
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
				result3 = stringBuilder.toString();
				Log.d("checl", result3);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "error 2",
						Toast.LENGTH_LONG).show();
			}
			return null;
		}

		protected void onPostExecute(Void v) {

			progressDialog.dismiss();
			Log.d("post", result3);
			expectedTime = result3;
			super.onPostExecute(v);
		}

	}
}
