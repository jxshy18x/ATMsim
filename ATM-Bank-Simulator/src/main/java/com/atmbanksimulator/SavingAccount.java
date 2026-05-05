//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atmbanksimulator;

public class SavingAccount extends BankAccount {
    public SavingAccount(String var1, String var2, int var3) {
        super(var1, var2, var3);
    }

    @Override
    public boolean withdraw(int var1) {
        return var1 > 300 ? false : super.withdraw(var1);
    }
}
