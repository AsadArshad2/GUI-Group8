package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class MainHallController {

    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> eventSelector;
    @FXML private GridPane seatGrid;

    /**
     * Called when the user selects a seat.
     * If not disabled (i.e. not booked), toggle between available and restricted styles.
     */
    @FXML
    public void toggleSeat(ActionEvent event) {
        Button seat = (Button) event.getSource();
        if (seat.isDisabled()) return;

        if (seat.getStyle().contains("#f44336")) {
            // Toggle back to available
            seat.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        } else {
            // Mark as restricted
            seat.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        }
    }

    /**
     * Handles return to the previous screen.
     */
    @FXML
    public void goBack(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Seating.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    /**
     * Triggered when a date is selected.
     * Populates the event dropdown for that date.
     */
    @FXML
    private void onDateSelected() {
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate != null) {
            eventSelector.getItems().clear();
            eventSelector.getItems().addAll(getEventsForDate(selectedDate));
        }
    }

    /**
     * Triggered when an event is selected from the dropdown.
     * Loads the seat availability.
     */
    @FXML
    private void onEventSelected() {
        String selectedEvent = eventSelector.getValue();
        if (selectedEvent != null) {
            loadSeatConfigurationForEvent(selectedEvent);
        }
    }

    /**
     * Simulates retrieval of events for a given date.
     * Replace with real DB/API call.
     */
    private List<String> getEventsForDate(LocalDate date) {
        return List.of("Matinee Show", "Evening Performance");
    }

    /**
     * Loads and resets all seat buttons for a selected event.
     * Disables and styles booked seats.
     */
    private void loadSeatConfigurationForEvent(String event) {
        List<String> bookedSeats = getBookedSeatsForEvent(event);

        for (Node node : seatGrid.getChildren()) {
            if (node instanceof Button seatButton) {
                seatButton.setDisable(false);
                seatButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

                if (bookedSeats.contains(seatButton.getText())) {
                    seatButton.setDisable(true);
                    seatButton.setStyle("-fx-background-color: #B71C1C; -fx-text-fill: white;");
                }
            }
        }
    }

    /**
     * Simulates which seats are booked for an event.
     * Replace with real DB/API lookup.
     */
    private List<String> getBookedSeatsForEvent(String event) {
        // Stub: Replace with actual logic to fetch booked seats for the event
        return List.of("a1", "b3", "c7", "m5", "q10", "AA20", "BB9");
    }
}
