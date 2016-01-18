package com.manifera.pdfparser.domain;

import org.apache.pdfbox.pdmodel.PDDocumentInformation;

public class PdfInfo {

	private PDDocumentInformation information;
	
	private String text;
	
	private int totalPages;
	
	private String filePath;

	public PdfInfo() {
		
	}
	
	public PdfInfo(PDDocumentInformation information, String text, int totalPages, String filePath) {
		this.information = information;
		this.text = text;
		this.totalPages = totalPages;
		this.filePath = filePath;
	}
	
	public PDDocumentInformation getInformation() {
		return information;
	}

	public void setInformation(PDDocumentInformation information) {
		this.information = information;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
}
