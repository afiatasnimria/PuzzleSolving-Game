package com.example.mysticmaze.controllers;

import com.example.mysticmaze.utils.DBUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class LoginPageController {

    @FXML public TextField usernameField;
    @FXML public PasswordField passwordField;

    // Custom navigation variables
    private String previousPageFXML;
    private String targetPageFXML;

    // Called from other controllers before loading LoginPage
    public void setNavigationContext(String previousPageFXML, String targetPageFXML) {
        this.previousPageFXML = previousPageFXML;
        this.targetPageFXML = targetPageFXML;
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Username and password cannot be empty.");
            return;
        }

        if (DBUtil.validateUser(username, password)) {
            try {
                // Determine next page
                String nextPage = (targetPageFXML != null) ? targetPageFXML : "/com/example/mysticmaze/fxmls/DashboardPage.fxml";

                FXMLLoader loader = new FXMLLoader(getClass().getResource(nextPage));
                Parent nextRoot = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(nextRoot));
                stage.setTitle("Mystic Maze");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load next page.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            String backPage = (previousPageFXML != null) ? previousPageFXML : "/com/example/mysticmaze/fxmls/HomePage.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(backPage));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not go back.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
