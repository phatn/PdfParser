package com.manifera.pdfparser.util;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import flexjson.JSONDeserializer;

public class PdfParserDeserializer<T> implements Serializable {
	
	private Class<T> domainClass;
	
	public Class<T> domainClass() {
		if(domainClass == null) {
			ParameterizedType thisType = (ParameterizedType)getClass().getGenericSuperclass();
			this.domainClass = (Class<T>)thisType.getActualTypeArguments()[0];
		}
		return domainClass;
	}
	
	public T deserializer(String jsonString) {
		JSONDeserializer<T> deserializer = new JSONDeserializer<>();
		return deserializer.use(null, domainClass()).deserialize(jsonString);
	}
	
}
