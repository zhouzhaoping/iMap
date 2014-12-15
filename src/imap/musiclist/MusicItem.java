package imap.musiclist;

import imap.nettools.Variable;

import org.json.JSONException;
import org.json.JSONObject;

public class MusicItem {
	private String id;
	private String name;
	private String title;
	private String description;
	private String postTime;
	private int face;
	private int likesum;
	
	public MusicItem()
	{
		
	}
	public MusicItem(JSONObject obj) 
	{
		try {
			id = obj.getString("voiceId");
			name = obj.getString("name");
			title = obj.getString("title");
			description = obj.getString("description");
			postTime = obj.getString("postTime");
			face = Variable.int2pic(Integer.parseInt(obj.getString("face")));
			likesum = Integer.parseInt(obj.getString("likesum"));
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
	public String getName() {
		return name;
	}
	public void setName(String name1) {
		this.name = name1;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String s) {
		this.title = s;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String d) {
		this.description = d;
	}
	public String getPostTime() {
		return postTime;
	}
	public void setPosttTime(String time) {
		this.postTime = time;
	}
	public int getFace() {
		return face;
	}
	public void setFace(int f) {
		this.face = f;
	}
	public int getLikeSum() {
		return likesum;
	}
	public void setLikeSum(int n) {
		this.likesum = n;
	}	
}
