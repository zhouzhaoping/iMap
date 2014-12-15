package com.musiclist.imap;

import imap.nettools.NetThread;
import imap.nettools.Variable;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.imap.LoginActivity;
import com.example.imap.MainActivity;
import com.example.imap.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;

public class PopularFragment extends Fragment {
	
	ListView listview;
	TextView preview;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		FrameLayout fl = new FrameLayout(getActivity());
		
		fl.setLayoutParams(params);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, dm);
		TextView v = new TextView(getActivity());
		params.setMargins(margin, margin, margin, margin);
		v.setLayoutParams(params);
		v.setLayoutParams(params);
		v.setGravity(Gravity.CENTER);
		v.setText("聊天界面");
		v.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, dm));
		fl.addView(v);
		return fl;
		*/
		
		View rootView = inflater.inflate(R.layout.musiclist_popular, container, false);
		findViews(rootView);
		showResults();
		setListensers();
		return rootView;
	}
	
	/*
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	}*/
	
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
	
	private ArrayList<MusicItem> getData()
	{
		// TODO：用数据库中的数据替代
		//String txPath[] = {R.drawable.face0+"", R.drawable.face1+"", R.drawable.face2+"", R.drawable.face3+"", R.drawable.face4+"", R.drawable.face5+"", R.drawable.face6+"", R.drawable.face7+""};
		String name[] = {"张三", "小黑", "傻逼", "禽兽", "风清", "呵呵", "鄙人", "人人人", "法克", "诗人"};
		String lastContent[] = {"未名湖是个好地方", "柳树和湖面相得益彰", "人有点少哈", "我爱上了这里", "我来介绍一些历史", "突然就有感而发了", "春天，好地方", "记得一年前的今天，这里。。", "it's very good", "最美不过未名"};
		String postTime[] = {"下午 18:00", "下午 18:00", "下午 17:40", "下午 17:00", "下午 16:22", "下午 16:11", "下午 15:08", "下午 15:01", "下午 14:50", "下午 14:00", "中午 12:00"};
			
		ArrayList<MusicItem> musicItemList = new ArrayList<MusicItem>();
				
		for (int i = 0; i < name.length; ++i)
		{
			MusicItem mi = new MusicItem();
			mi.setId(i + "");
			mi.setFace(Variable.int2pic(i));
			mi.setName(name[i]);
			mi.setTitle(lastContent[i]);
			mi.setPosttTime(postTime[i]);
			mi.setDescription("    这是语音简述这是语音简述这是语音简述这是语音简述这是语音简述这是语音简述这是语音简述这是语音简述这是语音简述这是语音简述这是语音简述这是语音简述这是语音简述这是语音简述");
			mi.setLikeSum(i * 100);
			musicItemList.add(mi);
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
		return musicItemList;
	} 
}
