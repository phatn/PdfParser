package com.manifera.pdfparser.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesFileUtil {
	
	private static Logger LOG = LoggerFactory.getLogger(PropertiesFileUtil.class);
	
	private static Properties prop = new Properties();
	
	private static File USER_HOME;
	
	private String fileName;
	
	static {
		
		// Get user home for Mac
		if(SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX) {
			USER_HOME = new File(System.getProperty("user.home") + File.separator + "Documents");
		} 
		// Get user home for Windows
		else if(SystemUtils.IS_OS_WINDOWS) {
			USER_HOME = new File(System.getProperty("user.home") + File.separator + "My Documents");
		} 
		// Get user home for others (Linux)
		else {
			USER_HOME = new File(".");
		}
		
		LOG.info("USER_HOME: " + USER_HOME.getAbsolutePath());
	}
	
	public PropertiesFileUtil(String fileName) {
		this.fileName = fileName;
	}
	
	public String getProperty(String key) {
		
		try(InputStream inputStream = new FileInputStream(new File(USER_HOME + File.separator + fileName))) {
			prop.load(inputStream);
			return prop.getProperty(key);
		} catch(FileNotFoundException ex) {
			
			LOG.error("Cannot file " + fileName + " - Error: "+ ex.getMessage());
			ex.printStackTrace();
		} catch(IOException ex) {
			
			LOG.error("Cannot load file: " + fileName + " - Error: " + ex.getMessage());
			ex.printStackTrace();
		}

		return "";
	}
	
	public void setProperty(String key, String value) {
		try (OutputStream outputStream = new FileOutputStream(new File(USER_HOME + File.separator + fileName))){
			prop.setProperty(key, value);
			prop.store(outputStream, "");
		} catch(FileNotFoundException ex) {
			
			LOG.error("Cannot file " + fileName + " - Error: "+ ex.getMessage());
			ex.printStackTrace();
		} catch(IOException ex) {
			
			LOG.error("Cannot load file: " + fileName + " - Error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void createAppConfigFile() {
		
		File propertiesFile = new File(USER_HOME, fileName);
		if(!propertiesFile.exists()) {
			
			LOG.info(propertiesFile.getAbsolutePath() + "is not existed - should crate a new one.");
			try {
				propertiesFile.createNewFile();
			} catch (IOException e) {
				
				LOG.error("Error when creating " + propertiesFile.getAbsolutePath() + " file - Error: "+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
}
