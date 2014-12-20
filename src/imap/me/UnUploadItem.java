package imap.me;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import imap.nettools.Variable;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;

public class UnUploadItem {
	
	private String path;
	private String path2;
	
	private String title;
	private int spotid;
	private String description;
	private String time;
	
	public UnUploadItem()
	{
		
	}
	public UnUploadItem(String obj)
	{
		path = Variable.voicepath + "/" + obj + ".amr";
		path2 = Variable.voicepath + "/" + obj + ".txt";
		String[] str = obj.split("_");
		spotid = Integer.parseInt(str[1]);
		time = str[2];
		
		try {
			Scanner sc = new Scanner(new FileReader(path2));
			title = sc.nextLine();
			description = sc.nextLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String p) {
		this.path = p;
	}
	public String getPath2() {
		return path2;
	}
	public void setPath2(String p) {
		this.path2 = p;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String s) {
		this.title = s;
	}
	public int getSpotId() {
		return spotid;
	}
	public void setSpotId(int n) {
		this.spotid = n;
	}
	public String getDescription() {
		return description;
	}
	public void setDescroption(String s) {
		this.description = s;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String s) {
		this.time = s;
	}
}
