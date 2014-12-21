package imap.rank;

import imap.nettools.NetThread;
import imap.nettools.Variable;

import java.util.ArrayList;
import com.example.imap.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class RankListFragment extends Fragment {
	
	ListView listview;
	int type;
	
	public RankListFragment(int i) {
		type = i;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.musiclist_popular, container, false);
		findViews(rootView);
		showResults();
		return rootView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	}
	
	private void findViews(View rootView)
	{		
		listview = (ListView) rootView.findViewById(R.id.listView_popilar);
	}
	
	private void showResults() 
	{
		if (type == 0)
		{
			PersonRankItemAdapter mia = new PersonRankItemAdapter(getActivity(), getData1());
			listview.setAdapter(mia);
			listview.setCacheColorHint(0);
		}
		else if (type == 1)
		{	SpotRankItemAdapter mia = new SpotRankItemAdapter(getActivity(), getData2());
		listview.setAdapter(mia);
		listview.setCacheColorHint(0);
		}
	}	
	
	private ArrayList<PersonRankItem> getData1()
	{
		ArrayList<PersonRankItem> list = new ArrayList<PersonRankItem>();
		
		SharedPreferences sp = getActivity().getSharedPreferences("imap", 0);
		String username = sp.getString("username", "");
		String password = sp.getString("password", "");
		
		NetThread netthread = new NetThread(username, password);
		netthread.makeParam(Variable.personRank);
		int returnCode = netthread.beginDeal();
		
		if (returnCode == 0)
		{			 
			return netthread.getPersonRankList();
		}
		else if (returnCode == -1)
		{
			  Toast.makeText(getActivity(), "网络错误！", 
		                 Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(getActivity(), Variable.errorCode[returnCode] + "！", 
		                 Toast.LENGTH_SHORT).show();
		}
		return list;
	}
	private ArrayList<SpotRankItem> getData2()
	{
		ArrayList<SpotRankItem> list = new ArrayList<SpotRankItem>();
		
		SharedPreferences sp = getActivity().getSharedPreferences("imap", 0);
		String username = sp.getString("username", "");
		String password = sp.getString("password", "");
		
		NetThread netthread = new NetThread(username, password);
		netthread.makeParam(Variable.spotRank);
		int returnCode = netthread.beginDeal();
		
		if (returnCode == 0)
		{			 
			return netthread.getSpotRankList();
		}
		else if (returnCode == -1)
		{
			  Toast.makeText(getActivity(), "网络错误！", 
		                 Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(getActivity(), Variable.errorCode[returnCode] + "！", 
		                 Toast.LENGTH_SHORT).show();
		}
		return list;
	}
}
