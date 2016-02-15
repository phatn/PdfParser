package com.manifera.pdfparser.domain;

public class Article {
	
	private String title;
	
	private String introduction;
	
	private String body;
	
	public Article() {
		title = "";
		introduction = "";
		body = "";
	}
	
	public Article(String title, String introduction, String body) {
		this.title = title;
		this.introduction = introduction;
		this.body = body;
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
}
