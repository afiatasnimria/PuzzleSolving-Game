package com.example.mysticmaze.controllers;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LevelController {

    @FXML private Button level1Btn, level2Btn, level3Btn, level4Btn;
    @FXML private Button level5Btn, level6Btn, level7Btn, level8Btn;
    @FXML private Button backBtn;

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        try {
          //  System.out.println("cliced");
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/com/example/mysticmaze/fxmls/HomePage.fxml"));
            Scene loginScene = new Scene(loginRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // 🌌 Apply mystic styles
        mysticStyle(level1Btn, Color.FORESTGREEN);   // Whispering Woods
        mysticStyle(level2Btn, Color.DARKRED);       // Cursed Cavern
        mysticStyle(level3Btn, Color.ORANGERED);     // Ember Peak
        mysticStyle(level4Btn, Color.ROYALBLUE);     // Silent Depths
        mysticStyle(level5Btn, Color.MEDIUMPURPLE);  // Twilight Path
        mysticStyle(level6Btn, Color.SKYBLUE);       // Frozen Hollow
        mysticStyle(level7Btn, Color.GOLDENROD);     // Stormspire
        mysticStyle(level8Btn, Color.SADDLEBROWN);   // Necro Keep

        styleBackButton(backBtn);

        // 🎉 Animate entrance of level buttons
        List<Button> levelButtons = Arrays.asList(
                level1Btn, level2Btn, level3Btn, level4Btn,
                level5Btn, level6Btn, level7Btn, level8Btn
        );

        animateButtons(levelButtons);
    }

    private void mysticStyle(Button btn, Color glowColor) {
        btn.setFont(Font.font("Palatino Linotype", 15));
        btn.setTextFill(Color.WHITE);

        btn.setStyle(
                "-fx-background-color: rgba(30, 30, 30, 0.75);" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.2);" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 10;"
        );

        Glow glow = new Glow(0.3);
        btn.setEffect(glow);

        DropShadow hoverShadow = new DropShadow(18, glowColor);
        hoverShadow.setOffsetX(0);
        hoverShadow.setOffsetY(0);
        hoverShadow.setSpread(0.3);
        hoverShadow.setRadius(20);

        btn.setOnMouseEntered(e -> {
            btn.setEffect(hoverShadow);
            btn.setScaleX(1.05);
            btn.setScaleY(1.05);
        });

        btn.setOnMouseExited(e -> {
            btn.setEffect(glow);
            btn.setScaleX(1.0);
            btn.setScaleY(1.0);
        });
    }

    private void styleBackButton(Button btn) {
        btn.setFont(Font.font("Georgia", 14));
        btn.setTextFill(Color.LAVENDER);
        btn.setStyle("-fx-background-color: rgba(30,30,30,0.7); -fx-background-radius: 10;");
        DropShadow shadow = new DropShadow(10, Color.MEDIUMPURPLE);
        btn.setEffect(shadow);

        btn.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            btn.setScaleX(1.1);
            btn.setScaleY(1.1);
        });
        btn.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            btn.setScaleX(1);
            btn.setScaleY(1);
        });
    }

    private void animateButtons(List<Button> buttons) {
        double delay = 0;

        for (Button btn : buttons) {
            btn.setTranslateY(30);
            btn.setOpacity(0);

            FadeTransition fade = new FadeTransition(Duration.millis(600), btn);
            fade.setFromValue(0);
            fade.setToValue(1);

            TranslateTransition bounce = new TranslateTransition(Duration.millis(600), btn);
            bounce.setFromY(30);
            bounce.setToY(0);
            bounce.setInterpolator(Interpolator.EASE_OUT);

            ParallelTransition animation = new ParallelTransition(fade, bounce);
            animation.setDelay(Duration.millis(delay));
            animation.play();

            delay += 300;
        }
    }
    public void tohButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mysticmaze/fxmls/TohPage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("Player");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally show an alert to the user:
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load page");
            alert.setContentText("Could not load TohPage.fxml.");
            alert.showAndWait();
        }
    }


    // 🎮 Level actions
    @FXML private void handleLevel1() { System.out.println("Entering Whispering Woods..."); }
    @FXML private void handleLevel2() { System.out.println("Entering Cursed Cavern..."); }
    @FXML private void handleLevel3() { System.out.println("Entering Ember Peak..."); }
    @FXML private void handleLevel4() { System.out.println("Entering Silent Depths..."); }
    @FXML private void handleLevel5() { System.out.println("Entering Twilight Path..."); }
    @FXML private void handleLevel6() { System.out.println("Entering Frozen Hollow..."); }
    @FXML private void handleLevel7() { System.out.println("Entering Stormspire..."); }
    @FXML private void handleLevel8() { System.out.println("Entering Necro Keep..."); }
}