package imap.nettools;

import org.json.JSONException;
import org.json.JSONObject;

public class MusicItemData
{
	int id;
	String name;
	int face;
	String title;
	String description;
	String time;
	int likesum;
	
	public MusicItemData(JSONObject obj) 
	{
		try {
			id = Integer.parseInt(obj.getString("voiceId"));
			name = obj.getString("name");
			face = Integer.parseInt(obj.getString("face"));
			title = obj.getString("title");
			description = obj.getString("description");
			time = obj.getString("time");
			likesum = Integer.parseInt(obj.getString("likeSum"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}

	public int getFace()
	{
		return face;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getTime()
	{
		return time;	
	}
	
	public int getLikesum()
	{
		return likesum;	
	}
}
