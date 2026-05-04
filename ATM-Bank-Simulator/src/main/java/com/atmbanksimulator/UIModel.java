//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atmbanksimulator;

import java.util.ArrayList;

public class UIModel {
    View view;
    private Bank bank;
    private final String STATE_ACCOUNT_NO = "account_no";
    private final String STATE_PASSWORD = "password";
    private final String STATE_LOGGED_IN = "logged_in";
    private String state = "account_no";
    private String accNumber = "";
    private String accPasswd = "";
    private String message;
    private String numberPadInput;
    private String result;
    private String Confirmation = "";
    private ArrayList<String> transactions = new ArrayList();

    public UIModel(Bank var1) {
        super();
        this.bank = var1;
    }

    public void initialise() {
        this.setState("account_no");
        this.numberPadInput = "";
        this.message = "Welcome to the ATM";
        this.result = "Enter your account number\nFollowed by \"Ent\"";
        this.update();
    }

    private void reset(String var1) {
        this.setState("account_no");
        this.numberPadInput = "";
        this.message = var1;
        this.result = "Enter your account number\nFollowed by \"Ent\"";
    }

    private void setState(String var1) {
        if (!this.state.equals(var1)) {
            String var2 = this.state;
            this.state = var1;
            System.out.println("UIModel::setState: changed state from " + var2 + " to " + var1);
        }

    }

    public void processNumber(String var1) {
        this.numberPadInput = this.numberPadInput + var1;
        this.message = "Beep! " + var1 + " received";
        this.update();
    }

    public void processClear() {
        this.message = "Are you sure you want to clear? Y/N";
        if (!this.numberPadInput.isEmpty()) {
            this.numberPadInput = "";
            this.message = "Input Cleared";
            this.update();
        }

    }

    public void processEnter() {
        switch (this.state) {
            case "account_no":
                if (this.numberPadInput.equals("")) {
                    SFX.playSFX("Error.wav");
                    this.message = "Invalid Account Number";
                    this.reset(this.message);
                } else {
                    this.accNumber = this.numberPadInput;
                    this.numberPadInput = "";
                    this.setState("password");
                    this.message = "Account Number Accepted";
                    this.result = "Now enter your password\nFollowed by \"Ent\"";
                }
                break;
            case "password":
                this.accPasswd = this.numberPadInput;
                this.numberPadInput = "";
                if (this.bank.login(this.accNumber, this.accPasswd)) {
                    this.setState("logged_in");
                    this.message = "Logged In";
                    this.result = "Now enter the amount\nThen press transaction\n(Dep = Deposit, W/D = Withdraw)";
                } else {
                    SFX.playSFX("Error.wav");
                    this.message = "Login failed: Unknown Account/Password";
                    this.reset(this.message);
                }
            case "logged_in":
        }

        this.update();
    }

    private int parseValidAmount(String var1) {
        if (var1.isEmpty()) {
            return 0;
        } else {
            try {
                return Integer.parseInt(var1);
            } catch (NumberFormatException var3) {
                return 0;
            }
        }
    }

    public void processBalance() {
        if (this.state.equals("logged_in")) {
            this.numberPadInput = "";
            this.message = "Balance Available";
            this.result = "Your Balance is: " + this.bank.getBalance();
        } else {
            SFX.playSFX("Error.wav");
            this.reset("You are not logged in");
        }

        this.update();
    }

    public void processWithdraw() {
        if (this.state.equals("logged_in")) {
            int var1 = this.parseValidAmount(this.numberPadInput);
            if (var1 > 0) {
                this.Confirmation = "Withdraw:" + var1;
                this.message = "Press Y to confirm or N to cancel";
            } else {
                SFX.playSFX("Error.wav");
                this.message = "Invalid Amount";
                this.result = "Now enter the amount\nThen press transaction\n(Dep = Deposit, W/D = Withdraw)";
            }

            this.numberPadInput = "";
        } else {
            SFX.playSFX("Error.wav");
            this.reset("You are not logged in");
        }

        this.update();
    }

    public void processDeposit() {
        if (this.state.equals("logged_in")) {
            int var1 = this.parseValidAmount(this.numberPadInput);
            if (var1 > 0) {
                this.Confirmation = "Deposit:" + var1;
                this.message = "Press Y to confirm or N to cancel";
            } else {
                SFX.playSFX("Error.wav");
                this.message = "Invaild Amount";
                this.result = "Now enter the amount\nThen press transaction\n(Dep = Deposit, W/D = Withdraw)";
            }

            this.numberPadInput = "";
            this.update();
        } else {
            SFX.playSFX("Error.wav");
            this.reset("You are not logged in");
        }

        this.update();
    }

    public void processConfirm() {
        if (this.Confirmation.isEmpty()) {
            this.message = "No action to confirm";
            this.update();
        } else {
            if (this.Confirmation.startsWith("Deposit:")) {
                int var1 = Integer.parseInt(this.Confirmation.split(":")[1]);
                this.bank.deposit(var1);
                this.message = "Deposit successful";
            } else if (this.Confirmation.startsWith("Withdraw:")) {
                int var2 = Integer.parseInt(this.Confirmation.split(":")[1]);
                if (this.bank.withdraw(var2)) {
                    SFX.playSFX("Withdrawl.wav");
                    this.message = "Withdraw successful";
                } else {
                    this.message = "Withdraw failed";
                }
            }

            this.Confirmation = "";
            this.update();
        }
    }

    public void processCancel() {
        this.Confirmation = "";
        this.message = "Cancelled";
        this.update();
    }

    public void processFinish() {
        if (this.state.equals("logged_in")) {
            this.reset("Thank you for using the Bank ATM");
            this.bank.logout();
        } else {
            SFX.playSFX("Error.wav");
            this.reset("You are not logged in");
        }

        this.update();
    }

    public void processUnknownKey(String var1) {
        SFX.playSFX("Error.wav");
        this.reset("Invalid Command");
        this.update();
    }

    public boolean isLoggedIn() {
        return this.state.equals("logged_in");
    }

    public boolean accountExists(String var1) {
        return this.bank.findBankAccount(var1) != null;
    }

    public int getLoggedInBalance() {
        return this.bank.getBalance();
    }

    public boolean processTransfer(String var1, int var2) {
        return var2 <= 0 ? false : this.bank.transfer(var1, var2);
    }

    public String changePassword(String var1, String var2) {
        String var3 = this.bank.validatePasswordChange(var1, var2);
        if (!var3.isEmpty()) {
            return var3;
        } else {
            return this.bank.changePassword(var1, var2) ? "Password changed successfully." : "Password could not be changed.";
        }
    }

    public String createNewAccount(String var1, String var2, String var3, String var4) {
        String var5 = this.bank.validateNewAccount(var1, var2, var3, var4);
        if (!var5.isEmpty()) {
            return var5;
        } else {
            int var6 = this.bank.parseInitialBalance(var3);
            return this.bank.createNewAccount(var1, var2, var6, var4) ? var4 + " account " + var1 + " created with balance " + var6 + "." : "Account could not be created.";
        }
    }

    private void update() {
        this.view.update(this.message, this.numberPadInput, this.result);
    }
}
