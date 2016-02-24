package com.manifera.pdfparser.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manifera.pdfparser.domain.PdfExtractConfig;
import com.manifera.pdfparser.domain.PdfInfo;
import com.manifera.pdfparser.domain.ToolParser;
import com.manifera.pdfparser.facade.PdfHandler;
import com.manifera.pdfparser.facade.PdfHandlerFacade;
import com.manifera.pdfparser.task.ParserTask;
import com.manifera.pdfparser.tools.ImageExtractorImpl;
import com.manifera.pdfparser.tools.PdfParser;
import com.manifera.pdfparser.tools.icepdf.IcePdfImageExtractor;
import com.manifera.pdfparser.tools.icepdf.IcePdfParser;
import com.manifera.pdfparser.tools.itext.ItextImageExtractor;
import com.manifera.pdfparser.tools.itext.ItextParser;
import com.manifera.pdfparser.tools.pdfbox.PdfBoxImageExtractor;
import com.manifera.pdfparser.tools.pdfbox.PdfBoxParser;
import com.manifera.pdfparser.util.Constant;
import com.manifera.pdfparser.util.PropertiesFileUtil;


public class MainWindow extends JFrame {

	private static Logger logger = LoggerFactory.getLogger(MainWindow.class);
	
	private static final long serialVersionUID = 1L;
	
	private static final String OPEN_PDF_FILE_TEXT = "Open pdf file...";
	
	private static final String EXIT_APPLICATION_TEXT = "Exit";
	
	private static final int LENGTH_FILE_PATH_SHOW = 60;
	
	private static final int LENGTH_FOLDER_PATH_SHOW = 35;
	
	private JButton btnOpenFile;

	private JPanel contentPane;
	
	//For title icon and size/positioning of JFrame
	private Toolkit tk = Toolkit.getDefaultToolkit();  
		
	private Dimension screen = tk.getScreenSize();
	private JLabel lblPages;
	private JLabel lblPages_1;
	private JLabel lblFrom;
	private JLabel lblTo;
	private JTextField txtEndPage;
	private JTextField txtStartPage;
	private JButton btnExtractPages;
	private JButton btnExit;
	private JLabel lblFile;
	private JPanel topButtonPanel;
	private JFileChooser fileChooser;
	
	private JFileChooser folderChooser;
	
	private boolean isFileSelected;
	
	private PdfExtractConfig config;
	private JLabel lblTargetFolder;
	private JButton btnChoose;
	private JLabel lblDirPath;
	
	private String filePath = "";
	
	private String selectedFileName = "";
	
	private String selectedDirExtract = "";
	
	private ProgressBarDialog progressBarDialog;
	
	/**
	 * Create the frame.
	 */
	public MainWindow(String title) {
		setTitle(title);
		initialize();
	}

	private void initialize() {
		
		//1/2 Screen Height/Width, Positioned in the Middle
	    setSize(screen.width/3, screen.height/4);
	    setLocation(screen.width/4, screen.height/4);
	    
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		topButtonPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) topButtonPanel.getLayout();
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		btnOpenFile = new JButton(OPEN_PDF_FILE_TEXT);
		btnOpenFile.addActionListener(new OpenFileHanlder());
		topButtonPanel.add(btnOpenFile);
		
		contentPane.add(topButtonPanel, BorderLayout.NORTH);
		
		lblFile = new JLabel("");
		topButtonPanel.add(lblFile);
		
		
		// Init chooser dir
		folderChooser = new JFileChooser();
		//folderChooser.setCurrentDirectory(new java.io.File(Constant.DESTOP_PATH));
		folderChooser.setDialogTitle("Choose folder to extract");
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		folderChooser.setAcceptAllFileFilterUsed(false);
		
		// Init chooser file
		fileChooser = new JFileChooser();
		//fileChooser.setCurrentDirectory(new File(Constant.DESTOP_PATH));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF","pdf");
		fileChooser.setFileFilter(filter);
		fileChooser.setMultiSelectionEnabled(false);
		
		JPanel configPanel = new JPanel();
		configPanel.setBorder(new CompoundBorder());
		
		contentPane.add(configPanel, BorderLayout.CENTER);
		GridBagLayout gbl_configPanel = new GridBagLayout();
		gbl_configPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_configPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_configPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_configPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		configPanel.setLayout(gbl_configPanel);
		
		lblPages = new JLabel("Extracting setttings");
		GridBagConstraints gbc_lblPages = new GridBagConstraints();
		gbc_lblPages.fill = GridBagConstraints.VERTICAL;
		gbc_lblPages.anchor = GridBagConstraints.WEST;
		gbc_lblPages.insets = new Insets(0, 8, 5, 5);
		gbc_lblPages.gridx = 0;
		gbc_lblPages.gridy = 1;
		configPanel.add(lblPages, gbc_lblPages);
		
		lblPages_1 = new JLabel("Pages:");
		GridBagConstraints gbc_lblPages_1 = new GridBagConstraints();
		gbc_lblPages_1.fill = GridBagConstraints.VERTICAL;
		gbc_lblPages_1.anchor = GridBagConstraints.WEST;
		gbc_lblPages_1.insets = new Insets(0, 25, 5, 5);
		gbc_lblPages_1.gridx = 0;
		gbc_lblPages_1.gridy = 2;
		configPanel.add(lblPages_1, gbc_lblPages_1);
		
		lblFrom = new JLabel("from");
		GridBagConstraints gbc_lblFrom = new GridBagConstraints();
		gbc_lblFrom.fill = GridBagConstraints.VERTICAL;
		gbc_lblFrom.anchor = GridBagConstraints.EAST;
		gbc_lblFrom.insets = new Insets(0, 0, 5, 5);
		gbc_lblFrom.gridx = 1;
		gbc_lblFrom.gridy = 2;
		configPanel.add(lblFrom, gbc_lblFrom);
		
		txtStartPage = new JTextField();
		GridBagConstraints gbc_txtStartPage= new GridBagConstraints();
		gbc_txtStartPage.fill = GridBagConstraints.BOTH;
		gbc_txtStartPage.insets = new Insets(0, 0, 5, 5);
		gbc_txtStartPage.gridx = 2;
		gbc_txtStartPage.gridy = 2;
		configPanel.add(txtStartPage, gbc_txtStartPage);
		txtStartPage.setColumns(5);
		
		lblTo = new JLabel("to");
		GridBagConstraints gbc_lblTo = new GridBagConstraints();
		gbc_lblTo.fill = GridBagConstraints.VERTICAL;
		gbc_lblTo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTo.anchor = GridBagConstraints.EAST;
		gbc_lblTo.gridx = 3;
		gbc_lblTo.gridy = 2;
		configPanel.add(lblTo, gbc_lblTo);
		
		txtEndPage = new JTextField();
		GridBagConstraints gbc_txtEndPage = new GridBagConstraints();
		gbc_txtEndPage.fill = GridBagConstraints.BOTH;
		gbc_txtEndPage.insets = new Insets(0, 0, 5, 5);
		gbc_txtEndPage.gridx = 4;
		gbc_txtEndPage.gridy = 2;
		configPanel.add(txtEndPage, gbc_txtEndPage);
		txtEndPage.setColumns(5);
		
		btnExtractPages = new JButton("Extract pages");
		GridBagConstraints gbc_btnExtractPages = new GridBagConstraints();
		gbc_btnExtractPages.insets = new Insets(0, 0, 5, 5);
		gbc_btnExtractPages.gridx = 0;
		gbc_btnExtractPages.gridy = 7;
		btnExtractPages.addActionListener(new ExtractPagesHanlder());
		
		lblTargetFolder = new JLabel("Target folder");
		GridBagConstraints gbc_lblTargetFolder = new GridBagConstraints();
		gbc_lblTargetFolder.anchor = GridBagConstraints.WEST;
		gbc_lblTargetFolder.insets = new Insets(0, 8, 5, 5);
		gbc_lblTargetFolder.gridx = 0;
		gbc_lblTargetFolder.gridy = 4;
		configPanel.add(lblTargetFolder, gbc_lblTargetFolder);
		
		lblDirPath = new JLabel("");
		lblDirPath.setText(Constant.DESTOP_PATH);
		GridBagConstraints gbc_lblDirPath = new GridBagConstraints();
		gbc_lblDirPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDirPath.gridwidth = 3;
		gbc_lblDirPath.insets = new Insets(0, 25, 5, 5);
		gbc_lblDirPath.gridx = 0;
		gbc_lblDirPath.gridy = 5;
		configPanel.add(lblDirPath, gbc_lblDirPath);
		
		btnChoose = new JButton("Choose");
		btnChoose.addActionListener(new ChooseTargetFolderHanlder());
		GridBagConstraints gbc_btnChoose = new GridBagConstraints();
		gbc_btnChoose.gridwidth = 2;
		gbc_btnChoose.anchor = GridBagConstraints.WEST;
		gbc_btnChoose.insets = new Insets(0, 5, 5, 5);
		gbc_btnChoose.gridx = 3;
		gbc_btnChoose.gridy = 5;
		configPanel.add(btnChoose, gbc_btnChoose);
		configPanel.add(btnExtractPages, gbc_btnExtractPages);
		
		btnExit = new JButton(EXIT_APPLICATION_TEXT);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.anchor = GridBagConstraints.WEST;
		gbc_btnExit.insets = new Insets(0, 0, 5, 5);
		gbc_btnExit.gridx = 1;
		gbc_btnExit.gridy = 7;
		configPanel.add(btnExit, gbc_btnExit);
	}
	
	private String showShortFilePath(String filePath, int length) {
		if(filePath.length() > length)
			return "..." + filePath.substring(filePath.length() - length);
		return filePath;
	}
	
	//===========================================================================
	//                       EVENT HANDLERs START
	//===========================================================================
	
	// File chooser
	private class OpenFileHanlder implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String initDirPath = StringUtils.isEmpty(PropertiesFileUtil.getProperty(Constant.DIR_FILE_PATH_KEY)) ? Constant.DESTOP_PATH : PropertiesFileUtil.getProperty(Constant.DIR_EXTRACT_PATH_KEY);
			logger.info("0. initFileDirPath: " + PropertiesFileUtil.getProperty(Constant.DIR_FILE_PATH_KEY));
			if(!StringUtils.isEmpty(PropertiesFileUtil.getProperty(Constant.DIR_FILE_PATH_KEY))) {
				initDirPath = PropertiesFileUtil.getProperty(Constant.DIR_FILE_PATH_KEY);
				logger.info("1. initFileDirPath: " + initDirPath);
			}
			logger.info("2. initFileDirPath: " + initDirPath);
			fileChooser.setCurrentDirectory(new java.io.File(initDirPath));
			
			int result = fileChooser.showOpenDialog(contentPane);
			
			// If a pdf file is selected
			if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = fileChooser.getSelectedFile();
			    selectedFileName = selectedFile.getName();
			    PropertiesFileUtil.setProperty(Constant.DIR_FILE_PATH_KEY, fileChooser.getCurrentDirectory().getAbsolutePath());
			    System.out.println("Phat: " + fileChooser.getCurrentDirectory().getAbsolutePath());
			    lblFile.setText(showShortFilePath(selectedFile.getAbsolutePath(), LENGTH_FILE_PATH_SHOW));
			    lblFile.setToolTipText(selectedFile.getAbsolutePath());
			    filePath = selectedFile.getAbsolutePath();
			    topButtonPanel.revalidate();
			    isFileSelected = true;
			}
		}
	}
	
	// Extract pages handler
	private class ExtractPagesHanlder implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean isCorrectPageRanges = false;
			int startPage = -1;
			int endPage = -1;
			
			try {
				startPage = Integer.parseInt(txtStartPage.getText());
				endPage = Integer.parseInt(txtEndPage.getText());
				if(startPage > 0 && endPage > 0 && endPage >= startPage) {
					isCorrectPageRanges = true;
				}
			} catch(NumberFormatException ex) {
				logger.error(ex.getMessage());
			}
			
			// If user selected a pdf file and enter page ranges correctly.
			if(isFileSelected && isCorrectPageRanges) {
				//setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				progressBarDialog = new ProgressBarDialog();
				progressBarDialog.setVisible(true);
				progressBarDialog.revalidate();
				
				selectedFileName = getFileName(selectedFileName);
				
				// Construct Extract Config
				config = new PdfExtractConfig();
				config.setPathDirExtract(selectedDirExtract + File.separator + selectedFileName);
				config.setStartPage(Integer.parseInt(txtStartPage.getText()));
				config.setEndPage(Integer.parseInt(txtEndPage.getText()));
				config.setFilePath(filePath);
				config.setSelectedFile(selectedFileName);
				
				// Extract text and images
				boolean result = doParser();
				
				// Reset mouse to be normal
/*			    setCursor(null);
			    if(result) {
				    // Inform user the Extract process is successful
				    //JOptionPane.showMessageDialog(new JFrame(), "Extract file successfully", "Extract file", JOptionPane.INFORMATION_MESSAGE);
				   
				    // Navigate to the extracted directory
				    Desktop desktop = Desktop.getDesktop();
			        File dirToOpen = null;
			        try {
			            dirToOpen = new File(config.getPathDirExtract());
			            desktop.open(dirToOpen);
			        } catch (IOException ex) {
			            logger.error("Cannot open: " + config.getPathDirExtract() + "\n" + ex.getStackTrace());
			        }
			    }*/
			} else if(!isFileSelected){
				JOptionPane.showMessageDialog(new JFrame(),
					    "Please select pdf file before extracting pages!",
					    "Extract pages",
					    JOptionPane.ERROR_MESSAGE);
			} else if(!isCorrectPageRanges){
				JOptionPane.showMessageDialog(new JFrame(),
					    "Please correct the page ranges",
					    "Extract pages",
					    JOptionPane.ERROR_MESSAGE);
			} else {
				logger.error("There is something wrong!!!!!");
			}
		}
	}
	
	// Choose target folder handler
	private class ChooseTargetFolderHanlder implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String initDirPath = StringUtils.isEmpty(PropertiesFileUtil.getProperty(Constant.DIR_EXTRACT_PATH_KEY)) ? Constant.DESTOP_PATH : PropertiesFileUtil.getProperty(Constant.DIR_EXTRACT_PATH_KEY);
			
			logger.info("0. initDirPath: " + PropertiesFileUtil.getProperty(Constant.DIR_EXTRACT_PATH_KEY));
			if(!StringUtils.isEmpty(PropertiesFileUtil.getProperty(Constant.DIR_EXTRACT_PATH_KEY))) {
				initDirPath = PropertiesFileUtil.getProperty(Constant.DIR_EXTRACT_PATH_KEY);
				logger.info("1. initDirPath: " + initDirPath);
			}
			logger.info("2. initDirPath: " + initDirPath);
			folderChooser.setCurrentDirectory(new java.io.File(initDirPath));
			int result = folderChooser.showOpenDialog(contentPane);
			if(result == JFileChooser.APPROVE_OPTION) {
				selectedDirExtract = folderChooser.getSelectedFile().getAbsolutePath();
				PropertiesFileUtil.setProperty(Constant.DIR_EXTRACT_PATH_KEY, selectedDirExtract);
				lblDirPath.setText(showShortFilePath(selectedDirExtract, LENGTH_FOLDER_PATH_SHOW));
				lblDirPath.setToolTipText(selectedDirExtract);
				contentPane.revalidate();
			}
		}
		
	}
	
	//===========================================================================
	//                       EVENT HANDLERs END
	//===========================================================================
	
	
	private boolean writeResult(PdfHandler pdfHandlerFacade, ToolParser toolParser) {
		
		String partialDirName = "";
		String tempDir = selectedDirExtract + File.separator + selectedFileName + File.separator;
		if(toolParser == ToolParser.PDFBOX) {
			partialDirName = "_pdfbox";
			config.setDirImageName(tempDir + "Images_pdfbox");
		} else if(toolParser == ToolParser.ITEXT) {
			partialDirName = "_itext";
			config.setDirImageName(tempDir + "Images_itext");
		} else if(toolParser == ToolParser.ICEPDF) {
			partialDirName = "_icepdf";
			config.setDirImageName(tempDir + "Images_icepdf");
		}
		
		
		BufferedWriter bufferedWriter = null;
		FileWriter fileWriter = null;
		boolean isCreatedDirExtractImages = true;
	    try {
	    	String pathExtract = config.getDirImageName();
	    	File resultDir = new File(pathExtract);
	    	if(!resultDir.exists()) {
	    		
	    		// Cannot create dir to extract images
	    		if(!resultDir.mkdirs()) {
	    			isCreatedDirExtractImages = false;
	    			JOptionPane.showMessageDialog(new JFrame(),
	    					toolParser.toString() + " - Cannot create folder to extract images",
						    "Error extract file",
						    JOptionPane.ERROR_MESSAGE);
	    			return false;
	    		}
	    	}
	    	
	    	if(isCreatedDirExtractImages) {
	    		// Extract images
	    		//pdfHandlerFacade.extract(config);
	    	}
			
	    	// Construct file path to extract text
	    	String path = config.getPathDirExtract() + File.separator + selectedFileName + partialDirName  + Constant.TEXT_EXTENSION;
	    	boolean isCreatedFileExtractText = true;
	    	
			File file = new File(path);
			if(!file.exists()) {
				 if(!file.createNewFile()) {
					 isCreatedFileExtractText = false;
					 JOptionPane.showMessageDialog(new JFrame(),
							 toolParser.toString() + " - Cannot create file to extract",
							    "Error extract file",
							    JOptionPane.ERROR_MESSAGE);
					 return false;
				 }
			}
			
			if(isCreatedFileExtractText) {
				// Extract text
				PdfInfo pdfInfo = pdfHandlerFacade.parse(config);
				fileWriter = new FileWriter(file.getAbsoluteFile());
				bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(pdfInfo.getText());
				bufferedWriter.flush();
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
				logger.error("Cannot free resources: " + ex.getMessage());
			}
		}
	    return true;
	}
	
	
	private boolean doParser() {
		// ====================== Use PDF Box =================================
	    
		String tempDir = selectedDirExtract + File.separator + selectedFileName + File.separator;
		//config.setDirImageName(tempDir + "Images_" + ToolParser.PDFBOX.toString().toLowerCase());
		PdfExtractConfig pdfBoxConfig = new PdfExtractConfig(config);
		pdfBoxConfig.setDirImageName(tempDir + "Images_" + ToolParser.PDFBOX.toString().toLowerCase());
	    // Pdf Parser
	    PdfBoxParser pdfBoxParser = new PdfBoxParser();
	    PdfParser pdfBoxPdfParser = new PdfParser();
	    pdfBoxPdfParser.setPdfParserAlgorithm(pdfBoxParser);
	    
	    // Image Extractor
	    PdfBoxImageExtractor pdfBoxImageExtractor = new PdfBoxImageExtractor();
	    ImageExtractorImpl pdfBoxImageExtractorImpl = new ImageExtractorImpl();
	    pdfBoxImageExtractorImpl.setAlgorithm(pdfBoxImageExtractor);
	    
	    PdfHandlerFacade pdfBoxHandlerFacade = new PdfHandlerFacade(pdfBoxPdfParser, pdfBoxImageExtractorImpl);
	    //boolean result = writeResult(pdfBoxHandlerFacade, ToolParser.PDFBOX);
	    //if(!result) {
	    	//return false;
	    //ßßßß}
	    //JProgressBar progressBar, PdfHandler pdfHandler, PdfExtractConfig config, ToolParser toolParser
	    //SwingUtilities.invokeLater(new ParserTask(progressBarDialog.getPdfBoxProgressBar(), pdfBoxHandlerFacade, config, ToolParser.PDFBOX));
	    ParserTask pdfBoxTask = new ParserTask(progressBarDialog.getPdfBoxProgressBar(), pdfBoxHandlerFacade, pdfBoxConfig, ToolParser.PDFBOX);
	    pdfBoxTask.execute();
	    
	    
	    //=================== Use iText ==================================
	    
	    // iText Parser
	    ItextParser itextPdfParser = new ItextParser();
	    PdfParser iTextPdfParser = new PdfParser();
	    iTextPdfParser.setPdfParserAlgorithm(itextPdfParser);
	    
	    // Image Extractor
	    ItextImageExtractor itextImageExtractor = new ItextImageExtractor();
	    ImageExtractorImpl iTextImageExtractorImpl = new ImageExtractorImpl();
	    iTextImageExtractorImpl.setAlgorithm(itextImageExtractor);
	    
	    // Inject DocumentParser and ImageExtractor
	    PdfHandlerFacade iTextHandlerFacade = new PdfHandlerFacade(iTextPdfParser, iTextImageExtractorImpl);
	    /*result = writeResult(iTextHandlerFacade, ToolParser.ITEXT);
	    if(!result) {
	    	return false;
	    }*/
	    //SwingUtilities.invokeLater(new ResultWriterTask(iTextHandlerFacade, ToolParser.ITEXT));
	    //config.setDirImageName(tempDir + "Images_" + ToolParser.ITEXT.toString().toLowerCase());
	    PdfExtractConfig iTextConfig = new PdfExtractConfig(config);
	    iTextConfig.setDirImageName(tempDir + "Images_" + ToolParser.ITEXT.toString().toLowerCase());
	    ParserTask iTextTask = new ParserTask(progressBarDialog.getiTextProgressBar(), iTextHandlerFacade, iTextConfig, ToolParser.ITEXT);
	    iTextTask.execute();
	    
	    
	    //=================== Use IcePdf ==================================
			    
	    // iText Parser
	    IcePdfParser iceParser = new IcePdfParser();
	    PdfParser icePdfParser = new PdfParser();
	    icePdfParser.setPdfParserAlgorithm(iceParser);
	    
	    // Image Extractor
	    IcePdfImageExtractor icePdfImageExtractor = new IcePdfImageExtractor();
	    ImageExtractorImpl iceImageExtractorImpl = new ImageExtractorImpl();
	    iceImageExtractorImpl.setAlgorithm(icePdfImageExtractor);
	    
	    // Inject DocumentParser and ImageExtractor
	    PdfHandlerFacade icePdfHandlerFacade = new PdfHandlerFacade(icePdfParser, iceImageExtractorImpl);
	    
	    PdfExtractConfig icePdfConfig = new PdfExtractConfig(config);
	    icePdfConfig.setDirImageName(tempDir + "Images_" + ToolParser.ICEPDF.toString().toLowerCase());
	    ParserTask icePdfTask = new ParserTask(progressBarDialog.getIcePdfProgressBar(), icePdfHandlerFacade, icePdfConfig, ToolParser.ICEPDF);
	    icePdfTask.execute();
	    /*result = writeResult(icePdfHandlerFacade, ToolParser.ICEPDF);
	    if(!result) {
	    	return false;
	    }*/
	    return true;
	}
	
	// Get the file name of filePath with out extension
	private String getFileName(String filePath) {
		int pos = filePath.lastIndexOf(".");
		if(pos <= 0)
			return filePath;
		return filePath.substring(0, pos);
	}
}
