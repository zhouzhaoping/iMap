package imap.me;

import imap.nettools.NetThread;
import imap.nettools.Variable;

import java.io.File;
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

public class MeListFragment extends Fragment {
	
	ListView listview;
	int type;
	
	TextView preview;
	
	public MeListFragment(int i) {
		type = i;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.musiclist_popular, container, false);
		findViews(rootView);
		showResults();
		return rootView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	}
	
	private void findViews(View rootView)
	{		
		listview = (ListView) rootView.findViewById(R.id.listView_popilar);
		preview = null;
	}
	
	private void showResults() 
	{
		if (type == 0)
		{
			UnUploadItemAdapter mia = new UnUploadItemAdapter(getActivity(), getData1());
			listview.setAdapter(mia);
			listview.setCacheColorHint(0);
			listview.setOnItemClickListener(new ListView.OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					System.out.println(position + " is press! is press! is press! is press!");
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
		else if (type == 1)
		{
			UploadItemAdapter mia = new UploadItemAdapter(getActivity(), getData2());
			listview.setAdapter(mia);
			listview.setCacheColorHint(0);
			listview.setOnItemClickListener(new ListView.OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					System.out.println(position + " is press! is press! is press! is press!");
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
		else if (type == 2)
		{
			SpotItemAdapter mia = new SpotItemAdapter(getActivity(), getData3());
			listview.setAdapter(mia);
			listview.setCacheColorHint(0);
		}
	}
	
	private ArrayList<UnUploadItem> getData1()
	{
		ArrayList<UnUploadItem> unUpItemList = new ArrayList<UnUploadItem>();
		
		File dir = new File(Variable.voicepath);                 //新建文件实例
		if (dir.exists() && dir.isFile())
			dir.delete();
		if (!dir.exists())
			dir.mkdirs();
		
		File[] list = dir.listFiles();
		for(int i = 0; i < list.length; i++)
		{
			String name = list[i].getName();
			if (name.endsWith(".amr"))
			{
				String name2 = Variable.voicepath + "/" + name.substring(0, name.length() - 4) + ".txt";
				if (new File(name2).exists())
				{
					UnUploadItem mi = new UnUploadItem(name.substring(0, name.length() - 4));
					unUpItemList.add(mi);
				}
			}
		}
		return unUpItemList;
	} 
	
	private ArrayList<UploadItem> getData2()
	{
		ArrayList<UploadItem> upItemList = new ArrayList<UploadItem>();
		
		SharedPreferences sp = getActivity().getSharedPreferences("imap", 0);
		String username = sp.getString("username", "");
		String password = sp.getString("password", "");
		
		NetThread netthread = new NetThread(username, password);
		netthread.makeParam(Variable.myVoice);
		int returnCode = netthread.beginDeal();
		
		if (returnCode == 0)
		{			 
			return netthread.getMyVoiceList();
		}
		else if (returnCode == -1)
		{
			  Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(getActivity(), Variable.errorCode[returnCode] + "！", 
		                 Toast.LENGTH_SHORT).show();
		}
		return upItemList;
	} 
	
	private ArrayList<SpotItem> getData3()
	{
		ArrayList<SpotItem> spotItemList = new ArrayList<SpotItem>();
		
		SharedPreferences sp = getActivity().getSharedPreferences("imap", 0);
		String username = sp.getString("username", "");
		String password = sp.getString("password", "");
		
		NetThread netthread = new NetThread(username, password);
		netthread.makeParam(Variable.mySign);
		int returnCode = netthread.beginDeal();
		
		if (returnCode == 0)
		{			 
			return netthread.getMySignList();
		}
		else if (returnCode == -1)
		{
			  Toast.makeText(getActivity(), "网络错误！", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(getActivity(), Variable.errorCode[returnCode] + "！", 
		                 Toast.LENGTH_SHORT).show();
		}
		return spotItemList;
	} 
}
