package controllers;

import DatabaseLogic.DatabaseConnection;
import DatabaseLogic.Event;
import DatabaseLogic.Ticket;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class TicketAdjusterController {

    @FXML private ComboBox<Event> eventComboBox;
    @FXML private ComboBox<Ticket> ticketComboBox;
    @FXML private TextField basePriceField;
    @FXML private TextField discountField;
    @FXML private Label maxDiscountLabel;
    @FXML private Label statusLabel;
    @FXML private Button goBackButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private List<Event> events;
    private List<Ticket> tickets;

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
                // Load tickets for the selected event
                tickets = DatabaseConnection.getTicketsForEvent(newVal.getEventId());
                ticketComboBox.setItems(FXCollections.observableArrayList(tickets));
                ticketComboBox.getSelectionModel().clearSelection();
                basePriceField.clear();
                discountField.setText("0");
                maxDiscountLabel.setText("Max: " + newVal.getMaxDiscount() + "%");
            } else {
                ticketComboBox.getItems().clear();
                basePriceField.clear();
                discountField.setText("0");
                maxDiscountLabel.setText("Max: 0%");
            }
        });

        // Ticket selection listener
        ticketComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Ticket item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("Ticket ID: " + item.getTicketID() + " (Seats: " + item.getNumberOfSeats() + ")");
                }
            }
        });
        ticketComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Ticket item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("Ticket ID: " + item.getTicketID() + " (Seats: " + item.getNumberOfSeats() + ")");
                }
            }
        });
        ticketComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Set the base price to the event's default price, not the ticket's current price
                Event selectedEvent = eventComboBox.getSelectionModel().getSelectedItem();
                if (selectedEvent != null) {
                    basePriceField.setText(String.format("%.2f", selectedEvent.getSellingPrice()));
                } else {
                    basePriceField.clear();
                }
                discountField.setText("0");
            } else {
                basePriceField.clear();
                discountField.setText("0");
            }
        });

        // Restrict input to numbers
        restrictToNumeric(basePriceField);
        restrictToNumeric(discountField);
    }

    @FXML
    private void handleSave() {
        Event selectedEvent = eventComboBox.getSelectionModel().getSelectedItem();
        Ticket selectedTicket = ticketComboBox.getSelectionModel().getSelectedItem();

        if (selectedEvent == null) {
            statusLabel.setText("Please select an event.");
            return;
        }
        if (selectedTicket == null) {
            statusLabel.setText("Please select a ticket.");
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

            // Apply discount to the base price (which starts as the event's default price)
            double finalPrice = newPrice * (1 - discount / 100);
            selectedTicket.setSellingPrice(finalPrice);

            // Update the ticket price in the database
            DatabaseConnection.updateTicketPrice(selectedTicket.getTicketID(), finalPrice);

            statusLabel.setText("Ticket price updated successfully to £" + String.format("%.2f", finalPrice));
            basePriceField.setText(String.format("%.2f", finalPrice));
            discountField.setText("0");

        } catch (NumberFormatException e) {
            statusLabel.setText("Please enter valid numeric values.");
        } catch (Exception e) {
            statusLabel.setText("Error saving changes: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleGoBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
            Stage stage = (Stage) goBackButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error loading Menu: " + e.getMessage());
        }
    }

    private void restrictToNumeric(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d.]", ""));
            }
        });
    }
}