package controllers;

import DatabaseLogic.*;
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
import javafx.util.converter.IntegerStringConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarController {
    @FXML private DatePicker datePicker;
    @FXML private TableView<Calendar> calendarTableView;
    @FXML private TableColumn<Calendar, String> eventNameColumn;
    @FXML private TableColumn<Calendar, String> roomNameColumn;
    @FXML private TableColumn<Calendar, String> startTimeColumn;
    @FXML private TableColumn<Calendar, String> endTimeColumn;
    @FXML private TableColumn<Calendar, Double> totalCostColumn;
    @FXML private TableColumn<Calendar, String> statusColumn;
    @FXML private TableColumn<Calendar, Integer> capacityColumn;
    @FXML private Label statusLabel;

    private List<Calendar> calendarList = DatabaseConnection.getAllCalendarBookings();
    private ObservableList<Calendar> calendarBookings = FXCollections.observableArrayList();

    public void initialize() {
        calendarTableView.setEditable(true);
        calendarTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        eventNameColumn.setCellValueFactory(cellData -> cellData.getValue().getEventNameProperty());
        eventNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        eventNameColumn.setOnEditCommit(event -> event.getRowValue().setEventName(event.getNewValue()));

        roomNameColumn.setCellValueFactory(cellData -> cellData.getValue().getRoomNameProperty());
        roomNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        roomNameColumn.setOnEditCommit(event -> event.getRowValue().setRoomName(event.getNewValue()));

        startTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getStartTimeProperty());
        startTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        startTimeColumn.setOnEditCommit(event -> event.getRowValue().setStartTime(event.getNewValue()));

        endTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getEndTimeProperty());
        endTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        endTimeColumn.setOnEditCommit(event -> event.getRowValue().setEndTime(event.getNewValue()));

        totalCostColumn.setCellValueFactory(cellData -> cellData.getValue().getTotalCostProperty().asObject());
        totalCostColumn.setCellFactory(TextFieldTableCell.<Calendar, Double>forTableColumn(new DoubleStringConverter()));
        totalCostColumn.setOnEditCommit(event -> event.getRowValue().setTotalCost(event.getNewValue()));

        statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
        statusColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        statusColumn.setOnEditCommit(event -> event.getRowValue().setStatus(event.getNewValue()));

        capacityColumn.setCellValueFactory(cellData -> cellData.getValue().getCapacityProperty().asObject());
        capacityColumn.setCellFactory(TextFieldTableCell.<Calendar, Integer>forTableColumn(new IntegerStringConverter()));
        capacityColumn.setOnEditCommit(event -> event.getRowValue().setCapacity(event.getNewValue()));

        calendarTableView.setItems(calendarBookings);
        datePicker.setValue(LocalDate.now());

        refreshBookings(LocalDate.now());
    }

    private void refreshBookings(LocalDate selectedDate) {
        List<Calendar> latestBookingsFromDB = DatabaseConnection.getAllCalendarBookings();

        List<Calendar> filteredBookings = latestBookingsFromDB.stream()
                .filter(b -> LocalDate.parse(b.getDate()).equals(selectedDate))
                .collect(Collectors.toList());

        calendarBookings.setAll(filteredBookings);
        statusLabel.setText("Availability loaded for " + selectedDate);

        adjustTableColumnWidths();
    }

    private void adjustTableColumnWidths() {
        double widthPercentage = 1.0 / calendarTableView.getColumns().size();
        calendarTableView.getColumns().forEach(column ->
                column.prefWidthProperty().bind(calendarTableView.widthProperty().multiply(widthPercentage))
        );
    }

    public static void pushEditsToDatabase(List<Calendar> calendarBookings) {
        String updateSQL = "UPDATE Bookings SET start_time = ?, end_time = ?, total_cost = ?, " +
                "status = ? WHERE booking_id = ?";

        String updateRoomsSQL = "UPDATE Rooms SET capacity = ? WHERE room_id = ?";

        String updateEventsSQL = "UPDATE Event_details SET name = ? WHERE event_id = ?";

        try (Connection conn = DatabaseConnection.connectToDatabase();
             PreparedStatement updateBookingsStmt = conn.prepareStatement(updateSQL);
             PreparedStatement updateRoomsStmt = conn.prepareStatement(updateRoomsSQL);
             PreparedStatement updateEventsStmt = conn.prepareStatement(updateEventsSQL)) {

            for (Calendar c : calendarBookings) {
                if (c.getBookingID() > 0) {
                    // Update Bookings table
                    updateBookingsStmt.setString(1, c.getStartTime());
                    updateBookingsStmt.setString(2, c.getEndTime());
                    updateBookingsStmt.setDouble(3, c.getTotalCost());
                    updateBookingsStmt.setString(4, c.getStatus());
                    updateBookingsStmt.setInt(5, c.getBookingID());
                    updateBookingsStmt.addBatch();

                    // Update Rooms table
                    updateRoomsStmt.setInt(1, c.getCapacity());
                    updateRoomsStmt.setInt(2, c.getRoomID());
                    updateRoomsStmt.addBatch();

                    // Update Event_details table
                    updateEventsStmt.setString(1, c.getEventName());
                    updateEventsStmt.setInt(2, c.getEventID());
                    updateEventsStmt.addBatch();
                }
            }

            updateBookingsStmt.executeBatch();
            updateRoomsStmt.executeBatch();
            updateEventsStmt.executeBatch();

            System.out.println("All changes pushed to database.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to push changes: " + e.getMessage());
        }
    }

    @FXML
    private void handleCheckBookings() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Booking.fxml"));
        if (loader.getLocation() == null) {
            throw new IllegalStateException("CheckBookings.fxml not found in /views/");
        }
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) calendarTableView.getScene().getWindow();
        stage.setScene(scene);
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

        List<Calendar> filteredBookings = calendarList.stream()
                .filter(booking -> LocalDate.parse(booking.getDate()).equals(selected))
                .collect(Collectors.toList());

        calendarBookings.setAll(filteredBookings);
        statusLabel.setText("Availability loaded for " + selected);
    }

    public void handleAddBooking() {
        Dialog<Booking> dialog = new Dialog<>();
        dialog.setTitle("Add New Booking");
        dialog.setHeaderText("Enter details for the new booking");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Fetch from DB
        List<Client> clients = DatabaseConnection.getAllClients();
        List<Event> events = DatabaseConnection.getAllEvents();
        List<Room> rooms = DatabaseConnection.getAllRooms();

        ComboBox<Client> clientBox = new ComboBox<>(FXCollections.observableArrayList(clients));
        clientBox.setPromptText("Select Client");

        ComboBox<Event> eventBox = new ComboBox<>(FXCollections.observableArrayList(events));
        eventBox.setPromptText("Select Event");

        ComboBox<Room> roomBox = new ComboBox<>(FXCollections.observableArrayList(rooms));
        roomBox.setPromptText("Select Room");

        TextField dateField = new TextField(LocalDate.now().toString());
        TextField startTimeField = new TextField("09:00");
        TextField endTimeField = new TextField("17:00");
        TextField costField = new TextField();
        TextField configField = new TextField();

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
        grid.add(dateField, 1, 3);
        grid.add(new Label("Start Time:"), 0, 4);
        grid.add(startTimeField, 1, 4);
        grid.add(new Label("End Time:"), 0, 5);
        grid.add(endTimeField, 1, 5);
        grid.add(new Label("Cost:"), 0, 6);
        grid.add(costField, 1, 6);
        grid.add(new Label("Configuration:"), 0, 7);
        grid.add(configField, 1, 7);
        grid.add(new Label("Status:"), 0, 8);
        grid.add(statusBox, 1, 8);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                Client selectedClient = clientBox.getValue();
                Event selectedEvent = eventBox.getValue();
                Room selectedRoom = roomBox.getValue();

                if (selectedClient == null || selectedEvent == null || selectedRoom == null) return null;

                Booking newBooking = new Booking(
                        0, // booking_id = 0 → INSERT
                        selectedClient.getClientID(),
                        selectedEvent.getEventId(),
                        selectedRoom.getRoomID(),
                        dateField.getText(),
                        startTimeField.getText(),
                        endTimeField.getText(),
                        Double.parseDouble(costField.getText()),
                        configField.getText(),
                        statusBox.getValue()
                );

                // Set names so they appear in the table
                newBooking.setClientName(selectedClient.getName());
                newBooking.setEventName(selectedEvent.getName());

                return newBooking;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(booking -> {
            //bookings.add(booking); // Adds to observable table
            statusLabel.setText("New booking added.");
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
                pushEditsToDatabase(new ArrayList<>(calendarTableView.getItems()));
            }
        });
    }

    @FXML
    private void handleBackToMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) calendarTableView.getScene().getWindow();
        stage.setScene(scene);
    }
}