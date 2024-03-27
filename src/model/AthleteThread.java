package model;

import java.util.concurrent.Semaphore;

public class AthleteThread extends Thread {

	public static int position = 1;
	public static int[] classification;
	public static final int  RUN_TOTAL = 3000;
	public static final int SHOOTS_AMOUNT = 3;
	public static final int  BIKE_TOTAL = 5000; 
	
	private Semaphore gunsSemaphore;
	private Semaphore finalMutex;
	private int points;
	private int shoots;
	
	public AthleteThread() {
		super();
	}

	public AthleteThread(Semaphore gunsSemaphore, Semaphore finalMutex) {
		this.gunsSemaphore = gunsSemaphore;
		this.finalMutex = finalMutex;
		this.points = 0;
		this.shoots = 0;
	}
	
	@Override
	public void run() {
		
	}
	
	private void performRun() throws InterruptedException {
		int progress = 0;
		int rolledMovement = 0;
		while(progress < RUN_TOTAL) {
			rolledMovement = (int) ((Math.random() * 6) + 20);
			progress += rolledMovement;
			sleep(30);
		}
	}
	
	private void performShoots() throws InterruptedException {
		gunsSemaphore.acquire();
		int rolledTime = 0;
		for(int i = 0; i < SHOOTS_AMOUNT; i++) {
			points += (int) ((Math.random() * 11));
			rolledTime = (int) ((Math.random() * 2501) + 500);
			sleep(rolledTime);
		}
		gunsSemaphore.release();
	}
	
	private void performBike() throws InterruptedException {
		int progress = 0;
		int rolledMovement = 0;
		while(progress < BIKE_TOTAL) {
			rolledMovement = (int) ((Math.random() * 11) + 30);
			progress += rolledMovement;
			sleep(40);
		}
	}
	
}
