package controllers;

import DatabaseLogic.DatabaseConnection;
import DatabaseLogic.Event;
import DatabaseLogic.Seat;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainHallController {

    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> eventSelector;
    @FXML private GridPane seatGrid;

    private static final int MAIN_HALL_ROOM_ID = 1; // Assuming Main Hall has room_id = 1 in the Rooms table
    private List<Event> eventsForDate = new ArrayList<>();

    @FXML
    public void initialize() {
        // Load events for the current date when the controller is initialized
        LocalDate today = LocalDate.now();
        datePicker.setValue(today);
        onDateSelected();
    }

    /**
     * Called when the user selects a seat.
     * If not disabled (i.e., not booked), toggle between available and restricted styles,
     * and update the Restricted_view_seats table accordingly.
     */
    @FXML
    public void toggleSeat(ActionEvent event) {
        Button seatButton = (Button) event.getSource();
        if (seatButton.isDisabled()) return;

        String seatLabel = seatButton.getText();
        String row = seatLabel.substring(0, seatLabel.length() - (seatLabel.length() > 2 && Character.isDigit(seatLabel.charAt(2)) ? 2 : 1));
        String number = seatLabel.substring(row.length());

        // Check if the seat is already in the Restricted_view_seats table
        int seatId = DatabaseConnection.getRestrictedSeatId(MAIN_HALL_ROOM_ID, row, number);

        if (seatButton.getStyle().contains("#f44336")) {
            // Currently restricted, toggle to available
            seatButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            if (seatId != -1) {
                // Remove from Restricted_view_seats
                DatabaseConnection.removeRestrictedSeat(seatId);
            }
        } else {
            // Currently available, toggle to restricted
            seatButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
            if (seatId == -1) {
                // Add to Restricted_view_seats
                Seat seat = new Seat(0, MAIN_HALL_ROOM_ID, row, number, "restricted");
                DatabaseConnection.addRestrictedSeat(seat);
            }
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
            eventsForDate = DatabaseConnection.getAllEvents().stream()
                    .filter(event -> {
                        try {
                            LocalDate eventStart = LocalDate.parse(event.getStartDate());
                            LocalDate eventEnd = LocalDate.parse(event.getEndDate());
                            return !selectedDate.isBefore(eventStart) && !selectedDate.isAfter(eventEnd);
                        } catch (Exception e) {
                            return false;
                        }
                    })
                    .collect(Collectors.toList());
            eventSelector.getItems().addAll(eventsForDate.stream().map(Event::getName).collect(Collectors.toList()));
        }
    }

    /**
     * Triggered when an event is selected from the dropdown.
     * Loads the seat availability and restricted seats.
     */
    @FXML
    private void onEventSelected() {
        String selectedEvent = eventSelector.getValue();
        if (selectedEvent != null) {
            loadSeatConfigurationForEvent(selectedEvent);
        }
    }

    /**
     * Loads and resets all seat buttons for a selected event.
     * Disables and styles booked seats, and marks restricted seats.
     */
    private void loadSeatConfigurationForEvent(String eventName) {
        List<String> bookedSeats = getBookedSeatsForEvent(eventName);
        List<Seat> restrictedSeats = DatabaseConnection.getRestrictedSeatsForRoom(MAIN_HALL_ROOM_ID);

        for (Node node : seatGrid.getChildren()) {
            if (node instanceof Button seatButton) {
                seatButton.setDisable(false);
                seatButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

                // Check if the seat is booked
                if (bookedSeats.contains(seatButton.getText())) {
                    seatButton.setDisable(true);
                    seatButton.setStyle("-fx-background-color: #B71C1C; -fx-text-fill: white;");
                } else {
                    // Check if the seat is restricted
                    String seatLabel = seatButton.getText();
                    String row = seatLabel.substring(0, seatLabel.length() - (seatLabel.length() > 2 && Character.isDigit(seatLabel.charAt(2)) ? 2 : 1));
                    String number = seatLabel.substring(row.length());
                    boolean isRestricted = restrictedSeats.stream()
                            .anyMatch(seat -> seat.getRow().equals(row) && seat.getNumber().equals(number));
                    if (isRestricted) {
                        seatButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                    }
                }
            }
        }
    }

    /**
     * Fetches booked seats for an event.
     * Replace with real DB lookup if needed.
     */
    private List<String> getBookedSeatsForEvent(String eventName) {
        // Stub: Replace with actual logic to fetch booked seats for the event
        return List.of("a1", "b3", "c7", "m5", "q10", "AA20", "BB9");
    }
}