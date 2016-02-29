package com.manifera.pdfparser.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util {
	
	public static String getJsonFromFile(String fileName) {
		StringBuilder jsonBuilder = new StringBuilder();
		try (
			InputStream dataInputStream = new FileInputStream(fileName);
			InputStreamReader dataReader = new InputStreamReader(dataInputStream);
			BufferedReader br = new BufferedReader(dataReader);) {
			
			String line;
			while ((line = br.readLine()) != null) {
				jsonBuilder.append(line);
			}
		} catch(IOException ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}
		
		return jsonBuilder.toString();
	}
	
	public String addClassName(String jsonString, String className) {
		
		int index = jsonString.lastIndexOf("}");
		if(index < -1) {
			return jsonString;
		}
		
		String classNameValue = ",\"class\": \"" + className + "\"";
		jsonString = jsonString.substring(0, index) + classNameValue + "}";
		return jsonString;
	}
}
