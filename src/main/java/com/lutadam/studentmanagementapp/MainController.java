package com.lutadam.studentmanagementapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private double x;
    private double y;

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
            String hashed = Encryptor.encrypt(password);
            String queryString = "SELECT * FROM admin WHERE name = ? AND password = ?";
            ResultSet result = DBUtils.fetchDb(queryString, name, hashed);
            try {
                if(result.next()) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Successful");
                    alert.showAndWait();
                    //Show Dashboard
                    Parent root = null;
                    Stage stage = new Stage();
                    try {
                        root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    }catch (IOException e) { e.printStackTrace();}
                    Scene scene = new Scene(root, 1100,600);
                    root.setOnMousePressed((MouseEvent e) -> {
                        x = e.getSceneX();
                        y = e.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent e) -> {
                        stage.setX(e.getScreenX() - x);
                        stage.setY(e.getScreenY() - y);
                        stage.setOpacity(.5);
                    });

                    root.setOnMouseReleased((MouseEvent e) -> {
                        stage.setOpacity(1);
                    });
//                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
//                    stage.centerOnScreen();
                    apMainForm.getScene().getWindow().hide();
                    stage.show();
                }else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a valid username and password");
                    alert.show();
                }

            }catch (SQLException e) { e.printStackTrace();}
            DBUtils.closeAllResources();
        }
    }

}