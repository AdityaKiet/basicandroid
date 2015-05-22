package info.androidhive.tabsswipe;

import info.androidhive.slidingmenu.R;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.androidhive.database.DBActivity;
import com.androidhive.dto.PCDTO;

public class TopRatedFragment extends Fragment {

	private LinearLayout linearLayout;
	private Button checkMyPC, clearList;
	private String[] pc_Names, previous_pc_names = {};
	private ListView pcListView;
	private List<PCDTO> allPcs;
	private ArrayList<String> old_pc_array_list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		old_pc_array_list = new ArrayList<String>();
		linearLayout = (LinearLayout) inflater.inflate(
				R.layout.fragment_top_rated, container, false);
		checkMyPC = (Button) linearLayout.findViewById(R.id.checkPCWifi);
		clearList = (Button) linearLayout.findViewById(R.id.clearListPCWifi);
		pcListView = (ListView) linearLayout.findViewById(R.id.listPCWifi);
		DBActivity dbActivity = new DBActivity(getActivity());
		dbActivity.open();
		allPcs = dbActivity.allPreviousPCs();
		dbActivity.close();
		if (allPcs.size() == 0) {
			old_pc_array_list.add("None PCs have been stored so far");
		}
		for (int i = 0; i < allPcs.size(); i++) {
			if (old_pc_array_list.contains("None PCs have been stored so far")) {
				old_pc_array_list.remove("None PCs have been stored so far");
			}
			old_pc_array_list.add(allPcs.get(i).getPc_name().toString());
		}

		previous_pc_names = old_pc_array_list
				.toArray(new String[old_pc_array_list.size()]);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, previous_pc_names);
		pcListView.setAdapter(adapter);
		checkMyPC.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showAlertDialogBox();
			}
		});
		clearList.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(getActivity())
						.setIcon(R.drawable.ic_launcher)
						.setTitle("Warning")
						.setMessage(
								"Are you sure you want to clear all previously stored pcs ?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										DBActivity db = new DBActivity(
												getActivity());
										db.open();
										if (db.deletePreviousPCs()) {
											Toast.makeText(
													getActivity(),
													"All Previously stored pcs have been deleted",
													Toast.LENGTH_LONG).show();
											Intent intent = new Intent(
													"info.androidhive.tabsswipe.MainActivity");
											startActivity(intent);
											getActivity().finish();
										} else {

										}
										db.close();
									}
								}).setNegativeButton("No", null).show();

			}
		});
		return linearLayout;
	}

	private void showAlertDialogBox() {
		getPCNames();
		new AlertDialog.Builder(getActivity())
				.setTitle("Select PC")
				.setOnItemSelectedListener(null)
				.setItems(pc_Names, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int choice) {
						Log.d("log", pc_Names[choice]);
						openPopUpForPin(pc_Names[choice]);
					}

				})
				.setCancelable(true)
				.setPositiveButton("Refresh",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {

								showAlertDialogBox();
							}
						}).setNegativeButton("Cancel", null).show();
		Log.d(getClass().getCanonicalName(), "Button Clicked");

	}

	/**
	 * Get The Names of PCs Inside this method to array List.
	 */
	private void getPCNames() {
		ArrayList<String> pc_names_list = new ArrayList<String>();
		pc_names_list.add("PC One");
		pc_names_list.add("PC Two");
		pc_Names = pc_names_list.toArray(new String[pc_names_list.size()]);
	}

	private void openPopUpForPin(final String choice) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
		alertDialog.setTitle(choice);
		alertDialog.setMessage("Enter Password");
		final EditText input = new EditText(getActivity());
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		input.setLayoutParams(lp);
		input.setHint("Enter you pin here");
		alertDialog.setView(input);
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.setPositiveButton("Confirm",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						confirmPin(input.getText().toString().trim(), choice);
					}
				});
		alertDialog.setNegativeButton("Cancel", null);
		alertDialog.show();
	}

	/**
	 * Pin is the value of pin user entered.
	 * 
	 * @param pin
	 */
	private void confirmPin(String pin, String pc_name) {
		Log.d(getClass().getCanonicalName(), pin);
		if (pin.equals("Aditya")) {
			PCDTO pcdto = new PCDTO();
			pcdto.setPc_id("123");
			pcdto.setPin(pin);
			pcdto.setPc_name(pc_name);
			pcdto.setPc_ip("12345");
			pcdto.getPc_last_connected_time();
			DBActivity db = new DBActivity(getActivity());
			db.open();
			db.createEntry(pcdto);
			db.close();
			Intent intent = new Intent(
					"info.androidhive.slidingmenu.MainActivity");
			startActivity(intent);
			getActivity().finish();
		} else {
			Toast.makeText(getActivity(), "Wrong PIN Entered",
					Toast.LENGTH_LONG).show();
		}
	}

}
