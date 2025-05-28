package com.example.mysticmaze.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class JoinTeamController {

    @FXML
    private Label teamNameLabel;

    public void initData(String teamName) {
        teamNameLabel.setText("You're about to join: " + teamName);
    }
}
