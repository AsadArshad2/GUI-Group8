module com.example.guitest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.models to javafx.fxml;
    exports com.example.models;
    exports services;
    exports main;
    opens services to javafx.fxml;
    exports controllers;
    opens controllers to javafx.fxml;
}