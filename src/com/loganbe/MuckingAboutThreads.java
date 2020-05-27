package com.loganbe;

import java.util.ArrayList;
import java.util.List;

public class MuckingAboutThreads {

	public static void main(String[] args) {
		System.out.println("Started to Muck About!");
		new MuckingAboutThreads();
	}

	public MuckingAboutThreads() {
		int threadCount = 10;
		int[] attemptCounter = new int[threadCount];
		List<Boolean> threadStatus = new ArrayList<Boolean>(threadCount);
		for (int i = 0; i < threadCount; i++) {
			threadStatus.add(new Boolean(false));
		}

		for(int i = 0; i < threadCount; i++) {

			final int threadID = i;

			new Thread() {
				public synchronized void run() {
					int counter = 0;
					double answer = 0;
					while (answer != 10) {
						//answer = Math.floor(Math.random() * 10); // will never reach 10, because 0.9 will always round down?
						answer = Math.round(Math.random() * 10);
						counter++;
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println("Thread: " + threadID + ". " + (int)answer + " reached on the " + counter + "th attempt");
					attemptCounter[threadID] = counter;

					threadStatus.set(threadID, true);
				}
			}.start();
		}

		// let's add two things
		// calculate the average number of attempts - so store each threads result in a global array
		// log the thread ID to see if there is any correlation between order (i.e. did thread created earlier get there quicker)

		while(threadStatus.contains(new Boolean(false))) {
			// wait, do nothing (not really doing nothing though! can we make this more efficient?)
		}

		int totalAttempts = 0; // by all threads
		for(int i = 0; i < attemptCounter.length; i++) {
			totalAttempts += attemptCounter[i];
		}
		double averageAttempts = totalAttempts / attemptCounter.length;
		System.out.println("Average number of attempts was " + averageAttempts);

	}

}
