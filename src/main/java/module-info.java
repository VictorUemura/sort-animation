module com.example.sortanimation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sortanimation to javafx.fxml;
    exports com.example.sortanimation;
}