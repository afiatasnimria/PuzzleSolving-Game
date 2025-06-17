module org.example.colourmapping {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.colourmapping to javafx.fxml;
    exports org.example.colourmapping;
}