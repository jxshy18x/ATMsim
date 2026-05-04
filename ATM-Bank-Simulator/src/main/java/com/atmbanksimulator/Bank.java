//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atmbanksimulator;

public class Bank {
    private int maxAccounts = 10;
    private int numAccounts = 0;
    private BankAccount[] accounts;
    private BankAccount loggedInAccount;
    // public static final String ACCOUNT_TYPE_STANDARD = "Standard";
    //public static final String ACCOUNT_TYPE_STUDENT = "Student";
    //public static final String ACCOUNT_TYPE_PRIME = "Prime";
   // public static final String ACCOUNT_TYPE_SAVING = "Saving";

    public Bank() {
        super();
        this.accounts = new BankAccount[this.maxAccounts];
        this.loggedInAccount = null;
    }

    public BankAccount makeBankAccount(String var1, String var2, int var3) {
        return new BankAccount(var1, var2, var3);
    }

    public StudentAccount makeStudentAccount(String var1, String var2, int var3) {
        return new StudentAccount(var1, var2, var3);
    }

    public PrimeAccount makePrimeAccount(String var1, String var2, int var3) {
        return new PrimeAccount(var1, var2, var3);
    }

    public SavingAccount makeSavingAccount(String var1, String var2, int var3) {
        return new SavingAccount(var1, var2, var3);
    }

    public boolean addBankAccount(BankAccount var1) {
        if (var1 != null && this.numAccounts < this.maxAccounts && this.isValidAccountNumber(var1.getAccNumber()) && this.isValidPassword(var1.getaccPasswd()) && this.findBankAccount(var1.getAccNumber()) == null) {
            this.accounts[this.numAccounts] = var1;
            ++this.numAccounts;
            return true;
        } else {
            return false;
        }
    }

    public boolean addBankAccount(String var1, String var2, int var3) {
        return this.addBankAccount(this.makeBankAccount(var1, var2, var3));
    }

    public boolean createNewAccount(String var1, String var2, int var3, String var4) {
        if (this.isValidAccountNumber(var1) && this.isValidPassword(var2) && var3 >= 0 && this.findBankAccount(var1) == null && this.numAccounts < this.maxAccounts) {
            boolean var10000;
            switch (var4) {
                case "Student" -> var10000 = this.addBankAccount(this.makeStudentAccount(var1, var2, var3));
                case "Prime" -> var10000 = this.addBankAccount(this.makePrimeAccount(var1, var2, var3));
                case "Saving" -> var10000 = this.addBankAccount(this.makeSavingAccount(var1, var2, var3));
                case "Standard" -> var10000 = this.addBankAccount(this.makeBankAccount(var1, var2, var3));
                default -> var10000 = false;
            }

            return var10000;
        } else {
            return false;
        }
    }

    public String validateNewAccount(String var1, String var2, String var3, String var4) {
        if (!this.isValidAccountNumber(var1)) {
            return "Account number must be exactly 5 digits.";
        } else if (this.findBankAccount(var1) != null) {
            return "An account with that number already exists.";
        } else if (!this.isValidPassword(var2)) {
            return getPasswordRulesMessage();
        } else {
            int var5 = this.parseBalance(var3);
            if (var5 < 0) {
                return "Initial balance must be a whole number of 0 or more.";
            } else if (!this.isValidAccountType(var4)) {
                return "Choose a valid account type.";
            } else {
                return this.numAccounts >= this.maxAccounts ? "The bank cannot create more accounts." : "";
            }
        }
    }

    public boolean login(String var1, String var2) {
        this.logout();

        for(BankAccount var6 : this.accounts) {
            if (var6 != null && var6.getAccNumber().equals(var1) && var6.getaccPasswd().equals(var2)) {
                this.loggedInAccount = var6;
                return true;
            }
        }

        this.loggedInAccount = null;
        return false;
    }

    public void logout() {
        if (this.loggedIn()) {
            this.loggedInAccount = null;
        }

    }

    public boolean loggedIn() {
        return this.loggedInAccount != null;
    }

    public boolean deposit(int var1) {
        return this.loggedIn() ? this.loggedInAccount.deposit(var1) : false;
    }

    public boolean withdraw(int var1) {
        return this.loggedIn() ? this.loggedInAccount.withdraw(var1) : false;
    }

    public BankAccount findBankAccount(String var1) {
        for(BankAccount var5 : this.accounts) {
            if (var5 != null && var5.getAccNumber().equals(var1)) {
                return var5;
            }
        }

        return null;
    }

    public boolean transfer(String var1, int var2) {
        BankAccount var3 = this.findBankAccount(var1);
        if (this.loggedIn() && var2 > 0) {
            if (var3 == null) {
                return false;
            } else if (this.loggedInAccount.getBalance() < var2) {
                return false;
            } else {
                this.loggedInAccount.withdraw(var2);
                var3.deposit(var2);
                return true;
            }
        } else {
            return false;
        }
    }

    public int getBalance() {
        return this.loggedIn() ? this.loggedInAccount.getBalance() : -1;
    }

    public boolean changePassword(String var1, String var2) {
        if (this.loggedIn() && this.loggedInAccount.getaccPasswd().equals(var1) && this.isValidPassword(var2) && !var1.equals(var2)) {
            this.loggedInAccount.setAccPasswd(var2);
            return true;
        } else {
            return false;
        }
    }

    public String validatePasswordChange(String var1, String var2) {
        if (!this.loggedIn()) {
            return "You must be logged in to change your password.";
        } else if (!this.loggedInAccount.getaccPasswd().equals(var1)) {
            return "Old password is incorrect.";
        } else if (!this.isValidPassword(var2)) {
            return getPasswordRulesMessage();
        } else {
            return var1.equals(var2) ? "New password must be different from the old password." : "";
        }
    }

    public int parseInitialBalance(String var1) {
        return this.parseBalance(var1);
    }

    public static String getPasswordRulesMessage() {
        return "Password must be exactly 5 digits and cannot be 00000.";
    }

    private boolean isValidAccountNumber(String var1) {
        return var1 != null && var1.matches("\\d{5}");
    }

    private boolean isValidPassword(String var1) {
        return var1 != null && var1.matches("\\d{5}") && !var1.equals("00000");
    }

    private boolean isValidAccountType(String var1) {
        return "Standard".equals(var1) || "Student".equals(var1) || "Prime".equals(var1) || "Saving".equals(var1);
    }

    private int parseBalance(String var1) {
        if (var1 != null && var1.matches("\\d+")) {
            try {
                return Integer.parseInt(var1);
            } catch (NumberFormatException var3) {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
