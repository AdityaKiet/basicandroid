package ainaa.acup.javaLogic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import ainaa.acup.data.GlobalData;
import android.content.Context;
import android.util.Log;

public class StartConnection extends Thread {
	private Context context;
	private String ip;
	private int port = 8000;

	public StartConnection(String ip, Context context) {
		this.context = context;
		this.ip = ip.substring(1);
		start();

	}

	@Override
	public void run() {
		super.run();
		try {
			Log.d("context", context.toString());
			Log.d("ipsdjf", this.ip);
			Log.d(" starting connection", "start connection doen");
			((GlobalData) context).setSocket(new Socket(this.ip, port));
			Log.d("connection", "start connection doen");
			Log.d("hi", ((GlobalData) context).getObjectIn().toString());
			((GlobalData) context).setObjectIn(new ObjectInputStream(
					(((GlobalData) context).getSocket().getInputStream())));
			((GlobalData) context).setObjectOut(new ObjectOutputStream(
					(((GlobalData) context).getSocket().getOutputStream())));

			((GlobalData) context).getObjectOut().writeObject(
					new String("authenticate"));

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
