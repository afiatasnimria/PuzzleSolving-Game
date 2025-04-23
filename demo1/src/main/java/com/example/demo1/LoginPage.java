package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class LoginPage {

    @FXML public TextField usernameField;
    @FXML public PasswordField passwordField;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if ("123".equals(username) && "123".equals(password)) {
            try {
                Parent dashboard = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                Scene dashboardScene = new Scene(dashboard);
                Stage window = (Stage) ((Button) event.getSource()).getScene().getWindow();
                window.setScene(dashboardScene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Invalid Credentials");
            alert.setContentText("Please enter correct username and password.");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Scene loginScene = new Scene(loginRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
