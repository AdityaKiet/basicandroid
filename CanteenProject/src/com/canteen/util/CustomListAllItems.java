package com.canteen.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andappers.canteenproject.R;

public class CustomListAllItems extends ArrayAdapter<String> {
	private final Activity context;
	private final String[] web;

	// private Bitmap[] images;

	public CustomListAllItems(Activity context, String[] web) {
		super(context, R.layout.list_single, web);
		this.context = context;
		this.web = web;
		// this.images = images;
		Log.d("log", context.toString());
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.list_single, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		txtTitle.setText(web[position]);
		imageView.setImageResource(R.drawable.kiet);
		;
		return rowView;
	}
}
