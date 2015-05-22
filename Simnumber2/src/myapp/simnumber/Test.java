package myapp.simnumber;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;

public class Test extends Activity {

	SmsManager smsManager;

	SharedPreferences sp = null;
	String sim1num;
	String sim2num;
	String sim3num;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendms);
		sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

		sim1num = sp.getString("sim1", null);
		sim2num = sp.getString("sim2", null);
		sim3num = sp.getString("sim3", null);

		new AlertDialog.Builder(this)
				.setMessage("Do you want to check your security system ?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								sendMsg();
								finish();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

						sendMsg();
						finish();
					}
				}).show();
	}


	private void sendMsg() {
		if (sim1num != null && !sim1num.equals("")) {
			smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(sim1num, null, sim1num, null, null);
		}
		if (sim2num != null && !sim2num.equals("")) {
			smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(sim2num, null, sim2num, null, null);
		}
		if (sim3num != null && !sim3num.equals("")) {
			smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(sim3num, null, sim3num, null, null);
		}
	}
}
