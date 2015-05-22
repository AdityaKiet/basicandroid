package ainna.acup.util;

import ainna.acup.client.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {
	private final Activity context;
	private final String[] strings;
	private final Integer[] imageId;

	public CustomAdapter(Activity context, String[] strings, Integer[] imageId) {
		super(context, R.layout.list_single, strings);
		this.context = context;
		this.strings = strings;
		this.imageId = imageId;
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.list_single, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		txtTitle.setText(strings[position]);
		imageView.setImageResource(imageId[position]);
		return rowView;
	}
}
