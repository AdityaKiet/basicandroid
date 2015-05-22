package ainaa.acup.application.tabsswipe.adapter;

import ainaa.acup.application.tabsswipe.fragments.PresentationFragment;
import ainaa.acup.application.tabsswipe.fragments.BrowserFragment;
import ainaa.acup.application.tabsswipe.fragments.TextEditorFragment;
import ainaa.acup.application.tabsswipe.fragments.TrashFragment;
import ainaa.acup.application.tabsswipe.fragments.VlcFragment;
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
			return new VlcFragment();
		case 1:
			// Games fragment activity
			return new PresentationFragment();
		case 2:
			// Movies fragment activity
			return new BrowserFragment();
		case 3:
			return new TextEditorFragment();
		case 4:
			return new TrashFragment();

		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 5;
	}

}
