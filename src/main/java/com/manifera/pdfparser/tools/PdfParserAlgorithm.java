package com.manifera.pdfparser.tools;

import java.io.IOException;

import com.manifera.pdfparser.domain.PdfExtractConfig;
import com.manifera.pdfparser.domain.PdfInfo;

public interface PdfParserAlgorithm {
	
	public PdfInfo parse(PdfExtractConfig config) throws IOException;
	
}
