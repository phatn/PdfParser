package com.manifera.pdfparser.domain;

/**
 * 
 * @author phat
 * 
 * This class defines tags of article
 *
 */
public abstract class ArticleTag {
	
	public static class Title {
		public static final String START = "<title>";
		public static final String END = "</title>";
	}
	
	public static class Highlight {
		public static final String START = "<highlight>";
		public static final String END = "</highlight>";
	}
	
	public static class Body {
		public static final String START = "<body>";
		public static final String END = "</body>";
	}
}
