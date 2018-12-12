package hw5;
import java.util.Random;

import bankaccount.BankAccount;

public class AccountHolder extends Thread{
	
	private final static int MIN_WAIT = 1000;
	private final static int MAX_WAIT = 3000;
	
	private Random random = new Random();
	private int id;
	private hw5.BankAccount ba;
	private int balance;
	private int numTransactions;
	
	public AccountHolder(hw5.BankAccount bankaccounts, int id, int balance, int numTransactions) {
		this.id = id;
		if (numTransactions % 3 != 0) {
			throw new RuntimeException("Num. of transactions must be multiple of 3");
		}
		this.ba = bankaccounts;
		this.balance = balance;
		this.numTransactions = numTransactions;
	} // constructor
	
	public void run() {
		try {
			for (int i = 0; i < numTransactions; i++) {
				randomWait();
				if (i == 0 || i == 3)
					ba.deposit(balance);
				else if (i == 1 || i == 4) 
					ba.getBalance();
				else if (i == 2 || i == 5)
					ba.withdraw(balance);
			} // for
		} catch (InterruptedException e) {}
		System.out.println("The account holder " + id + " has completed his transactions");
	} // run 
	
	public long getId() {
		return id;
	} // getId
	
	private void randomWait() throws InterruptedException {
		Thread.sleep((random.nextInt(MAX_WAIT) - MIN_WAIT + 1) + MIN_WAIT);
	}
}// AccountHolder
