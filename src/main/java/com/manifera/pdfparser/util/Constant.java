package com.manifera.pdfparser.util;

import java.io.File;

public abstract class Constant {
	
	public static final String DESTOP_PATH = System.getProperty("user.home") + File.separator + "Desktop";
	
	public static final String TEXT_EXTENSION = ".txt";
	
	public static final String UNDER_SCORE = "_";
	
	public static final String DIR_EXTRACT_PATH_KEY = "dirExtractPath";
	
	public static final String DIR_FILE_PATH_KEY = "dirFilePath";
	
	public static final String NEW_LINE = System.getProperty("line.separator");
	
	public static final String PDFPARSER_PROPERTIES_NAME = "pdfparser.properties";
	
	public static final String WS_PROPERTIES_NAME = "webservice.properties";
	
	public static final int MAX_HIGHLIGHT_CHARACTER = 150;
	
}
