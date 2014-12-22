package imap.me;

import imap.nettools.Variable;
import imap.util.ParseTimeUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class UploadItem {
	private String id;
	private int spotid;
	private String title;
	private String description;
	private String time;
	private int likesum;
	
	public UploadItem()
	{
		
	}
	public UploadItem(JSONObject obj) 
	{
		try {
			id = obj.getString("voiceId");
			spotid = Integer.parseInt(obj.getString("viewSpotId"));
			title = obj.getString("title");
			description = obj.getString("description");
			time = ParseTimeUtil.getTimeDelta(obj.getString("time"), "yyyy-MM-dd HH:mm:ss");
			likesum = Integer.parseInt(obj.getString("likes"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	public String getId() {
		return id;
	}
	public void setId(String ID) {
		this.id = ID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String s) {
		this.title = s;
	}
	public int getLikeSum() {
		return likesum;
	}
	public void setLikeSum(int n) {
		this.likesum = n;
	}
	public int getSpotid() {
		return spotid;
	}
	public void setSpotid(int spotid) {
		this.spotid = spotid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
