package com.example.mysticmaze.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateRoom {

    @FXML
    private TextField roomNameField;

    @FXML
    private Button createRoomButton;

    @FXML
    private Button startGameButton;

    @FXML
    private Label statusLabel;

    // Called when "Create Room" button is clicked
    @FXML
    private void createRoom() {
        String roomName = roomNameField.getText().trim();

        if (roomName.isEmpty()) {
            statusLabel.setText("Please enter a room name!");
        } else {
            // Simulate room creation logic
            statusLabel.setText("Room '" + roomName + "' created successfully!");
            System.out.println("Room created: " + roomName);
        }
    }

    // Called when "Start Game" button is clicked
    @FXML
    private void startGame() {
        String roomName = roomNameField.getText().trim();

        if (roomName.isEmpty()) {
            statusLabel.setText("You need to create a room first!");
        } else {
            // Simulate starting the game logic
            statusLabel.setText("Game started for room: " + roomName);
            System.out.println("Game started for room: " + roomName);
        }
    }
}
