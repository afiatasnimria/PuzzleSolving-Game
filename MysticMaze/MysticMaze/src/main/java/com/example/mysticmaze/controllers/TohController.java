package com.example.mysticmaze.controllers;

import com.example.mysticmaze.models.Message;
import com.example.mysticmaze.models.Puzzle;
import com.example.mysticmaze.network.GameClient;
import com.example.mysticmaze.utils.DBUtil;
import com.google.gson.Gson;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TohController implements Initializable {
    private GameClient gameClient;
    private final Gson gson = new Gson();

    @FXML private TextArea chatBox;
    @FXML private TextField chatInput;
    @FXML private Button sendButton;
    @FXML private VBox sidebar;

    @FXML private Label disksLabel;
    @FXML private Label bestMovesLabel;
    @FXML private Label bestTimeLabel;
    @FXML private Label difficultyLabel;
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
    private final int totalDisks = 6;

    private String username = "monser";
    private String roomId = "1";
    private Timer syncTimer;
    private final Map<String, String> teammatesProgress = new ConcurrentHashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawRod(rod1);
        drawRod(rod2);
        drawRod(rod3);

        Platform.runLater(() -> rod1.getScene().getRoot().setStyle("-fx-background-color: #ffe6f0;"));

        int rodWidth = (int) rod1.getPrefWidth() - 20;
        int maxDiskWidth = rodWidth;
        for (int i = totalDisks; i > 0; i--) {
            int width = (int) (maxDiskWidth * (i / (double) totalDisks));
            Color color = Color.web(getRainbowColor(i));
            addDisk(rod1, stack1, width, color);
        }
        updateLayout(rod1, stack1);

        startTime = System.currentTimeMillis();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        initializePuzzleInfo();
        //initializeChatAndSync();
    }

    private void initializeChatAndSync() {
        try {
            //gameClient = new GameClient(message -> Platform.runLater(() -> handleServerMessage(message)));
            gameClient.connect("localhost");
            gameClient.send(gson.toJson(new Message("join", username, roomId, "")));

            sendButton.setOnAction(e -> {
                String msg = chatInput.getText();
                if (!msg.isEmpty()) {
                    gameClient.send(gson.toJson(new Message("chat", username, roomId, msg)));
                    chatInput.clear();
                }
            });

            syncTimer = new Timer(true);
            syncTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    long elapsed = (System.currentTimeMillis() - startTime) / 1000;
                    String progress = "Moves: " + moveCount + ", Time: " + elapsed + "s";
                    gameClient.send(gson.toJson(new Message("progress", username, roomId, progress)));
                }
            }, 4000, 4000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleServerMessage(String json) {
        Message msg = gson.fromJson(json, Message.class);
        switch (msg.getType()) {
            case "chat" -> chatBox.appendText("[" + msg.getSender() + "]: " + msg.getMessage() + "\n");
            case "progress" -> {
                teammatesProgress.put(msg.getSender(), msg.getMessage());
                updateSidebar();
            }
        }
    }

    private void updateSidebar() {
        sidebar.getChildren().clear();
        Label self = new Label("ME: Moves: " + moveCount + ", Time: " + (System.currentTimeMillis() - startTime) / 1000 + "s");
        sidebar.getChildren().add(self);
        teammatesProgress.forEach((name, progress) -> {
            if (!name.equals(username)) sidebar.getChildren().add(new Label(name + ": " + progress));
        });
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
                selectedDisk.setStroke(Color.BLACK);
                selectedDisk.setStrokeWidth(2);
            }
        } else {
            if (clickedRod == selectedRod) {
                if (selectedDisk != null) selectedDisk.setStroke(null);
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

            if (selectedDisk != null) selectedDisk.setStroke(null);
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

    public void initializePuzzleInfo() {
        DBUtil db = new DBUtil();
        Puzzle puzzle = db.getTowerOfHanoiPuzzle();

        if (puzzle != null) {
            int disks = Integer.parseInt(puzzle.getPuzzleData());
            int bestMoves = puzzle.getMinimum_moves();
            float avgTime = puzzle.getMinimum_time_taken();
            String difficulty = puzzle.getDifficulty();

            disksLabel.setText("Total Disks: " + disks);
            bestMovesLabel.setText("Best Solve: " + bestMoves + " moves");
            bestTimeLabel.setText("Average Time: " + avgTime + "s");
            difficultyLabel.setText("Difficulty: " + difficulty);
        }
    }
}
