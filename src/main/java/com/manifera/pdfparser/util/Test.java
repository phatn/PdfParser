package com.manifera.pdfparser.util;

import com.manifera.pdfparser.domain.Response;

public class Test {
	
	public static final String FILE_NAME = "/Users/phat/Development/workspace/PDFParser/json-data/data.txt";
	
	public static void main(String[] args) {
		String jsonString = Util.getJsonFromFile(FILE_NAME);
		Response response = new PdfParserDeserializer<Response>().deserializer(jsonString);
		System.out.println(response);
	}
}
