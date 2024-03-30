package model;

import java.util.concurrent.Semaphore;

public class AthleteThread extends Thread {
	
	private Match match;
	private int runTotal;
	private int shootsAmount;
	private int bikeTotal;
	private Semaphore gunsSemaphore;
	private Semaphore finalMutex;
	private int points;
	
	public AthleteThread() {
		super();
	}

	public AthleteThread(Match match) {
		this.match = match;
		this.gunsSemaphore = match.getGunsSemaphore();
		this.finalMutex = match.getFinalMutex();
		this.runTotal = match.getRunTotal();
		this.shootsAmount = match.getShootsAmount();
		this.bikeTotal = match.getBikeTotal();
	}

	public int getPoints() {
		return points;
	}

	@Override
	public void run() {
		try {
			performRun();
			performShoots();
			performBike();
			finish();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private void performRun() throws InterruptedException {
		System.out.println(this  + ": starts running");
		int progress = 0;
		int rolledMovement = 0;
		while(progress < runTotal) {
			rolledMovement = (int) ((Math.random() * 6) + 20);
			progress += rolledMovement;
			sleep(30);
		}
		System.out.println(this  + ": stops running");
	}
	
	private void performShoots() throws InterruptedException {
		System.out.println(this  + ": starts shooting");
		gunsSemaphore.acquire();
		int rolledTime = 0;
		for(int i = 0; i < shootsAmount; i++) {
			points += (int) ((Math.random() * 11));
			rolledTime = (int) ((Math.random() * 2501) + 500);
			sleep(rolledTime);
		}
		System.out.println(this  + ": stops shooting (make " + points + " points)");
		gunsSemaphore.release();
	}
	
	private void performBike() throws InterruptedException {
		System.out.println(this  + ": starts biking");
		int progress = 0;
		int rolledMovement = 0;
		while(progress < bikeTotal) {
			rolledMovement = (int) ((Math.random() * 11) + 30);
			progress += rolledMovement;
			sleep(40);
		}
		System.out.println(this  + ": stops biking");
	}
	
	private void finish() throws InterruptedException {
		finalMutex.acquire();
		points += match.catchFinalPoints();
		System.out.println(this  + ": finished with " + points);
		finalMutex.release();
	}
	
	@Override
	public String toString() {
		return this.getName().replace("Thread-", "Athlete ");
	}
	
}
