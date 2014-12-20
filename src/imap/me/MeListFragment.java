package imap.me;

import imap.nettools.Variable;

import java.io.File;
import java.util.ArrayList;
import com.example.imap.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
		if (dir.exists())
		{
			File[] list = dir.listFiles();       
			for(int i = 0; i < list.length; i++)
			{
				String name = list[i].getName();
				if (name.endsWith(".amr"))
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
		// TODO：用数据库中的数据替代
		ArrayList<UploadItem> upItemList = new ArrayList<UploadItem>();
		
		String title[] = {"未名湖是个好地方", "柳树和湖面相得益彰", "人有点少哈", "我爱上了这里", "我来介绍一些历史", "突然就有感而发了", "春天，好地方", "记得一年前的今天，这里。。", "it's very good", "最美不过未名"};
		for (int i = 0; i < title.length; ++i)
		{
			UploadItem mi = new UploadItem();
			mi.setTitle(title[i]);
			mi.setLikeSum(100);
			upItemList.add(mi);
		}
		return upItemList;
	} 
	
	private ArrayList<SpotItem> getData3()
	{
		// TODO：用数据库中的数据替代
		ArrayList<SpotItem> spotItemList = new ArrayList<SpotItem>();
		
		String name[] = {"北大", "未名湖", "学五", "二教", "北大南门", "清华大学", "五道口", "中关村", "小西门", "西门"};
		String postTime[] = {"下午 18:00", "下午 18:00", "下午 17:40", "下午 17:00", "下午 16:22", "下午 16:11", "下午 15:08", "下午 15:01", "下午 14:50", "下午 14:00", "中午 12:00"};
		
		for (int i = 0; i < name.length; ++i)
		{
			SpotItem mi = new SpotItem();
			mi.setName(name[i]);
			mi.setTime(postTime[i]);
			spotItemList.add(mi);
		}
		return spotItemList;
	} 
}
