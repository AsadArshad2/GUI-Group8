package controllers;

import DatabaseLogic.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

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

    @FXML
    private void handleFinishEdit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Edit");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you want to push these edits to the database?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                DatabaseConnection.pushCalendarEditsToDatabase(new ArrayList<>(calendarTableView.getItems()));
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
    private void handleCheckEvents() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Events.fxml"));
        if (loader.getLocation() == null) {
            throw new IllegalStateException("Events.fxml not found in /views/");
        }
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) calendarTableView.getScene().getWindow();
        stage.setScene(scene);
    }
}