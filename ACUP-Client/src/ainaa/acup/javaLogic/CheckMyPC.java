package ainaa.acup.javaLogic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import ainaa.acup.dto.CheckMyPcDTO;

public class CheckMyPC{ 
	Socket socket = null;
	ArrayList<CheckMyPcDTO> myPcList = new ArrayList<CheckMyPcDTO>();
	
	private ObjectInputStream objectIn;
	
	
/*	public ArrayList<CheckMyPcDTO> getMyPC() throws IOException{ 
	
	String ipString = new GetLocalIP().getLocalIPAddress();
	 Log.d("local ip", ipString);
	String[] ipNos = ipString.split("\\.");
	
	byte[] ip = {(byte)192, (byte)168, (byte) Integer.parseInt(ipNos[2]), 0}; // for 192.168.0.x addresses
	
	for (int i = 0; i <= 254; i++)
	{
	    ip[3] = (byte) i;
	    InetAddress address = InetAddress.getByAddress(ip);
	   if(address.isReachable(5))
	   {
		   try
		   {
			CheckMyPcDTO myPc = new CheckMyPcDTO();
		    socket = new Socket(address, 8000);
		    new ObjectOutputStream(socket.getOutputStream()).writeObject(new String("check"));
		    objectIn =  new ObjectInputStream(socket.getInputStream());
		    myPc.setIp(address.toString());
		    myPc.setPcName((String)objectIn.readObject());
		    myPc.setPlatform((String)objectIn.readObject());
		    myPcList.add(myPc);
		    socket.close();
		   }catch(Exception e)
		   {
			   
		   }
		  
	   }
	}
		return myPcList;
} */

	public ArrayList<CheckMyPcDTO> getMyPC() throws IOException{ 
		String ipString = "192.168.137.224";
		try
		   {
			CheckMyPcDTO myPc = new CheckMyPcDTO();
		    socket = new Socket(ipString, 8000);
		    new ObjectOutputStream(socket.getOutputStream()).writeObject(new String("check"));
		    objectIn =  new ObjectInputStream(socket.getInputStream());
		    myPc.setIp(ipString.toString());
		    myPc.setPcName((String)objectIn.readObject());
		    myPc.setPlatform((String)objectIn.readObject());
		    myPcList.add(myPc);
		    socket.close();
		   }catch(Exception e)
		   {
			   
		   }
		return myPcList;
	} 

}