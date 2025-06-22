module org.example.crossword {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.crossword to javafx.fxml;
    exports org.example.crossword;
}