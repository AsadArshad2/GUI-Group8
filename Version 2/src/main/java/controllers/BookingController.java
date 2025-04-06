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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingController {
    @FXML private DatePicker datePicker;
    @FXML private TableView<Booking> bookingTableView;
    @FXML private TableColumn<Booking, String> clientName;
    @FXML private TableColumn<Booking, String> eventName;
    @FXML private TableColumn<Booking, String> startTime;
    @FXML private TableColumn<Booking, String> endTime;
    @FXML private TableColumn<Booking, Double> totalCost;
    @FXML private TableColumn<Booking, String> configurationDetails;
    @FXML private TableColumn<Booking, String> status;
    @FXML private Label statusLabel;

    private List<Booking> bookingsLIST = DatabaseConnection.getBookings();
    private ObservableList<Booking> bookings = FXCollections.observableArrayList();

    public void initialize() {
        bookingTableView.setEditable(true);
        bookingTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        clientName.setCellValueFactory(cellData -> cellData.getValue().getClientNameProperty());
        clientName.setCellFactory(TextFieldTableCell.forTableColumn());
        clientName.setOnEditCommit(event -> event.getRowValue().setClientName(event.getNewValue()));

        eventName.setCellValueFactory(cellData -> cellData.getValue().getEventNameProperty());
        eventName.setCellFactory(TextFieldTableCell.forTableColumn());
        eventName.setOnEditCommit(event -> event.getRowValue().setEventName(event.getNewValue()));

        startTime.setCellValueFactory(cellData -> cellData.getValue().getStartTimeProperty());
        startTime.setCellFactory(TextFieldTableCell.forTableColumn());
        startTime.setOnEditCommit(event -> event.getRowValue().setStartTime(event.getNewValue()));

        endTime.setCellValueFactory(cellData -> cellData.getValue().getEndTimeProperty());
        endTime.setCellFactory(TextFieldTableCell.forTableColumn());
        endTime.setOnEditCommit(event -> event.getRowValue().setEndTime(event.getNewValue()));

        totalCost.setCellValueFactory(cellData -> cellData.getValue().getTotalCostProperty().asObject());
        totalCost.setCellFactory(TextFieldTableCell.<Booking, Double>forTableColumn(new DoubleStringConverter()));
        totalCost.setOnEditCommit(event -> event.getRowValue().setTotalCost(event.getNewValue()));

        configurationDetails.setCellValueFactory(cellData -> cellData.getValue().getConfigurationDetailsProperty());
        configurationDetails.setCellFactory(TextFieldTableCell.forTableColumn());
        configurationDetails.setOnEditCommit(event -> event.getRowValue().setConfigurationDetails(event.getNewValue()));

        status.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
        status.setCellFactory(TextFieldTableCell.forTableColumn());
        status.setOnEditCommit(event -> event.getRowValue().setStatus(event.getNewValue()));

        bookingTableView.setItems(bookings);
        datePicker.setValue(LocalDate.now());

        refreshBookings(LocalDate.now());
    }

    private void refreshBookings(LocalDate selectedDate) {
        List<Booking> latestBookingsFromDB = DatabaseConnection.getBookings();

        List<Booking> filteredBookings = latestBookingsFromDB.stream()
                .filter(b -> LocalDate.parse(b.getDate()).equals(selectedDate))
                .collect(Collectors.toList());

        bookings.setAll(filteredBookings); // automatically refreshes TableView

        statusLabel.setText("Availability loaded for " + selectedDate);

        adjustTableColumnWidths();
    }

    private void adjustTableColumnWidths() {
        double widthPercentage = 1.0 / bookingTableView.getColumns().size();
        bookingTableView.getColumns().forEach(column ->
                column.prefWidthProperty().bind(bookingTableView.widthProperty().multiply(widthPercentage))
        );
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

        List<Booking> filteredBookings = bookingsLIST.stream()
                .filter(booking -> LocalDate.parse(booking.getDate()).equals(selected))
                .collect(Collectors.toList());

        bookings.setAll(filteredBookings);

        statusLabel.setText("Availability loaded for " + selected);
    }

    @FXML
    private void handleAddBooking() {
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
            bookings.add(booking); // Adds to observable table
            statusLabel.setText("New booking added.");
        });
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
    private void handleFinishEdit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Edit");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you want to push these edits to the database?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                pushEditsToDatabase(new ArrayList<>(bookingTableView.getItems()));
            }
        });
    }

    public static void pushEditsToDatabase(List<Booking> bookings) {
        String updateSQL = "UPDATE Bookings SET start_time = ?, end_time = ?, total_cost = ?, " +
                "configuration_details = ?, status = ? WHERE booking_id = ?";

        String insertSQL = "INSERT INTO Bookings (client_id, event_id, room_id, date, start_time, end_time, total_cost, configuration_details, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn =  DatabaseConnection.connectToDatabase();
             PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {

            for (Booking b : bookings) {
                if (b.getBookingID() > 0) {
                    // Update existing booking
                    updateStmt.setString(1, b.getStartTime());
                    updateStmt.setString(2, b.getEndTime());
                    updateStmt.setDouble(3, b.getTotalCost());
                    updateStmt.setString(4, b.getConfigurationDetails());
                    updateStmt.setString(5, b.getStatus());
                    updateStmt.setInt(6, b.getBookingID());
                    updateStmt.addBatch();
                } else {
                    // Insert new booking
                    insertStmt.setInt(1, b.getClientID());
                    insertStmt.setInt(2, b.getEventID());
                    insertStmt.setInt(3, b.getRoomID());
                    insertStmt.setString(4, b.getDate());
                    insertStmt.setString(5, b.getStartTime());
                    insertStmt.setString(6, b.getEndTime());
                    insertStmt.setDouble(7, b.getTotalCost());
                    insertStmt.setString(8, b.getConfigurationDetails());
                    insertStmt.setString(9, b.getStatus());
                    insertStmt.addBatch();
                }
            }

            updateStmt.executeBatch();
            insertStmt.executeBatch();

            System.out.println("✔ All changes pushed to database.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Failed to push changes: " + e.getMessage());
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

}
