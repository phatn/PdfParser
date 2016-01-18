package com.manifera.pdfparser.tools.itext;

import java.io.IOException;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.manifera.pdfparser.domain.PdfExtractConfig;
import com.manifera.pdfparser.domain.PdfInfo;
import com.manifera.pdfparser.tools.PdfParserAlgorithm;

public class ItextParser implements PdfParserAlgorithm {

	@Override
	public PdfInfo parse(PdfExtractConfig config) throws IOException {
		PdfReader reader = new PdfReader(config.getFilePath());
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		TextExtractionStrategy strategy = null;
		StringBuilder textBuilder = new StringBuilder();
		
		// Determine the correct end page to extract
		int max = config.getEndPage() > reader.getNumberOfPages() ? reader.getNumberOfPages() : config.getEndPage();
		for(int i = config.getStartPage(); i <= max; i++) {
			strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
			textBuilder.append(strategy.getResultantText());
		}
		PdfInfo pdfInfo = new PdfInfo();
		pdfInfo.setText(textBuilder.toString());
		pdfInfo.setTotalPages(reader.getNumberOfPages());
		return pdfInfo;
	}
}
