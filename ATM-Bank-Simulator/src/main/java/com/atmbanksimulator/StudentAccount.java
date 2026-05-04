package com.atmbanksimulator;

import java.util.HashMap;

public class StudentAccount extends BankAccount { // Creating prime account with additional withdrawl rules.

    public StudentAccount(String a, String p, int b) { /* Calls BankAccount constructor and passes account number,
     pin number, and balance details. */
        super(a, p, b); //calls BankAccount constructor
    }

    @Override
    public boolean withdraw(int amount) { // Overrides withdraw method from BankAccount to add additional rules.

        // new rule
        if (amount > 100) {
            return false; // Prevents withdrawls over 100 and returns false to show the withdrawl was denied.
        }
        return super.withdraw(amount); // If within the allowed limit, calls the withdrawl method from BankAccount.
    }
}
