package imap.me;

import imap.main.MainActivity;
import imap.me.UnUploadItemAdapter.H;
import imap.nettools.NetThread;
import imap.nettools.Variable;
import imap.storage.VoiceCache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.example.imap.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class UploadItemAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<UploadItem> list = new ArrayList<UploadItem>();

	public UploadItemAdapter(Context context, ArrayList<UploadItem> list) {
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
		UploadItem hh = list.get(position);
		H h = null;
		if (view == null) {
			h = new H();
			view = LayoutInflater.from(context).inflate(
					R.layout.uploadlist_item, parent, false);

			h.title = (TextView) view.findViewById(R.id.title);
			h.spotname = (TextView) view.findViewById(R.id.spotname);
			h.time = (TextView) view.findViewById(R.id.time);
			h.likesum = (TextView) view.findViewById(R.id.likesum);
			h.description = (TextView) view.findViewById(R.id.description);
			h.play = (ImageButton) view.findViewById(R.id.button_play);
			h.delete = (ImageButton) view.findViewById(R.id.button_delete);
			h.thisview = view;

			view.setTag(h);
		} else {
			h = (H) view.getTag();
		}

		h.id = hh.getId();

		h.title.setText(hh.getTitle());
		h.likesum.setText(hh.getLikeSum() + "赞");
		for (int i = 0; i < MainActivity.viewspotlist.size(); ++i)
		{
			if (MainActivity.viewspotlist.get(i).getId() == hh.getSpotid())
			{
				h.spotname.setText(MainActivity.viewspotlist.get(i).getName());
				break;
			}
		}
		h.time.setText(hh.getTime());
		h.description.setText(hh.getDescription());
		h.play.setTag(hh.getId());
		h.delete.setTag(h);

		h.play.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
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
		h.delete.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				final H h = (H) v.getTag();
				
				new AlertDialog.Builder(context)
				.setTitle("确认刪除")
				.setMessage("刪除\"" + h.title.getText() + "\"?")
				.setPositiveButton("是",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) 
							{
								SharedPreferences sp = context.getSharedPreferences("imap", 0);
								String username = sp.getString("username", "");
								String password = sp.getString("password", "");
				
								NetThread netthread = new NetThread(username, password);
								netthread.makeParam(Variable.delVoice, h.id);
								int returnCode = netthread.beginDeal();
				
								if (returnCode == 0) {
				
									h.thisview.setVisibility(View.GONE);
									Toast.makeText(context, "语音删除成功！", Toast.LENGTH_SHORT)
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

	class H {
		View thisview;
		String id;

		TextView title;
		TextView spotname;
		TextView time;
		TextView likesum;
		ImageButton play;
		ImageButton delete;
		TextView description;
	}
}
