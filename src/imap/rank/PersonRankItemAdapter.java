package imap.rank;

import imap.nettools.Variable;

import java.util.ArrayList;

import com.example.imap.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonRankItemAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<PersonRankItem> list = new ArrayList<PersonRankItem>();
	
	public PersonRankItemAdapter(Context context,ArrayList<PersonRankItem> arrayList){
		this.context = context;
		this.list = arrayList;
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
		PersonRankItem hh = list.get(position);
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
		
		h.num.setImageResource(Variable.int2num(position));
		h.pic.setImageResource(hh.getFace());
		h.name.setText(hh.getName());
		h.title.setText("获得总点赞数" + hh.getLikeSum());
		
		return view;
	}

	class H{
		ImageView pic;
		ImageView num;
		TextView name;	
		TextView title;
	}
}
