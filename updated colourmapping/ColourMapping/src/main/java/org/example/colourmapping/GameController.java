package org.example.colourmapping;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class GameController {

    @FXML private Pane puzzleArea;
    @FXML private Label timerLabel, moveCounterLabel, tipCounterLabel, bestTimeLabel, targetColorLabel;
    @FXML private ComboBox<String> colorSelector;
    @FXML private Button tipButton;
    @FXML private TextField chatInputField;
    @FXML private Button sendButton;
    @FXML private VBox chatBox;


    // Player status elements
    @FXML private Label player1Moves, player1Time, player1Status;
    @FXML private Label player2Moves, player2Time, player2Status;
    @FXML private Label player3Moves, player3Time, player3Status;


    private final int gridSize = 5;
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
        setupPlayerStatus();
        setupInitialChat();
        setupSendMessage();
    }

    private void setupPlayerStatus() {
        // Player 1 (current player)
        player1Status.setText("Status: Playing");
        player1Status.setStyle("-fx-text-fill: #00ff88; -fx-font-weight: bold;");

        // Player 2
        player2Status.setText("Status: Waiting");
        player2Status.setStyle("-fx-text-fill: #ffcc00; -fx-font-weight: bold;");

        // Player 3
        player3Status.setText("Status: Offline");
        player3Status.setStyle("-fx-text-fill: #ff4444; -fx-font-weight: bold;");
    }

    private void setupInitialChat() {
        Label msg1 = new Label("Player1: Let's go!");
        msg1.setStyle("-fx-text-fill: #ffffff;");
        msg1.setWrapText(true);

        Label msg2 = new Label("Player2: Ready!");
        msg2.setStyle("-fx-text-fill: #ffffff;");
        msg2.setWrapText(true);

        chatBox.getChildren().addAll(msg1, msg2);
    }

    private void setupGrid() {
        double cellSize = 350.0 / gridSize;
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
        colorSelector.setStyle("-fx-background-color: #2a2a44; -fx-text-fill: red;");
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

        // Update move counters
        moveCounterLabel.setText("Moves: " + moves);
        player1Moves.setText("Moves: " + moves);

        checkWinCondition();
    }

    private boolean violatesAdjacencyRule(int row, int col, Color color) {
        if (row > 0 && grid[row - 1][col].getFill().equals(color)) return true;
        if (row < gridSize - 1 && grid[row + 1][col].getFill().equals(color)) return true;
        if (col > 0 && grid[row][col - 1].getFill().equals(color)) return true;
        if (col < gridSize - 1 && grid[row][col + 1].getFill().equals(color)) return true;
        return false;
    }

    private void checkWinCondition() {
        double total = gridSize * gridSize;
        double filled = 0;
        for (Rectangle[] row : grid) {
            for (Rectangle rect : row) {
                if (!rect.getFill().equals(Color.LIGHTGRAY)) filled++;
            }
        }

        if (filled == total) {
            timer.stop();
            player1Status.setText("Status: Completed");
            player1Status.setStyle("-fx-text-fill: #00ffff; -fx-font-weight: bold;");

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
            String timeString = formatTime(seconds);
            timerLabel.setText("Time: " + timeString);
            player1Time.setText("Time: " + timeString);
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int secs = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

    @FXML
    private void handleTip() {
        tipsUsed++;
        tipCounterLabel.setText("Tips Used: " + tipsUsed);

        // Add tip message to chat
        Label tipMsg = new Label("System: Try spreading colors to avoid adjacent conflicts!");
        tipMsg.setStyle("-fx-text-fill: #7cfcff;");
        tipMsg.setWrapText(true);
        chatBox.getChildren().add(tipMsg);

        Alert tip = new Alert(Alert.AlertType.INFORMATION);
        tip.setTitle("Tip");
        tip.setHeaderText("Coloring Tip");
        tip.setContentText("Try to spread colors and avoid same colors in adjacent cells.");
        tip.show();
    }

    private void setupSendMessage() {
        sendButton.setOnAction(e -> sendMessage());
        chatInputField.setOnAction(e -> sendMessage());  // Enter key sends message
    }

    private void sendMessage() {
        String message = chatInputField.getText().trim();
        if (!message.isEmpty()) {
            Label newMessage = new Label("You: " + message);
            newMessage.setStyle("-fx-text-fill: #ffffff;");
            newMessage.setWrapText(true);
            chatBox.getChildren().add(newMessage);

            chatInputField.clear();

            // Optional: Scroll to bottom
            chatBox.layout();
            ((ScrollPane)chatBox.getParent().getParent()).setVvalue(1.0);

            // TODO: Add networking or message dispatch logic here if multiplayer
        }
    }
}
