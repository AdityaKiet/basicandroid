package ainna.acup.slidingmenu;

import java.io.IOException;

import ainaa.acup.data.GlobalData;
import ainna.acup.client.R;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ComplierFragment extends Fragment {
	Button run;
	EditText code;
	
	
	public ComplierFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_compiler, container,
				false);

		run = (Button) rootView.findViewById(R.id.btnCompile);
		code = (EditText) rootView.findViewById(R.id.editText1);
		run.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("in compiler", "reached");
				String codes = code.getText().toString().trim();
				try {
					Log.d("code is ", codes);
					(((GlobalData) GlobalData.getContext())).getObjectOut().writeObject(new String(codes));
					Log.d("code is ", "code written");
					String output = "";
					try {
						output = (String) (((GlobalData) GlobalData.getContext())).getObjectIn().readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Log.d("output is  is ", output);
					code.setText(output);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.d("exception in compiler", e.getMessage());
				}
			}
		});
		return rootView;
	}
}
