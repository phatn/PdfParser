package com.manifera.pdfparser.tools.itext;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.manifera.pdfparser.domain.ImageExtractResult;
import com.manifera.pdfparser.domain.PdfExtractConfig;
import com.manifera.pdfparser.tools.ImageExtractorAlgorithm;

public class ItextImageExtractorBak implements ImageExtractorAlgorithm {

	@Override
	public ImageExtractResult extract(PdfExtractConfig config) throws IOException {
		PdfReader reader = new PdfReader(config.getFilePath());
		PRStream prStream = null;
		PdfImageObject pdfImageObject = null;
		PdfObject pdfObject = null;
		
		// Number of objects in pdf document
		int pdfObjectCount = reader.getXrefSize(); 
		int extractedImageCount = 0;
		for(int i = 0; i < pdfObjectCount; i++) {
			
			// Get the object at the index i in the objects collection
			pdfObject = reader.getPdfObject(i);
			
			// Object not found so continue
			if(pdfObject == null || !pdfObject.isStream())
			    continue;
			
			// Cast object to stream
			prStream = (PRStream)pdfObject;
			
			// Get the object type
			PdfObject type = prStream.get(PdfName.SUBTYPE);
			// Check if the object is the image type object
			if(type != null && type.toString().equals(PdfName.IMAGE.toString())) {
				
				// Get the image
				pdfImageObject = new PdfImageObject(prStream);
				
				// Convert the image to buffered image
			    BufferedImage bufferedImage = pdfImageObject.getBufferedImage();
			    
			    // Write the buffered image to local disk
			    ImageIO.write(bufferedImage, "jpg", new File(config.getDirImageName() + File.separator + i + ".jpg"));
			    extractedImageCount++;
			}
		}
		return new ImageExtractResult(extractedImageCount);
	}
}
