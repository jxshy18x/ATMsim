//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atmbanksimulator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bank {
    private static final Path DATA_DIR = Path.of("data");
    private static final Path ACCOUNTS_FILE = DATA_DIR.resolve("accounts.txt");
    private static final Path TRANSACTIONS_FILE = DATA_DIR.resolve("transactions.txt");
    private static final DateTimeFormatter TRANSACTION_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final int maxAccounts = 10;
    private int numAccounts = 0;
    private final BankAccount[] accounts;
    private BankAccount loggedInAccount;
    private final ArrayList<String> transactions = new ArrayList<>();
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
        if (var1 != null && this.numAccounts < this.maxAccounts && this.isValidAccountNumber(var1.getAccNumber()) && this.isValidPassword(var1.getAccPasswd()) && this.findBankAccount(var1.getAccNumber()) == null) {
            this.accounts[this.numAccounts] = var1;
            ++this.numAccounts;
            this.saveAccounts();
            return true;
        } else {
            return false;
        }
    }

    // public boolean addBankAccount(String var1, String var2, int var3) {
       // return this.addBankAccount(this.makeBankAccount(var1, var2, var3));
    //}

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

            if (var10000) {
                this.recordTransaction(var1, "Account created with balance " + var3);
            }

            return var10000;
        } else {
            return false;
        }
    }

    public String validateNewAccount(String var1, String var2, String var3, String var4) {
        if (!this.isValidAccountNumber(var1)) {
            return "Account number must be between 10000 and 10099.";
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
            if (var6 != null && var6.getAccNumber().equals(var1) && var6.getAccPasswd().equals(var2)) {
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
        if (!this.loggedIn()) {
            return false;
        } else if (this.loggedInAccount.deposit(var1)) {
            this.recordTransaction(this.loggedInAccount.getAccNumber(), "Deposit +" + var1 + " balance " + this.loggedInAccount.getBalance());
            this.saveAccounts();
            return true;
        } else {
            return false;
        }
    }

    public boolean withdraw(int var1) {
        if (!this.loggedIn()) {
            return false;
        } else if (this.loggedInAccount.withdraw(var1)) {
            this.recordTransaction(this.loggedInAccount.getAccNumber(), "Withdraw -" + var1 + " balance " + this.loggedInAccount.getBalance());
            this.saveAccounts();
            return true;
        } else {
            return false;
        }
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
                this.recordTransaction(this.loggedInAccount.getAccNumber(), "Transfer -" + var2 + " to " + var1 + " balance " + this.loggedInAccount.getBalance());
                this.recordTransaction(var1, "Transfer +" + var2 + " from " + this.loggedInAccount.getAccNumber() + " balance " + var3.getBalance());
                this.saveAccounts();
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
        if (this.loggedIn() && this.loggedInAccount.getAccPasswd().equals(var1) && this.isValidPassword(var2) && !var1.equals(var2)) {
            this.loggedInAccount.setAccPasswd(var2);
            this.saveAccounts();
            return true;
        } else {
            return false;
        }
    }

    public String validatePasswordChange(String var1, String var2) {
        if (!this.loggedIn()) {
            return "You must be logged in to change your password.";
        } else if (!this.loggedInAccount.getAccPasswd().equals(var1)) {
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

    public boolean hasAccounts() {
        return this.numAccounts > 0;
    }

    public String getLowBalanceWarning() {
        return this.loggedIn() && this.loggedInAccount.getBalance() < 20 ? "\nWarning: low balance." : "";
    }

    public String getMiniStatement() {
        if (!this.loggedIn()) {
            return "You are not logged in";
        }

        String accountNumber = this.loggedInAccount.getAccNumber();
        ArrayList<String> accountTransactions = new ArrayList<>();
        for (String transaction : this.transactions) {
            if (transaction.contains(" | " + accountNumber + " | ")) {
                accountTransactions.add(transaction);
            }
        }

        if (accountTransactions.isEmpty()) {
            return "No recent transactions.";
        }

        Collections.reverse(accountTransactions);
        int start = Math.max(0, accountTransactions.size() - 5);
        StringBuilder statement = new StringBuilder("Mini Statement\n");
        for (int i = start; i < accountTransactions.size(); ++i) {
            statement.append(accountTransactions.get(i)).append("\n");
        }

        return statement.toString().trim();
    }

    public String getAccountOptions() {
        StringBuilder options = new StringBuilder("Available accounts:\n");
        boolean hasAccount = false;
        for (BankAccount account : this.accounts) {
            if (account != null) {
                options.append(account.getAccNumber()).append(" - ").append(this.getAccountType(account)).append("\n");
                hasAccount = true;
            }
        }

        if (!hasAccount) {
            return "No accounts available.";
        }

        return options.toString().trim();
    }

    public void loadData() {
        this.loadAccounts();
        this.loadTransactions();
    }

    private void loadAccounts() {
        if (!Files.exists(ACCOUNTS_FILE)) {
            return;
        }

        try {
            List<String> lines = Files.readAllLines(ACCOUNTS_FILE);
            for (int i = 0; i < this.accounts.length; ++i) {
                this.accounts[i] = null;
            }
            this.numAccounts = 0;

            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    int balance = Integer.parseInt(parts[3]);
                    BankAccount account = this.makeAccountByType(parts[0], parts[1], parts[2], balance);
                    this.addLoadedAccount(account);
                }
            }
        } catch (IOException | NumberFormatException ignored) {
        }
    }

    private void loadTransactions() {
        if (!Files.exists(TRANSACTIONS_FILE)) {
            return;
        }

        try {
            this.transactions.clear();
            this.transactions.addAll(Files.readAllLines(TRANSACTIONS_FILE));
        } catch (IOException ignored) {
        }
    }

    private BankAccount makeAccountByType(String type, String accountNumber, String password, int balance) {
        return switch (type) {
            case "Student" -> this.makeStudentAccount(accountNumber, password, balance);
            case "Prime" -> this.makePrimeAccount(accountNumber, password, balance);
            case "Saving" -> this.makeSavingAccount(accountNumber, password, balance);
            default -> this.makeBankAccount(accountNumber, password, balance);
        };
    }

    private void addLoadedAccount(BankAccount account) {
        if (account != null && this.numAccounts < this.maxAccounts && this.findBankAccount(account.getAccNumber()) == null) {
            this.accounts[this.numAccounts] = account;
            ++this.numAccounts;
        }
    }

    private void saveAccounts() {
        ArrayList<String> lines = new ArrayList<>();
        for (BankAccount account : this.accounts) {
            if (account != null) {
                lines.add(this.getAccountType(account) + "|" + account.getAccNumber() + "|" + account.getAccPasswd() + "|" + account.getBalance());
            }
        }

        try {
            Files.createDirectories(DATA_DIR);
            Files.write(ACCOUNTS_FILE, lines);
        } catch (IOException ignored) {
        }
    }

    private String getAccountType(BankAccount account) {
        if (account instanceof StudentAccount) {
            return "Student";
        } else if (account instanceof PrimeAccount) {
            return "Prime";
        } else if (account instanceof SavingAccount) {
            return "Saving";
        } else {
            return "Standard";
        }
    }

    private void recordTransaction(String accountNumber, String description) {
        String transaction = LocalDateTime.now().format(TRANSACTION_TIME_FORMAT) + " | " + accountNumber + " | " + description;
        this.transactions.add(transaction);

        try {
            Files.createDirectories(DATA_DIR);
            Files.writeString(TRANSACTIONS_FILE, transaction + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ignored) {
        }
    }

    public static String getPasswordRulesMessage() {
        return "Password must be exactly 5 digits and cannot be 00000.";
    }

    private boolean isValidAccountNumber(String var1) {
        return var1 != null && var1.matches("100\\d{2}");
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
