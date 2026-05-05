//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atmbanksimulator;

public class BankAccount {
    private final String accNumber;
    private String accPasswd;
    private int balance = 0;

    public BankAccount(String accountNumber, String password, int initialBalance) {
        super();
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be empty.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }

        this.accNumber = accountNumber;
        this.accPasswd = password;
        this.balance = initialBalance;
    }

    public boolean withdraw(int amount) {
        if (amount > 0 && this.balance >= amount) {
            this.balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    public boolean deposit(int amount) {
        if (amount <= 0) {
            return false;
        } else {
            this.balance += amount;
            return true;
        }
    }

    public int getBalance() {
        return this.balance;
    }

    public String getAccNumber() {
        return this.accNumber;
    }

    public String getAccPasswd() {
        return this.accPasswd;
    }

    public void setAccPasswd(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        this.accPasswd = password;
    }
}
