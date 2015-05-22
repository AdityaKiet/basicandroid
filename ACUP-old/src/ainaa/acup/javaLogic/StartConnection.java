package ainaa.acup.javaLogic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class StartConnection extends Thread {

	private String ip;
	private int port = 8000;
	public StartConnection(String ip){
		
		this.ip = ip.substring(1);
		start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			Log.d("ipsdjf", this.ip);
			Log.d(" starting connection", "start connection doen");
			Data.socket = new Socket(this.ip, port);
			Log.d("connection", "start connection doen");
			Data.din = new DataInputStream(Data.socket.getInputStream());
			Data.dout = new DataOutputStream(Data.socket.getOutputStream());
			
			Data.dout.writeUTF("");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
