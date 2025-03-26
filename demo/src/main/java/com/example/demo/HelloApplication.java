package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Use an absolute path for the FXML file.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/login.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 1000, 1000);
        primaryStage.setTitle("GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
