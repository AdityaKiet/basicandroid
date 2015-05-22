package ainaa.acup.slidingmenu.adapter;



import ainna.acup.client.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {

	String[] web;
	Integer[] ids;
	Activity context;

	public CustomAdapter(Activity context, String[] web, Integer ids[]) {
		super(context, R.layout.file_explorer_list_view, web);
		this.context = context;
		this.ids = ids;
		this.web = web;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.file_explorer_list_view, null, true);
		TextView txtView = (TextView) rowView.findViewById(R.id.txt);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		txtView.setText(web[position]);
		imageView.setImageResource(ids[position]);
		return rowView;
	}

}

