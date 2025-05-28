module com.example.guessgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.guessgame to javafx.fxml;
    exports com.example.guessgame;
    exports GuessGame;
    opens GuessGame to javafx.fxml;
}