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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andappers.canteenproject.R;
import com.canteen.dto.ItemDTO;
import com.canteen.dto.OrderDTO;
import com.canteen.global.AllClassItems;
import com.canteen.util.CustomListAllItems;

public class PlaceOrderFragment extends Fragment {
	InputStream is;
	HttpEntity entity;
	EditText quantity;
	private LinearLayout linearLayout;
	public Bundle basket;
	String name, account_number, roll_no;
	SharedPreferences sp;
	SharedPreferences.Editor edit;
	TextView tv, textView;
	ArrayList<ItemDTO> boughtItems;
	private ArrayList<String> itemNames;
	private ListView itemsList;
	private ArrayList<Integer> prices;

	String[] list = { "Aloo Parantha", "Gobhi Parantha", "Paneer Parantha",
			"Aloo Patties", "Panner Patties" };
	boolean isDone = false;
	String token;
	ArrayList<ItemDTO> boughtItemList;
	int totalAmount = 0;
	StringBuilder items = new StringBuilder();
	String balance;
	String itemstring;
	String selectedItem;
	int selectedItemQuantity = 0;
	int selectedItemPrice = 0;

	// private ArrayList<Bitmap> imagesList;
	// String imageIds[];
	// private ArrayList<String> imagaIds;

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
		tv.setText("Available Items are..");
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
						"http://192.168.51.103:80/canteen/currentitems.php");
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
				textView.setText("");
				new AlertDialog.Builder(getActivity())
						.setMessage(
								"Canteen is closed at this time so you can't place any order right now..")
						.setTitle("Alert !!").setCancelable(true)
						.setIcon(R.drawable.ic_launcher)
						.setNegativeButton("Okay", null).show();
			} else {

				itemNames = new ArrayList<>();
				prices = new ArrayList<>();
				// imagaIds = new ArrayList<String>();
				final AllClassItems allClassItems = ((AllClassItems) getActivity()
						.getApplicationContext());
				try {

					JSONArray jsonArray = new JSONArray(result);
					Log.d("json", jsonArray.toString());
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = null;

						jsonObject = jsonArray.getJSONObject(i);
						itemNames.add(jsonObject.getString("name"));
						prices.add(jsonObject.getInt("price"));
						// imagaIds.add(jsonObject.getString("id"));
					}
					final String items[] = itemNames
							.toArray(new String[itemNames.size()]);
					// imageIds = imagaIds.toArray(new String[imagaIds.size()]);
					/*
					 * new LoadImageTask().execute(); while (!isDone) { }
					 */textView.setText("");
					/*
					 * final Bitmap[] imagesArray = imagesList .toArray(new
					 * Bitmap[imagesList.size()]);
					 */
					CustomListAllItems adapter = new CustomListAllItems(
							getActivity(), items);
					itemsList.setAdapter(adapter);
					itemsList
							.setOnItemClickListener(new AdapterView.OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, final int position, long id) {
									LinearLayout layout = new LinearLayout(
											getActivity());
									layout.setOrientation(LinearLayout.VERTICAL);
									quantity = new EditText(getActivity());
									ImageView image = new ImageView(
											getActivity());
									image.setImageResource(R.drawable.kiet);
									layout.addView(image);
									quantity.setHint("Enter Quantity");
									quantity.setInputType(InputType.TYPE_CLASS_NUMBER);
									layout.addView(quantity);
									new AlertDialog.Builder(getActivity())
											.setTitle(
													items[position]
															+ " - "
															+ prices.get(position)
															+ " Rs.")
											.setCancelable(true)
											.setView(layout)
											.setPositiveButton(
													"Buy",
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(
																DialogInterface dialog,
																int which) {

															if (((AllClassItems) getActivity()
																	.getApplicationContext())
																	.getOrder() == null) {
																boughtItems = allClassItems
																		.getItemList();

																buyItem(position);
															} else {
																new AlertDialog.Builder(
																		getActivity())
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

													})
											.setNegativeButton(
													"Add To cart",
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(
																DialogInterface dialog,
																int which) {

															boughtItems = allClassItems
																	.getItemList();

															addToCart(position);
														}
													}

											).show();

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

		private void buyItem(final int position) {

			if (quantity.getText().toString().trim().equals("")) {
				new AlertDialog.Builder(getActivity()).setTitle("Warning !!")
						.setCancelable(true).setIcon(R.drawable.ic_launcher)
						.setMessage("Please Enter the amount of product.!!")
						.setPositiveButton("Okay !!!", null).show();
			} else if (Integer.parseInt(quantity.getText().toString().trim()) == 0) {
				new AlertDialog.Builder(getActivity()).setTitle("Warning !!")
						.setCancelable(true).setIcon(R.drawable.ic_launcher)
						.setMessage("Please enter the amount of product.!!")
						.setPositiveButton("Okay !!!", null).show();
			} else {

				if (((AllClassItems) getActivity().getApplicationContext())
						.getItemList().size() > 0) {
					new AlertDialog.Builder(getActivity())
							.setTitle("Warning !!")
							.setCancelable(true)
							.setIcon(R.drawable.ic_launcher)
							.setMessage(
									"You have some items in your cart already Do you want to purchase them also.?")
							.setPositiveButton("Yes !!!",
									new OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											buyAllItems(position);

										}
									})
							.setNegativeButton("No !!", new OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {

									buyAnItem(position);
								}

							}).setNeutralButton("Cancel", null).show();
				} else {

					selectedItem = itemNames.get(position);
					selectedItemQuantity = Integer.parseInt(quantity.getText()
							.toString());
					selectedItemPrice = prices.get(position)
							* selectedItemQuantity;
					if (Integer.parseInt(sp.getString("balance", null)) < selectedItemPrice) {
						new AlertDialog.Builder(getActivity())
								.setMessage("You don't have sufficient balance")
								.setPositiveButton("Okay !!", null)
								.setTitle("Warning")
								.setIcon(R.drawable.ic_launcher).show();
					} else {
						new PlaceSingleItemOrder().execute();
					}

				}
			}
		}

		private void buyAllItems(int position) {
			boughtItemList = ((AllClassItems) getActivity()
					.getApplicationContext()).getItemList();
			addToCart(position);
			items = new StringBuilder();
			totalAmount = 0;
			for (ItemDTO itemDTO : boughtItemList) {
				items.append(itemDTO.getName() + " - " + itemDTO.getQuantity()
						+ "\n");
				totalAmount += (itemDTO.getPrice() * itemDTO.getQuantity());
			}
			if (totalAmount > Integer.parseInt(sp.getString("balance", null))) {
				new AlertDialog.Builder(getActivity()).setTitle("Warning !!")
						.setCancelable(true).setIcon(R.drawable.ic_launcher)
						.setMessage("You have insufficient balance.!!")
						.setPositiveButton("Okay !!!", null).show();
			} else {
				new PlaceOrder().execute();
			}
		}

		private void addToCart(int position) {
			if (boughtItems != null) {
				Log.d("arrayList", boughtItems.toString());

			} else {
				Log.d("arrayList", "first time");
			}
			if (quantity.getText().toString().trim().equals("")) {
				new AlertDialog.Builder(getActivity()).setTitle("Warning !!")
						.setCancelable(true).setIcon(R.drawable.ic_launcher)
						.setMessage("Please Enter the quantity of product.!!")
						.setPositiveButton("Okay !!!", null).show();
			} else if (Integer.parseInt(quantity.getText().toString().trim()) == 0) {
				new AlertDialog.Builder(getActivity()).setTitle("Warning !!")
						.setCancelable(true).setIcon(R.drawable.ic_launcher)
						.setMessage("Please enter the amount of product.!!")
						.setPositiveButton("Okay !!!", null).show();
			} else {
				ItemDTO itemDTO = new ItemDTO();
				itemDTO.setName(itemNames.get(position));
				itemDTO.setPrice(prices.get(position));
				itemDTO.setQuantity(Integer.parseInt(quantity.getText()
						.toString().trim()));
				boughtItems.add(itemDTO);
				((AllClassItems) getActivity().getApplicationContext())
						.setItemList(boughtItems);
				Toast.makeText(getActivity(),
						itemNames.get(position) + " has been added to cart",
						Toast.LENGTH_LONG).show();
			}
		}

		private void buyAnItem(int position) {
			selectedItem = itemNames.get(position);
			selectedItemQuantity = Integer.parseInt(quantity.getText()
					.toString());
			selectedItemPrice = prices.get(position) * selectedItemQuantity;
			if (Integer.parseInt(sp.getString("balance", null)) < selectedItemPrice) {
				new AlertDialog.Builder(getActivity())
						.setMessage("You don't have sufficient balance")
						.setPositiveButton("Okay !!", null).setTitle("Warning")
						.setIcon(R.drawable.ic_launcher).show();
			} else {
				new PlaceSingleItemOrder().execute();
			}
		}
	}

	class PlaceOrder extends AsyncTask<String, String, Void> {
		private ProgressDialog progressDialog = new ProgressDialog(
				getActivity());
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
				Log.d("checl", result);
			} catch (Exception e) {
				Toast.makeText(getActivity().getApplicationContext(),
						"error 2", Toast.LENGTH_LONG).show();
			}
			return null;
		}

		protected void onPostExecute(Void v) {

			progressDialog.dismiss();
			Log.d("log", result);
			splitString(result);
			if (isDone) {
				boughtItemList = new ArrayList<>();
				((AllClassItems) getActivity().getApplicationContext())
						.setItemList(boughtItemList);
				totalAmount = 0;
				OrderDTO order = new OrderDTO();
				order.setId(token);
				order.setOrder("Order with Id " + token + "has been placed");
				((AllClassItems) getActivity().getApplicationContext())
						.setOrder(order);
				edit.putString("balance", balance).commit();
				new AlertDialog.Builder(getActivity())
						.setMessage(
								"Your order has been placed ... Kindly wait for your order to be confirmed by admin.. You can also cancel it..")
						.setTitle("Success !!").setCancelable(true)
						.setIcon(R.drawable.ic_launcher)
						.setNegativeButton("okay", null).show();
			} else {
				new AlertDialog.Builder(getActivity())
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
		String f[] = a[3].split("Items ");
		String e[] = d[1].split("\"");
		if (b[1].equals("1"))
			isDone = true;
		else
			isDone = false;
		itemstring = f[1].trim();
		balance = c[1].trim();
		token = e[0].trim();
	}

	class PlaceSingleItemOrder extends AsyncTask<String, String, Void> {
		private ProgressDialog progressDialog = new ProgressDialog(
				getActivity());
		String result = "";

		protected void onPreExecute() {
			progressDialog.setTitle("Loading.....");
			progressDialog.setMessage("Loading.....");
			progressDialog.show();
			progressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface arg0) {
					PlaceSingleItemOrder.this.cancel(true);
				}
			});
			super.onPreExecute();
		}

		protected Void doInBackground(String... params) {

			List<NameValuePair> list = new ArrayList<NameValuePair>(1);
			list.add(new BasicNameValuePair("items", selectedItem + " - "
					+ selectedItemQuantity));
			list.add(new BasicNameValuePair("name", sp.getString("name", null)
					.toString().trim()));
			list.add(new BasicNameValuePair("amount", "" + selectedItemPrice));
			list.add(new BasicNameValuePair("account_number", sp.getString(
					"account_number", null)));
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://192.168.51.103:80/canteen/placesingleorder.php");
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
				splitString(result);
				Log.d("checl", result);
			} catch (Exception e) {
				Toast.makeText(getActivity().getApplicationContext(),
						"error 2", Toast.LENGTH_LONG).show();
			}
			return null;
		}

		protected void onPostExecute(Void v) {

			progressDialog.dismiss();
			Log.d("log", result);
			splitString(result);
			if (isDone) {
				selectedItem = null;
				selectedItemPrice = 0;
				selectedItemQuantity = 0;
				/*
				 * ((AllClassItems) getActivity().getApplicationContext())
				 * .setOrder(null);
				 */
				OrderDTO order = new OrderDTO();
				order.setId(token);
				order.setOrder("Order with Id " + token + " has been placed");
				((AllClassItems) getActivity().getApplicationContext())
						.setOrder(order);
				edit.putString("balance", balance).commit();
				new AlertDialog.Builder(getActivity())
						.setMessage(
								"Your order has been placed ... Kindly wait for your order to be confirmed by admin.. You can also cancel it..")
						.setTitle("Success !!").setCancelable(true)
						.setIcon(R.drawable.ic_launcher)
						.setNegativeButton("okay", null).show();
			} else {
				new AlertDialog.Builder(getActivity())
						.setMessage(
								"Something went wrong .. Please try again..")
						.setTitle("Success !!").setCancelable(true)
						.setIcon(R.drawable.ic_launcher)
						.setNegativeButton("okay", null).show();
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
