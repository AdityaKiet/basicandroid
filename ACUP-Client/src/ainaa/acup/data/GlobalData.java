package ainaa.acup.data;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.app.Application;
import android.content.Context;

public class GlobalData extends Application{

	private Socket socket = new Socket();
	private ObjectInputStream objectIn ;
	private ObjectOutputStream objectOut;
	private String platform;
	
	
	private static Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
	}

	public static Context getContext() {
		return mContext;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ObjectInputStream getObjectIn() {
		return objectIn;
	}

	public void setObjectIn(ObjectInputStream objectIn) {
		this.objectIn = objectIn;
	}

	public ObjectOutputStream getObjectOut() {
		return objectOut;
	}

	public void setObjectOut(ObjectOutputStream objectOut) {
		this.objectOut = objectOut;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	
}
