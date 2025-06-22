package org.example.crossword;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/crossword/layout.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Developer Crossword Puzzle");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
