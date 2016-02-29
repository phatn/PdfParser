package com.manifera.pdfparser.tools.pdfbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manifera.pdfparser.domain.Article;
import com.manifera.pdfparser.domain.ArticleTag;
import com.manifera.pdfparser.domain.PdfExtractConfig;
import com.manifera.pdfparser.domain.PdfInfo;
import com.manifera.pdfparser.tools.PdfParserAlgorithm;
import com.manifera.pdfparser.util.Constant;

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
			PdfArticleExtractor pdfStripper = new PdfArticleExtractor();
			
			// Extract text with page range
			pdfStripper.setStartPage(config.getStartPage());
			pdfStripper.setEndPage(max);
			
			// Get result of text extraction
			pdfInfo = new PdfInfo();
			
			//pdfInfo.setText(pdfStripper.getText(pdfDocument));
			
			String body = ArticleTag.Body.START + pdfStripper.getText(pdfDocument) + ArticleTag.Body.END;
			String title = ArticleTag.Title.START + pdfStripper.getArticles().get(0).getTitle() + ArticleTag.Title.END;
			String introduction = ArticleTag.Highlight.START + pdfStripper.getArticles().get(0).getHighlight() + ArticleTag.Highlight.END;
			
			pdfInfo.setText(title + Constant.NEW_LINE + introduction + Constant.NEW_LINE + body);
			
			Article artile = new Article();
			artile.setTitle(pdfStripper.getArticles().get(0).getTitle());
			List<String> bodyList = Arrays.asList(pdfStripper.getText(pdfDocument).split(Constant.NEW_LINE));
			artile.setBody(bodyList);
			
			if(pdfStripper.getArticles().get(0).getHighlight().isEmpty()) {
				artile.setHighlight(getHighlightFromBody(bodyList));
			} else {
				artile.setHighlight(pdfStripper.getArticles().get(0).getHighlight());
			}
			
			
			//pdfInfo.getArticles().addAll(pdfStripper.getArticles());
			pdfInfo.getArticles().clear();
			pdfInfo.getArticles().add(artile);
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

	private String getHighlightFromBody(List<String> body) {
		
		if(body == null || body.isEmpty()) {
			return "";
		}
		
		String highLight = "";
		boolean found = false;
		int i = 0;
		while(i < body.size() && !found) {
			String currentPara = body.get(i);
			if(currentPara.length() >= Constant.MAX_HIGHLIGHT_CHARACTER) {
				highLight = currentPara;
				found = true;
			}
			i++;
		}
		return highLight;
	}
}
