package ainaa.acup.application.tabsswipe.fragments;

import ainna.acup.client.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TextEditorFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.texteditor_fragment,
				container, false);

		return rootView;
	}
}
