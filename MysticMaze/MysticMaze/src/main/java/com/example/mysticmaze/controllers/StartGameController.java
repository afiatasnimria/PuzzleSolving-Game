package com.example.mysticmaze.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class StartGameController {

    @FXML private Button level1, level2, level3, level4, level5,
            level6, level7, level8, level9, level10;

    @FXML private Label lock1, lock2, lock3, lock4, lock5,
            lock6, lock7, lock8, lock9, lock10;

    private int currentUnlockedLevel = 1;

    @FXML
    public void initialize() {
        setupLevels();

        Button[] levels = {
                level1, level2, level3, level4, level5,
                level6, level7, level8, level9, level10
        };

        for (int i = 0; i < levels.length; i++) {
            final int levelNum = i + 1;
            levels[i].setOnAction(e -> unlockNextLevel(levelNum));
        }
    }

    private void setupLevels() {
        Button[] levels = {
                level1, level2, level3, level4, level5,
                level6, level7, level8, level9, level10
        };

        Label[] locks = {
                lock1, lock2, lock3, lock4, lock5,
                lock6, lock7, lock8, lock9, lock10
        };

        for (int i = 0; i < levels.length; i++) {
            boolean unlocked = (i < currentUnlockedLevel);
            levels[i].setDisable(!unlocked);
            locks[i].setVisible(!unlocked);
        }
    }

    private void unlockNextLevel(int levelCompleted) {
        if (levelCompleted == currentUnlockedLevel && currentUnlockedLevel < 10) {
            currentUnlockedLevel++;
            setupLevels();
            System.out.println("Unlocked level " + currentUnlockedLevel);
        }
    }
}
