package controllers;

import DatabaseLogic.Booking;
import DatabaseLogic.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
//import com.example.models.Venue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BookingController {
    @FXML private DatePicker datePicker;
    @FXML private TableView<Booking> bookingTableView;
    @FXML private TableColumn<Booking, String> clientName;
    @FXML private TableColumn<Booking, String> eventName;
    @FXML private TableColumn<Booking, String> date;
    @FXML private TableColumn<Booking, String> startTime;
    @FXML private TableColumn<Booking, String> endTime;
    @FXML private TableColumn<Booking, Double> totalCost;
    @FXML private TableColumn<Booking, String> configurationDetails;
    @FXML private TableColumn<Booking, String> status;
    @FXML private Label statusLabel;

    private List<Booking> bookingsLIST = DatabaseConnection.getBookings();
    private ObservableList<Booking> bookings = FXCollections.observableArrayList();

    public void initialize() {
        date.setCellValueFactory(cellData -> cellData.getValue().getClientNameProperty());
        startTime.setCellValueFactory(cellData -> cellData.getValue().getEventNameProperty());
        date.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        startTime.setCellValueFactory(cellData -> cellData.getValue().getStartTimeProperty());
        endTime.setCellValueFactory(cellData -> cellData.getValue().getEndTimeProperty()) ;
        totalCost.setCellValueFactory(cellData -> cellData.getValue().getTotalCostProperty().asObject());
        configurationDetails.setCellValueFactory(cellData -> cellData.getValue().getConfigurationDetailsProperty());
        status.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());

        for (Booking booking : bookingsLIST) {
            bookings.add(
                    new Booking(booking.getClientName(),booking.getEventName(), booking.getDate(), booking.getStartTime(), booking.getEndTime(), booking.getTotalCost(), booking.getConfigurationDetails(), booking.getStatus())
            );
        }
    }

    @FXML
    private void handleCheckAvailability() {
        if (datePicker.getValue() == null) {
            datePicker.setValue(LocalDate.now());
        }

        LocalDate selected = datePicker.getValue();
        if (selected == null) {
            statusLabel.setText("Please select a date.");
            return;
        }

        long daysAhead = ChronoUnit.DAYS.between(LocalDate.now(), selected);
        if (daysAhead > 30) {
            statusLabel.setText("Bookings are limited to 4 weeks in advance.");
            return;
        }

        ObservableList<Booking> bookingDates = FXCollections.observableArrayList();
        for (Booking booking : bookingsLIST){
            if (LocalDate.parse(booking.getDate()).equals(selected)) {
                bookingDates.add(booking);
            }
        }


        bookingTableView.setItems(bookingDates);
        statusLabel.setText("Availability loaded for " + selected);
    }

/*
    @FXML
    private void handleReserveSlot() {
        Venue selected = bookingTableView.getSelectionModel().getSelectedItem();
        if (selected != null && "Available".equals(selected.getStatus())) {
            if ("Main Hall".equals(selected.getVenueName())) {
                selected.setStatus("FILM SHOWING (Reserved)");
            } else {
                selected.setStatus("Reserved");
            }
            bookingTableView.refresh();
            statusLabel.setText("Reserved: " + selected.getVenueName());
        } else {
            statusLabel.setText("Please select an available venue.");
        }
    }

    @FXML
    private void handleDeleteBooking() {
        Venue selected = bookingTableView.getSelectionModel().getSelectedItem();
        if (selected != null && !selected.getStatus().equals("Available")) {
            selected.setStatus("Available");
            bookingTableView.refresh();
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
        Stage stage = (Stage) bookingTableView.getScene().getWindow();
        stage.setScene(scene);
    }
    */
}
