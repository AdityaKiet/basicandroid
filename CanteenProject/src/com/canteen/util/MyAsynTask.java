package com.canteen.util;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;

import com.canteen.global.AllClassItems;

public class MyAsynTask extends AsyncTask<Object, Object, Object> {
	Context context;
	InputStream is;
	HttpEntity entity;
	String[] imageId;
	private ArrayList<Bitmap> imagesList;

	public MyAsynTask(Context context, String[] imageId) {
		System.out.print("Aditya");
		this.context = context;
		this.imageId = imageId;

	}

	private ProgressDialog progressDialog = new ProgressDialog(context);
	String result = "";

	protected void onPreExecute() {

		progressDialog.setTitle("Loading.....");
		progressDialog.setMessage("Please Wait.....");
		progressDialog.show();
		progressDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface arg0) {
				MyAsynTask.this.cancel(true);
			}
		});
		super.onPreExecute();
	}

	protected Void doInBackground(Object... params) {
		imagesList = new ArrayList<>();
		try {
			for (String index : imageId) {
				String imagesUrl = "http://canteenproject.netau.net/photo/i"
						+ index + ".jpg";
				URL url = new URL(imagesUrl);
				Bitmap bmp = BitmapFactory.decodeStream(url.openConnection()
						.getInputStream());
				imagesList.add(bmp);
			}
			((AllClassItems) context.getApplicationContext())
					.setImages(imagesList);

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context.getApplicationContext(),
					"Exception raised " + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}

		return null;
	}

	protected void onPostExecute(Void v) {
		progressDialog.dismiss();

		super.onPostExecute(v);
	}

}
