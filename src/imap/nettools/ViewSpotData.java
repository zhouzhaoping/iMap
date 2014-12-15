package imap.nettools;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewSpotData
{
	int id;
	String name;
	String description;
	double longitude;
	double latitude;
	int visible;
	int parent;
	
	public ViewSpotData(JSONObject obj) 
	{
		try {
			id = Integer.parseInt(obj.getString("viewSpotId"));
			name = obj.getString("name");
			description = obj.getString("description");
			longitude = Double.parseDouble(obj.getString("longitude"));
			latitude = Double.parseDouble(obj.getString("latitude"));
			visible = Integer.parseInt(obj.getString("visible"));
			if (visible == 0)
				parent = Integer.parseInt(obj.getString("parent"));
			else 
				parent = -1;
			//System.out.println(name + " " + visible);
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

	public String getDescription()
	{
		return description;
	}
	
	public double getLongitude()
	{
		return longitude;
	}
	
	public double getLatitude()
	{
		return latitude;
	}
	
	public int getVisible()
	{
		return visible;	
	}
	
	public int getParent()
	{
		return parent;	
	}
	
}
