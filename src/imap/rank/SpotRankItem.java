package imap.rank;

import imap.nettools.Variable;

import org.json.JSONException;
import org.json.JSONObject;

public class SpotRankItem {
	private String name;
	private int signsum;
	
	public SpotRankItem()
	{
		
	}
	public SpotRankItem(JSONObject obj) 
	{
		try {
			name = obj.getString("name");
			signsum = Integer.parseInt(obj.getString("marks"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name1) {
		this.name = name1;
	}
	public int getSignsum() {
		return signsum;
	}
	public void setSignsum(int signsum) {
		this.signsum = signsum;
	}
}
