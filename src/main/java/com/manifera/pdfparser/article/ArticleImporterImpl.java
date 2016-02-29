package com.manifera.pdfparser.article;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manifera.pdfparser.domain.Article;
import com.manifera.pdfparser.exception.ImportArticleException;

import flexjson.JSONSerializer;

public class ArticleImporterImpl implements ArticleImporter {

	private static final Logger LOG = LoggerFactory.getLogger(ArticleImporterImpl.class);
	
	//private static final PropertiesFileUtil wsProperties = new PropertiesFileUtil(Constant.WS_PROPERTIES_NAME);
	
	@Override
	public String getJsonFormat(Article article) {
		String jsonString = new JSONSerializer().include("body").exclude("*.class").serialize(article);
		return jsonString;
	}

	@Override
	public String doImport(String articleJson) throws IOException, ImportArticleException {
		String wsEndpoint = "http://dev.magbe.com.sg/api/article";
		/*String magbeApiEndpoint = wsProperties.getProperty("ws.api.endpoint");
		if(magbeApiEndpoint == null || magbeApiEndpoint.isEmpty()) {
			throw new ImportArticleException("WEB SERIVCE API ENPOINT cannot be empty - Please update ws.api.endpoint property in webservice.properties");
		}*/
		
		LOG.info("Ws Enpoint: " + wsEndpoint);
		URL url = new URL(wsEndpoint);
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Accept", "application/json");
		connection.setDoOutput(true);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "utf-8"); 
		outputStreamWriter.write(articleJson);
		outputStreamWriter.flush();
      
		// Get the response
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String response = bufferedReader.readLine();
		
		LOG.info("Response: " + response);
		return response;
	}

}
