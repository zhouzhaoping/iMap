package imap.rank;

import imap.nettools.Variable;

import org.json.JSONException;
import org.json.JSONObject;

public class RankItem {
	private String name;
	private String title;
	private int face;
	private int num;
	private int likesum;
	private int voicesum;
	
	public RankItem()
	{
		
	}
	public RankItem(JSONObject obj) 
	{
		try {
			name = obj.getString("name");
			//title = obj.getString("title");
			face = Variable.int2pic(Integer.parseInt(obj.getString("face")));
			num = Integer.parseInt(obj.getString("num"));
			likesum = Variable.int2num(Integer.parseInt(obj.getString("likesum")));
			voicesum = Integer.parseInt(obj.getString("voicesum"));
			//title = "上传" + voicesum + "个语音，获得总点赞数" +likesum;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String s) {
		this.title = s;
	}
	public int getFace() {
		return face;
	}
	public void setFace(int f) {
		this.face = f;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int n) {
		this.num = n;
	}
	public int getLikeSum() {
		return likesum;
	}
	public void setLikeSum(int n) {
		this.likesum = n;
	}
	public int getVoiceSum() {
		return voicesum;
	}
	public void setVoiceSum(int n) {
		this.voicesum = n;
	}	
}
