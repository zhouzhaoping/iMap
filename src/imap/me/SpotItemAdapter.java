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

public class SpotItemAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<SpotItem> list = new ArrayList<SpotItem>();
	
	public SpotItemAdapter(Context context,ArrayList<SpotItem> list){
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
		SpotItem hh = list.get(position);
		H h = null;
		if(view==null){
			h = new H();
			view = LayoutInflater.from(context).inflate(R.layout.spotlist_item, parent, false);
			h.name = (TextView)view.findViewById(R.id.name);
			h.time = (TextView)view.findViewById(R.id.time);
			view.setTag(h);
		}else{
			h = (H)view.getTag();
		}
		
		h.name.setText(hh.getName());
		h.time.setText(hh.getTime());
		
		return view;
	}

	class H{	
		TextView name;
		TextView time; 
	}
}
