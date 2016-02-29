package com.manifera.pdfparser.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manifera.pdfparser.article.ArticleImporter;
import com.manifera.pdfparser.article.ArticleImporterImpl;
import com.manifera.pdfparser.domain.PdfExtractConfig;
import com.manifera.pdfparser.domain.PdfInfo;
import com.manifera.pdfparser.domain.ToolParser;
import com.manifera.pdfparser.facade.PdfHandler;
import com.manifera.pdfparser.gui.JHyperlinkLabel;
import com.manifera.pdfparser.util.Constant;

import flexjson.JSONDeserializer;

public class ParserTask extends SwingWorker<Void, Integer> {

	private static final Logger LOG = LoggerFactory.getLogger(ParserTask.class);
	
	private JProgressBar progressBar;
	
	private PdfHandler pdfHandler;
	
	private PdfExtractConfig config;
	
	private ToolParser toolParser;
	
	private ArticleImporter articleImporter;
	
	public ParserTask(JProgressBar progressBar, PdfHandler pdfHandler, PdfExtractConfig config, ToolParser toolParser) {
		this.progressBar = progressBar;
		this.pdfHandler = pdfHandler;
		this.config = config;
		this.toolParser = toolParser;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		BufferedWriter bufferedWriter = null;
		FileWriter fileWriter = null;
		boolean isCreatedDirExtractImages = true;
	    try {
	    	String pathExtract = config.getDirImageName();
	    	
	    	LOG.info("Thread-" + Thread.currentThread().getId() + ": pathExtract: " + pathExtract);
	    	File resultDir = new File(pathExtract);
	    	if(!resultDir.exists()) {
	    		
	    		// Cannot create dir to extract images
	    		if(!resultDir.mkdirs()) {
	    			isCreatedDirExtractImages = false;
	    			JOptionPane.showMessageDialog(new JFrame(),
	    					toolParser.toString() + " - Cannot create folder to extract images",
						    "Error extract file",
						    JOptionPane.ERROR_MESSAGE);
	    		}
	    	}
	    	
	    	// =============== EXTRACT IMAGES ===========
	    	if(isCreatedDirExtractImages) {
	    		
	    		pdfHandler.extract(config);
	    	}
	    	if(isPdfBox()) {
	    		publish(50);
	    	} else {
	    		publish(75);
	    	}
			
	    	// Construct file path to extract text
	    	String path = config.getPathDirExtract() + File.separator + config.getSelectedFile() + 
	    			Constant.UNDER_SCORE + toolParser.toString().toLowerCase()  + Constant.TEXT_EXTENSION;
	    	
	    	LOG.info("path: " + path);
	    	boolean isCreatedFileExtractText = true;
	    	
			File file = new File(path);
			if(!file.exists()) {
				 if(!file.createNewFile()) {
					 isCreatedFileExtractText = false;
					 JOptionPane.showMessageDialog(new JFrame(),
							 toolParser.toString() + " - Cannot create file to extract",
							    "Error extract file",
							    JOptionPane.ERROR_MESSAGE);
				 }
			}
			
			PdfInfo pdfInfo = null;
			// ========== EXTRACT TEXT =================
			if(isCreatedFileExtractText) {
				
				pdfInfo = pdfHandler.parse(config);
				fileWriter = new FileWriter(file.getAbsoluteFile());
				bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(pdfInfo.getText());
				bufferedWriter.flush();
			}
			if(isPdfBox()) {
				publish(70);
			} else {
				publish(100);
			}
			
			// TODO: SHOULD REFECTOR LATER: CURRENTLY JUST IMPORT ARTICLE USING PDFBOX ONLY
			if(isPdfBox()) {
				articleImporter = new ArticleImporterImpl();
				
				if(pdfInfo != null && pdfInfo.getArticles() != null && !pdfInfo.getArticles().isEmpty()) {
					String articleJson = articleImporter.getJsonFormat(pdfInfo.getArticles().get(0));
					String responseJson = articleImporter.doImport(articleJson);
					
					//responseJson = DeserializerUtil.addClassName(responseJson, Response.class.getName());
					
					Map<String, Object> response = new JSONDeserializer<Map<String, Object>>().deserialize(responseJson);
					
					System.out.println(response.get("article"));
					Map<String, String> article = (Map<String, String>)response.get("article");
					publish(100);
					JOptionPane.showMessageDialog(new JFrame(),
							new JHyperlinkLabel(article.get("url")),
							"Import article result: " + ((boolean)(response.get("success")) == true ? "success" : "fail"),
							JOptionPane.INFORMATION_MESSAGE);
				}
				if(!isPdfBox()) {
					publish(100);
				}
			}
		} catch (IOException ex) {

			ex.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(),
					toolParser.toString() + " - " + ex.getMessage(),
				    "Extract pages",
				    JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if(fileWriter != null) {
					fileWriter.close();
				}
				if(bufferedWriter != null) {
					bufferedWriter.close();
				}
			} catch(IOException ex) {
				ex.printStackTrace();
				
				LOG.error("Cannot free resources: " + ex.getMessage());
			}
		}
		return null;
	}
	
	@Override
	protected void process(List<Integer> chunks) {
		progressBar.setValue(chunks.get(chunks.size() - 1));
	}
	
	private boolean isPdfBox() {
		return config.getDirImageName().contains(ToolParser.PDFBOX.toString().toLowerCase());
	}
	
}
