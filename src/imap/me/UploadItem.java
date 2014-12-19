package imap.me;

import imap.nettools.Variable;

import org.json.JSONException;
import org.json.JSONObject;

public class UploadItem {
	private String id;
	private String title;
	private int likesum;
	
	public UploadItem()
	{
		
	}
	public UploadItem(JSONObject obj) 
	{
		try {
			id = obj.getString("voiceId");
			title = obj.getString("title");
			likesum = Integer.parseInt(obj.getString("likesum"));
			//title = "上传" + voicesum + "个语音，获得总点赞数" +likesum;
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
}
