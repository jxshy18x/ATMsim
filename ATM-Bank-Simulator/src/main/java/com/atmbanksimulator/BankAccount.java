//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atmbanksimulator;

public class BankAccount {
    private String accNumber = "";
    private String accPasswd = "";
    private int balance = 0;

    public BankAccount() {
        super();
    }

    public BankAccount(String var1, String var2, int var3) {
        super();
        this.accNumber = var1;
        this.accPasswd = var2;
        this.balance = var3;
    }

    public boolean withdraw(int var1) {
        if (var1 >= 0 && this.balance >= var1) {
            this.balance -= var1;
            return true;
        } else {
            return false;
        }
    }

    public boolean deposit(int var1) {
        if (var1 < 0) {
            return false;
        } else {
            this.balance += var1;
            return true;
        }
    }

    public int getBalance() {
        return this.balance;
    }

    public String getAccNumber() {
        return this.accNumber;
    }

    public String getaccPasswd() {
        return this.accPasswd;
    }

    public void setAccPasswd(String var1) {
        this.accPasswd = var1;
    }
}
