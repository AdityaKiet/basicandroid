package com.teamandappers.delhitourism;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterC extends ArrayAdapter<String> {
	String[] web;
	Integer[] ids;
	Activity context;

	public AdapterC(Activity context, String[] web, Integer ids[]) {
		super(context, R.layout.list, web);
		this.context = context;
		this.ids = ids;
		this.web = web;
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowiew = inflater.inflate(R.layout.list, null, true);
		TextView txtView = (TextView) rowiew.findViewById(R.id.txt);
		ImageView imageView = (ImageView) rowiew.findViewById(R.id.img);
		txtView.setText(web[position]);
		imageView.setImageResource(ids[position]);
		return rowiew;
	}

}
