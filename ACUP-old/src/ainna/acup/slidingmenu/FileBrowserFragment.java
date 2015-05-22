package ainna.acup.slidingmenu;

import java.util.ArrayList;

import ainaa.acup.dto.DirectoryDto;
import ainna.acup.client.R;
import ainna.acup.util.CustomAdapter;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class FileBrowserFragment extends Fragment {
	private LinearLayout linearLayout;
	private ArrayList<String> strings;
	private ArrayList<Integer> imageids;
	private ListView itemsList;
	private ArrayList<DirectoryDto> dtolist;

	public FileBrowserFragment(ArrayList<DirectoryDto> dtolist) {
		this.dtolist = dtolist;

	}

	public FileBrowserFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		linearLayout = (LinearLayout) inflater.inflate(
				R.layout.fragment_file_browser, container, false);
		itemsList = (ListView) linearLayout.findViewById(R.id.listAllItems);
		for (DirectoryDto directoryDto : dtolist) {
			strings.add(directoryDto.getDirectoryname());
			if (directoryDto.getIsDirectory()) {
				imageids.add(R.drawable.ic_launcher);
			} else {
				imageids.add(R.drawable.ic_home);
			}
			String filenames[] = strings.toArray(new String[strings.size()]);
			Integer images[] = imageids.toArray(new Integer[imageids.size()]);
			CustomAdapter adapter = new CustomAdapter(getActivity(), filenames,
					images);
			itemsList.setAdapter(adapter);
			itemsList.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if (dtolist.get(position).getIsDirectory()) {
						directoryMethod(position);
					} else {
						fileMethod(position);
					}
				}
			});
		}
		return linearLayout;
	}

	private void fileMethod(int position) {
		Toast.makeText(getActivity(), "File", Toast.LENGTH_LONG).show();
	}

	private void directoryMethod(int position) {
		Toast.makeText(getActivity(), "Directory", Toast.LENGTH_LONG).show();

	}
}
