package model;

import java.util.concurrent.Semaphore;

public class Match extends Thread {

	private AthleteThread[] athletes;
	private int runTotal;
	private int shootsAmount;
	private int bikeTotal;
	private Semaphore gunsSemaphore;
	private Semaphore finalMutex;
	private int positionPoints;

	public Match() {
		super();
	}

	public Match(int athletesAmount, int runTotal, int shootsAmount, int gunsAmount, int bikeTotal) {
		this.athletes = new AthleteThread[athletesAmount];
		this.positionPoints = athletesAmount * 10;
		this.runTotal = runTotal;
		this.shootsAmount = shootsAmount;
		this.bikeTotal = bikeTotal;
		this.finalMutex = new Semaphore(1);
		this.gunsSemaphore = new Semaphore(gunsAmount);
		for (int i = 0; i < athletesAmount; i++)
			athletes[i] = new AthleteThread(this);

	}

	public int getRunTotal() {
		return runTotal;
	}

	public int getShootsAmount() {
		return shootsAmount;
	}

	public int getBikeTotal() {
		return bikeTotal;
	}

	Semaphore getGunsSemaphore() {
		return gunsSemaphore;
	}

	Semaphore getFinalMutex() {
		return finalMutex;
	}

	@Override
	public void run() {
		try {
			startMatch();
			waitFinish();
			showResult();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	int catchFinalPoints() {
		int toReturn = positionPoints;
		positionPoints -= 10;
		return toReturn;
	}

	private void startMatch() {
		int athletesAmount = athletes.length;
		for (int i = 0; i < athletesAmount; i++)
			athletes[i].start();
	}

	private void waitFinish() throws InterruptedException {
		do {
			sleep(5);
		} while (!athletesConcluded());
	}

	private boolean athletesConcluded() {
		for (AthleteThread athlete : athletes)
			if (athlete.isAlive())
				return false;

		return true;
	}

	private void showResult() {
		quickSort(athletes, 0, athletes.length - 1);
		System.out.println();
		System.out.println("Classification:");
		
		int athletesAmount = athletes.length;
		for (int i = 0; i < athletesAmount; i++)
			System.out.println((i+1) + "º: " + athletes[athletesAmount - 1 - i] + ": " + athletes[athletesAmount - 1 - i].getPoints());
	}
	
	private static AthleteThread[] quickSort(AthleteThread[] array, int beginIndex, int endIndex) {
		if (beginIndex < endIndex) {
			int pivot = beginIndex;
			int leftPointer = pivot + 1;
			int rightPointer = endIndex;
			pivot = movePointers(array, pivot, leftPointer, rightPointer);
			quickSort(array, beginIndex, pivot - 1);
			quickSort(array, pivot + 1, endIndex);
		}
		return array;
	}

	private static int movePointers(AthleteThread[] array, int pivot, int leftPointer, int rightPointer) {
		while (leftPointer <= rightPointer) {
			while (leftPointer <= rightPointer && array[leftPointer].getPoints() <= array[pivot].getPoints()) {
				leftPointer++;
			}

			while (leftPointer <= rightPointer && array[rightPointer].getPoints() > array[pivot].getPoints()) {
				rightPointer--;
			}
			if (leftPointer <= rightPointer) {
				AthleteThread aux = array[leftPointer];
				array[leftPointer] = array[rightPointer];
				array[rightPointer] = aux;
			}
		}
		AthleteThread aux = array[pivot];
		array[pivot] = array[rightPointer];
		array[rightPointer] = aux;
		pivot = rightPointer;

		return pivot;
	}

}
