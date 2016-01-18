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
	
	private static final String FILE_NAME = "pdfparser.properties";
	
	private static File USER_HOME;
	
	static {
		if(SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX) {
			USER_HOME = new File(System.getProperty("user.home") + File.separator + "Documents");
		} else if(SystemUtils.IS_OS_WINDOWS) {
			USER_HOME = new File(System.getProperty("user.home") + File.separator + "My Documents");
		} else {
			USER_HOME = new File(".");
		}
		System.out.println("USER_HOME: " + USER_HOME);
	}
	
	private PropertiesFileUtil() {}
	
	public  static String getProperty(String key) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(USER_HOME + File.separator + FILE_NAME));
			prop.load(inputStream);
			return prop.getProperty(key);
		} catch(FileNotFoundException ex) {
			LOG.error(ex.getMessage());
		} catch(IOException ex) {
			LOG.error(ex.getMessage());
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return "";
	}
	
	public static void setProperty(String key, String value) {
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(new File(USER_HOME + File.separator + FILE_NAME));
			prop.setProperty(key, value);
			prop.store(outputStream, "");
		} catch(FileNotFoundException ex) {
			LOG.error(ex.getMessage());
		} catch(IOException ex) {
			LOG.error(ex.getMessage());
		} finally {
			if(outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void createAppConfigFile() {
		
		System.out.println("userHome: " + USER_HOME.getAbsolutePath());
		File propertiesFile = new File(USER_HOME, FILE_NAME);
		if(!propertiesFile.exists()) {
			System.out.println("Not existed");
			try {
				propertiesFile.createNewFile();
			} catch (IOException e) {
				LOG.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		//createAppConfigFile();
		System.out.println(System.getProperty("user.dir") + File.separator + "workspace");
	}
}
