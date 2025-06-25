package com.example.mysticmaze.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinDashboard {

    @FXML private Label player1Moves;
    @FXML private Label player1Time;
    @FXML private Label player1Status;

    @FXML private Label player2Moves;
    @FXML private Label player2Time;
    @FXML private Label player2Status;

    @FXML private Label player3Moves;
    @FXML private Label player3Time;
    @FXML private Label player3Status;

    @FXML private VBox chatBox;

    @FXML private Button startGameButton;

    @FXML
    public void initialize() {
        // Sample initial player statuses
        updatePlayerStatus(1, 12, "00:45", "Ready");
        updatePlayerStatus(2, 8, "00:38", "Waiting");
        updatePlayerStatus(3, 15, "01:02", "Offline");

        startGameButton.setOnAction(e -> {
            try {
                handleStartGame(e);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    private void updatePlayerStatus(int playerNum, int moves, String time, String status) {
        Label movesLabel = switch (playerNum) {
            case 1 -> player1Moves;
            case 2 -> player2Moves;
            case 3 -> player3Moves;
            default -> null;
        };

        Label timeLabel = switch (playerNum) {
            case 1 -> player1Time;
            case 2 -> player2Time;
            case 3 -> player3Time;
            default -> null;
        };

        Label statusLabel = switch (playerNum) {
            case 1 -> player1Status;
            case 2 -> player2Status;
            case 3 -> player3Status;
            default -> null;
        };

        if (movesLabel != null) movesLabel.setText("Moves: " + moves);
        if (timeLabel != null) timeLabel.setText("Time: " + time);
        if (statusLabel != null) {
            statusLabel.setText("Status: " + status);
            switch (status.toLowerCase()) {
                case "ready" -> statusLabel.setStyle("-fx-text-fill: #00ff00;");
                case "waiting" -> statusLabel.setStyle("-fx-text-fill: #ffff00;");
                case "offline" -> statusLabel.setStyle("-fx-text-fill: #ff0000;");
                default -> statusLabel.setStyle("-fx-text-fill: white;");
            }
        }
    }

    private void handleStartGame(ActionEvent e) throws IOException {
        System.out.println("🟢 Start Game button clicked!");
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/mysticmaze/fxmls/startGame.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setTitle("Create a Team");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
