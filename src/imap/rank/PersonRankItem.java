package imap.rank;

import imap.nettools.Variable;

import org.json.JSONException;
import org.json.JSONObject;

public class PersonRankItem {
	private String name;
	private int face;
	private int likesum;
	
	public PersonRankItem()
	{
		
	}
	public PersonRankItem(JSONObject obj) 
	{
		try {
			name = obj.getString("userName");
			face = Variable.int2pic(Integer.parseInt(obj.getString("facePic")));
			likesum = Integer.parseInt(obj.getString("totalLikes"));
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
