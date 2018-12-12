package hw5;

public class BankTest {
	
	public static void main(String[] args) throws InterruptedException {
		
		int numBankAccounts = 3;
		int numAccountHolders = 6;
		int amount = 100;
		int numTransactions = 3;
		
		int initialBalance0 = 1000;
		int initialBalance1 = 2000;
		int initialBalance2 = 3000;
		
		// initialize the 3 bank accounts
		BankAccount[] bankaccounts = new BankAccount[numBankAccounts];
		
		bankaccounts[0] = new BankAccount(initialBalance0);
		bankaccounts[1] = new BankAccount(initialBalance1);
		bankaccounts[2] = new BankAccount(initialBalance2);
		
		//initialize the 6 account holders
		AccountHolder [] accountholders = new AccountHolder[numAccountHolders];
		
		for(int i = 0; i < numAccountHolders; i++) {
			if (i >= 0 && i <= 2)
				accountholders[i] = new AccountHolder(bankaccounts[0], i, amount, numTransactions);
			else if (i >= 3 && i <= 4)
				accountholders[i] = new AccountHolder(bankaccounts[1], i, amount, numTransactions);
			else // i == 5
				accountholders[i] = new AccountHolder(bankaccounts[2], i, amount, numTransactions);
			accountholders[i].start();
		}// for
		
		for (int i = 0; i < numAccountHolders; i++)
			accountholders[i].join();
		if (bankaccounts[0].getBalance() == initialBalance0
				&& bankaccounts[1].getBalance() == initialBalance1
				&& bankaccounts[2].getBalance() == initialBalance2) {
			System.out.format("Correct!!!");
		} else {
			System.out.format("Error!");
		}
	}// main

}//BankTest
