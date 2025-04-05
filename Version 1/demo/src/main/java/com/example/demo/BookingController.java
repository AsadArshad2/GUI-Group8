package com.example.demo;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BookingController {

    @FXML
    private TextField dateField;

    @FXML
    private TextField timeField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField clientField;

    @FXML
    private TextField contactField;

    // Seating fields
    @FXML
    private TextField seatsField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField totalField;

    @FXML
    private Button confirmButton;

    @FXML
    public void initialize() {
        // Listener to update total dynamically as the seating values change.
        ChangeListener<String> computeTotalListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateTotal();
            }
        };
        seatsField.textProperty().addListener(computeTotalListener);
        priceField.textProperty().addListener(computeTotalListener);
    }

    private void updateTotal() {
        try {
            int seats = Integer.parseInt(seatsField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());
            double total = seats * price;
            totalField.setText(String.format("%.2f", total));
        } catch (NumberFormatException ex) {
            totalField.setText("");
        }
    }

    @FXML
    public void handleConfirmButton(ActionEvent event) {
        // For demonstration, print booking details.
        System.out.println("Date: " + dateField.getText());
        System.out.println("Time: " + timeField.getText());
        System.out.println("Name: " + nameField.getText());
        System.out.println("Description: " + descriptionField.getText());
        System.out.println("Client: " + clientField.getText());
        System.out.println("Contact: " + contactField.getText());
        System.out.println("Seats: " + seatsField.getText());
        System.out.println("Price per Seat: " + priceField.getText());
        System.out.println("Total: " + totalField.getText());

        // Get the current scene's root, which is assumed to be a BorderPane.
        Scene scene = confirmButton.getScene();
        if (scene == null) {
            System.err.println("Scene is null!");
            return;
        }
        BorderPane rootPane = (BorderPane) scene.getRoot();
        // The center of the BorderPane contains the booking form.
        Node centerNode = rootPane.getCenter();
        if (centerNode == null) {
            System.err.println("Center node is null!");
            return;
        }

        // Fade out the current center (booking form).
        FadeTransition fadeOut = new FadeTransition(Duration.millis(600), centerNode);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            try {
                // Load the confirmation screen.
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/confirmation.fxml"));
                Parent confirmationRoot = loader.load();
                // Set initial opacity for fade-in.
                confirmationRoot.setOpacity(0.0);
                // Set the confirmation screen into the center of the BorderPane.
                rootPane.setCenter(confirmationRoot);

                // Fade in the confirmation screen.
                FadeTransition fadeIn = new FadeTransition(Duration.millis(600), confirmationRoot);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        fadeOut.play();
    }
}
