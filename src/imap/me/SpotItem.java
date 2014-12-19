package imap.me;

import imap.nettools.Variable;

import org.json.JSONException;
import org.json.JSONObject;

public class SpotItem {
	private String name;
	private String time;
	
	public SpotItem()
	{
		
	}
	public SpotItem(JSONObject obj) 
	{
		try {
			name = obj.getString("name");
			time = obj.getString("time");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String s) {
		this.name = s;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String p) {
		this.time = p;
	}
}
