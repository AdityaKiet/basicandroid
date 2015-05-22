package ainna.acup.slidingmenu;

import ainna.acup.client.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MouseFragment extends Fragment {
	LinearLayout layout;
	Button btnLeft, btnRight;

	public MouseFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		layout = (LinearLayout) inflater.inflate(R.layout.fragment_mouse,
				container, false);
		btnLeft = (Button) layout.findViewById(R.id.btnleft);
		btnRight = (Button) layout.findViewById(R.id.btnright);
		btnLeft.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			}
		});
		btnRight.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			}
		});
		return layout;
	}

}
