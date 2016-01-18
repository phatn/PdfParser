package com.manifera.pdfparser.domain;

public class PdfExtractConfig {
	
	// Extract file from page
	private int startPage;
	
	// Extract file to page
	private int endPage;
	
	// Path of directory to extract file
	private String pathDirExtract;
	
	// Directory result name (contains text and images) 
	private String dirImageName;

	// Path to extract file
	private String filePath;
	
	public String selectedFile;
	
	public String getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(String selectedFile) {
		this.selectedFile = selectedFile;
	}

	public PdfExtractConfig() {
		this.startPage = 1;
		this.endPage = 1;
		this.pathDirExtract = "";
		this.dirImageName = "";
	}
	
	public PdfExtractConfig(int startPage, int endPage, String pathDirExtract, String dirImageName, String filePath) {
		this.startPage = startPage;
		this.endPage = endPage;
		this.pathDirExtract = pathDirExtract;
		this.dirImageName = dirImageName;
		this.filePath = filePath;
	}
	
	public PdfExtractConfig(PdfExtractConfig config) {
		this.startPage = config.startPage;
		this.endPage = config.endPage;
		this.pathDirExtract = config.pathDirExtract;
		this.dirImageName = config.dirImageName;
		this.filePath = config.filePath;
		this.selectedFile = config.selectedFile;
	}
	
	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public String getPathDirExtract() {
		return pathDirExtract;
	}

	public void setPathDirExtract(String pathDirExtract) {
		this.pathDirExtract = pathDirExtract;
	}

	
	public String getDirImageName() {
		return dirImageName;
	}

	public void setDirImageName(String dirImageName) {
		this.dirImageName = dirImageName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
