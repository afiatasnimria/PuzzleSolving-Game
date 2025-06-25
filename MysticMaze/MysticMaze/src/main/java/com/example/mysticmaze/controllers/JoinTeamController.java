package com.example.mysticmaze.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinTeamController {

    @FXML
    private TextField teammateInput;

    @FXML
    private Label statusLabel;

    @FXML
    private Button requestButton;

    @FXML
    private void Join(ActionEvent event) throws IOException, IOException {
        String input = teammateInput.getText().trim();
        if (input.isEmpty()) {
            statusLabel.setText("Please enter Team Code");
            return;
        }

        if (input.equalsIgnoreCase("Mystic1058")) {
            statusLabel.setText("✅ Mystic1058 is online and ready to team up!");

            // Load JoinDashboard.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/mysticmaze/fxmls/JoinDashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setTitle("Team Dashboard");
            stage.setScene(new Scene(root));
            stage.show();

        } else {
            statusLabel.setText("❌ " + input + " is currently offline.");
        }
    }


}
