package ainna.acup.tabsswipe.adapter;

import ainna.acup.tabsswipe.WANFragment;
import ainna.acup.tabsswipe.HelpFragment;
import ainna.acup.tabsswipe.LANFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new LANFragment();
		case 1:
			// Games fragment activity
			return new WANFragment();
		case 2:
			// Movies fragment activity
			return new HelpFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
