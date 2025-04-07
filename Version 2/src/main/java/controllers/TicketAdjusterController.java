package controllers;

import DatabaseLogic.DatabaseConnection;
import DatabaseLogic.Event;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class TicketAdjusterController {

    @FXML private ComboBox<Event> eventComboBox;
    @FXML private TextField basePriceField;
    @FXML private TextField discountField;
    @FXML private Label maxDiscountLabel;
    @FXML private Label statusLabel;
    @FXML private Button goBackButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private List<Event> events;

    @FXML
    public void initialize() {
        // Load events from database
        events = DatabaseConnection.getAllEvents();
        eventComboBox.setItems(FXCollections.observableArrayList(events));
        eventComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Event item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " (ID: " + item.getEventId() + ")");
                }
            }
        });
        eventComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Event item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " (ID: " + item.getEventId() + ")");
                }
            }
        });

        // Event selection listener
        eventComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                basePriceField.setText(String.format("%.2f", newVal.getSellingPrice()));
                discountField.setText("0"); // Reset discount
                maxDiscountLabel.setText("Max: " + newVal.getMaxDiscount() + "%");
            }
        });

        // Restrict input to numbers
        restrictToNumeric(basePriceField);
        restrictToNumeric(discountField);
    }

    @FXML
    private void handleSave() {
        Event selectedEvent = eventComboBox.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            statusLabel.setText("Please select an event.");
            return;
        }

        try {
            double newPrice = Double.parseDouble(basePriceField.getText());
            double discount = Double.parseDouble(discountField.getText());
            double maxDiscount = selectedEvent.getMaxDiscount();

            if (newPrice < 0) {
                statusLabel.setText("Price cannot be negative.");
                return;
            }
            if (discount < 0 || discount > maxDiscount) {
                statusLabel.setText("Discount must be between 0 and " + maxDiscount + "%.");
                return;
            }

            // Apply discount to price
            double finalPrice = newPrice * (1 - discount / 100);
            selectedEvent.setSellingPrice(finalPrice);

            // Update database
            DatabaseConnection.pushEventEditsToDatabase(List.of(selectedEvent));
            statusLabel.setText("Price updated successfully to £" + String.format("%.2f", finalPrice));
            basePriceField.setText(String.format("%.2f", finalPrice));
            discountField.setText("0");

        } catch (NumberFormatException e) {
            statusLabel.setText("Please enter valid numeric values.");
        } catch (Exception e) {
            statusLabel.setText("Error saving changes: " + e.getMessage());
        }
    }

    @FXML
    private void handleGoBack() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.setScene(scene);
    }

    private void restrictToNumeric(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d.]", ""));
            }
        });
    }
}