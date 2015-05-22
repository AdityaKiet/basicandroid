package ainna.acup.slidingmenu;

import java.io.IOException;
import java.util.ArrayList;

import ainaa.acup.data.GlobalData;
import ainaa.acup.dto.FileExplorerDTO;
import ainaa.acup.slidingmenu.adapter.CustomAdapter;
import ainna.acup.client.R;
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

public class FileExplorerFragment extends Fragment {
	private LinearLayout linearLayout;
	ListView listview;
	String[] file = new String[200];
	Integer[] icon = new Integer[200];
	ArrayList<FileExplorerDTO> fileList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		setList();

		linearLayout = (LinearLayout) inflater.inflate(
				R.layout.fragment_file_explorer, container, false);
		listview = (ListView) linearLayout.findViewById(R.id.listView1);
		CustomAdapter adapter = new CustomAdapter(getActivity(), file, icon);
		listview.setAdapter(adapter);

		try {
			(((GlobalData) GlobalData.getContext())).getObjectOut()
					.writeObject(new String("fileExplorer"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getActivity(), file[arg2], Toast.LENGTH_LONG)
						.show();

			}
		});
		return linearLayout;
	}

	@SuppressWarnings({ "unchecked" })
	private void setList() {
		try {
			fileList = (ArrayList<FileExplorerDTO>) (((GlobalData) GlobalData
					.getContext())).getObjectIn().readObject();
			int i = 0;
			for (FileExplorerDTO file : fileList) {
				this.file[i] = file.getName();
				if (file.getIsDirectory() == false) {
					this.icon[i] = R.drawable.ic_file;
				} else if (file.getIsDirectory() == true) {
					this.icon[i] = R.drawable.ic_folder;
				} else {
					this.icon[i] = R.drawable.ic_unknown_file;
				}
			}
		} catch (Exception e) {

		}
	}
}