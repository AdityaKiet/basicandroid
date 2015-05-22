package info.androidhive.tabsswipe;

import info.androidhive.slidingmenu.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class GamesFragment extends Fragment {

	private LinearLayout lLayoutFrgEmpresas;
	private Button btnNewEmpresa;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		lLayoutFrgEmpresas = (LinearLayout) inflater.inflate(
				R.layout.fragment_games, container, false);
		btnNewEmpresa = (Button) lLayoutFrgEmpresas.findViewById(R.id.btn);
		btnNewEmpresa.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(
						"info.androidhive.slidingmenu.MainActivity");
				startActivity(intent);
				Log.d(getClass().getCanonicalName(),
						"Se ha presionado el botton de nueva empresa");

			}
		});
		return lLayoutFrgEmpresas;
	}
}
