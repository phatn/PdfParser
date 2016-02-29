package com.manifera.pdfparser.domain;

public class Response {
	
	private boolean success;
	
	private ArticleResponse article;
	
	public Response(ArticleResponse article, boolean success) {
		this.article = article;
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}


	public ArticleResponse getArticle() {
		return article;
	}

	public void setArticle(ArticleResponse article) {
		this.article = article;
	}
	
	@Override
	public String toString() {
		return "success: " + success + ", article[title, url]: " + article.getTitle() + ","  + article.getUrl();
	}
}
