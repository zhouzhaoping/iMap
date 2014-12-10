package com.musiclist.imap;

import java.util.ArrayList;

import com.example.imap.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MusicItemAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<MusicItem> list = new ArrayList<MusicItem>();
	
	public MusicItemAdapter(Context context,ArrayList<MusicItem> list){
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		MusicItem hh = list.get(position);
		H h = null;
		if(view==null){
			h = new H();
			view = LayoutInflater.from(context).inflate(R.layout.musiclist_item, parent, false);
			h.pic = (ImageView)view.findViewById(R.id.l1);
			h.name = (TextView)view.findViewById(R.id.name);
			h.time = (TextView)view.findViewById(R.id.time);
			h.lastmsg = (TextView)view.findViewById(R.id.lastmsg);
			h.play = (ImageButton)view.findViewById(R.id.button_play);
			h.like = (ImageButton)view.findViewById(R.id.button_like);
			h.report = (ImageButton)view.findViewById(R.id.button_report);
			
			view.setTag(h);
		}else{
			h = (H)view.getTag();
		}
		
		h.pic.setImageResource(Integer.parseInt(hh.getTxPath()));
		h.name.setText(hh.getName());
		h.time.setText(hh.getPostTime());
		h.lastmsg.setText(hh.getTitle());
		h.play.setTag(position);
		h.like.setTag(position);
		h.report.setTag(position);
		h.play.setOnClickListener(new ImageButton.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//Log.v("点击", "播放"+v.getTag());
				System.out.println("play play play play play " + v.getTag());
			}
		});
		h.like.setOnClickListener(new ImageButton.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//Log.v("点击", "点赞"+v.getTag());
				System.out.println("like like like like like " + v.getTag());
			}
		});h.report.setOnClickListener(new ImageButton.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//Log.v("点击", "举报"+v.getTag());
				System.out.println("report report report report report " + v.getTag());
			}
		});
		return view;
	}

	class H{
		ImageView pic;
		TextView name;
		TextView time;
		TextView lastmsg;
		ImageButton play;
		ImageButton like;
		ImageButton report; 
	}
}
