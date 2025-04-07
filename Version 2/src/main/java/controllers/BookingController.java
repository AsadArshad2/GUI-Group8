package controllers;

import DatabaseLogic.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.converter.DoubleStringConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingController {
    @FXML private TableView<Booking> bookingTableView;
    @FXML private TableColumn<Booking, String> clientName;
    @FXML private TableColumn<Booking, String> eventName;
    @FXML private TableColumn<Booking, String> roomName;
    @FXML private TableColumn<Booking, String> date;
    @FXML private TableColumn<Booking, String> startTime;
    @FXML private TableColumn<Booking, String> endTime;
    @FXML private TableColumn<Booking, Double> totalCost;
    @FXML private TableColumn<Booking, String> status;
    @FXML private Label statusLabel;

    private ObservableList<Booking> bookings = FXCollections.observableArrayList();

    public void initialize() {
        System.out.println("BookingController: Initializing...");
        System.out.println("BookingController: Stylesheet URL = " + getClass().getResource("/styles/style.css"));

        bookingTableView.setEditable(true);
        bookingTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        clientName.setCellValueFactory(cellData -> cellData.getValue().getClientNameProperty());
        clientName.setCellFactory(TextFieldTableCell.forTableColumn());
        clientName.setOnEditCommit(event -> event.getRowValue().setClientName(event.getNewValue()));

        eventName.setCellValueFactory(cellData -> cellData.getValue().getEventNameProperty());
        eventName.setCellFactory(TextFieldTableCell.forTableColumn());
        eventName.setOnEditCommit(event -> event.getRowValue().setEventName(event.getNewValue()));

        roomName.setCellValueFactory(cellData -> cellData.getValue().getRoomNameProperty());
        roomName.setCellFactory(TextFieldTableCell.forTableColumn());
        roomName.setOnEditCommit(event -> event.getRowValue().setRoomName(event.getNewValue()));

        date.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        date.setCellFactory(TextFieldTableCell.forTableColumn());
        date.setOnEditCommit(event -> event.getRowValue().setDate(event.getNewValue()));

        startTime.setCellValueFactory(cellData -> cellData.getValue().getStartTimeProperty());
        startTime.setCellFactory(TextFieldTableCell.forTableColumn());
        startTime.setOnEditCommit(event -> event.getRowValue().setStartTime(event.getNewValue()));

        endTime.setCellValueFactory(cellData -> cellData.getValue().getEndTimeProperty());
        endTime.setCellFactory(TextFieldTableCell.forTableColumn());
        endTime.setOnEditCommit(event -> event.getRowValue().setEndTime(event.getNewValue()));

        totalCost.setCellValueFactory(cellData -> cellData.getValue().getTotalCostProperty().asObject());
        totalCost.setCellFactory(TextFieldTableCell.<Booking, Double>forTableColumn(new DoubleStringConverter()));
        totalCost.setOnEditCommit(event -> event.getRowValue().setTotalCost(event.getNewValue()));

        status.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
        status.setCellFactory(TextFieldTableCell.forTableColumn());
        status.setOnEditCommit(event -> event.getRowValue().setStatus(event.getNewValue()));

        loadAllBookings();
        bookingTableView.setItems(bookings);

        adjustTableColumnWidths();
        System.out.println("BookingController: Initialized successfully.");
    }

    private void loadAllBookings() {
        bookings.setAll(DatabaseConnection.getBookings());
        System.out.println("BookingController: Loaded bookings = " + bookings.size());
        statusLabel.setText("All bookings loaded (" + bookings.size() + " bookings)");
    }

    private void adjustTableColumnWidths() {
        double widthPercentage = 1.0 / bookingTableView.getColumns().size();
        bookingTableView.getColumns().forEach(column ->
                column.prefWidthProperty().bind(bookingTableView.widthProperty().multiply(widthPercentage))
        );
    }

    @FXML
    private void handleHeldBooking() {
        Booking selected = bookingTableView.getSelectionModel().getSelectedItem();
        if (selected != null && !selected.getStatus().equalsIgnoreCase("held")) {
            selected.setStatus("held");
            bookingTableView.refresh();
            statusLabel.setText("Booking marked as held for: " + selected.getEventName());
        } else {
            statusLabel.setText("Select a booking to mark as held.");
        }
    }

    @FXML
    private void handleConfirmedBooking() {
        Booking selected = bookingTableView.getSelectionModel().getSelectedItem();
        if (selected != null && !selected.getStatus().equalsIgnoreCase("confirmed")) {
            selected.setStatus("confirmed");
            bookingTableView.refresh();
            statusLabel.setText("Booking confirmed for: " + selected.getEventName());
        } else {
            statusLabel.setText("Select a booking to mark as confirmed.");
        }
    }

    @FXML
    private void handleCancelledBooking() {
        Booking selected = bookingTableView.getSelectionModel().getSelectedItem();
        if (selected != null && !selected.getStatus().equalsIgnoreCase("cancelled")) {
            selected.setStatus("cancelled");
            bookingTableView.refresh();
            statusLabel.setText("Booking cancelled for: " + selected.getEventName());
        } else {
            statusLabel.setText("Select a booking to mark as cancelled.");
        }
    }

    @FXML
    private void handleAddBooking() {
        Dialog<Booking> dialog = new Dialog<>();
        dialog.setTitle("Add New Booking");
        dialog.setHeaderText("Enter details for the new booking");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        List<Client> clients = DatabaseConnection.getAllClients();
        List<Event> events = DatabaseConnection.getAllEvents();
        List<Room> rooms = DatabaseConnection.getAllRooms();

        ComboBox<Client> clientBox = new ComboBox<>(FXCollections.observableArrayList(clients));
        clientBox.setPromptText("Select Client");

        ComboBox<Event> eventBox = new ComboBox<>(FXCollections.observableArrayList(events));
        eventBox.setPromptText("Select Event");

        ComboBox<Room> roomBox = new ComboBox<>(FXCollections.observableArrayList(rooms));
        roomBox.setPromptText("Select Room");

        DatePicker datePicker = new DatePicker(LocalDate.now());
        ComboBox<Timeslot> timeslotBox = new ComboBox<>();
        timeslotBox.setPromptText("Select Timeslot");

        Runnable updateTimeslots = () -> {
            timeslotBox.getItems().clear();
            if (datePicker.getValue() != null && roomBox.getValue() != null) {
                String venueType = "Room";
                String venueName = roomBox.getValue().getName();
                if (venueName.equals("Main Hall") || venueName.equals("Small Hall") || venueName.equals("Rehearsal Space")) {
                    venueType = "Performance Space";
                }
                List<Timeslot> timeslots = DatabaseConnection.getAvailableTimeslots(venueType, venueName, datePicker.getValue());
                timeslotBox.setItems(FXCollections.observableArrayList(timeslots));
            }
        };

        datePicker.valueProperty().addListener((obs, oldVal, newVal) -> updateTimeslots.run());
        roomBox.valueProperty().addListener((obs, oldVal, newVal) -> updateTimeslots.run());

        ComboBox<String> statusBox = new ComboBox<>();
        statusBox.getItems().addAll("held", "confirmed", "cancelled");
        statusBox.setValue("held");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Client:"), 0, 0);
        grid.add(clientBox, 1, 0);
        grid.add(new Label("Event:"), 0, 1);
        grid.add(eventBox, 1, 1);
        grid.add(new Label("Room:"), 0, 2);
        grid.add(roomBox, 1, 2);
        grid.add(new Label("Date:"), 0, 3);
        grid.add(datePicker, 1, 3);
        grid.add(new Label("Timeslot:"), 0, 4);
        grid.add(timeslotBox, 1, 4);
        grid.add(new Label("Status:"), 0, 5);
        grid.add(statusBox, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                Client selectedClient = clientBox.getValue();
                Event selectedEvent = eventBox.getValue();
                Room selectedRoom = roomBox.getValue();
                Timeslot selectedTimeslot = timeslotBox.getValue();

                if (selectedClient == null || selectedRoom == null || selectedTimeslot == null) {
                    statusLabel.setText("Please select a client, room, and timeslot.");
                    return null;
                }

                int roomId = selectedRoom.getRoomID();
                if (roomId <= 0) {
                    statusLabel.setText("Invalid room selected: room ID must be greater than 0.");
                    return null;
                }

                String venueType = "Room";
                String venueName = selectedRoom.getName();
                if (venueName.equals("Main Hall") || venueName.equals("Small Hall") || venueName.equals("Rehearsal Space")) {
                    venueType = "Performance Space";
                }
                List<Timeslot> availableTimeslots = DatabaseConnection.getAvailableTimeslots(venueType, venueName, datePicker.getValue());
                boolean isTimeslotAvailable = availableTimeslots.stream().anyMatch(slot ->
                        slot.getStartTime().equals(selectedTimeslot.getStartTime()) &&
                                slot.getEndTime().equals(selectedTimeslot.getEndTime()) &&
                                slot.getRateType().equals(selectedTimeslot.getRateType())
                );

                if (!isTimeslotAvailable) {
                    statusLabel.setText("Selected timeslot is no longer available. Please choose another timeslot.");
                    return null;
                }

                System.out.println("Creating booking with room_id: " + roomId + " (Room: " + selectedRoom.getName() + ")");

                Booking newBooking = new Booking(
                        0,
                        selectedClient.getClientID(),
                        selectedEvent != null ? selectedEvent.getEventId() : 0,
                        roomId,
                        datePicker.getValue().toString(),
                        selectedTimeslot.getStartTime(),
                        selectedTimeslot.getEndTime(),
                        selectedTimeslot.getCost(),
                        statusBox.getValue()
                );

                newBooking.setClientName(selectedClient.getName());
                newBooking.setEventName(selectedEvent != null ? selectedEvent.getName() : "");
                newBooking.setRoomName(selectedRoom.getName());

                return newBooking;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(booking -> {
            if (booking != null) {
                try {
                    bookings.add(booking);
                    List<Booking> newBookingList = new ArrayList<>();
                    newBookingList.add(booking);
                    DatabaseConnection.pushBookingEditsToDatabase(newBookingList);
                    loadAllBookings();
                    statusLabel.setText("New booking added: " + booking.getEventName());
                } catch (RuntimeException e) {
                    bookings.remove(booking);
                    statusLabel.setText("Failed to add booking: " + e.getMessage());
                }
            }
        });
    }

    @FXML
    private void handleFinishEdit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Edit");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you want to push these edits to the database?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                List<Booking> bookingsToUpdate = new ArrayList<>();
                for (Booking booking : bookingTableView.getItems()) {
                    // Only update bookings that already exist in the database (booking_id > 0)
                    if (booking.getBookingID() > 0) {
                        bookingsToUpdate.add(booking);
                    }
                }

                if (bookingsToUpdate.isEmpty()) {
                    statusLabel.setText("No bookings to update.");
                    return;
                }

                try {
                    DatabaseConnection.pushBookingEditsToDatabase(bookingsToUpdate);
                    loadAllBookings();
                    statusLabel.setText("Bookings updated successfully.");
                } catch (RuntimeException e) {
                    System.out.println("Error updating bookings: " + e.getMessage());
                    statusLabel.setText("Error updating bookings: " + e.getMessage());
                }
            }
        });
    }

    @FXML
    private void handleBackToMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) bookingTableView.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    public void handleBackToCalendar(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Calendar.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) bookingTableView.getScene().getWindow();
        stage.setScene(scene);
    }
}