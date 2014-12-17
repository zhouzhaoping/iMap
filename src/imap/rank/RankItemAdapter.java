package imap.rank;

import java.util.ArrayList;

import com.example.imap.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RankItemAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<RankItem> list = new ArrayList<RankItem>();
	
	public RankItemAdapter(Context context,ArrayList<RankItem> list){
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
		RankItem hh = list.get(position);
		H h = null;
		if(view==null){
			h = new H();
			view = LayoutInflater.from(context).inflate(R.layout.ranklist_item, parent, false);
			h.num = (ImageView)view.findViewById(R.id.num);
			h.pic = (ImageView)view.findViewById(R.id.facepic);
			h.name = (TextView)view.findViewById(R.id.name);
			h.title = (TextView)view.findViewById(R.id.title);
			
			view.setTag(h);
		}else{
			h = (H)view.getTag();
		}
		
		h.num.setImageResource(hh.getNum());
		h.pic.setImageResource(hh.getFace());
		h.name.setText(hh.getName());
		h.title.setText(hh.getTitle());
		
		return view;
	}

	class H{
		ImageView pic;
		ImageView num;
		TextView name;	
		TextView title;
	}
}
