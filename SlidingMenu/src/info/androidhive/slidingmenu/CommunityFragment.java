package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class CommunityFragment extends Fragment {
	private LinearLayout lLayoutFrgEmpresas;
	private Button btnNewEmpresa;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		lLayoutFrgEmpresas = (LinearLayout) inflater.inflate(
				R.layout.fragment_community, container, false);
		btnNewEmpresa = (Button) lLayoutFrgEmpresas.findViewById(R.id.button1);
		btnNewEmpresa.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Log.d(getClass().getCanonicalName(),
						"Se ha presionado el botton de nueva empresa");
			}
		});
		return lLayoutFrgEmpresas;
	}

}
