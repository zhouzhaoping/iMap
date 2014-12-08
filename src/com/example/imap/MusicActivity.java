package com.example.imap;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MusicActivity extends Activity implements OnViewChangeListener, OnClickListener{
	private MyScrollLayout mScrollLayout;	
	private LinearLayout[] mImageViews;	
	private int mViewCount;	
	private int mCurSel;
	private ImageView set;
	private ImageView add;
	
	private TextView popular_title;
	private TextView new_title;
	private TextView shaixuan_title;
	
	private boolean isOpen = false;
	
	private ListView listview1;
	private ListView listview2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music);
		init();
	}

	private void init()
    {
		popular_title = (TextView)findViewById(R.id.popular_title);
		new_title = (TextView)findViewById(R.id.new_title);
		shaixuan_title = (TextView)findViewById(R.id.shaixuan_title);
		
		listview1 = (ListView)findViewById(R.id.listView1);
		listview2 = (ListView)findViewById(R.id.listView2);
		
		MusicItemAdapter ha1 = new MusicItemAdapter(this,getHuahui());
		listview1.setAdapter(ha1);
		listview1.setCacheColorHint(0);
		
		MusicItemAdapter ha2 = new MusicItemAdapter(this,getHuahui());
		listview2.setAdapter(ha2);
		listview2.setCacheColorHint(0);
		/*
		ContactAdapter hc = new ContactAdapter(this,getContact());
		listview2.setAdapter(hc);
		listview2.setCacheColorHint(0);
		*/
		
    	mScrollLayout = (MyScrollLayout) findViewById(R.id.ScrollLayout); 	
    	LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lllayout);   	
    	mViewCount = mScrollLayout.getChildCount();
    	mImageViews = new LinearLayout[mViewCount];   	
    	for(int i = 0; i < mViewCount; i++)    	{
    		mImageViews[i] = (LinearLayout) linearLayout.getChildAt(i);
    		mImageViews[i].setEnabled(true);
    		mImageViews[i].setOnClickListener(this);
    		mImageViews[i].setTag(i);
    	}    	
    	mCurSel = 0;
    	mImageViews[mCurSel].setEnabled(false);    	
    	mScrollLayout.SetOnViewChangeListener(this);
    	
    }
	
	private ArrayList<MusicItem> getHuahui(){
		// TODO：用数据库中的数据替代
		String txPath[] = {R.drawable.face0+"", R.drawable.face1+"", R.drawable.face2+"", R.drawable.face3+"", R.drawable.face4+"", R.drawable.face5+"", R.drawable.face6+"", R.drawable.face7+""};
		String name[] = {"张三", "小黑", "傻逼", "禽兽", "风清", "呵呵", "鄙人", "人人人", "法克", "诗人"};
		String lastContent[] = {"未名湖是个好地方", "柳树和湖面相得益彰", "人有点少哈", "我爱上了这里", "我来介绍一些历史", "突然就有感而发了", "春天，好地方", "记得一年前的今天，这里。。", "it's very good", "最美不过未名"};
		String postTime[] = {"下午 18:00", "下午 18:00", "下午 17:40", "下午 17:00", "下午 16:22", "下午 16:11", "下午 15:08", "下午 15:01", "下午 14:50", "下午 14:00", "中午 12:00"};
		
		ArrayList<MusicItem> hhList = new ArrayList<MusicItem>();
		MusicItem h1 = new MusicItem();
		
		for (int i = 0; i < name.length; ++i)
		{
			MusicItem h = new MusicItem();
			h.setTxPath(txPath[i % 8]);
			h.setName1(name[i]);
			h.setLastContent(lastContent[i]);
			h.setLastTime(postTime[i]);
			hhList.add(h);
		}
		return hhList;
	} 
	
	    
	private void setCurPoint(int index)
    {
    	if (index < 0 || index > mViewCount - 1 || mCurSel == index){
    		return ;
    	}    	
    	mImageViews[mCurSel].setEnabled(true);
    	mImageViews[index].setEnabled(false);    	
    	mCurSel = index;
    	
    	if(index == 0){
    		popular_title.setTextColor(0xff228B22);
    		new_title.setTextColor(Color.BLACK);
    		shaixuan_title.setTextColor(Color.BLACK);
    	}else if(index==1){
    		popular_title.setTextColor(Color.BLACK);
    		new_title.setTextColor(0xff228B22);
    		shaixuan_title.setTextColor(Color.BLACK);
    	}else{
    		popular_title.setTextColor(Color.BLACK);
    		new_title.setTextColor(Color.BLACK);
    		shaixuan_title.setTextColor(0xff228B22);
    		
    	}
    }

    @Override
	public void OnViewChange(int view) {
		// TODO Auto-generated method stub
		setCurPoint(view);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int pos = (Integer)(v.getTag());
		setCurPoint(pos);
		mScrollLayout.snapToScreen(pos);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if ((keyCode == KeyEvent.KEYCODE_MENU)) {       
	            return true;
	        }
		return super.onKeyDown(keyCode, event);
	}

}