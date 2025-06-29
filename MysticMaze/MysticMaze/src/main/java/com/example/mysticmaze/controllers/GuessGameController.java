package com.example.mysticmaze.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

public class GuessGameController {

    @FXML private Label sequenceLabel;
    @FXML private TextField answerField;
    @FXML private Button submitButton;
    @FXML private Button hintButton;
    @FXML private Label timerLabel;
    @FXML private Label progressPlayer1;
    @FXML private Label progressPlayer2;
    @FXML private Label bestSolveLabel;

    private int correctAnswer = 64;
    private int secondsElapsed = 0;
    private int bestTime = Integer.MAX_VALUE;
    private int player1Progress = 0;
    private int player2Progress = 0;
    private int currentPlayer = 1;
    private Timeline timer;

    @FXML
    public void initialize() {
        startTimer();

        submitButton.setOnAction(e -> checkAnswer());
        hintButton.setOnAction(e -> sequenceLabel.setText("Hint: It’s a cube number."));
    }

    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            secondsElapsed++;
            timerLabel.setText("Time: " + secondsElapsed + "s");
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void checkAnswer() {
        try {
            int input = Integer.parseInt(answerField.getText().trim());
            if (input == correctAnswer) {
                timer.stop();
                sequenceLabel.setText("Correct! 1, 8, 27, 64");

                if (secondsElapsed < bestTime) {
                    bestTime = secondsElapsed;
                    bestSolveLabel.setText("Best Move: " + bestTime + "s");
                }

                if (currentPlayer == 1) {
                    player1Progress++;
                    progressPlayer1.setText("Player 1 Progress: " + player1Progress);
                } else {
                    player2Progress++;
                    progressPlayer2.setText("Player 2 Progress: " + player2Progress);
                }

                submitButton.setDisable(true);
            } else {
                sequenceLabel.setText("Wrong! Try again.");
            }
        } catch (NumberFormatException e) {
            sequenceLabel.setText("Enter a valid number.");
        }
    }
}
