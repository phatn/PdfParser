package com.manifera.pdfparser.test;

public class Task implements Runnable {

	private String threadName;
	
	public Task(String threadName) {
		this.threadName = threadName;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < Integer.MAX_VALUE; i++) {
			System.out.println(threadName + " - " + i);
		}
	}
}
