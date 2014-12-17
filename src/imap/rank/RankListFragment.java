package imap.rank;

import imap.nettools.Variable;

import java.util.ArrayList;
import com.example.imap.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RankListFragment extends Fragment {
	
	ListView listview;
	int type;
	
	public RankListFragment(int i) {
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
	}
	
	private void showResults() 
	{
		RankItemAdapter mia = new RankItemAdapter(getActivity(), getData());
		listview.setAdapter(mia);
		listview.setCacheColorHint(0);
	}	
	
	private ArrayList<RankItem> getData()
	{
		// TODO：用数据库中的数据替代
		ArrayList<RankItem> rankItemList = new ArrayList<RankItem>();
		
		if (type == 0)
		{
			String name[] = {"张三", "小黑", "傻逼", "禽兽", "风清", "呵呵", "鄙人", "人人人", "法克", "诗人"};
			for (int i = 0; i < name.length; ++i)
			{
				RankItem mi = new RankItem();
				mi.setFace(Variable.int2pic(i));
				mi.setNum(Variable.int2num(i));
				mi.setName(name[i]);
				mi.setVoiceSum((10 - i) * 10);
				mi.setLikeSum((10 - i) * 100);
				mi.setTitle("上传" + mi.getVoiceSum() + "个语音，获得总点赞数" + mi.getLikeSum());
				rankItemList.add(mi);
			}
		}
		else
		{
			String name[] = {"北大", "未名湖", "学五", "二教", "北大南门", "清华大学", "五道口", "中关村", "小西门", "西门"};
			for (int i = 0; i < name.length; ++i)
			{
				RankItem mi = new RankItem();
				mi.setFace(R.drawable.icon);
				mi.setNum(Variable.int2num(i));
				mi.setName(name[i]);
				mi.setLikeSum((10 - i) * 100);
				mi.setTitle("总计" + mi.getLikeSum() + "个人签到");
				rankItemList.add(mi);
			}
		}
		/*
		SharedPreferences sp = PopularFragmentActivity.this.getSharedPreferences("imap", MODE_PRIVATE);
		String username = sp.getString("username", "");
		String password = sp.getString("password", "");
		
		NetThread netthread = new NetThread(username, password);
		netthread.makeParam(Variable.getVoice, add_name, null, null, null);
		int returnCode = netthread.beginDeal();
		
		
		if (returnCode == 0)
		{			 
			return netthread.getPopularList();
		}
		else if (returnCode == -1)
		{
			  Toast.makeText(PopularFragmentActivity.this, "网络错误！", 
		                 Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(PopularFragmentActivity.this, Variable.errorCode[returnCode] + "！", 
		                 Toast.LENGTH_SHORT).show();
		}
		*/
		return rankItemList;
	} 
}
