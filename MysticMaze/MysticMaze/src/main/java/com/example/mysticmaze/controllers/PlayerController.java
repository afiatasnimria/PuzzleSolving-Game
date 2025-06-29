package com.example.mysticmaze.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.mysticmaze.utils.DBUtil;

public class PlayerController {

    @FXML
    private HBox carousel;

    private final int USERS_PER_PAGE = 5;
    private int currentPage = 0;

    private final List<User> allUsers = new ArrayList<>();

    // Default images for fallback
    private final List<String> defaultImages = List.of(
            "/com/example/mysticmaze/images/ashira.png"
    );

    public static class User {
        String username;
        byte[] profileImage;

        User(String username, byte[] profileImage) {
            this.username = username;
            this.profileImage = profileImage;
        }
    }

    @FXML
    public void initialize() {
        fetchAllUsers();
        displayPage(currentPage);
    }

    private void fetchAllUsers() {
        allUsers.clear();
        String query = "SELECT username, profile_image FROM users";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                byte[] profileImage = rs.getBytes("profile_image");
                allUsers.add(new User(username, profileImage));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayPage(int page) {
        carousel.getChildren().clear();

        int start = page * USERS_PER_PAGE;
        int end = Math.min(start + USERS_PER_PAGE, allUsers.size());

        for (int i = start; i < end; i++) {
            User user = allUsers.get(i);

            ImageView imageView = new ImageView();
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setStyle("-fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 40;");

            Image image = null;
            if (user.profileImage != null && user.profileImage.length > 0) {
                image = new Image(new ByteArrayInputStream(user.profileImage));
            } else {
                // Rotate through default images
                String fallbackPath = defaultImages.get(0);
                InputStream fallbackStream = getClass().getResourceAsStream(fallbackPath);
                if (fallbackStream != null) {
                    image = new Image(fallbackStream);
                }
            }

            if (image != null) {
                imageView.setImage(image);
            }

            Label nameLabel = new Label(user.username);
            nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-alignment: center;");
            nameLabel.setMaxWidth(80);
            nameLabel.setWrapText(true);

            VBox userBox = new VBox(5);
            userBox.setStyle("-fx-alignment: center;");
            userBox.getChildren().addAll(imageView, nameLabel);

            carousel.getChildren().add(userBox);
        }
    }

    @FXML
    public void handleNext() {
        int maxPage = (int) Math.ceil((double) allUsers.size() / USERS_PER_PAGE) - 1;
        if (currentPage < maxPage) {
            currentPage++;
            displayPage(currentPage);
        }
    }

    @FXML
    public void handlePrevious() {
        if (currentPage > 0) {
            currentPage--;
            displayPage(currentPage);
        }
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent backRoot = FXMLLoader.load(getClass().getResource("/com/example/mysticmaze/fxmls/HomePage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(backRoot));
    }

    @FXML
    private void goToNext(ActionEvent event) throws IOException {
        Parent nextRoot = FXMLLoader.load(getClass().getResource("/com/example/mysticmaze/fxmls/NextPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(nextRoot));
    }
}
