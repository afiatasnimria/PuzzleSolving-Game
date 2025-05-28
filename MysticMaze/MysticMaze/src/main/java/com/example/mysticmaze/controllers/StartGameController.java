package com.example.mysticmaze.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;

public class StartGameController {

    @FXML
    private ComboBox<String> avatarSelect;

    @FXML
    private ImageView avatarPreview;

    @FXML
    public void initialize() {
        avatarSelect.getItems().addAll("ashira", "echo", "iris", "rook","juno","vex");
        avatarSelect.setOnAction(e -> updateAvatarPreview());
    }

    private void updateAvatarPreview() {
        String selected = avatarSelect.getValue();
        if (selected != null) {
            String imagePath = "/com/example/mysticmaze/images/" + selected.toLowerCase() + ".png";
            avatarPreview.setImage(new javafx.scene.image.Image(imagePath));
        }
    }

    public void startAdventure(ActionEvent event) {
        String chosenAvatar = avatarSelect.getValue();
        if (chosenAvatar == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select your avatar before starting!");
            alert.show();
        } else {
            // Load the first level or navigate forward
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Starting game as " + chosenAvatar + "!");
            alert.show();
        }
    }
}
