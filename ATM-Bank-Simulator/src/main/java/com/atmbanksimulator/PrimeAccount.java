package com.atmbanksimulator;

public class PrimeAccount extends BankAccount { // Creating prime account with additional withdrawl rules.

    public PrimeAccount(String a, String p, int b) {
        super(a, p, b); // Calls BankAccount constructor and passes account number, pin number, and balance details.
    }

    @Override
    public boolean withdraw(int amount) { // Overrides withdraw method from BankAccount to add additional rules.

        if(amount > 1000) {
            return false; // Prevents withdrawls over 1,000 and returns false to show the withdrawl was denied.
        }
        return super.withdraw(amount); // If within the allowed limit, calls the withdraw method from BankAccount.
    }
}

