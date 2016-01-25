package com.manifera.pdfparser.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressBarDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JProgressBar pdfBoxProgressBar;
	
	private JProgressBar iTextProgressBar;
	
	private JProgressBar icePdfProgressBar;
	
	private static final String EXTRACT_ON_PROGRESS = "Extracting...";
	private JLabel lblPdfBox;
	private JLabel lblItext;
	private JLabel lblIcepdf;
	
	public ProgressBarDialog() {
		initialize();
	}
	
	public void updatePdfBoxProgressBar(int value) {
		pdfBoxProgressBar.setValue(value);
	}
	
	public void updateITextProgressBar(int value) {
		iTextProgressBar.setValue(value);
	}
	
	public void updateIcePdfProgressBar(int value) {
		icePdfProgressBar.setValue(value);
	}
	
	public void updateTitle(String title) {
		setTitle(title);
	}
	
	public JProgressBar getPdfBoxProgressBar() {
		return pdfBoxProgressBar;
	}

	public JProgressBar getiTextProgressBar() {
		return iTextProgressBar;
	}

	public JProgressBar getIcePdfProgressBar() {
		return icePdfProgressBar;
	}

	private void initialize() {
		setTitle(EXTRACT_ON_PROGRESS);
		JPanel panel = new JPanel();
		
		// Pdf Box
		panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{45, 150, 1, 150, 1, 150, 1, 0};
		gbl_panel.rowHeights = new int[] {25, 20, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblPdfBox = new JLabel("PDF Box");
		GridBagConstraints gbc_lblPdfBox = new GridBagConstraints();
		gbc_lblPdfBox.insets = new Insets(0, 0, 5, 5);
		gbc_lblPdfBox.gridx = 1;
		gbc_lblPdfBox.gridy = 0;
		panel.add(lblPdfBox, gbc_lblPdfBox);
		
		lblItext = new JLabel("iText");
		GridBagConstraints gbc_lblItext = new GridBagConstraints();
		gbc_lblItext.insets = new Insets(0, 0, 5, 5);
		gbc_lblItext.gridx = 3;
		gbc_lblItext.gridy = 0;
		panel.add(lblItext, gbc_lblItext);
		
		lblIcepdf = new JLabel("IcePdf");
		GridBagConstraints gbc_lblIcepdf = new GridBagConstraints();
		gbc_lblIcepdf.insets = new Insets(0, 0, 5, 5);
		gbc_lblIcepdf.gridx = 5;
		gbc_lblIcepdf.gridy = 0;
		panel.add(lblIcepdf, gbc_lblIcepdf);
		pdfBoxProgressBar = new JProgressBar();
		pdfBoxProgressBar.setMaximumSize(new Dimension(150, 20));
		pdfBoxProgressBar.setMinimumSize(new Dimension(150, 20));
		pdfBoxProgressBar.setPreferredSize(new Dimension(150, 20));
		pdfBoxProgressBar.setAlignmentX(0f);
		pdfBoxProgressBar.setStringPainted(true);
		GridBagConstraints gbc_pdfBoxProgressBar = new GridBagConstraints();
		gbc_pdfBoxProgressBar.anchor = GridBagConstraints.NORTHWEST;
		gbc_pdfBoxProgressBar.insets = new Insets(0, 0, 0, 5);
		gbc_pdfBoxProgressBar.gridx = 1;
		gbc_pdfBoxProgressBar.gridy = 1;
		panel.add(pdfBoxProgressBar, gbc_pdfBoxProgressBar);
		
		// iText
		panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.gridx = 2;
		gbc.gridy = 1;
		Component rigidArea = Box.createRigidArea(new Dimension(0, 20));
		panel.add(rigidArea, gbc);
		iTextProgressBar = new JProgressBar();
		iTextProgressBar.setMaximumSize(new Dimension(150, 20));
		iTextProgressBar.setMinimumSize(new Dimension(150, 20));
		iTextProgressBar.setPreferredSize(new Dimension(150, 20));
		iTextProgressBar.setAlignmentX(0f);
		iTextProgressBar.setStringPainted(true);
		GridBagConstraints gbc_iTextProgressBar = new GridBagConstraints();
		gbc_iTextProgressBar.anchor = GridBagConstraints.NORTHWEST;
		gbc_iTextProgressBar.insets = new Insets(0, 0, 0, 5);
		gbc_iTextProgressBar.gridx = 3;
		gbc_iTextProgressBar.gridy = 1;
		panel.add(iTextProgressBar, gbc_iTextProgressBar);
		GridBagConstraints gbc_1 = new GridBagConstraints();
		gbc_1.anchor = GridBagConstraints.NORTH;
		gbc_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_1.insets = new Insets(0, 0, 0, 5);
		gbc_1.gridx = 4;
		gbc_1.gridy = 1;
		Component rigidArea_2 = Box.createRigidArea(new Dimension(0, 20));
		panel.add(rigidArea_2, gbc_1);
		icePdfProgressBar = new JProgressBar();
		icePdfProgressBar.setMaximumSize(new Dimension(150, 20));
		icePdfProgressBar.setMinimumSize(new Dimension(150, 20));
		icePdfProgressBar.setPreferredSize(new Dimension(150, 20));
		icePdfProgressBar.setAlignmentX(0f);
		icePdfProgressBar.setStringPainted(true);
		GridBagConstraints gbc_icePdfProgressBar = new GridBagConstraints();
		gbc_icePdfProgressBar.anchor = GridBagConstraints.NORTHWEST;
		gbc_icePdfProgressBar.insets = new Insets(0, 0, 0, 5);
		gbc_icePdfProgressBar.gridx = 5;
		gbc_icePdfProgressBar.gridy = 1;
		panel.add(icePdfProgressBar, gbc_icePdfProgressBar);
		GridBagConstraints gbc_2 = new GridBagConstraints();
		gbc_2.anchor = GridBagConstraints.NORTH;
		gbc_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_2.gridx = 6;
		gbc_2.gridy = 1;
		Component rigidArea_1 = Box.createRigidArea(new Dimension(0, 20));
		panel.add(rigidArea_1, gbc_2);
		
		// IcePdf
		panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		
	    getContentPane().add(panel, BorderLayout.CENTER);
	    pack();
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    setVisible(true);
	}
	
	public static void main(String[] args) {
		ProgressBarDialog progressBar = new ProgressBarDialog();
		//progressBar.updateProgess(30);
	}
}
