package com.manifera.pdfparser.tools.pdfbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manifera.pdfparser.domain.PdfExtractConfig;
import com.manifera.pdfparser.domain.PdfInfo;
import com.manifera.pdfparser.tools.PdfParserAlgorithm;

public class PdfBoxParser implements PdfParserAlgorithm {
	
	private static final Logger LOG = LoggerFactory.getLogger(PdfBoxParser.class);
	
	@Override
	public PdfInfo parse(PdfExtractConfig config) throws IOException {
		PDDocument pdfDocument = null;
		PdfInfo pdfInfo = null;
		File file = null;
		PDFParser parser = null;
		FileInputStream fileInputStream = null;
		try {
			file = new File(config.getFilePath());
			fileInputStream = new FileInputStream(file);
			
			parser = new PDFParser(fileInputStream);
			parser.parse();
			COSDocument cosDocument = parser.getDocument();
			pdfDocument = new PDDocument(cosDocument);
			int max = config.getEndPage() > pdfDocument.getNumberOfPages() ? pdfDocument.getNumberOfPages() : config.getEndPage();
			LOG.info("PDFBOX extract text from: " + config.getStartPage() + " to " + max);
			MyPDFTextStripper pdfStripper = new MyPDFTextStripper();
			
			// Extract text with page range
			pdfStripper.setStartPage(config.getStartPage());
			pdfStripper.setEndPage(max);
			
			// Get result of text extraction
			pdfInfo = new PdfInfo();
			pdfInfo.setText(pdfStripper.getText(pdfDocument));
		} catch(IOException ex) {
			throw ex;
		} finally {
			if(pdfDocument != null) {
				try {
					pdfDocument.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fileInputStream != null) {
				fileInputStream.close();
			}
			
			if(parser != null) {
				parser.clearResources();
			}
		}
		
		return pdfInfo;
	}

}
