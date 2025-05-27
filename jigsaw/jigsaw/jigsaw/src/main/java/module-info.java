module org.example.jigsaw {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.jigsaw to javafx.fxml;
    exports org.example.jigsaw;
}