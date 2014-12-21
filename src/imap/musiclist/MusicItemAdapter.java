package imap.musiclist;

import imap.nettools.NetThread;
import imap.nettools.Variable;
import imap.storage.VoiceCache;

import java.io.FileOutputStream;
import java.util.ArrayList;

import com.example.imap.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
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
			h.pic = (ImageView)view.findViewById(R.id.facepic);
			h.name = (TextView)view.findViewById(R.id.name);
			h.time = (TextView)view.findViewById(R.id.time);
			h.title = (TextView)view.findViewById(R.id.title);
			h.description = (TextView)view.findViewById(R.id.description);
			h.play = (ImageButton)view.findViewById(R.id.button_play);
			h.like = (ImageButton)view.findViewById(R.id.button_like);
			h.report = (ImageButton)view.findViewById(R.id.button_report);
			
			view.setTag(h);
		}else{
			h = (H)view.getTag();
		}
		
		h.id = hh.getId();
		
		h.pic.setImageResource(hh.getFace());
		h.name.setText(hh.getName());
		h.time.setText(hh.getPostTime());
		h.title.setText(hh.getTitle());
		h.description.setText(hh.getDescription());
		
		h.play.setTag(hh.getId());
		h.like.setTag(hh.getId());
		h.report.setTag(hh.getId());
		
		h.play.setOnClickListener(new ImageButton.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String str = (String) v.getTag();
				str = VoiceCache.getVoiceById(context, str);
				
				if (str != null)
				{
					try {
						MediaPlayer player = new MediaPlayer();
						player.setDataSource(str);
						player.prepare();
						player.start();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} 
			}
		});
		h.like.setOnClickListener(new ImageButton.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final String str = (String) v.getTag();
				SharedPreferences sp = context.getSharedPreferences("imap", 0);
				String username = sp.getString("username", "");
				String password = sp.getString("password", "");

				NetThread netthread = new NetThread(username, password);
				netthread.makeParam(Variable.likeVoice, str);
				int returnCode = netthread.beginDeal();
				
				if (returnCode == 0) {
					Toast.makeText(context, "点赞成功！", Toast.LENGTH_SHORT).show();
				} else if (returnCode == -1) {
					Toast.makeText(context, "网络错误！", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(context,
							Variable.errorCode[returnCode] + "！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});h.report.setOnClickListener(new ImageButton.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final H h = (H) v.getTag();
				new AlertDialog.Builder(context)
				.setTitle("确认举报")
				.setMessage("举报\"" + h.title.getText() + "\"?")
				.setPositiveButton("是",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) 
							{
								SharedPreferences sp = context.getSharedPreferences("imap", 0);
								String username = sp.getString("username", "");
								String password = sp.getString("password", "");
				
								NetThread netthread = new NetThread(username, password);
								netthread.makeParam(Variable.reportVoice, h.id);
								int returnCode = netthread.beginDeal();
				
								if (returnCode == 0) {
									Toast.makeText(context, "举报成功！", Toast.LENGTH_SHORT)
											.show();
								} else if (returnCode == -1) {
									Toast.makeText(context, "网络错误！", Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(context,
											Variable.errorCode[returnCode] + "！",
											Toast.LENGTH_SHORT).show();
								}
							}
				}).setNegativeButton("否", null).show();
			}
		});
		return view;
	}

	class H{
		String id;
		
		ImageView pic;
		TextView name;
		TextView time;
		TextView title;
		TextView description;
		ImageButton play;
		ImageButton like;
		ImageButton report; 
	}
}
