package com.manifera.pdfparser.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manifera.pdfparser.domain.PdfExtractConfig;
import com.manifera.pdfparser.domain.PdfInfo;
import com.manifera.pdfparser.domain.ToolParser;
import com.manifera.pdfparser.facade.PdfHandler;
import com.manifera.pdfparser.util.Constant;

public class ParserTask extends SwingWorker<Void, Integer> {

	private static final Logger LOG = LoggerFactory.getLogger(ParserTask.class);
	
	private JProgressBar progressBar;
	
	private PdfHandler pdfHandler;
	
	private PdfExtractConfig config;
	
	private ToolParser toolParser;
	
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
	    	
	    	if(isCreatedDirExtractImages) {
	    		// Extract images
	    		pdfHandler.extract(config);
	    	}
			publish(75);
	    	// Construct file path to extract text
	    	String path = config.getPathDirExtract() + File.separator + config.getSelectedFile() + Constant.UNDER_SCORE + toolParser.toString().toLowerCase()  + Constant.TEXT_EXTENSION;
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
			
			if(isCreatedFileExtractText) {
				// Extract text
				PdfInfo pdfInfo = pdfHandler.parse(config);
				fileWriter = new FileWriter(file.getAbsoluteFile());
				bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(pdfInfo.getText());
				bufferedWriter.flush();
			}
			publish(100);
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
}
