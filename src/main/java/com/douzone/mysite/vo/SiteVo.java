package com.douzone.mysite.vo;

public class SiteVo {
	private String title;
	private String welcome;
	private String profile;
	private String disc;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWelcome() {
		return welcome;
	}
	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getDisc() {
		return disc;
	}
	public void setDisc(String disc) {
		this.disc = disc;
	}
	@Override
	public String toString() {
		return "SiteVo [title=" + title + ", welcome=" + welcome + ", profile=" + profile + ", disc=" + disc + "]";
	}
	
	
}
