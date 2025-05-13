package com.example.mysticmaze.controllers;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.List;

public class LevelController {

    @FXML private Button level1Btn, level2Btn, level3Btn, level4Btn;
    @FXML private Button level5Btn, level6Btn, level7Btn, level8Btn;
    @FXML private Button backBtn;

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

        // Add border and increase background opacity for visibility
        btn.setStyle(
                "-fx-background-color: rgba(30, 30, 30, 0.75);" +  // slightly more opaque
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.2);" +    // soft white border
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 10;"
        );

        // Base glow
        Glow glow = new Glow(0.3);
        btn.setEffect(glow);

        // On-hover shadow with color
        DropShadow hoverShadow = new DropShadow(18, glowColor);
        hoverShadow.setOffsetX(0);
        hoverShadow.setOffsetY(0);
        hoverShadow.setSpread(0.3);
        hoverShadow.setRadius(20);

        // Hover effects
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

            delay += 300; // delay each button
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

    @FXML private void handleBack() { System.out.println("Returning to Realm Selection..."); }
}
