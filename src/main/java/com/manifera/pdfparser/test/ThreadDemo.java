package com.manifera.pdfparser.test;

import java.util.concurrent.TimeUnit;

public class ThreadDemo {

	private static boolean stopRequested;
	
	public static void main(String[] args) throws InterruptedException {
		Thread backgroudThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int i = 0;
				while(!stopRequested) {
					System.out.println(i++);
				}
			}
		});
		backgroudThread.start();
		TimeUnit.SECONDS.sleep(1);
		
		stopRequested = true;
		System.out.println("DONE");
	}
}
