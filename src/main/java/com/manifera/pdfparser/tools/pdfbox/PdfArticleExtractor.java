package com.manifera.pdfparser.tools.pdfbox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;
import org.apache.pdfbox.util.TextType;

import com.manifera.pdfparser.domain.Article;
import com.manifera.pdfparser.util.Constant;


public class PdfArticleExtractor extends PDFTextStripper {
	
	private static final float DELTA_IS_TITLE = 5;
	
	private static final float DELTA_IS_BODY = 5;
	
	private List<TextPosition> textPositions = new ArrayList<>();
	
	private List<TextPosition> titlePositions = new ArrayList<>();
	
	private List<TextPosition> introductionPositions = new ArrayList<>();
	
	private List<Article> articles = new ArrayList<>();
	
	public PdfArticleExtractor() throws IOException {
		super();
	}
	
	public List<Article> fillArticles() {
		
		if(!textPositions.isEmpty()) {
			float minFontSize = textPositions.get(0).getFontSizeInPt();
			float maxFontSize = textPositions.get(0).getFontSizeInPt();
			
			// Find the min and max of size of text positions
			for(int i = 1; i < textPositions.size(); i++) {
				
				if(minFontSize > textPositions.get(i).getFontSizeInPt()) {
					minFontSize = textPositions.get(i).getFontSizeInPt();
				}
				
				if(maxFontSize < textPositions.get(i).getFontSizeInPt()) {
					maxFontSize = textPositions.get(i).getFontSizeInPt();
				}
				
			}
			
			// Determine the potential text positions is in title or introduction (default is body)
			for(int i = 0; i < textPositions.size(); i++) {
				
				// If the text is title
				if(Math.abs(maxFontSize - textPositions.get(i).getFontSizeInPt()) <= DELTA_IS_TITLE ) {
					textPositions.get(i).setTextType(TextType.TITLE);
					titlePositions.add(textPositions.get(i));
				}
				
				// If the text is introduction
				else if(Math.abs(minFontSize - textPositions.get(i).getFontSizeInPt()) > DELTA_IS_BODY
						&& Math.abs(maxFontSize - textPositions.get(i).getFontSizeInPt()) > DELTA_IS_TITLE) {
					textPositions.get(i).setTextType(TextType.INTRODUCTION);
					introductionPositions.add(textPositions.get(i));
				}
			}
			
			StringBuilder titleBuilder = new StringBuilder();
			StringBuilder highlightBuilder = new StringBuilder();
			StringBuilder bodyBuilder = new StringBuilder();
			
			for(TextPosition text : textPositions) {
				
				if(text.getTextType() == TextType.TITLE) {
					titleBuilder.append(text.getCharacter());
				} else if (text.getTextType() == TextType.BODY) {
					bodyBuilder.append(text.getCharacter());
				} else {
					highlightBuilder.append(text.getCharacter());
				}
			}
			List<String> body = Arrays.asList(bodyBuilder.toString().split(Constant.NEW_LINE));
			
			articles.add(new Article(titleBuilder.toString(), body, highlightBuilder.toString()));
		}
		return articles;
	}
	
	@Override
	protected void processTextPosition(TextPosition text) {
		textPositions.add(text);
		super.processTextPosition(text);
		//fillArticles();
	}
	
	@Override
	protected void writePage() throws IOException {
		fillArticles();
		List<TextPosition> items = charactersByArticle.get(0);
		List<TextPosition> temp = new ArrayList<>();
		temp.addAll(introductionPositions);
		temp.addAll(titlePositions);
		items.removeAll(temp);
		charactersByArticle.set(0, items);
		super.writePage();
		
	}

	public List<Article> getArticles() {
		return articles;
	}
	
}
