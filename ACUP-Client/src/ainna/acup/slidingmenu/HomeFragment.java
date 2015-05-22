package ainna.acup.slidingmenu;

import ainna.acup.client.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HomeFragment extends Fragment {
	private LinearLayout lLayoutFrgEmpresas;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		lLayoutFrgEmpresas = (LinearLayout) inflater.inflate(
				R.layout.fragment_file_explorer, container, false);
		
		return lLayoutFrgEmpresas;
	}

}
