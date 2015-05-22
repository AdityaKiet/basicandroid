package com.canteen.order;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.andappers.canteenproject.R;

public class ViewItem extends Activity {
	int image, price;
	String name;
	TextView tv;
	ImageView imageView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewitem);
		imageView = (ImageView) findViewById(R.id.imageProduct);
		tv = (TextView) findViewById(R.id.test);
		Bundle bundle = getIntent().getExtras();
		name = bundle.getString("item");
		image = bundle.getInt("image");
		price = bundle.getInt("price");
		imageView.setImageResource(image);
		tv.setText(name + image + price);
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(ViewItem.this)
				.setMessage("Have you placed your order ??")
				.setTitle("Alert !!")
				.setCancelable(true)
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("Yes !!",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								ViewItem.super.onBackPressed();
							}
						}).setNegativeButton("No!", null).show();

	}

}
