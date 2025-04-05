package controllers;

import com.example.models.Venue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.stage.Stage;
import services.VenueService;

import java.io.IOException;

public class VenueDashboardController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Venue> venueTable;

    @FXML
    private TableColumn<Venue, String> venueNameColumn;

    @FXML
    private TableColumn<Venue, String> statusColumn;

    @FXML
    private TableColumn<Venue, Integer> capacityColumn;

    @FXML
    private ComboBox<String> venueComboBox;

    @FXML
    private Label statusLabel;

    private VenueService venueService = new VenueService();

    public void initialize() {
        // Initialize the venue table columns
        venueNameColumn.setCellValueFactory(cellData -> cellData.getValue().venueNameProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        capacityColumn.setCellValueFactory(cellData -> cellData.getValue().capacityProperty().asObject());

        // Load available venues
        ObservableList<String> venueNames = FXCollections.observableArrayList(venueService.getAllVenueNames());
        venueComboBox.setItems(venueNames);
    }
    @FXML
    private void handleBackToMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) venueTable.getScene().getWindow();
        stage.setScene(scene);
    }
    @FXML
    private void handleReserveSlot() {
        Venue selected = venueTable.getSelectionModel().getSelectedItem();
        if (selected != null && "Available".equals(selected.getStatus())) {
            selected.setStatus("Reserved");
            venueTable.refresh();
            statusLabel.setText("Slot successfully reserved for: " + selected.getVenueName());
        } else {
            statusLabel.setText("Please select an available slot to reserve.");
        }
    }

    @FXML
    private void handleDeleteBooking() {
        Venue selected = venueTable.getSelectionModel().getSelectedItem();
        if (selected != null && "Reserved".equals(selected.getStatus())) {
            selected.setStatus("Available");
            venueTable.refresh();
            statusLabel.setText("Reservation removed for: " + selected.getVenueName());
        } else {
            statusLabel.setText("Please select a reserved slot to delete.");
        }
    }

    @FXML
    private void handleCheckAvailability(ActionEvent event) {
        if (datePicker.getValue() != null) {
            String selectedDate = datePicker.getValue().toString();
            ObservableList<Venue> venues = FXCollections.observableArrayList(venueService.checkAvailability(selectedDate));
            venueTable.setItems(venues);
            statusLabel.setText("Availability for " + selectedDate + " loaded.");
        } else {
            statusLabel.setText("Please select a date.");
        }
    }

    @FXML
    private void handleReserveSlot(ActionEvent event) {
        String selectedVenue = venueComboBox.getValue();
        Venue selected = venueTable.getSelectionModel().getSelectedItem();

        if (selectedVenue != null && selected != null) {
            boolean success = venueService.reserveVenue(selected, selectedVenue);
            if (success) {
                statusLabel.setText("Slot reserved for " + selectedVenue);
            } else {
                statusLabel.setText("Failed to reserve slot.");
            }
        } else {
            statusLabel.setText("Please select a venue and an available slot.");
        }
    }
}
