package org.example.demo5;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class HelloController {

    @FXML private Pane rod1;
    @FXML private Pane rod2;
    @FXML private Pane rod3;

    @FXML private Label moveCountLabel;
    @FXML private Label timerLabel;

    private Pane selectedRod = null;
    private boolean gameOver = false;
    private int totalDisks = 4;
    private int moveCount = 0;
    private int seconds = 0;

    private Timeline timeline;

    private final int baseWidth = 120;
    private final int height = 20;
    private final int spacing = 5;

    private final Color[] colors = {
            Color.SKYBLUE, Color.LIGHTGREEN, Color.LIGHTCORAL, Color.GOLD, Color.VIOLET
    };

    @FXML
    public void initialize() {
        addDisks(rod1, totalDisks);
        startTimer();
    }

    private void addDisks(Pane rod, int count) {
        rod.getChildren().clear();
        for (int i = count; i > 0; i--) {
            int width = baseWidth - (count - i) * 25;
            Rectangle disk = new Rectangle(width, height);
            disk.setArcWidth(15);
            disk.setArcHeight(15);
            disk.setFill(colors[i % colors.length]);
            disk.setUserData(i); // Disk size
            rod.getChildren().add(disk);
        }
        updateLayout(rod);
    }

    @FXML
    private void onRodClicked(MouseEvent event) {
        if (gameOver) return;

        Pane clickedRod = (Pane) event.getSource();

        if (selectedRod == null) {
            if (!clickedRod.getChildren().isEmpty()) {
                selectedRod = clickedRod;
                highlightTopDisk(clickedRod, true);
            }
        } else {
            if (clickedRod != selectedRod) {
                boolean moved = moveDisk(selectedRod, clickedRod);
                if (!moved) {
                    showAlert("Game Over!", "You tried an illegal move. Game Over!");
                    timeline.stop();
                    gameOver = true;
                } else {
                    moveCount++;
                    moveCountLabel.setText(String.valueOf(moveCount));
                    checkWinCondition();
                }
            }
            highlightTopDisk(selectedRod, false);
            selectedRod = null;
        }
    }

    private boolean moveDisk(Pane fromRod, Pane toRod) {
        if (fromRod.getChildren().isEmpty()) return false;

        Rectangle movingDisk = (Rectangle) fromRod.getChildren().get(fromRod.getChildren().size() - 1);
        int movingSize = (int) movingDisk.getUserData();

        if (!toRod.getChildren().isEmpty()) {
            Rectangle topDisk = (Rectangle) toRod.getChildren().get(toRod.getChildren().size() - 1);
            int topSize = (int) topDisk.getUserData();
            if (movingSize > topSize) return false; // Illegal move
        }

        fromRod.getChildren().remove(movingDisk);
        toRod.getChildren().add(movingDisk);
        updateLayout(fromRod);
        updateLayout(toRod);
        return true;
    }

    private void updateLayout(Pane rod) {
        int rodWidth = 150;
        int centerX = rodWidth / 2;

        for (int i = 0; i < rod.getChildren().size(); i++) {
            Rectangle disk = (Rectangle) rod.getChildren().get(i);
            int width = (int) disk.getWidth();
            disk.setX(centerX - width / 2);
            disk.setY(300 - (i + 1) * (height + spacing));
        }
    }

    private void highlightTopDisk(Pane rod, boolean highlight) {
        if (!rod.getChildren().isEmpty()) {
            Rectangle topDisk = (Rectangle) rod.getChildren().get(rod.getChildren().size() - 1);
            topDisk.setStroke(highlight ? Color.DARKBLUE : null);
            topDisk.setStrokeWidth(highlight ? 3 : 0);
        }
    }

    private void checkWinCondition() {
        if (rod2.getChildren().size() == totalDisks) { // Changed destination rod to rod2
            showAlert("You Win!", "Congratulations, you solved the Tower of Hanoi!");
            timeline.stop();
            gameOver = true;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            seconds++;
            timerLabel.setText(seconds + " sec");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
