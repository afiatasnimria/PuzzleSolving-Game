package com.example.mysticmaze.controllers;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LeaderBoardController {

    @FXML
    private ImageView trophyImage;

    @FXML
    public void initialize() {
        startTrophyZoomAndBlinkLoop();
    }

    private void startTrophyZoomAndBlinkLoop() {
        // Zoom In
        ScaleTransition zoomIn = new ScaleTransition(Duration.seconds(3), trophyImage);
        zoomIn.setToX(1.5);
        zoomIn.setToY(1.5);

        // Fade Out
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), trophyImage);
        fadeOut.setToValue(0.3);

        // Zoom Out
        ScaleTransition zoomOut = new ScaleTransition(Duration.seconds(3), trophyImage);
        zoomOut.setToX(1.0);
        zoomOut.setToY(1.0);

        // Fade In
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), trophyImage);
        fadeIn.setToValue(1.0);

        // Combine zoom + fade
        ParallelTransition zoomAndFadeOut = new ParallelTransition(zoomIn, fadeOut);
        ParallelTransition zoomAndFadeIn = new ParallelTransition(zoomOut, fadeIn);

        // Loop forever
        SequentialTransition blinkZoomLoop = new SequentialTransition(zoomAndFadeOut, zoomAndFadeIn);
        blinkZoomLoop.setCycleCount(Animation.INDEFINITE);
        blinkZoomLoop.play();
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
