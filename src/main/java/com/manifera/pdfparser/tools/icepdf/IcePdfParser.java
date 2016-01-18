package com.manifera.pdfparser.tools.icepdf;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.graphics.text.PageText;

import com.manifera.pdfparser.domain.PdfExtractConfig;
import com.manifera.pdfparser.domain.PdfInfo;
import com.manifera.pdfparser.tools.PdfParserAlgorithm;

public class IcePdfParser implements PdfParserAlgorithm {

	@Override
	public PdfInfo parse(PdfExtractConfig config) throws IOException {
		String filePath = config.getFilePath();
		Document document = new Document();
		try {
            document.setFile(filePath);
        } catch (PDFException ex) {
            System.out.println("Error parsing PDF document " + ex);
        } catch (PDFSecurityException ex) {
            System.out.println("Error encryption not supported " + ex);
        } catch (FileNotFoundException ex) {
            System.out.println("Error file not found " + ex);
        }
		StringBuilder textBuilder = new StringBuilder();
		PdfInfo pdfInfo = new PdfInfo();
		try {
            // create a file to write the extracted text to
            //File file = new File("files/text/18.txt");
            //FileWriter fileWriter = new FileWriter(file);

            // Get text from the first page of the document, assuming that there
            // is text to extract.
			int max = config.getEndPage() >= document.getNumberOfPages() ? document.getNumberOfPages(): config.getEndPage();
            for (int pageNumber = config.getStartPage() - 1; pageNumber < max; pageNumber++) {
                PageText pageText = document.getPageText(pageNumber);
                //System.out.println("Extracting page text: " + pageNumber);
                if (pageText != null && pageText.getPageLines() != null) {
                    //fileWriter.write(pageText.toString());
                    textBuilder.append(pageText.toString());
                }
            }
            
            pdfInfo.setText(textBuilder.toString());
            pdfInfo.setFilePath(config.getFilePath());

            // close the writer
            //fileWriter.close();

        } catch (InterruptedException ex) {
            System.out.println("Error paring page " + ex);
        }

        // clean up resources
        document.dispose();
		return pdfInfo;
	}
}
