package hw5;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class BankAccount {
	
	private int numOfReaders = 0; // how many readers are reading the shared data
	
	private Semaphore mutex = new Semaphore(1); // used as a mutual exclusion for "numOfReaders"
	
	private Semaphore wrt = new Semaphore(1); // used for mutual exclusion for the writers
	
	private final static int MIN_CHECKING_TIME = 1000;
	private final static int MAX_CHECKING_TIME = 4000;
	
	private final static int MIN_DEPOSIT_TIME = 1000;
	private final static int MAX_DEPOSIT_TIME = 2000;
	
	private final static int MIN_WITHDRAW_TIME = 1000;
	private final static int MAX_WITHDRAW_TIME = 2000;
	
	private int accountId;
	private int balance;
	private Random random;
	
	public BankAccount(int balance) {
		this.balance = balance;
		random = new Random();
	} // Constructor
	
	public int getBalance() throws InterruptedException {
		long threadId = Thread.currentThread().getId();
		int bal;
		System.out.println("The account holder " + threadId + " is requesting a GETBALANCE operation");
		startReading();
		System.out.println("The account holder " + threadId + " is performing a GETBALANCE operation");
		bal = balance;
		randomWait(MIN_CHECKING_TIME, MAX_CHECKING_TIME);
		endReading();
		System.out.println("The account holder " + threadId + " has completed a GETBALANCE operation");
		return bal;
	}// getBalance 
	
	public void deposit(int amount) throws InterruptedException {
		long threadId = Thread.currentThread().getId();
		System.out.println("The account holder " + threadId + " has requesting a DEPOSIT operation");
		startWriting();
		System.out.println("The account holder " + threadId + " has performing a DEPOSIT operation");
		balance += amount;
		randomWait(MIN_DEPOSIT_TIME, MAX_DEPOSIT_TIME);
		endWriting();
		System.out.println("The account holder " + threadId + " has completed a DEPOSIT operation");
	} // deposit
	
	public void withdraw(int amount) throws InterruptedException {
		long threadId = Thread.currentThread().getId();
		System.out.println("The account holder " + threadId + " has requesting a WITHDRAW operation");
		startWriting();
		System.out.println("The account holder " + threadId + " has performing a WITHDRAW operation");
		balance -= amount;
		randomWait(MIN_WITHDRAW_TIME, MAX_WITHDRAW_TIME);
		endWriting();
		System.out.println("The account holder " + threadId + " has completed a WITHDRAW operation");
	} // withdraw
	
	private void startWriting() throws InterruptedException {
		wrt.acquire();
	} // startWriting
	
	private void endWriting() {
		wrt.release();
	} // startWriting
	
	private void startReading() throws InterruptedException {
		mutex.acquire();
		numOfReaders++;
		if (numOfReaders == 1)
			wrt.acquire();
		mutex.release();
	} // startWriting
	
	private void endReading() throws InterruptedException {
		mutex.acquire();
		numOfReaders--;
		if (numOfReaders == 0)
			wrt.release();
		mutex.release();
	} // startWriting
	
	private void randomWait(int minTime, int maxTime) throws InterruptedException {
		Thread.sleep((random.nextInt(maxTime - minTime + 1) + minTime));
	} // randomWait
	
}// BankAccount
