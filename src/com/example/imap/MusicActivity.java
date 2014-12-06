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
	
	//自定义的弹出框类
	//SelectPicPopupWindow menuWindow; //弹出框
	//SelectAddPopupWindow menuWindow2; //弹出框
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music);
		init();
	}

	private void init()
    {
		popular_title = (TextView)findViewById(R.id.liaotian);
		new_title = (TextView)findViewById(R.id.tongxunlu);
		shaixuan_title = (TextView)findViewById(R.id.faxian);
		
		listview1 = (ListView)findViewById(R.id.listView1);
		listview2 = (ListView)findViewById(R.id.listView2);
		
		HuihuaAdapter ha = new HuihuaAdapter(this,getHuahui());
		listview1.setAdapter(ha);
		listview1.setCacheColorHint(0);
		
		ContactAdapter hc = new ContactAdapter(this,getContact());
		listview2.setAdapter(hc);
		listview2.setCacheColorHint(0);
		
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
	
	private ArrayList<ContactP> getContact(){
		// TODO：用数据库中的数据替代
		ArrayList<ContactP> hcList = new ArrayList<ContactP>();
		ContactP c0 = new ContactP();
		c0.setTxPath(R.drawable.face0+"");
		c0.setName("张三");
		
		ContactP c1 = new ContactP();
		c1.setTxPath(R.drawable.face1+"");
		c1.setName("李四");
		
		ContactP c2 = new ContactP();
		c2.setTxPath(R.drawable.face2+"");
		c2.setName("周杰伦");
		
		ContactP c3 = new ContactP();
		c3.setTxPath(R.drawable.face3+"");
		c3.setName("N女神");
		
		ContactP c4 = new ContactP();
		c4.setTxPath(R.drawable.face4+"");
		c4.setName("大炮哥");
		
		ContactP c5 = new ContactP();
		c5.setTxPath(R.drawable.face5+"");
		c5.setName("就是你");
		
		ContactP c6 = new ContactP();
		c6.setTxPath(R.drawable.face6+"");
		c6.setName("傻逼");
		
		ContactP c7 = new ContactP();
		c7.setTxPath(R.drawable.face7+"");
		c7.setName("这谁啊");
		
		ContactP c8 = new ContactP();
		c8.setTxPath(R.drawable.face0+"");
		c8.setName("风清");
		
		ContactP c9 = new ContactP();
		c9.setTxPath(R.drawable.face1+"");
		c9.setName("云淡");
		
		ContactP c10 = new ContactP();
		c10.setTxPath(R.drawable.face2+"");
		c10.setName("鄙人");
		
		ContactP c11 = new ContactP();
		c11.setTxPath(R.drawable.face3+"");
		c11.setName("小瘪三");
		
		ContactP c12 = new ContactP();
		c12.setTxPath(R.drawable.face4+"");
		c12.setName("就是你");
		
		ContactP c13 = new ContactP();
		c13.setTxPath(R.drawable.face5+"");
		c13.setName("校长");
		
		hcList.add(c0);
		hcList.add(c1);
		hcList.add(c2);
		hcList.add(c3);
		hcList.add(c4);
		hcList.add(c5);
		hcList.add(c6);
		hcList.add(c7);
		hcList.add(c8);
		hcList.add(c9);
		hcList.add(c10);
		hcList.add(c11);
		hcList.add(c12);
		hcList.add(c13);
		
		return hcList;
	}
	private ArrayList<HuiHua> getHuahui(){
		// TODO：用数据库中的数据替代
		ArrayList<HuiHua> hhList = new ArrayList<HuiHua>();
		HuiHua h1 = new HuiHua();
		h1.setTxPath(R.drawable.face0+"");
		h1.setName1("张三");
		h1.setLastContent("未名湖是个好地方");
		h1.setLastTime("下午 18:00");
		
		HuiHua h2 = new HuiHua();
		h2.setTxPath(R.drawable.face1+"");
		h2.setName1("小黑");
		h2.setLastContent("柳树和湖面相得益彰");
		h2.setLastTime("下午 17:40");
		
		HuiHua h3 = new HuiHua();
		h3.setTxPath(R.drawable.face2+"");
		h3.setName1("傻逼");
		h3.setLastContent("人有点少哈");
		h3.setLastTime("下午 17:00");
		
		HuiHua h4 = new HuiHua();
		h4.setTxPath(R.drawable.face3+"");
		h4.setName1("禽兽");
		h4.setLastContent("我爱上了这里");
		h4.setLastTime("下午 16:22");
		
		HuiHua h5 = new HuiHua();
		h5.setTxPath(R.drawable.face4+"");
		h5.setName1("风清");
		h5.setLastContent("我来介绍一些历史");
		h5.setLastTime("下午 16:11");
		
		HuiHua h6 = new HuiHua();
		h6.setTxPath(R.drawable.face5+"");
		h6.setName1("呵呵");
		h6.setLastContent("突然就有感而发了");
		h6.setLastTime("下午 15:08");
		
		HuiHua h7 = new HuiHua();
		h7.setTxPath(R.drawable.face6+"");
		h7.setName1("鄙人");
		h7.setLastContent("春天，好地方");
		h7.setLastTime("下午 15:01");
		
		HuiHua h8 = new HuiHua();
		h8.setTxPath(R.drawable.face7+"");
		h8.setName1("人人人");
		h8.setLastContent("记得一年前的今天，这里。。");
		h8.setLastTime("下午 14:50");
		
		HuiHua h9 = new HuiHua();
		h9.setTxPath(R.drawable.face0+"");
		h9.setName1("法克");
		h9.setLastContent("this is very good");
		h9.setLastTime("下午 14:00");
		
		HuiHua h0 = new HuiHua();
		h0.setTxPath(R.drawable.face1+"");
		h0.setName1("诗人");
		h0.setLastContent("最美不过未名");
		h0.setLastTime("中午 12:00");
		
		hhList.add(h1);
		hhList.add(h2);
		hhList.add(h3);
		hhList.add(h4);
		hhList.add(h5);
		hhList.add(h6);
		hhList.add(h7);
		hhList.add(h8);
		hhList.add(h9);
		hhList.add(h0);
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
    		new_title.setTextColor(Color.BLACK);
    		shaixuan_title.setTextColor(0xff228B22);
    	}else{
    		popular_title.setTextColor(Color.BLACK);
    		new_title.setTextColor(0xff228B22);
    		shaixuan_title.setTextColor(Color.BLACK);
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