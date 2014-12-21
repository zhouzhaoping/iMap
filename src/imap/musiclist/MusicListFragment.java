package imap.musiclist;

import imap.nettools.NetThread;
import imap.nettools.Variable;

import java.util.ArrayList;

import com.example.imap.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MusicListFragment extends Fragment {
	int type;
	String id;
	ListView listview;
	TextView preview;
	
	public MusicListFragment(int t, String str)
	{
		id = str;
		type = t;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.musiclist_popular, container, false);
		findViews(rootView);
		showResults();
		setListensers();
		return rootView;
	}
	
	private void findViews(View rootView)
	{		
		listview = (ListView) rootView.findViewById(R.id.listView_popilar);
		preview = null;
	}
	
	private void showResults() 
	{
		MusicItemAdapter mia = new MusicItemAdapter(getActivity(), getData());
		listview.setAdapter(mia);
		listview.setCacheColorHint(0);
	}
	
	private void setListensers()
	{
		listview.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView detail = (TextView) view.findViewById(R.id.description);
				if (preview != null)
					preview.setVisibility(View.GONE);
				if (preview != detail)	
				{
					detail.setVisibility(View.VISIBLE);
					preview = detail;
				}
				else
					preview = null;
			}
		});
	}
	
	private ArrayList<MusicItem> getData()
	{
		ArrayList<MusicItem> list = new ArrayList<MusicItem>();
		
		SharedPreferences sp = getActivity().getSharedPreferences("imap", 0);
		String username = sp.getString("username", "");
		String password = sp.getString("password", "");
		
		NetThread netthread = new NetThread(username, password);
		
		if (type == 0)
			netthread.makeParam(Variable.getHotVoiceFromSpot, id, "0", "0", "0");
		else if (type == 1)
			netthread.makeParam(Variable.getNewVoiceFromSpot, id, "0", "0", "0");
		
		int returnCode = netthread.beginDeal();
		
		if (returnCode == 0)
		{			 
			if (type == 0)
				return netthread.getVoiceList("hot");
			else if (type == 1)
				return netthread.getVoiceList("new");
			
		}
		else if (returnCode == -1)
		{
			  Toast.makeText(getActivity(), "网络错误！", 
		                 Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(getActivity(), Variable.errorCode[returnCode] + "！", 
		                 Toast.LENGTH_SHORT).show();
		}
		return list;
	} 
}
