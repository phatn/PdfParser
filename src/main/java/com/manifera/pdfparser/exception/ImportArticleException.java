package com.manifera.pdfparser.exception;

/**
 * 
 * @author phat
 *
 */
public class ImportArticleException extends Exception {

	
	private static final long serialVersionUID = 1L;
	
	public ImportArticleException(){}

	public ImportArticleException(String message) {
		super(message);
	}

	public ImportArticleException(Throwable cause) {
		super(cause);
	}

	public ImportArticleException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImportArticleException(String message, Throwable cause, 
                                       boolean enableSuppression, boolean writableStackTrace) {
		
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
