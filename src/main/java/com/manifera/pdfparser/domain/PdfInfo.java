package com.manifera.pdfparser.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocumentInformation;

/**
 * The Class PdfInfo.
 */
public class PdfInfo {

	/** The information. */
	private PDDocumentInformation information;
	
	/** The text of pdf document */
	private String text;
	
	/** The total pages. */
	private int totalPages;
	
	/** The file path. */
	private String filePath;
	
	/** The articles of a magazine */
	private List<Article> articles = new ArrayList<>();

	/**
	 * Instantiates a new pdf info.
	 */
	public PdfInfo() {
		
	}
	
	/**
	 * Instantiates a new pdf info.
	 *
	 * @param information the information
	 * @param text the text
	 * @param totalPages the total pages
	 * @param filePath the file path
	 */
	public PdfInfo(PDDocumentInformation information, String text, 
			int totalPages, String filePath) {
		
		this.information = information;
		this.text = text;
		this.totalPages = totalPages;
		this.filePath = filePath;
	}
	
	/**
	 * Instantiates a new pdf info.
	 *
	 * @param information the information
	 * @param text the text
	 * @param totalPages the total pages
	 * @param filePath the file path
	 * @param articles the articles
	 */
	public PdfInfo(PDDocumentInformation information, String text, int totalPages, 
			String filePath, List<Article> articles) {
		
		this(information, text, totalPages, filePath);
		this.articles = articles;
	}
	
	/**
	 * Gets the information.
	 *
	 * @return the information
	 */
	public PDDocumentInformation getInformation() {
		return information;
	}

	/**
	 * Sets the information.
	 *
	 * @param information the new information
	 */
	public void setInformation(PDDocumentInformation information) {
		this.information = information;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the file path.
	 *
	 * @return the file path
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Sets the file path.
	 *
	 * @param filePath the new file path
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Gets the total pages.
	 *
	 * @return the total pages
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * Sets the total pages.
	 *
	 * @param totalPages the new total pages
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * Gets the articles.
	 *
	 * @return the articles
	 */
	public List<Article> getArticles() {
		return articles;
	}

	/**
	 * Sets the articles.
	 *
	 * @param articles the new articles
	 */
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	
}
