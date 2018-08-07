package com.telstra.health.shot.dto;

import java.io.Serializable;

public class HomePageActionsDto implements Serializable{
 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3101612640643270800L;
	
	
	private int id;	
	private String webPageUrl;
	private String webPageName;
	private String iconName;
	
	
	public HomePageActionsDto() {}
	
	public HomePageActionsDto(int id, String webPageUrl, String webPageName, String iconName) {
		super();
		this.id = id;
		this.webPageUrl = webPageUrl;
		this.webPageName = webPageName;
		this.iconName = iconName;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWebPageUrl() {
		return webPageUrl;
	}
	public void setWebPageUrl(String webPageUrl) {
		this.webPageUrl = webPageUrl;
	}
	public String getWebPageName() {
		return webPageName;
	}
	public void setWebPageName(String webPageName) {
		this.webPageName = webPageName;
	}
	public String getIconName() {
		return iconName;
	}
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}


}
