package com.telstra.health.shot.dto;

public class NewsFeedDTO {

	private Long id;
	
	private String header;

	private String postedBy;
	
	private String shortDesc;
	
	private String longDesc;
	
	private String publishDate;

	public NewsFeedDTO() {
	}

	public NewsFeedDTO(Long id, String header, String postedBy, String shortDesc, String longDesc, String updatedDate) {
		this.id = id;
		this.header = header;
		this.postedBy = postedBy;
		this.shortDesc = shortDesc;
		this.longDesc = longDesc;
		this.publishDate = updatedDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public String getLongDesc() {
		return longDesc;
	}

	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String updatedDate) {
		this.publishDate = updatedDate;
	}
}
