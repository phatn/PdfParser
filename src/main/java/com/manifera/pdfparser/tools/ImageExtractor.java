package com.manifera.pdfparser.tools;

import java.io.IOException;

import com.manifera.pdfparser.domain.ImageExtractResult;
import com.manifera.pdfparser.domain.PdfExtractConfig;

public interface ImageExtractor {
	
	public ImageExtractResult extract(PdfExtractConfig config) throws IOException;
	
}
