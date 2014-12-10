package imap.nettools;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class NetThread
{
	String userName;
	String password;
	String url;
	
	JSONObject param;
	
	String retSrc;
	
	public NetThread(String u, String p)
	{
		userName = u;
		password = p;
		url = "http://123.57.38.118/soft_project/";
		param = new JSONObject();
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
				param.put(args[i], realIn[i]);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int beginDeal()
	{
		try{
			Thread t = new Thread(connect);
			t.start();
			t.join();
			JSONObject object = new JSONObject(retSrc);
			return object.getInt("status");
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	public Runnable connect = new Runnable(){
		@Override
		public void run(){
			System.out.println("hehhe");
			try{
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(url);
				//request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
				
				// 绑定到请求 Entry
				StringEntity se = new StringEntity(param.toString(), "utf-8"); 
				request.setEntity(se);

				// 发送请求
				HttpResponse httpResponse = client.execute(request);

				// 得到应答的字符串，这也是一个 JSON 格式保存的数据
				retSrc = EntityUtils.toString(httpResponse.getEntity());
			} catch (Exception e) {
				Log.d("myerror", "Oops!");
				e.printStackTrace();
			}
		}

	};
}