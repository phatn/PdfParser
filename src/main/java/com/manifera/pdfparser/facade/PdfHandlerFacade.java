package com.manifera.pdfparser.facade;

import java.io.IOException;

import com.manifera.pdfparser.domain.ImageExtractResult;
import com.manifera.pdfparser.domain.PdfExtractConfig;
import com.manifera.pdfparser.domain.PdfInfo;
import com.manifera.pdfparser.tools.DocumentParser;
import com.manifera.pdfparser.tools.ImageExtractor;

public class PdfHandlerFacade implements PdfHandler {
	
	private DocumentParser documentParser;
	
	private ImageExtractor imageExtractor;
	
	public PdfHandlerFacade() {
		
	}
	
	public PdfHandlerFacade(DocumentParser documentParser, ImageExtractor imageExtractor) {
		this.documentParser = documentParser;
		this.imageExtractor = imageExtractor;
	}
	
	@Override
	public PdfInfo parse(PdfExtractConfig config) throws IOException {
		return documentParser.parse(config);
	}

	@Override
	public ImageExtractResult extract(PdfExtractConfig config) throws IOException {
		return imageExtractor.extract(config);
	}

	public DocumentParser getDocumentParser() {
		return documentParser;
	}

	public void setDocumentParser(DocumentParser documentParser) {
		this.documentParser = documentParser;
	}

	public ImageExtractor getImageExtractor() {
		return imageExtractor;
	}

	public void setImageExtractor(ImageExtractor imageExtractor) {
		this.imageExtractor = imageExtractor;
	}

}
