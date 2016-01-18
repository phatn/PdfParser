package com.manifera.pdfparser.tools;

import java.io.IOException;

import com.manifera.pdfparser.domain.PdfExtractConfig;
import com.manifera.pdfparser.domain.PdfInfo;

public class PdfParser implements DocumentParser {

	private PdfParserAlgorithm pdfParserAlgorithm;
	
	@Override
	public PdfInfo parse(PdfExtractConfig config) throws IOException {
		return pdfParserAlgorithm.parse(config);
	}

	public PdfParserAlgorithm getPdfParserAlgorithm() {
		return pdfParserAlgorithm;
	}

	public void setPdfParserAlgorithm(PdfParserAlgorithm pdfParserAlgorithm) {
		this.pdfParserAlgorithm = pdfParserAlgorithm;
	}

}
