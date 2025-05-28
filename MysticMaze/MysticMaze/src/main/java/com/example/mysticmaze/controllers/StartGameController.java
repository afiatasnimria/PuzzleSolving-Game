package com.example.mysticmaze.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StartGameController {

    @FXML
    private Label playerNameLabel;

    public void initData(String playerName) {
        playerNameLabel.setText("Welcome, " + playerName + "! Ready to enter the maze?");
    }
}
