package com.manifera.pdfparser.domain;

public class ImageExtractResult {
	
	private int totalExtractedImages;

	public ImageExtractResult() {
		
	}
	
	public ImageExtractResult(int totalExtractedImages) {
		this.totalExtractedImages = totalExtractedImages;
	}
	
	public int getTotalExtractedImages() {
		return totalExtractedImages;
	}

	public void setTotalExtractedImages(int totalExtractedImages) {
		this.totalExtractedImages = totalExtractedImages;
	}
	
}
