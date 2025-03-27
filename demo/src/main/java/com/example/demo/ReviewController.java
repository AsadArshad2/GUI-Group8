package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class ReviewController {
    @FXML
    private void handleAddReview() throws IOException {
        System.out.println("add review");
    }


    @FXML
    private void handleCheckReview() throws IOException {
        System.out.println("check review");
    }

@FXML
    private void navigateToFinancesScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/finances.fxml"));
            Parent financesForm = loader.load();
//            BorderPane rootPane = (BorderPane) menuBar.getScene().getRoot();
//            rootPane.setCenter(financesForm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}