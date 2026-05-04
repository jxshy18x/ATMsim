//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atmbanksimulator;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class View {
    private Scene atmScene;
    private Scene transferScene;
    private Scene goodbyeScene;
    private Scene changePasswordScene;
    private Scene createAccountScene;
    private Stage window;
    int H = 500;
    int W = 500;
    Controller controller;
    private Label laMsg;
    private TextField tfInput;
    private TextArea taResult;
    private ScrollPane scrollPane;
    private GridPane grid;
    private TilePane buttonPane;
    private TextField transferInput;
    private TextArea transferResult;
    private String transferState = "account";
    private String recipientAccount = "";

    View() {
        super();
    }

    public void start(Stage var1) {
        this.window = var1;
        VBox var2 = new VBox((double)20.0F);
        var2.setStyle("-fx-alignment: center;");
        Label var3 = new Label("Welcome to the ATM!");
        var3.setStyle("-fx-font-size: 24px;");
        Scene var4 = new Scene(var2, (double)this.W, (double)this.H);
        Button var5 = new Button("Enter");
        var2.getChildren().addAll(new Node[]{var3, var5});
        Label var6 = new Label("Thank you for using our ATM!");
        var6.setStyle("-fx-font-size: 24px");
        Button var7 = new Button("Exit");
        var7.setOnAction((var0) -> System.exit(0));
        VBox var8 = new VBox((double)20.0F, new Node[]{var6, var7});
        var8.setStyle("-fx-alignment: center");
        this.goodbyeScene = new Scene(var8, (double)this.W, (double)this.H);
        this.createChangePasswordScene();
        this.createAccountCreationScene();
        GridPane var9 = new GridPane();
        var9.setId("Layout");
        Button var10 = new Button("Transfer");
        Button var11 = new Button("Change Password");
        Button var12 = new Button("New Account");
        Label var13 = new Label("Transfer Money");
        Button var14 = new Button("Back");
        var14.setOnAction((var2x) -> var1.setScene(this.atmScene));
        HBox var15 = new HBox((double)10.0F, new Node[]{var13, var14});
        var9.add(var15, 0, 0);
        this.transferInput = new TextField();
        this.transferInput.setEditable(false);
        var9.add(this.transferInput, 0, 1);
        this.transferResult = new TextArea();
        this.transferResult.setEditable(false);
        ScrollPane var16 = new ScrollPane(this.transferResult);
        var9.add(var16, 0, 2);
        var10.setOnAction((var2x) -> {
            if (this.controller.UIModel.isLoggedIn()) {
                var1.setScene(this.transferScene);
                this.transferResult.setText("Enter recipient's account number");
            } else {
                this.laMsg.setText("You have to be logged in first!");
            }

        });
        var11.setOnAction((var2x) -> {
            if (this.controller.UIModel.isLoggedIn()) {
                var1.setScene(this.changePasswordScene);
            } else {
                this.laMsg.setText("You have to be logged in first!");
            }

        });
        var12.setOnAction((var2x) -> var1.setScene(this.createAccountScene));
        TilePane var17 = new TilePane();
        var17.setId("Buttons");
        String[][] var18 = new String[][]{{"7", "8", "9", "", "Dep", ""}, {"4", "5", "6", "", "W/D", ""}, {"1", "2", "3", "", "Bal", "Fin"}, {"CLR", "0", "", "", "", "Ent"}};

        for(String[] var22 : var18) {
            for(String var26 : var22) {
                if (var26.length() >= 1) {
                    Button var27 = new Button(var26);
                    var27.setOnAction(this::buttonClicked);
                    var17.getChildren().add(var27);
                } else {
                    var17.getChildren().add(new Text());
                }
            }
        }

        var9.add(var17, 0, 3);
        this.transferScene = new Scene(var9, (double)this.W, (double)this.H);
        this.transferScene.getStylesheets().add("atm.css");
        this.transferScene.getRoot().setFocusTraversable(true);
        this.transferScene.getRoot().setOnKeyPressed((var2x) -> {
            switch (var2x.getCode()) {
                case Y -> this.controller.process("Y");
                case N -> this.controller.process("N");
            }

            var1.setScene(this.transferScene);
            this.transferScene.getRoot().requestFocus();
        });
        Button var31 = new Button("\ud83d\udd0a");
        var31.setOnAction((var1x) -> {
            SFX.muted = !SFX.muted;
            if (SFX.muted) {
                ATMNoise.stop();
                var31.setText("\ud83d\udd07");
            } else {
                ATMNoise.start("Global.wav");
                var31.setText("\ud83d\udd0a");
            }

        });
        this.grid = new GridPane();
        this.grid.setId("Layout");
        this.buttonPane = new TilePane();
        this.buttonPane.setId("Buttons");
        this.laMsg = new Label("Welcome to Bank-ATM");
        FlowPane var32 = new FlowPane((double)10.0F, (double)6.0F, new Node[]{this.laMsg, var31, var10, var11, var12});
        this.grid.add(var32, 0, 0);
        this.tfInput = new TextField();
        this.tfInput.setEditable(false);
        this.grid.add(this.tfInput, 0, 1);
        this.taResult = new TextArea();
        this.taResult.setEditable(false);
        this.scrollPane = new ScrollPane();
        this.scrollPane.setContent(this.taResult);
        this.grid.add(this.scrollPane, 0, 2);
        String[][] var10000 = new String[][]{{"7", "8", "9", "", "Dep", ""}, {"4", "5", "6", "", "W/D", ""}, {"1", "2", "3", "", "Bal", "Fin"}, {"CLR", "0", "", "", "", "Ent"}};

        for(String[] var36 : var18) {
            for(String var29 : var36) {
                if (var29.length() >= 1) {
                    Button var30 = new Button(var29);
                    var30.setOnAction(this::buttonClicked);
                    this.buttonPane.getChildren().add(var30);
                } else {
                    this.buttonPane.getChildren().add(new Text());
                }
            }
        }

        this.grid.add(this.buttonPane, 0, 3);
        this.atmScene = new Scene(this.grid, (double)this.W, (double)this.H);
        this.atmScene.getStylesheets().add("atm.css");
        var5.setOnAction((var2x) -> var1.setScene(this.atmScene));
        var1.setScene(var4);
        var1.setTitle("Welcome");
        var1.show();
    }

    private void createChangePasswordScene() {
        GridPane var1 = this.createFormGrid();
        Label var2 = new Label("Change Password");
        Button var3 = new Button("Back");
        var3.setOnAction((var1x) -> this.window.setScene(this.atmScene));
        var1.add(new HBox((double)10.0F, new Node[]{var2, var3}), 0, 0, 2, 1);
        PasswordField var4 = new PasswordField();
        PasswordField var5 = new PasswordField();
        TextArea var6 = new TextArea();
        var6.setEditable(false);
        var6.setPrefRowCount(4);
        var1.add(new Label("Old password"), 0, 1);
        var1.add(var4, 1, 1);
        var1.add(new Label("New password"), 0, 2);
        var1.add(var5, 1, 2);
        Button var7 = new Button("Change");
        var7.setOnAction((var4x) -> {
            String var5x = this.controller.UIModel.changePassword(var4.getText(), var5.getText());
            var6.setText(var5x + "\n" + Bank.getPasswordRulesMessage());
            var4.clear();
            var5.clear();
        });
        var1.add(var7, 1, 3);
        var1.add(var6, 0, 4, 2, 1);
        this.changePasswordScene = new Scene(var1, (double)this.W, (double)this.H);
        this.changePasswordScene.getStylesheets().add("atm.css");
    }

    private void createAccountCreationScene() {
        GridPane var1 = this.createFormGrid();
        Label var2 = new Label("Create New Account");
        Button var3 = new Button("Back");
        var3.setOnAction((var1x) -> this.window.setScene(this.atmScene));
        var1.add(new HBox((double)10.0F, new Node[]{var2, var3}), 0, 0, 2, 1);
        TextField var4 = new TextField();
        PasswordField var5 = new PasswordField();
        TextField var6 = new TextField("0");
        ComboBox var7 = new ComboBox();
        var7.getItems().addAll(new String[]{"Standard", "Student", "Prime", "Saving"});
        var7.setValue("Standard");
        TextArea var8 = new TextArea();
        var8.setEditable(false);
        var8.setPrefRowCount(5);
        var1.add(new Label("Account number"), 0, 1);
        var1.add(var4, 1, 1);
        var1.add(new Label("Password"), 0, 2);
        var1.add(var5, 1, 2);
        var1.add(new Label("Initial balance"), 0, 3);
        var1.add(var6, 1, 3);
        var1.add(new Label("Account type"), 0, 4);
        var1.add(var7, 1, 4);
        Button var9 = new Button("Create");
        var9.setOnAction((var6x) -> {
            String var7x = this.controller.UIModel.createNewAccount(var4.getText(), var5.getText(), var6.getText(), (String)var7.getValue());
            var8.setText(var7x + "\nAccount number must be exactly 5 digits.\n" + Bank.getPasswordRulesMessage());
            if (var7x.startsWith((String)var7.getValue() + " account ")) {
                var4.clear();
                var5.clear();
                var6.setText("0");
                var7.setValue("Standard");
            }

        });
        var1.add(var9, 1, 5);
        var1.add(var8, 0, 6, 2, 1);
        this.createAccountScene = new Scene(var1, (double)this.W, (double)this.H);
        this.createAccountScene.getStylesheets().add("atm.css");
    }

    private GridPane createFormGrid() {
        GridPane var1 = new GridPane();
        var1.setId("Layout");
        var1.setHgap((double)10.0F);
        var1.setVgap((double)10.0F);
        var1.setPadding(new Insets((double)15.0F));
        return var1;
    }

    private void buttonClicked(ActionEvent var1) {
        Button var2 = (Button)var1.getSource();
        String var3 = var2.getText();
        System.out.println("View::buttonClicked: label = " + var3);
        if (var3.equals("Fin")) {
            this.window.setScene(this.goodbyeScene);
        } else if (this.window.getScene() == this.transferScene) {
            if (var3.equals("Ent")) {
                String var4 = this.transferInput.getText();
                if (this.transferState.equals("account")) {
                    if (this.controller.UIModel.accountExists(var4)) {
                        this.recipientAccount = var4;
                        this.transferInput.setText("");
                        int var5 = this.controller.UIModel.getLoggedInBalance();
                        this.transferResult.setText("Account found!\nYour balance is £" + var5 + "\nEnter amount to transfer:");
                        this.transferState = "amount";
                    } else {
                        this.transferInput.setText("");
                        this.transferResult.setText("Account not found, try again!");
                    }
                } else if (this.transferState.equals("amount")) {
                    int var6 = Integer.parseInt(var4);
                    if (this.controller.UIModel.processTransfer(this.recipientAccount, var6)) {
                        this.transferResult.setText("Transfer successful!\n£" + var6 + " sent to " + this.recipientAccount);
                    } else {
                        this.transferResult.setText("Transfer failed! Insufficient funds.");
                    }

                    this.transferInput.setText("");
                    this.transferState = "account";
                }
            } else {
                this.controller.process(var3);
            }

        } else {
            this.controller.process(var3);
        }
    }

    public void update(String var1, String var2, String var3) {
        this.laMsg.setText(var1);
        this.tfInput.setText(var2);
        this.taResult.setText(var3);
        this.transferInput.setText(var2);
    }
}
