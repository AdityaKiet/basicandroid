package com.example.sharmaproj;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientThread implements Runnable {
	static DataOutputStream dout;
	static DataInputStream din;

	public static Socket socket;

	public void run() {

		try {

			socket = new Socket(MainActivity.ip, 5000);
			dout = new DataOutputStream(socket.getOutputStream());
			din = new DataInputStream(socket.getInputStream());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public static void sendCommands(String message) {
		try {
			{

				dout.writeUTF(message);

			}

		} catch (IOException ie) {
			System.out.println(ie);
		}
	}
}
