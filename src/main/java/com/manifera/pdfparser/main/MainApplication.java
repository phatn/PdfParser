package com.manifera.pdfparser.main;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.manifera.pdfparser.gui.MainWindow;

public class MainApplication {

	private MainWindow mainWindow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApplication window = new MainApplication();
					window.mainWindow.setVisible(true);
					System.out.println(Thread.currentThread().getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainApplication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		mainWindow = new MainWindow("MagBe PDF Parser - 1.0.0");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
