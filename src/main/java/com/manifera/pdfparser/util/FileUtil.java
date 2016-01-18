package com.manifera.pdfparser.util;

public class FileUtil {
	
	private FileUtil() {}
	
	public static String getFileName(String filePath) {
		int pos = filePath.lastIndexOf(".");
		if(pos <= 0)
			return "untitled";
		return filePath.substring(0, pos);
	}
	
}
