package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import models.VenueAvailability;

import java.io.IOException;
import java.time.LocalDate;

public class VenueCalendarController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<VenueAvailability> availabilityTable;

    @FXML
    private TableColumn<VenueAvailability, String> timeSlotColumn;

    @FXML
    private TableColumn<VenueAvailability, String> venueColumn;

    @FXML
    private TableColumn<VenueAvailability, String> statusColumn;

    @FXML
    private TableColumn<VenueAvailability, Void> actionColumn;

    @FXML
    public void initialize() {
        // Setup table columns
        timeSlotColumn.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        venueColumn.setCellValueFactory(new PropertyValueFactory<>("venue"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load today's availability by default
        datePicker.setValue(LocalDate.now());
        loadAvailabilityForDate(LocalDate.now());

        // Set action on date change
        datePicker.setOnAction(e -> loadAvailabilityForDate(datePicker.getValue()));
    }

    private void loadAvailabilityForDate(LocalDate date) {
        // TODO: Load from your data source (e.g., database or dummy list)
        availabilityTable.getItems().clear();
        // Example data
        availabilityTable.getItems().addAll(
                new VenueAvailability("09:00 - 11:00", "Main Hall", "Available"),
                new VenueAvailability("11:00 - 13:00", "Rehearsal Room", "Booked")
        );
    }

    @FXML
    private void goToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
