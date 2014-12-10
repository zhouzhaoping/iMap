package com.musiclist.imap;

public class MusicItem {
	private boolean isVisible;
	private String musicID;
	private String name;
	private String title;
	private String postTime;
	private String TxPath;
	private int likeNum;
	public boolean getVisible() {
		return isVisible;
	}
	public void setVisible(boolean v) {
		isVisible = v;
	}
	public String getMusicId() {
		return musicID;
	}
	public void setMusicId(String ID) {
		this.musicID = ID;
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
	public String getPostTime() {
		return postTime;
	}
	public void setPosttTime(String time) {
		this.postTime = time;
	}
	public String getTxPath() {
		return TxPath;
	}
	public void setTxPath(String txPath) {
		TxPath = txPath;
	}
	public int getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(int n) {
		this.likeNum = n;
	}	
}
