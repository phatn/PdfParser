package com.manifera.pdfparser.facade;

import java.io.IOException;

import com.manifera.pdfparser.domain.ImageExtractResult;
import com.manifera.pdfparser.domain.PdfExtractConfig;
import com.manifera.pdfparser.domain.PdfInfo;

public interface PdfHandler {
	
	public PdfInfo parse(PdfExtractConfig config) throws IOException;
	
	public ImageExtractResult extract(PdfExtractConfig config) throws IOException;
	
}
