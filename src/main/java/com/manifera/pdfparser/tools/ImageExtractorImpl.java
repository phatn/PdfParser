package com.manifera.pdfparser.tools;

import java.io.IOException;

import com.manifera.pdfparser.domain.ImageExtractResult;
import com.manifera.pdfparser.domain.PdfExtractConfig;

public class ImageExtractorImpl implements ImageExtractor {

	private ImageExtractorAlgorithm algorithm;
	
	public ImageExtractorAlgorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(ImageExtractorAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	@Override
	public ImageExtractResult extract(PdfExtractConfig config) throws IOException {
		return algorithm.extract(config);
	}
}
