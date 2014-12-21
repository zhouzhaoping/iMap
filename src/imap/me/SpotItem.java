package imap.me;

import imap.nettools.Variable;

import org.json.JSONException;
import org.json.JSONObject;

public class SpotItem {
	private int spotid;
	private String time;
	
	public SpotItem()
	{
		
	}
	public SpotItem(JSONObject obj) 
	{
		try {
			spotid = Integer.parseInt(obj.getString("viewSpotId"));
			time = obj.getString("time");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	public String getTime() {
		return time;
	}
	public void setTime(String p) {
		this.time = p;
	}
	public int getSpotid() {
		return spotid;
	}
	public void setSpotid(int spotid) {
		this.spotid = spotid;
	}
}
