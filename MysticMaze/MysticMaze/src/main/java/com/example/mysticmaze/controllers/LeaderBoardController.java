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


    private String previousPageFXML = "/com/example/mysticmaze/fxmls/HomePage.fxml"; // ← set the previous page here

    @FXML
    private void goToNext(ActionEvent event) throws IOException {
        // Set current page as previous before navigating
        previousPageFXML = "/com/example/mysticmaze/fxmls/ThisPage.fxml"; // ← set current FXML file name

        Parent nextRoot = FXMLLoader.load(getClass().getResource("/com/example/mysticmaze/fxmls/NextPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(nextRoot));
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent backRoot = FXMLLoader.load(getClass().getResource(previousPageFXML));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(backRoot));
    }
}
