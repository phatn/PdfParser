package com.manifera.pdfparser.article;

import java.io.IOException;

import com.manifera.pdfparser.domain.Article;
import com.manifera.pdfparser.exception.ImportArticleException;

public interface ArticleImporter {
	
	public String getJsonFormat(Article article);
	
	public String doImport(String articleJson) throws IOException, ImportArticleException;
	
}
