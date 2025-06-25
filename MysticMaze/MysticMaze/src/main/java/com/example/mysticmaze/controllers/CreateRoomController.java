package com.example.mysticmaze.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class CreateRoomController {

    @FXML
    private TextField roomNameField;

    @FXML
    private Label statusLabel;

    // Called when "Create Room" button is clicked
    @FXML
    public void createRoom(javafx.event.ActionEvent actionEvent) throws IOException {
        String roomName = roomNameField.getText().trim();

        if (roomName.isEmpty()) {
            statusLabel.setText("Please enter a room name!");
        } else {
            // Simulate room creation logic
            statusLabel.setText("Room '" + roomName + "' created successfully!");
            System.out.println("Room created: " + roomName);

            // Load RoomDashboard.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/mysticmaze/fxmls/JoinDashboard.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setTitle("Room Dashboard");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

}

