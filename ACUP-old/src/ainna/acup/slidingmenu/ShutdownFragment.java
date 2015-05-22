package ainna.acup.slidingmenu;

import ainaa.acup.javaLogic.Data;
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

	public ShutdownFragment() {
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
				Data.dout.writeUTF(Data.reader.getHibernate());

				break;
			case R.id.btnLock:
				Log.d("lock before", "reached");
				Data.dout.writeUTF(Data.reader.getLock());
				Log.d("lock after", "reached");
				break;
			case R.id.btnRestart:
				Data.dout.writeUTF(Data.reader.getRestart());
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

				Data.dout.writeUTF(Data.reader.getShutdown());
				break;
			case R.id.btnSleep:
				Data.dout.writeUTF(Data.reader.getSleep());
				break;
			}
		} catch (Exception e) {
			Log.d("exception occured", e.getStackTrace().toString());
		}
	}
}
