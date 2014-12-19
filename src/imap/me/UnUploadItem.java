package imap.me;

import imap.nettools.Variable;

import org.json.JSONException;
import org.json.JSONObject;

public class UnUploadItem {
	private String path;
	private String title;
	
	public UnUploadItem()
	{
		
	}
	public UnUploadItem(JSONObject obj) 
	{
		try {
			path = obj.getString("path");
			title = obj.getString("title");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String s) {
		this.title = s;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String p) {
		this.path = p;
	}
}
