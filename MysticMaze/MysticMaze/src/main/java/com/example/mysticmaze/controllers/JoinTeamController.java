package com.example.mysticmaze.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class JoinTeamController {

    @FXML
    private TextField teammateInput;

    @FXML
    private Label statusLabel;

    @FXML
    private Button requestButton;

    @FXML
    private void checkStatus(ActionEvent event) {
        String input = teammateInput.getText().trim();
        if (input.isEmpty()) {
            statusLabel.setText("Please enter a username or ID.");
            return;
        }

        // Simulate status check (you can connect to real backend later)
        if (input.equalsIgnoreCase("Mystic1058")) {
            statusLabel.setText("✅ Mystic1058 is online and ready to team up!");
        } else {
            statusLabel.setText("❌ " + input + " is currently offline.");
        }
    }

    @FXML
    private void sendRequest(ActionEvent event) {
        String input = teammateInput.getText().trim();
        if (input.isEmpty()) {
            statusLabel.setText("Enter a teammate's name first.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Team Request Sent");
        alert.setHeaderText(null);
        alert.setContentText("A magical request has been sent to " + input + "!");
        alert.show();
    }
}
