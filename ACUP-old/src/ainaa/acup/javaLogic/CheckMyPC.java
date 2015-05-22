package ainaa.acup.javaLogic;

import java.io.IOException;
import java.util.ArrayList;

import android.util.Log;

public class CheckMyPC{ 
	ArrayList<CheckMyPcDTO> myPcList = new ArrayList<CheckMyPcDTO>();
	
	/*public ArrayList<CheckMyPcDTO> getMyPC() throws IOException{ 
	Socket socket = null;
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
			   Log.d("sdsfhj", address.toString());
			CheckMyPcDTO myPc = new CheckMyPcDTO();
		    socket = new Socket(address, 8000);
		    new DataOutputStream(socket.getOutputStream()).writeUTF("check");
		    myPc.setIp(address.toString());
		    myPc.setPcName(new DataInputStream(socket.getInputStream()).readUTF());
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
		String ipString = new GetLocalIP().getLocalIPAddress();
		 Log.d("local ip", ipString);
		CheckMyPcDTO myPc = new CheckMyPcDTO();
			  //  new DataOutputStream(socket.getOutputStream()).writeUTF("check");
			    myPc.setIp("/192.168.0.104");
			    myPc.setPcName("hp abhishek sharma");
			    myPcList.add(myPc);
			  //  socket.close();

			    return myPcList;
	} 


}