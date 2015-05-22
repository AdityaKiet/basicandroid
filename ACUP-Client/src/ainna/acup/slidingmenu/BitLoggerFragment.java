package ainna.acup.slidingmenu;

import ainna.acup.client.R;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class BitLoggerFragment extends Fragment {
	LinearLayout layout;
	Spinner hourspinner, minsspinner;
	Button button;
	String hours = "0", minutes = "0";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		layout = (LinearLayout) inflater.inflate(R.layout.fragment_bitlogger,
				container, false);
		hourspinner = (Spinner) layout.findViewById(R.id.spnhours);
		minsspinner = (Spinner) layout.findViewById(R.id.spnmins);
		button = (Button) layout.findViewById(R.id.btnsettime);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.hours,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		hourspinner.setAdapter(adapter);
		hourspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				hours = (String) hourspinner.getItemAtPosition(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				getActivity(), R.array.mins,
				android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		minsspinner.setAdapter(adapter1);
		minsspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				minutes = (String) minsspinner.getItemAtPosition(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				new AlertDialog.Builder(getActivity())
						.setIcon(R.drawable.ic_launcher)
						.setTitle("Warning")
						.setMessage("Do you want to download the file ??")
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										downloadFile();
										Log.d("hours", hours);
										Log.d("mins", minutes);
									}

									private void downloadFile() {

									}
								}).setNegativeButton("No", null).show();
			}
		});
		return layout;
	}
}
