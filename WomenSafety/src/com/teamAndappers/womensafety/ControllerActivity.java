package com.teamAndappers.womensafety;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ControllerActivity extends FragmentActivity {
	private TextView mLatLng;
	private TextView mAddress;
	private TextView mLastUp;
	static int p = 0;
	String saidText;
	final static int datacamera = 0;
	private final int REQ_CODE_SPEECH_INPUT = 100;
	Intent cameraintent;
	int q = 0;
	String num1, num2, num3;
	// String phonenumber = "+918447257969";
	String message;
	private LocationManager mLocationMgr;
	private Handler mHandler;
	SharedPreferences sp = null;
	SharedPreferences.Editor edit = null;
	private Location mLastLocation;
	private boolean mGeocoderAvailable;
	private static final int UPDATE_LASTLATLNG = 4;
	private static final int LAST_UP = 3;
	private static final int UPDATE_LATLNG = 2;
	private static final int UPDATE_ADDRESS = 1;

	private static final int SECONDS_TO_UP = 10000;
	private static final int METERS_TO_UP = 10;
	private static final int MINUTES_TO_STALE = 1000 * 60 * 2;
	Button tts, btcam;
	String addressText;

	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mLatLng = (TextView) findViewById(R.id.latlng);
		mLastUp = (TextView) findViewById(R.id.lastup);
		mAddress = (TextView) findViewById(R.id.address);
		tts = (Button) findViewById(R.id.tts);
		btcam = (Button) findViewById(R.id.btncamera);
		sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		edit = sp.edit();
		if (sp.getString("key1", null) != null) {
			Log.d("num1", "num1");
			num1 = sp.getString("key1", null);
		}
		if (sp.getString("key2", null) != null) {
			num2 = sp.getString("key2", null);
		}
		if (sp.getString("key3", null) != null) {
			num3 = sp.getString("key3", null);
		}
		tts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				promptSpeechInput();
			}
		});
		btcam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cameraintent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraintent, datacamera);
			}
		});
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case UPDATE_ADDRESS:
					mAddress.setText((String) msg.obj);
					/*
					 * if (((String) msg.obj) != null && q < 1) { String message
					 * = "Hi! I am in trouble and my location is " + (String)
					 * msg.obj; autoMessage(message); q++;
					 * 
					 * }
					 */
					break;
				case UPDATE_LATLNG:
					mLatLng.setText((String) msg.obj);
					/*
					 * if (((String) msg.obj) != null && p<1) { autoMessage();
					 * p++; }
					 */
					break;
				case LAST_UP:
					mLastUp.setText((String) msg.obj);
					break;
				}
			}

		};
		// mHandler.
		mGeocoderAvailable = Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
				&& Geocoder.isPresent();
		mLocationMgr = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setup();
	}

	public void onStart() {
		super.onStart();
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		final boolean gpsEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (!gpsEnabled) {
			new EnableGpsDialogFragment().show(getSupportFragmentManager(),
					"enableGpsDialog");
		}
	}

	private void enableLocationSettings() {
		Intent settingsIntent = new Intent(
				Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(settingsIntent);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mLocationMgr.removeUpdates(listener);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	private void setup() {

		Location newLocation = null;
		mLocationMgr.removeUpdates(listener);
		mLatLng.setText(R.string.unknown);
		newLocation = requestUpdatesFromProvider(LocationManager.GPS_PROVIDER,
				R.string.no_gps_support);

		// If gps location doesn't work, try network location
		if (newLocation == null) {
			newLocation = requestUpdatesFromProvider(
					LocationManager.NETWORK_PROVIDER,
					R.string.no_network_support);
		}

		if (newLocation != null) {
			updateUILocation(getBestLocation(newLocation, mLastLocation));
		}
	}

	/**
	 * This code is based on this code:
	 * http://developer.android.com/guide/topics
	 * /location/obtaining-user-location.html
	 * 
	 * @param newLocation
	 * @param currentBestLocation
	 * @return
	 */
	protected Location getBestLocation(Location newLocation,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			return newLocation;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
		boolean isNewerThanStale = timeDelta > MINUTES_TO_STALE;
		boolean isOlderThanStale = timeDelta < -MINUTES_TO_STALE;
		boolean isNewer = timeDelta > 0;

		if (isNewerThanStale) {
			return newLocation;
		} else if (isOlderThanStale) {
			return currentBestLocation;
		}

		int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(newLocation.getProvider(),
				currentBestLocation.getProvider());
		if (isMoreAccurate) {
			return newLocation;
		} else if (isNewer && !isLessAccurate) {
			return newLocation;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return newLocation;
		}
		return currentBestLocation;
	}

	private Location requestUpdatesFromProvider(final String provider,
			final int errorResId) {
		Location location = null;
		if (mLocationMgr.isProviderEnabled(provider)) {
			mLocationMgr.requestLocationUpdates(provider, SECONDS_TO_UP,
					METERS_TO_UP, listener);
			location = mLocationMgr.getLastKnownLocation(provider);
		} else {
			Toast.makeText(this, errorResId, Toast.LENGTH_LONG).show();
		}
		return location;
	}

	private void doReverseGeocoding(Location location) {
		(new ReverseGeocode(this)).execute(new Location[] { location });
	}

	private void updateUILocation(Location location) {
		Message.obtain(mHandler, UPDATE_LATLNG,
				location.getLatitude() + ", " + location.getLongitude())
				.sendToTarget();
		if (mLastLocation != null) {
			Message.obtain(
					mHandler,
					UPDATE_LASTLATLNG,
					mLastLocation.getLatitude() + ", "
							+ mLastLocation.getLongitude()).sendToTarget();
		}
		mLastLocation = location;
		Date now = new Date();
		Message.obtain(mHandler, LAST_UP, now.toString()).sendToTarget();

		if (mGeocoderAvailable)
			doReverseGeocoding(location);
	}

	private final LocationListener listener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			updateUILocation(location);
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	private class ReverseGeocode extends AsyncTask<Location, Void, Void> {
		Context mContext;

		public ReverseGeocode(Context context) {
			super();
			mContext = context;
		}

		@Override
		protected Void doInBackground(Location... params) {
			Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

			Location loc = params[0];
			List<Address> addresses = null;
			try {
				addresses = geocoder.getFromLocation(loc.getLatitude(),
						loc.getLongitude(), 1);
			} catch (IOException e) {
				e.printStackTrace();
				// Update address field with the exception.
				Message.obtain(mHandler, UPDATE_ADDRESS, e.toString())
						.sendToTarget();
			}
			if (addresses != null && addresses.size() > 0) {
				Address address = addresses.get(0);
				// Format the first line of address (if available), city, and
				// country name.
				addressText = String.format(
						"%s, %s, %s",
						address.getMaxAddressLineIndex() > 0 ? address
								.getAddressLine(0) : "", address.getLocality(),
						address.getCountryName());
				// Update address field on UI.
				Message.obtain(mHandler, UPDATE_ADDRESS, addressText)
						.sendToTarget();
				if (addressText != null && p < 1) {
					// autoMessage(addressText);
					// autoMessage(addressText);
					Log.d("log", addressText);
					autoCall();
					Log.d("log", "hello i m called");
					p++;
					// ControllerActivity.this.finish();
					// tts.setVisibility(View.VISIBLE);
				}
			}
			return null;
		}
	}

	/**
	 * Dialog to prompt users to enable GPS on the device.
	 */

	@SuppressLint("ValidFragment")
	public class EnableGpsDialogFragment extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new AlertDialog.Builder(getActivity())
					.setTitle(R.string.enable_gps)
					.setMessage(R.string.enable_gps_dialog)
					.setPositiveButton(R.string.enable_gps,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									enableLocationSettings();
								}
							}).create();
		}
	}

	public void autoCall() {
		Uri uri = Uri.fromParts("tel", num1, null);

		Intent callIntent = new Intent(Intent.ACTION_CALL, uri);
		callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(callIntent);
		/*
		 * EndCallListener callListener = new EndCallListener();
		 * TelephonyManager mTM = (TelephonyManager) this
		 * .getSystemService(Context.TELEPHONY_SERVICE);
		 * mTM.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);
		 */
	}

	public void autoMessage(String message) {
		Log.d("log", "I have been called");
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
				ControllerActivity.class), 0);
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(num1, null, message, pi, null);
		int i = 0;
		while (i < 100000) {
			i++;
		}

	}

	private void promptSpeechInput() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				getString(R.string.speech_prompt));
		try {
			startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
		} catch (ActivityNotFoundException a) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.speech_not_supported),
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Receiving speech input
	 * */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case REQ_CODE_SPEECH_INPUT: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> result = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				saidText = result.get(0);
				Log.d("log", saidText);
				/*
				 * Intent i = new Intent(android.content.Intent.ACTION_SEND);
				 * i.setType("text/plain");
				 * i.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
				 * i.putExtra(android.content.Intent.EXTRA_TEXT,
				 * "Hi ! friends I am in trouble " + saidText +
				 * "My Current Location is " + addressText);
				 * startActivity(Intent.createChooser(i, "Share via"));
				 */
				PackageManager pm = getPackageManager();
				try {

					Intent waIntent = new Intent(Intent.ACTION_SEND);
					waIntent.setType("text/plain");
					String text = "Hi ! friends I am in trouble " + saidText
							+ "My Current Location is " + addressText;

					PackageInfo info = pm.getPackageInfo("com.whatsapp",
							PackageManager.GET_META_DATA);
					// Check if package exists or not. If not then code
					// in catch block will be called
					waIntent.setPackage("com.whatsapp");

					waIntent.putExtra(Intent.EXTRA_TEXT, text);
					startActivity(Intent.createChooser(waIntent, "Share with"));

				} catch (NameNotFoundException e) {
					Toast.makeText(this, "WhatsApp not Installed",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}

		}
	}
}
