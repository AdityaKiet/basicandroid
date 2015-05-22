package com.example.sharmaproj;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	Button vlc, firefox, shutdown, sleep, restart, chrome, textEditor,
			terminal, lock, connect, run;
	static TextView txtOutput;
	EditText command;
	static String ip;
	InetAddress host;
	Socket socket;
	int port;
	DataInputStream din;
	DataOutputStream dout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		vlc = (Button) findViewById(R.id.btnVLC);
		firefox = (Button) findViewById(R.id.btnFirefox);
		sleep = (Button) findViewById(R.id.btnSleep);
		shutdown = (Button) findViewById(R.id.btnShutdown);
		restart = (Button) findViewById(R.id.btnRestart);
		lock = (Button) findViewById(R.id.btnLock);
		command = (EditText) findViewById(R.id.editText1);
		txtOutput = (TextView) findViewById(R.id.txtout);
		connect = (Button) findViewById(R.id.btnConnect);
		textEditor = (Button) findViewById(R.id.btnTextEditor);
		chrome = (Button) findViewById(R.id.btnChrome);
		terminal = (Button) findViewById(R.id.btnTerminal);
		run = (Button) findViewById(R.id.btnRun);

		vlc.setOnClickListener(this);
		firefox.setOnClickListener(this);
		shutdown.setOnClickListener(this);
		chrome.setOnClickListener(this);
		connect.setOnClickListener(this);
		run.setOnClickListener(this);
		sleep.setOnClickListener(this);
		restart.setOnClickListener(this);
		lock.setOnClickListener(this);
		textEditor.setOnClickListener(this);
		terminal.setOnClickListener(this);

	}


	@Override
	public void onClick(View v) {

		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnVLC:
			ClientThread.sendCommands("vlc");
			break;
		case R.id.btnTerminal:
			ClientThread.sendCommands("gnome-terminal");
			break;
		case R.id.btnChrome:
			ClientThread.sendCommands("chromium-browser");
			break;
		case R.id.btnFirefox:
			ClientThread.sendCommands("firefox");
			break;
		case R.id.btnLock:
			ClientThread.sendCommands("gnome-screensaver-command -l");
			break;
		case R.id.btnRestart:
			ClientThread.sendCommands("reboot");
			break;
		case R.id.btnShutdown:
			ClientThread.sendCommands("poweroff");
			break;
		case R.id.btnSleep:
			ClientThread.sendCommands("pmi action suspend");
			break;
		case R.id.btnTextEditor:
			ClientThread.sendCommands("gedit");
			break;
		case R.id.btnRun:
			String cmd = command.getText().toString();
			ClientThread.sendCommands(cmd);
			;
			break;
		case R.id.btnConnect:
			ip = command.getText().toString();
			new Thread(new ClientThread()).start();
			break;
		}

	}

	public static void setOutput(String message) {
		txtOutput.setText(message);
	}

}
