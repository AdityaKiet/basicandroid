package ainna.acup.slidingmenu;

import ainna.acup.client.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ComplierFragment extends Fragment {
	public ComplierFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_compiler, container,
				false);

		return rootView;
	}
}
