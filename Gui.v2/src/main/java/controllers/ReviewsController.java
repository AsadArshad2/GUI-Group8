package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class ReviewsController {
    @FXML private ListView<String> reviewList;
    @FXML private TextField nameField;
    @FXML private TextField reviewField;

    @FXML
    private void handleFetchReviews() {
        reviewList.setItems(FXCollections.observableArrayList(
                "Alice: Great venue and acoustics!",
                "Anonymous: Crowd was rowdy but staff handled it well.",
                "Bob: Booking process was smooth."
        ));
    }

    @FXML
    private void handleAddReview() {
        String name = nameField.getText().trim();
        String review = reviewField.getText().trim();

        if (!review.isEmpty()) {
            String fullReview = (name.isEmpty() ? "Anonymous" : name) + ": " + review;
            reviewList.getItems().add(fullReview);
            nameField.clear();
            reviewField.clear();
        }
    }

    @FXML
    private void handleBackToMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) reviewList.getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void handleDeleteReview() {
        String selected = reviewList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            reviewList.getItems().remove(selected);
        } else {
            // Optional: alert if nothing is selected
            System.out.println("No review selected to delete.");
        }
    }

}
