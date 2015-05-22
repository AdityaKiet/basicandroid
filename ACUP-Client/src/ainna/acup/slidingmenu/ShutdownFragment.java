package ainna.acup.slidingmenu;

import ainaa.acup.config.LinuxSystemUtilsConfig;
import ainaa.acup.data.GlobalData;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ShutdownFragment extends Fragment implements OnClickListener {
	private LinearLayout layout;
	Button lock, shutdown, sleep, restart, hibernate;
	EditText etpassword;
	String password;
	private LinuxSystemUtilsConfig config;

	public ShutdownFragment() {
		if((((GlobalData) GlobalData.getContext())).getPlatform().trim().equalsIgnoreCase("windows"))
		{
			
		}
		else if((((GlobalData) GlobalData.getContext())).getPlatform().trim().equalsIgnoreCase("linux"))
		{
			 config = new LinuxSystemUtilsConfig(); 
		}
		else if((((GlobalData) GlobalData.getContext())).getPlatform().trim().equalsIgnoreCase("mac"))
		{
			
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		layout = (LinearLayout) inflater.inflate(R.layout.fragment_shutdown,
				container, false);
		lock = (Button) layout.findViewById(R.id.btnLock);
		shutdown = (Button) layout.findViewById(R.id.btnShutDown);
		sleep = (Button) layout.findViewById(R.id.btnSleep);
		restart = (Button) layout.findViewById(R.id.btnRestart);
		hibernate = (Button) layout.findViewById(R.id.btnHibernate);

		lock.setOnClickListener(this);
		shutdown.setOnClickListener(this);
		sleep.setOnClickListener(this);
		restart.setOnClickListener(this);
		hibernate.setOnClickListener(this);
		return layout;
	}

	@Override
	public void onClick(View v) {
		try {
			switch (v.getId()) {

			case R.id.btnHibernate:
				(((GlobalData) GlobalData.getContext())).getObjectOut().writeUTF(new String(config.getHibernate()));

				break;
			case R.id.btnLock:
				Log.d("lock before", "reached");
				(((GlobalData) GlobalData.getContext())).getObjectOut().writeObject(new String(config.getLock()));
				Log.d("lock after", "reached");
				break;
			case R.id.btnRestart:
				(((GlobalData) GlobalData.getContext())).getObjectOut().writeObject(new String(config.getRestart()));
				break;
			case R.id.btnShutDown:

				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						getActivity());
				alertDialog.setTitle("Reset Password");
				etpassword = new EditText(getActivity());
				etpassword.setHint("Enter Password");
				LinearLayout layout = new LinearLayout(getActivity());
				layout.setOrientation(LinearLayout.VERTICAL);
				layout.addView(etpassword);
				alertDialog.setView(layout);
				alertDialog.setMessage("Enter your password");
				alertDialog.setIcon(R.drawable.ic_launcher);
				alertDialog.setNegativeButton("Cancel", null);
				alertDialog.setPositiveButton("Confirm",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								if (etpassword.getText() == null
										|| etpassword.getText().toString()
												.trim().equals("")) {
									new AlertDialog.Builder(getActivity())
											.setMessage(
													"Password can't be blank")
											.setTitle("Warning !!")
											.setCancelable(true)
											.setIcon(R.drawable.ic_launcher)
											.setNegativeButton("Okay", null)
											.show();
								} else {
									password = etpassword.getText().toString();
								}
							}
						});
				alertDialog.show();

				(((GlobalData) GlobalData.getContext())).getObjectOut().writeObject(new String(config.getShutdown()));
				break;
			case R.id.btnSleep:
				(((GlobalData) GlobalData.getContext())).getObjectOut().writeObject(new String(config.getSleep()));
				break;
			}
		} catch (Exception e) {
			Log.d("exception occured", e.getStackTrace().toString());
		}
	}
}
