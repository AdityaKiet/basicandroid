package ainna.acup.slidingmenu;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ainaa.acup.data.GlobalData;
import ainna.acup.client.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash);
try
{
		Socket socket = new Socket();
		ObjectOutputStream oo = new ObjectOutputStream(socket.getOutputStream());
		((GlobalData) getApplicationContext()).setObjectOut(oo);
		ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());
		((GlobalData) getApplicationContext()).setObjectIn(oi);
}
catch(Exception e)
{
	
}
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent intent = new Intent(
							"ainna.acup.tabsswipe.MainActivity");
					startActivity(intent);
				}
			}

		};

		timer.start();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
