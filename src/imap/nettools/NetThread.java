package imap.nettools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;

import android.util.Log;

public class NetThread
{
	String userName;
	String password;
	String url;
	
	JSONObject param, returnJson;
	
	String retSrc;
	
	public NetThread(String u, String p)
	{
		userName = u;
		password = p;
		url = "http://123.57.38.118/soft_project/";
		param = new JSONObject();
		System.out.println(userName + ":" + password);
	}
	
	public void makeParam(String[] args, String ...realIn)
	{
		url = url + args[0] + ".php";
		
		try {
			param.put("userName", userName);
			param.put("password", password);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		for (int i = 1; i < args.length; ++i)
		{
			try {
				param.put(args[i], realIn[i-1]);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		System.out.println(url + ":" + param.toString());
	}
	
	public int beginDeal()
	{
		try{
			Thread t = new Thread(connect);
			t.start();
			t.join();
			returnJson = new JSONObject(retSrc);
			return returnJson.getInt("status");
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public JSONObject getReturn()
	{
		return returnJson;
	}
	
	public List<ViewSpotData> getSpotsList()
	{
		List<ViewSpotData> list = new ArrayList<ViewSpotData>();
		
		try {
			JSONArray viewSpotList = returnJson.getJSONArray("viewSpotList");
			for (int i = 0; i < viewSpotList.length(); ++i)
			{
				JSONObject obj = viewSpotList.getJSONObject(i);
				ViewSpotData vsd = new ViewSpotData(obj);
				list.add(vsd);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return list;
	}
	
	public List<MusicItemData> getPopularList()
	{
		List<MusicItemData> list = new ArrayList<MusicItemData>();
		
		try {
			JSONArray viewSpotList = returnJson.getJSONArray("popular");
			for (int i = 0; i < viewSpotList.length(); ++i)
			{
				JSONObject obj = viewSpotList.getJSONObject(i);	
				MusicItemData vsd = new MusicItemData(obj);
				list.add(vsd);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return list;
	}
	
	public Runnable connect = new Runnable(){
		@Override
		public void run(){
			System.out.println("running");
			try{
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(url);
				//request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
				
				System.out.println(param.toString());
				
				// 绑定到请求 Entry
				StringEntity se = new StringEntity(param.toString(), "utf-8"); 
				request.setEntity(se);

				// 发送请求
				HttpResponse httpResponse = client.execute(request);

				// 得到应答的字符串，这也是一个 JSON 格式保存的数据
				retSrc = EntityUtils.toString(httpResponse.getEntity());

				System.out.println("|" + retSrc + "|");
				
			} catch (Exception e) {
				Log.d("myerror", "Oops!");
				e.printStackTrace();
			}
		}

	};
}