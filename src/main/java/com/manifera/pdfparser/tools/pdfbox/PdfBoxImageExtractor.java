package com.manifera.pdfparser.tools.pdfbox;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manifera.pdfparser.domain.ImageExtractResult;
import com.manifera.pdfparser.domain.PdfExtractConfig;
import com.manifera.pdfparser.tools.ImageExtractorAlgorithm;

public class PdfBoxImageExtractor implements ImageExtractorAlgorithm {

	private static final Logger LOG = LoggerFactory.getLogger(PdfBoxImageExtractor.class);
	
	private int imageCounter = 1;
	
	@Override
	public ImageExtractResult extract(PdfExtractConfig config) throws IOException {
		LOG.info("Thread-" + Thread.currentThread().getId() + ": Enter extract method");
		int totalExtectedPage = 0;
		 PDDocument document = null;
		 document = PDDocument.load(config.getFilePath());
		 List<PDPage> pages = document.getDocumentCatalog().getAllPages();
		 int max = config.getEndPage() > pages.size() ? pages.size() : config.getEndPage();
		 PDXObjectImage image;
		 for(int i = config.getStartPage() - 1; i < max; i++) {
			 PDResources resources = pages.get(i).getResources();
			 Map<String, PDXObjectImage> images = resources.getImages();
			 if(images != null) {
				for(Map.Entry<String, PDXObjectImage> entry : images.entrySet()) {
					image = entry.getValue();
					String name = getUniqueFileName(entry.getKey(), image.getSuffix());
					LOG.info("Thread-" + Thread.currentThread().getId() + ": write to file: " + config.getDirImageName() + File.separator + name);
					image.write2file(config.getDirImageName() + File.separator + name);
					totalExtectedPage += 1;
					image = null;
				}
			 }
		 }
		 System.out.println("Total extracted images: " + totalExtectedPage);
		return new ImageExtractResult(totalExtectedPage);
	}
	private String getUniqueFileName( String prefix, String suffix ) {
        String uniqueName = null;
        File f = null;
        while(f == null || f.exists()) {
            uniqueName = prefix + "-" + imageCounter;
            f = new File(uniqueName + "." + suffix);
            imageCounter++;
        }
        return uniqueName;
    }
}
