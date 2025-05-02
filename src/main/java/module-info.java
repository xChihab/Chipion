module com.example.chipion1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chipion1 to javafx.fxml;
    exports com.example.chipion1;
}