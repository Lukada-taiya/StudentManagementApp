package com.lutadam.studentmanagementapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MainController {

    @FXML
    private AnchorPane apMainForm;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private TextField tfUsername;

    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    public void btnLoginAction() {
        Alert alert;
        String name = tfUsername.getText().trim();
        String password = tfPassword.getText().trim();

        if(name.isEmpty() || password.isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("All blank fields must be filled");
            alert.show();
        }else {
            //Start db test
        }
    }

}