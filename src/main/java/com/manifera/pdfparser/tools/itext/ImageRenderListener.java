package com.manifera.pdfparser.tools.itext;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

public class ImageRenderListener implements RenderListener {

	private static final Logger LOG = LoggerFactory.getLogger(ImageRenderListener.class);
	
	protected String path = "";
	
	public ImageRenderListener(String path) {
		this.path = path;
	}
	
	public void beginTextBlock() { }

	public void endTextBlock() { }

	public void renderImage(ImageRenderInfo renderInfo) {
		try {
			String fileName;
			FileOutputStream os;
			PdfImageObject image = renderInfo.getImage();
			PdfName filter = (PdfName)image.get(PdfName.FILTER);
			if(PdfName.DCTDECODE.equals(filter)) {
				fileName = String.format(path, renderInfo.getRef().getNumber(), "jpg");
				LOG.info("Thread-" + Thread.currentThread().getId() + ": Enter DCTDECODE block - fileName: " + fileName);
				os = new FileOutputStream(fileName);
				os.write(image.getImageAsBytes());
				os.flush();
				os.close();
			} else if (PdfName.JPXDECODE.equals(filter)) {
				fileName = String.format(path, renderInfo.getRef().getNumber(), "jp2");
				LOG.info("Thread-" + Thread.currentThread().getId() + ": Enter JPXDECODE block - fileName: " + fileName);
				os = new FileOutputStream(fileName);
				os.write(image.getImageAsBytes());
				os.flush();
				os.close();
			} else {
				BufferedImage awtimage = renderInfo.getImage().getBufferedImage();
				if(awtimage != null) {
					fileName = String.format(path, renderInfo.getRef().getNumber(), "png");
					LOG.info("Thread-" + Thread.currentThread().getId() + ": Enter ELSE block - fileName: " + fileName);
					ImageIO.write(awtimage, "png", new FileOutputStream(fileName));
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void renderText(TextRenderInfo renderInfo) { }
}
