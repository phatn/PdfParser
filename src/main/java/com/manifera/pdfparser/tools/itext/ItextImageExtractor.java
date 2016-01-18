package com.manifera.pdfparser.tools.itext;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.manifera.pdfparser.domain.ImageExtractResult;
import com.manifera.pdfparser.domain.PdfExtractConfig;
import com.manifera.pdfparser.tools.ImageExtractorAlgorithm;

public class ItextImageExtractor implements ImageExtractorAlgorithm {

	private static final Logger LOG = LoggerFactory.getLogger(ItextImageExtractor.class);
	
	@Override
	public ImageExtractResult extract(PdfExtractConfig config) throws IOException {
		LOG.info("Thread-" + Thread.currentThread().getId() + ": Enter extract method");
		int extractedImageCount = 0;
		PdfReader reader = new PdfReader(config.getFilePath());
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		ImageRenderListener listener = new ImageRenderListener(config.getDirImageName() + "/Img%s.%s");
		int max = config.getEndPage() > reader.getNumberOfPages() ? reader.getNumberOfPages() : config.getEndPage();
		for(int i = config.getStartPage(); i <= max; i++) {
			LOG.info("Thread-" + Thread.currentThread().getId() + ": Extract page: " + i);
			parser.processContent(i, listener);
			extractedImageCount++;
		}
		return new ImageExtractResult(extractedImageCount);
	}
}
