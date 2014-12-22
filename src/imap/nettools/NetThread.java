package imap.nettools;

import imap.me.SpotItem;
import imap.me.UploadItem;
import imap.musiclist.MusicItem;
import imap.rank.PersonRankItem;
import imap.rank.SpotRankItem;

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

public class NetThread {
	String userName;
	String password;
	String url;

	public JSONObject param, returnJson;

	String retSrc;

	public NetThread(String u, String p) {
		userName = u;
		password = p;
		url = "http://123.57.38.118/soft_project/";
		param = new JSONObject();
		System.out.println(userName + ":" + password);
	}

	public void makeParam(String[] args, String... realIn) {
		url = url + args[0] + ".php";
		
		if (realIn.length + 1 != args.length)
			System.out.println("fuck you ass");
		
		try {
			param.put("userName", userName);
			param.put("password", password);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		for (int i = 1; i < args.length; ++i) {
			try {
				param.put(args[i], realIn[i - 1]);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		System.out.println(url + ":" + param.toString());
	}

	public int beginDeal() {
		try {
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

	public JSONObject getReturn() {
		return returnJson;
	}

	/*
	 * public <T> ArrayList<T> hehe(String str) { ArrayList<T> list = new
	 * ArrayList<T>();
	 * 
	 * try { JSONArray jsonlist = returnJson.getJSONArray(str); for (int i = 0;
	 * i < jsonlist.length(); ++i) { JSONObject obj = jsonlist.getJSONObject(i);
	 * T vsd = new T(obj); list.add(vsd); } } catch (JSONException e) {
	 * e.printStackTrace(); } return list; }
	 */

	public ArrayList<ViewSpotData> getSpotsList() {
		ArrayList<ViewSpotData> list = new ArrayList<ViewSpotData>();

		try {
			JSONArray viewSpotList = returnJson.getJSONArray("viewSpotList");
			for (int i = 0; i < viewSpotList.length(); ++i) {
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

	public ArrayList<MusicItem> getVoiceList(String str) {
		ArrayList<MusicItem> list = new ArrayList<MusicItem>();

		try {
			JSONArray viewSpotList = returnJson.getJSONArray(str);
			for (int i = 0; i < viewSpotList.length(); ++i) {
				JSONObject obj = viewSpotList.getJSONObject(i);
				MusicItem vsd = new MusicItem(obj);
				list.add(vsd);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<UploadItem> getMyVoiceList() {
		ArrayList<UploadItem> list = new ArrayList<UploadItem>();

		try {
			JSONArray jsonList = returnJson.getJSONArray("voiceList");
			for (int i = 0; i < jsonList.length(); ++i) {
				JSONObject obj = jsonList.getJSONObject(i);
				UploadItem vsd = new UploadItem(obj);
				list.add(vsd);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<SpotItem> getMySignList() {
		ArrayList<SpotItem> list = new ArrayList<SpotItem>();

		try {
			JSONArray jsonList = returnJson.getJSONArray("viewSpotList");
			for (int i = 0; i < jsonList.length(); ++i) {
				JSONObject obj = jsonList.getJSONObject(i);
				SpotItem vsd = new SpotItem(obj);
				list.add(vsd);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<PersonRankItem> getPersonRankList() {
		ArrayList<PersonRankItem> list = new ArrayList<PersonRankItem>();

		try {
			JSONArray jsonList = returnJson.getJSONArray("hotUserList");
			for (int i = 0; i < jsonList.length(); ++i) {
				JSONObject obj = jsonList.getJSONObject(i);
				PersonRankItem vsd = new PersonRankItem(obj);
				list.add(vsd);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<SpotRankItem> getSpotRankList() {
		ArrayList<SpotRankItem> list = new ArrayList<SpotRankItem>();

		try {
			JSONArray jsonList = returnJson.getJSONArray("hotSpotList");
			for (int i = 0; i < jsonList.length(); ++i) {
				JSONObject obj = jsonList.getJSONObject(i);
				SpotRankItem vsd = new SpotRankItem(obj);
				list.add(vsd);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Runnable connect = new Runnable() {
		@Override
		public void run() {
			System.out.println("running");
			try {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(url);
				// request.setHeader("Content-Type",
				// "application/x-www-form-urlencoded; charset=utf-8");

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