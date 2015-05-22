package com.canteen.profile.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andappers.canteenproject.R;

public class LogoutFragment extends Fragment {
	
	public LogoutFragment(){}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.splash, container, false);
         
        return rootView;
    }
}
