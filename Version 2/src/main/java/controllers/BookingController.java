package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import com.example.models.Venue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookingController {
    @FXML private DatePicker datePicker;
    @FXML private TableView<Venue> venueTable;
    @FXML private TableColumn<Venue, String> venueNameColumn;
    @FXML private TableColumn<Venue, String> statusColumn;
    @FXML private TableColumn<Venue, Integer> capacityColumn;
    @FXML private Label statusLabel;

    private ObservableList<Venue> venues = FXCollections.observableArrayList();

    public void initialize() {
        venueNameColumn.setCellValueFactory(cellData -> cellData.getValue().venueNameProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        capacityColumn.setCellValueFactory(cellData -> cellData.getValue().capacityProperty().asObject());

        venues.addAll(
                new Venue("Main Hall", "Available", 370),
                new Venue("Meeting Room A", "Available", 20),
                new Venue("Meeting Room B", "Available", 20)
        );
    }

    @FXML
    private void handleCheckAvailability() {
        LocalDate selected = datePicker.getValue();
        if (selected == null) {
            statusLabel.setText("Please select a date.");
            return;
        }

        long daysAhead = ChronoUnit.DAYS.between(LocalDate.now(), selected);
        if (daysAhead > 21) {
            statusLabel.setText("Bookings are limited to 3 weeks in advance.");
            return;
        }

        venueTable.setItems(venues);
        statusLabel.setText("Availability loaded for " + selected);
    }

    @FXML
    private void handleReserveSlot() {
        Venue selected = venueTable.getSelectionModel().getSelectedItem();
        if (selected != null && "Available".equals(selected.getStatus())) {
            if ("Main Hall".equals(selected.getVenueName())) {
                selected.setStatus("FILM SHOWING (Reserved)");
            } else {
                selected.setStatus("Reserved");
            }
            venueTable.refresh();
            statusLabel.setText("Reserved: " + selected.getVenueName());
        } else {
            statusLabel.setText("Please select an available venue.");
        }
    }

    @FXML
    private void handleDeleteBooking() {
        Venue selected = venueTable.getSelectionModel().getSelectedItem();
        if (selected != null && !selected.getStatus().equals("Available")) {
            selected.setStatus("Available");
            venueTable.refresh();
            statusLabel.setText("Booking removed for: " + selected.getVenueName());
        } else {
            statusLabel.setText("Select a reserved venue to delete.");
        }
    }

    @FXML
    private void handleBackToMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) venueTable.getScene().getWindow();
        stage.setScene(scene);
    }
}
