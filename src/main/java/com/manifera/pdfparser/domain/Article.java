package com.manifera.pdfparser.domain;

import java.util.ArrayList;
import java.util.List;

import com.manifera.pdfparser.util.Constant;

public class Article {
	
	private String title = "<NO TITLE>";
	
	private String highlight = "<NO HIGHLIGHT>";
	
	private List<String> body = new ArrayList<>();
	
	public Article() {}
	
	public Article(String title, List<String> body) {
		this.title = title;
		this.body = body;
	}
	
	public Article(String title, List<String> body, String highlight) {
		this(title, body);
		this.highlight = highlight;
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHighlight() {
		return highlight;
	}

	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}

	public List<String> getBody() {
		return body;
	}

	public void setBody(List<String> body) {
		this.body = body;
	}

	@Override
	public String toString() {
		
		StringBuilder article = new StringBuilder();
		
		if(title != null && !title.isEmpty()) {
			article.
				append(ArticleTag.Title.START).
				append(title).
				append(ArticleTag.Title.END);
		}
		
		if(highlight != null && !highlight.isEmpty()) {
			article.
				append(Constant.NEW_LINE).
				append(ArticleTag.Highlight.START).
				append(Constant.NEW_LINE).
				append(highlight).
				append(Constant.NEW_LINE).
				append(ArticleTag.Highlight.END);
		}
		
		if(body != null && !body.isEmpty()) {
			article.append(Constant.NEW_LINE);
			for(String paragraph : body) {
				article.append(ArticleTag.Body.START).
					append(Constant.NEW_LINE).
					append(paragraph).
					append(Constant.NEW_LINE);
			}
			article.append(ArticleTag.Body.END);
		}
		
		return article.toString();
	}
}
