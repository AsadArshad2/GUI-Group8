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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingController {
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

        configurationDetails.setCellValueFactory(cellData -> cellData.getValue().getConfigurationDetailsProperty());
        configurationDetails.setCellFactory(TextFieldTableCell.forTableColumn());
        configurationDetails.setOnEditCommit(event -> event.getRowValue().setConfigurationDetails(event.getNewValue()));

        status.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
        status.setCellFactory(TextFieldTableCell.forTableColumn());
        status.setOnEditCommit(event -> event.getRowValue().setStatus(event.getNewValue()));

        loadAllBookings();
        bookingTableView.setItems(bookings);

        adjustTableColumnWidths();
    }

    private void loadAllBookings() {
        bookings.setAll(DatabaseConnection.getBookings());
        statusLabel.setText("All bookings loaded (" + bookings.size() + " bookings)");
    }

    private void adjustTableColumnWidths() {
        double widthPercentage = 1.0 / bookingTableView.getColumns().size();
        bookingTableView.getColumns().forEach(column ->
                column.prefWidthProperty().bind(bookingTableView.widthProperty().multiply(widthPercentage))
        );
    }

    public static void pushEditsToDatabase(List<Booking> bookings) {
        String updateSQL = "UPDATE Bookings SET date = ?, start_time = ?, end_time = ?, total_cost = ?, " +
                "configuration_details = ?, status = ? WHERE booking_id = ?";

        String insertSQL = "INSERT INTO Bookings (client_id, event_id, room_id, date, start_time, end_time, total_cost, configuration_details, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connectToDatabase();
             PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {

            for (Booking b : bookings) {
                if (b.getBookingID() > 0) {
                    // Update existing booking
                    updateStmt.setString(1, b.getDate());
                    updateStmt.setString(2, b.getStartTime());
                    updateStmt.setString(3, b.getEndTime());
                    updateStmt.setDouble(4, b.getTotalCost());
                    updateStmt.setString(5, b.getConfigurationDetails());
                    updateStmt.setString(6, b.getStatus());
                    updateStmt.setInt(7, b.getBookingID());
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

            System.out.println("All changes pushed to database.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to push changes: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddBooking() {
        Dialog<Booking> dialog = new Dialog<>();
        dialog.setTitle("Add New Booking");
        dialog.setHeaderText("Enter details for the new booking");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        TextField clientIdField = new TextField();
        clientIdField.setPromptText("Client ID");

        TextField eventIdField = new TextField();
        eventIdField.setPromptText("Event ID");

        TextField roomIdField = new TextField();
        roomIdField.setPromptText("Room ID");

        TextField dateField = new TextField(LocalDate.now().toString());
        TextField startTimeField = new TextField("09:00");
        TextField endTimeField = new TextField("17:00");
        TextField costField = new TextField("0.0");
        TextField configField = new TextField();

        ComboBox<String> statusBox = new ComboBox<>();
        statusBox.getItems().addAll("held", "confirmed", "cancelled");
        statusBox.setValue("held");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Client ID:"), 0, 0);
        grid.add(clientIdField, 1, 0);
        grid.add(new Label("Event ID:"), 0, 1);
        grid.add(eventIdField, 1, 1);
        grid.add(new Label("Room ID:"), 0, 2);
        grid.add(roomIdField, 1, 2);
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
                try {
                    int clientId = Integer.parseInt(clientIdField.getText());
                    int eventId = Integer.parseInt(eventIdField.getText());
                    int roomId = Integer.parseInt(roomIdField.getText());
                    double cost = Double.parseDouble(costField.getText());

                    // Fetch client name and event name for display
                    String clientName = DatabaseConnection.getClientNameById(clientId);
                    String eventName = DatabaseConnection.getEventNameById(eventId);

                    if (clientName.isEmpty() || eventName.isEmpty()) {
                        statusLabel.setText("Invalid Client ID or Event ID.");
                        return null;
                    }

                    Booking newBooking = new Booking(
                            0, // booking_id = 0 → INSERT
                            clientId,
                            eventId,
                            roomId,
                            dateField.getText(),
                            startTimeField.getText(),
                            endTimeField.getText(),
                            cost,
                            configField.getText(),
                            statusBox.getValue()
                    );

                    // Set the client name and event name for display in the table
                    newBooking.setClientName(clientName);
                    newBooking.setEventName(eventName);

                    return newBooking;
                } catch (NumberFormatException e) {
                    statusLabel.setText("Please enter valid numeric values for IDs and cost.");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(booking -> {
            if (booking != null) {
                bookings.add(booking); // Adds to observable table
                statusLabel.setText("New booking added.");
                loadAllBookings(); // Refresh the table to show all bookings
            }
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
                loadAllBookings(); // Refresh the table after saving changes
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