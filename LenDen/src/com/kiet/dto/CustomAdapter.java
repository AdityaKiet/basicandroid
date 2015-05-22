package com.kiet.dto;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kei.lenden.R;

public class CustomAdapter extends ArrayAdapter<String> {

	String[] web;
	Activity context;

	public CustomAdapter(Activity context, String[] web) {
		super(context, R.layout.single_list, web);
		this.context = context;
		this.web = web;
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowiew = inflater.inflate(R.layout.single_list, null, true);
		TextView txtView = (TextView) rowiew.findViewById(R.id.txt);
		txtView.setText(web[position]);
		return rowiew;
	}

}
