package org.example.colourmapping;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class GameController {
    @FXML
    private Pane puzzleArea;
    @FXML
    private ProgressBar player1Progress, player2Progress;
    @FXML
    private Label timerLabel, moveCounterLabel, tipCounterLabel, bestTimeLabel, targetColorLabel;
    @FXML
    private ComboBox<String> colorSelector;
    @FXML
    private Button tipButton;

    private final int gridSize = 5; // Developer-controlled grid size
    private final Rectangle[][] grid = new Rectangle[gridSize][gridSize];
    private final Color[] availableColors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
    private Color currentColor = Color.RED;
    private int moves = 0;
    private int tipsUsed = 0;
    private int seconds = 0;
    private Timeline timer;

    private final Map<String, Color> colorMap = new HashMap<>() {{
        put("Red", Color.RED);
        put("Green", Color.GREEN);
        put("Blue", Color.BLUE);
        put("Yellow", Color.YELLOW);
    }};

    @FXML
    public void initialize() {
        setupGrid();
        setupColorSelector();
        startTimer();
    }

    private void setupGrid() {
        double cellSize = 600.0 / gridSize;
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Rectangle rect = new Rectangle(cellSize, cellSize);
                rect.setFill(Color.LIGHTGRAY);
                rect.setStroke(Color.BLACK);
                rect.setLayoutX(col * cellSize);
                rect.setLayoutY(row * cellSize);
                int r = row, c = col;
                rect.setOnMouseClicked(e -> handleCellClick(r, c));
                grid[row][col] = rect;
                puzzleArea.getChildren().add(rect);
            }
        }
    }

    private void setupColorSelector() {
        colorSelector.getItems().addAll("Red", "Green", "Blue", "Yellow");
        colorSelector.setValue("Red");
        colorSelector.setOnAction(e -> {
            String selected = colorSelector.getValue();
            currentColor = colorMap.get(selected);
        });
    }

    private void handleCellClick(int row, int col) {
        Color chosen = currentColor;
        Rectangle cell = grid[row][col];

        if (cell.getFill().equals(chosen)) return;
        if (violatesAdjacencyRule(row, col, chosen)) return;

        cell.setFill(chosen);
        moves++;
        moveCounterLabel.setText("Moves: " + moves);
        updateProgress();
    }

    private boolean violatesAdjacencyRule(int row, int col, Color color) {
        if (row > 0 && grid[row - 1][col].getFill().equals(color)) return true;
        if (row < gridSize - 1 && grid[row + 1][col].getFill().equals(color)) return true;
        if (col > 0 && grid[row][col - 1].getFill().equals(color)) return true;
        if (col < gridSize - 1 && grid[row][col + 1].getFill().equals(color)) return true;
        return false;
    }

    private void updateProgress() {
        double total = gridSize * gridSize;
        double filled = 0;
        for (Rectangle[] row : grid) {
            for (Rectangle rect : row) {
                if (!rect.getFill().equals(Color.LIGHTGRAY)) filled++;
            }
        }
        double progress = filled / total;
        player1Progress.setProgress(progress);

        if (progress == 1.0) {
            timer.stop();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congratulations");
            alert.setHeaderText("Puzzle Completed");
            alert.setContentText("You finished in " + moves + " moves and " + formatTime(seconds) + "!");
            alert.show();
        }
    }

    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            seconds++;
            timerLabel.setText("Time: " + formatTime(seconds));
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @FXML
    private void handleTip() {
        tipsUsed++;
        tipCounterLabel.setText("Tips Used: " + tipsUsed);
        Alert tip = new Alert(Alert.AlertType.INFORMATION);
        tip.setTitle("Tip");
        tip.setHeaderText("Try spreading different colors in non-adjacent rows!");
        tip.show();
    }
}
