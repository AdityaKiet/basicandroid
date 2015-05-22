package com.teamAndappers.womensafety;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class EndCallListener extends PhoneStateListener {
	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		if (TelephonyManager.CALL_STATE_RINGING == state) {
		}
		if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
			// wait for phone to go offhook (probably set a boolean flag) so
			// you know your app initiated the call.
		}
		if (TelephonyManager.CALL_STATE_IDLE == state) {
			Log.d("log", "restarted madarchod");
		}
	}
}