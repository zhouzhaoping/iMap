package imap.me;

import java.util.ArrayList;

import com.example.imap.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class UnUploadItemAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<UnUploadItem> list = new ArrayList<UnUploadItem>();
	
	public UnUploadItemAdapter(Context context,ArrayList<UnUploadItem> list){
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
		UnUploadItem hh = list.get(position);
		H h = null;
		if(view==null){
			h = new H();
			view = LayoutInflater.from(context).inflate(R.layout.unuploadlist_item, parent, false);
			h.title = (TextView)view.findViewById(R.id.title);
			h.play = (ImageButton)view.findViewById(R.id.button_play);
			h.upload = (ImageButton)view.findViewById(R.id.button_upload);
			h.delete = (ImageButton)view.findViewById(R.id.button_delete);
			view.setTag(h);
		}else{
			h = (H)view.getTag();
		}
		
		h.title.setText(hh.getTitle());
		
		h.play.setOnClickListener(new ImageButton.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//Log.v("点击", "播放"+v.getTag());
				System.out.println("play play play play play " + v.getTag());
			}
		});
		h.upload.setOnClickListener(new ImageButton.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//Log.v("点击", "点赞"+v.getTag());
				System.out.println("upload upload upload upload upload " + v.getTag());
			}
		});
		h.delete.setOnClickListener(new ImageButton.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//Log.v("点击", "点赞"+v.getTag());
				System.out.println("delete delete delete delete delete " + v.getTag());
			}
		});
		return view;
	}

	class H{	
		TextView title;
		ImageButton play;
		ImageButton upload;
		ImageButton delete; 
	}
}
