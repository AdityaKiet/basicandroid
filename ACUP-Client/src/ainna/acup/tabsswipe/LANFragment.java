package ainna.acup.tabsswipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ainaa.acup.data.GlobalData;
import ainaa.acup.database.DBActivity;
import ainaa.acup.dto.CheckMyPcDTO;
import ainaa.acup.dto.PCDTO;
import ainaa.acup.javaLogic.CheckMyPC;
import ainaa.acup.javaLogic.StartConnection;
import ainna.acup.client.R;
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

public class LANFragment extends Fragment {

	private LinearLayout linearLayout;
	private Button connectMyPC, clearList;
	private String[] pc_Names, previous_pc_names = {};
	private ListView storedPCListView;
	private List<PCDTO> storePCs;
	private ArrayList<String> StoredPCsList;
	ArrayList<CheckMyPcDTO> myPCList;
	int i = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		StoredPCsList = new ArrayList<String>();
		linearLayout = (LinearLayout) inflater.inflate(
				R.layout.tab_fragment_lan, container, false);
		connectMyPC = (Button) linearLayout.findViewById(R.id.checkPCWifi);
		clearList = (Button) linearLayout.findViewById(R.id.clearListPCWifi);
		storedPCListView = (ListView) linearLayout
				.findViewById(R.id.listPCWifi);
		DBActivity dbActivity = new DBActivity(getActivity());
		dbActivity.open();
		storePCs = dbActivity.allPreviousPCs();
		dbActivity.close();
		if (storePCs.size() == 0) {
			StoredPCsList.add("None PCs have been stored so far");
		}
		for (int i = 0; i < storePCs.size(); i++) {
			if (StoredPCsList.contains("None PCs have been stored so far")) {
				StoredPCsList.remove("None PCs have been stored so far");
			}
			StoredPCsList.add(storePCs.get(i).getPc_name().toString());
		}
		previous_pc_names = StoredPCsList.toArray(new String[StoredPCsList
				.size()]);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, previous_pc_names);
		storedPCListView.setAdapter(adapter);
		connectMyPC.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try {
					showAlertDialogBox();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		clearList.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(getActivity())
						.setIcon(R.drawable.ic_launcher)
						.setTitle("Warning")
						.setMessage(
								"Are you sure you want to clear all previously stored PCs?")
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
													"ainna.acup.tabsswipe.MainActivity");
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

	private void showAlertDialogBox() throws IOException {
		getPCNames();

		new AlertDialog.Builder(getActivity())
				.setTitle("Select PC")
				.setOnItemSelectedListener(null)
				.setItems(pc_Names, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int choice) {
						Log.d("log", pc_Names[choice]);
						/*
						 * if(myPCList.get(choice).getPlatform().trim().
						 * equalsIgnoreCase("windows")) {
						 * 
						 * } else if(myPCList.get(choice).getPlatform().trim().
						 * equalsIgnoreCase("linux")) { (((GlobalData)
						 * GlobalData.getContext())).setPlatform("linux"); }
						 * else if(myPCList.get(choice).getPlatform().trim().
						 * equalsIgnoreCase("mac")) {
						 * 
						 * } else { new AlertDialog.Builder(getActivity())
						 * .setTitle("Error") .setOnItemSelectedListener(null)
						 * .setMessage
						 * ("Your pc is not supporting the requirements");
						 * getActivity().closeContextMenu(); return; }
						 */

						new StartConnection(myPCList.get(choice).getIp(),
								getActivity().getApplicationContext());
						openPopUpForPin(myPCList.get(choice));
					}

				})
				.setCancelable(true)
				.setPositiveButton("Refresh",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {

								try {
									showAlertDialogBox();
								} catch (IOException e) {
									try {
										throw e;
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							}
						}).setNegativeButton("Cancel", null).show();
		Log.d(getClass().getCanonicalName(), "Button Clicked");

	}

	/**
	 * Get The Names of PCs Inside this method to array List.
	 * 
	 * @throws IOException
	 */
	private void getPCNames() throws IOException {
		ArrayList<String> pc_names_list = new ArrayList<String>();
		myPCList = new ArrayList<CheckMyPcDTO>();
		CheckMyPC checkMyPcObj = new CheckMyPC();
		myPCList = checkMyPcObj.getMyPC();
		Log.d("log", myPCList.toString());
		for (CheckMyPcDTO myPc : myPCList) {
			pc_names_list.add(myPc.getPcName());
		}
		pc_Names = pc_names_list.toArray(new String[pc_names_list.size()]);
	}

	private void openPopUpForPin(final CheckMyPcDTO checkMyPcDTO) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
		alertDialog.setTitle(checkMyPcDTO.getPcName());
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
						try {

							/*
							 * Data.dout.writeUTF(input.getText().toString().trim
							 * ());
							 */

							confirmPin(input.getText().toString().trim(),
									checkMyPcDTO);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		alertDialog.setNegativeButton("Cancel", null);
		alertDialog.show();
	}

	/**
	 * Pin is the value of pin user entered.
	 * 
	 * @param pin
	 * @throws IOException
	 */
	private void confirmPin(String pin, CheckMyPcDTO checkMyPcDTO)
			throws IOException {
		Log.d("ad", pin);
		/*
		 * Log.d("sajdshd", (((GlobalData)
		 * GlobalData.getContext())).getObjectOut().toString());
		 */
		/*
		 * (((GlobalData)
		 * GlobalData.getContext())).getObjectOut().writeObject(new
		 * String(pin));
		 */
		String verified = "success";
		try {
			Log.d("log", getActivity().getApplicationContext().toString());
			Log.d("log", (((GlobalData) getActivity().getApplicationContext())
					.getObjectIn()).toString());
			verified = (String) (((GlobalData) getActivity()
					.getApplicationContext())).getObjectIn().readObject();
		} catch (ClassNotFoundException e) {
		}
		Log.d("fera", verified);
		if (verified.equals("success")) {
			PCDTO pcdto = new PCDTO();
			pcdto.setPc_id("1");
			pcdto.setPin(pin);
			pcdto.setPc_name(checkMyPcDTO.getPcName());
			pcdto.setPc_ip(checkMyPcDTO.getIp());
			pcdto.getPc_last_connected_time();
			DBActivity db = new DBActivity(getActivity());
			db.open();
			db.createEntry(pcdto);
			db.close();
			Intent intent = new Intent("ainna.acup.slidingmenu.MainActivity");
			startActivity(intent);
			getActivity().finish();
		} else {
			i++;

			Toast.makeText(getActivity(), verified, Toast.LENGTH_LONG).show();
			if (i == 5) {
				return;
			}
			openPopUpForPin(checkMyPcDTO);
		}
	}

}
