package com.example.demo4;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Stack;

public class HelloController {

    @FXML private Pane rod1, rod2, rod3;
    @FXML private Label moveLabel, timerLabel;

    private Pane selectedRod = null;
    private Rectangle selectedDisk = null;
    private final int diskHeight = 20;
    private final int spacing = 5;

    private final Stack<Rectangle> stack1 = new Stack<>();
    private final Stack<Rectangle> stack2 = new Stack<>();
    private final Stack<Rectangle> stack3 = new Stack<>();

    private int moveCount = 0;
    private long startTime;
    private Timeline timeline;
    private final int totalDisks = 6; // YOU can change the number of disks here

    public void initialize() {
        drawRod(rod1);
        drawRod(rod2);
        drawRod(rod3);

        javafx.application.Platform.runLater(() -> {
            rod1.getScene().getRoot().setStyle("-fx-background-color: #ffe6f0;");
        });

        // Calculate the available space for disks, considering a bit of padding
        int rodWidth = (int) rod1.getPrefWidth() - 20;  // Padding on both sides of the rod
        int maxDiskWidth = rodWidth;  // Bottom disk will take up the full width of the rod

        // Add disks in descending size
        for (int i = totalDisks; i > 0; i--) {
            // Scale the disk width such that the bottom disk matches the rod's width
            int width = (int) (maxDiskWidth * (i / (double) totalDisks));
            Color color = Color.web(getRainbowColor(i));
            addDisk(rod1, stack1, width, color);
        }
        updateLayout(rod1, stack1);

        startTime = System.currentTimeMillis();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void drawRod(Pane rod) {
        Line line = new Line(75, 50, 75, 300);
        line.setStrokeWidth(8);
        line.setStroke(Color.LIGHTGRAY);
        rod.getChildren().add(line);
        rod.setStyle("-fx-background-color: transparent; -fx-border-color: #ccc; -fx-border-width: 2;");
    }

    private void addDisk(Pane rod, Stack<Rectangle> stack, int width, Color color) {
        Rectangle disk = new Rectangle(width, diskHeight, color);
        disk.setArcWidth(10);
        disk.setArcHeight(10);
        stack.push(disk);
        rod.getChildren().add(disk);
    }

    private void updateLayout(Pane rod, Stack<Rectangle> stack) {
        int centerX = (int) rod.getPrefWidth() / 2;
        int baseY = 300;

        for (int i = 0; i < stack.size(); i++) {
            Rectangle disk = stack.get(i);
            disk.setX(centerX - disk.getWidth() / 2);
            disk.setY(baseY - (i + 1) * (diskHeight + spacing));
            disk.setStroke(null);
        }
    }
    @FXML
    private void onRodClicked(MouseEvent event) {
        Pane clickedRod = (Pane) event.getSource();
        Stack<Rectangle> clickedStack = getStack(clickedRod);

        if (selectedRod == null) {
            if (!clickedStack.isEmpty()) {
                selectedRod = clickedRod;
                selectedDisk = clickedStack.peek();
                selectedDisk.setStroke(Color.BLACK);  // Set border color to black
                selectedDisk.setStrokeWidth(2);      // Set border width to a thin value
            }
        } else {
            if (clickedRod == selectedRod) {
                if (selectedDisk != null) selectedDisk.setStroke(null);  // Remove border when deselected
                selectedRod = null;
                selectedDisk = null;
                return;
            }

            Stack<Rectangle> sourceStack = getStack(selectedRod);
            Stack<Rectangle> targetStack = getStack(clickedRod);

            if (sourceStack.isEmpty()) {
                selectedRod = null;
                selectedDisk = null;
                return;
            }

            Rectangle topDisk = sourceStack.peek();
            if (targetStack.isEmpty() || topDisk.getWidth() < targetStack.peek().getWidth()) {
                sourceStack.pop();
                targetStack.push(topDisk);

                selectedRod.getChildren().remove(topDisk);
                clickedRod.getChildren().add(topDisk);

                updateLayout(selectedRod, sourceStack);
                updateLayout(clickedRod, targetStack);

                moveCount++;
                moveLabel.setText("Moves: " + moveCount);

                if (stack3.size() == totalDisks) {
                    timeline.stop();
                    long totalTime = (System.currentTimeMillis() - startTime) / 1000;
                    showStyledAlert("🎉 YOU WON 🎉", "🏆 Well done! You solved it in\n" + moveCount + " moves and " + totalTime + " seconds! 🎮");
                    disableRods();
                }
            } else {
                timeline.stop();
                long totalTime = (System.currentTimeMillis() - startTime) / 1000;
                showStyledAlert("💥 GAME OVER 💥", "⛔ Invalid move!\nTotal Moves: " + moveCount + "\nTime: " + totalTime + " seconds. Try again! 🔁");
                disableRods();
            }

            if (selectedDisk != null) selectedDisk.setStroke(null);  // Remove border after move
            selectedRod = null;
            selectedDisk = null;
        }
    }



    private Stack<Rectangle> getStack(Pane rod) {
        if (rod == rod1) return stack1;
        if (rod == rod2) return stack2;
        return stack3;
    }

    private void updateTimer() {
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        timerLabel.setText("Time: " + elapsed + "s");
    }

    private void disableRods() {
        rod1.setDisable(true);
        rod2.setDisable(true);
        rod3.setDisable(true);
    }

    private void showStyledAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.getDialogPane().setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #fff0f5; -fx-text-fill: #222; -fx-border-color: #ff69b4; -fx-border-width: 2px;");
        alert.showAndWait();
    }

    private String getRainbowColor(int i) {
        String[] rainbow = {"#FF0000", "#FF7F00", "#FFFF00", "#00FF00", "#0000FF", "#4B0082", "#8B00FF"};
        return rainbow[(i - 1) % rainbow.length];
    }
}
