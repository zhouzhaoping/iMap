package imap.me;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import imap.nettools.Variable;

public class UnUploadItem {
	
	private String path;
	private String path2;
	
	private String title;
	private int spotid;
	private String description;
	private String time;
	
	private String gender;
	private String language;
	private String style;
	
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
			gender = sc.nextLine();
			language = sc.nextLine();
			style = sc.nextLine();
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
}
