package com.manifera.pdfparser.tools.icepdf;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;

import com.manifera.pdfparser.domain.ImageExtractResult;
import com.manifera.pdfparser.domain.PdfExtractConfig;
import com.manifera.pdfparser.tools.ImageExtractorAlgorithm;

public class IcePdfImageExtractor implements ImageExtractorAlgorithm {

	private PdfExtractConfig config;
	
	@Override
	public ImageExtractResult extract(PdfExtractConfig config) throws IOException {
		this.config = config;
		String filePath = config.getFilePath();
		Document document = new Document();
		// setup two threads to handle image extraction.
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        
        try {
            document.setFile(filePath);
            // create a list of callables.
            int pages = config.getEndPage() > document.getNumberOfPages() ? document.getNumberOfPages() : config.getEndPage();
            List<Callable<Void>> callables = new ArrayList<Callable<Void>>(pages);
            for (int i = config.getStartPage() - 1; i <= pages; i++) {
                callables.add(new CapturePageImages(document, i));
            }
            executorService.invokeAll(callables);

            executorService.submit(new DocumentCloser(document)).get();
        } catch (InterruptedException e) {
            System.out.println("Error parsing PDF document " + e);
        } catch (ExecutionException e) {
            System.out.println("Error parsing PDF document " + e);
        } catch (PDFException ex) {
            System.out.println("Error parsing PDF document " + ex);
        } catch (PDFSecurityException ex) {
            System.out.println("Error encryption not supported " + ex);
        } catch (FileNotFoundException ex) {
            System.out.println("Error file not found " + ex);
        } catch (IOException ex) {
            System.out.println("Error handling PDF document " + ex);
        }
        executorService.shutdown();
		return null;
	}
	/**
     * Captures images found in a page  parse to file.
     */
    public class CapturePageImages implements Callable<Void> {
        private Document document;
        private int pageNumber;

        private CapturePageImages(Document document, int pageNumber) {
            this.document = document;
            this.pageNumber = pageNumber;
        }

        public Void call() {
            try {
                Page currentPage = document.getPageTree().getPage(pageNumber);
                int count = 0;
                RenderedImage rendImage;
                List<Image> images = currentPage.getImages();
                for (Image image : images) {
                    count++;
                    if (image != null) {
                        rendImage = (BufferedImage) image;
                        //System.out.println("Capture page " + pageNumber + " image " + count);
                        //File file = new File("files/images/imageCapture_" + pageNumber + "_" + count + ".png");
                        File file = new File(config.getDirImageName() + File.separator + "imageCapture_" + pageNumber + "_" + count + ".png");
                        ImageIO.write(rendImage, "png", file);
                        image.flush();
                    }
                }
                // clears most resource.
                images.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Disposes the document.
     */
    public class DocumentCloser implements Callable<Void> {
        private Document document;

        private DocumentCloser(Document document) {
            this.document = document;
        }

        public Void call() {
            if (document != null) {
                document.dispose();
                //System.out.println("Document disposed");
            }
            return null;
        }
    }
}
