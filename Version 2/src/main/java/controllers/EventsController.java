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

public class EventsController {
    @FXML private TableView<Event> eventsTableView;
    @FXML private TableColumn<Event, String> name;
    @FXML private TableColumn<Event, Double> sellingPrice;
    @FXML private TableColumn<Event, String> startDate;
    @FXML private TableColumn<Event, String> endDate;
    @FXML private TableColumn<Event, String> description;
    @FXML private TableColumn<Event, Double> maxDiscount;
    @FXML private Label statusLabel;

    private ObservableList<Event> events = FXCollections.observableArrayList();

    public void initialize() {

        eventsTableView.setEditable(true);
        eventsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(event -> event.getRowValue().setName(event.getNewValue()));

        sellingPrice.setCellValueFactory(cellData -> cellData.getValue().sellingPriceProperty().asObject());
        sellingPrice.setCellFactory(TextFieldTableCell.<Event, Double>forTableColumn(new DoubleStringConverter()));
        sellingPrice.setOnEditCommit(event -> event.getRowValue().setSellingPrice(event.getNewValue()));

        startDate.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
        startDate.setCellFactory(TextFieldTableCell.forTableColumn());
        startDate.setOnEditCommit(event -> event.getRowValue().setStartDate(event.getNewValue()));

        endDate.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());
        endDate.setCellFactory(TextFieldTableCell.forTableColumn());
        endDate.setOnEditCommit(event -> event.getRowValue().setEndDate(event.getNewValue()));

        description.setCellValueFactory(cellData -> cellData.getValue().eventDescriptionProperty());
        description.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setOnEditCommit(event -> event.getRowValue().setEventDescription(event.getNewValue()));

        maxDiscount.setCellValueFactory(cellData -> cellData.getValue().maxDiscountProperty().asObject());
        maxDiscount.setCellFactory(TextFieldTableCell.<Event, Double>forTableColumn(new DoubleStringConverter()));
        maxDiscount.setOnEditCommit(event -> event.getRowValue().setMaxDiscount(event.getNewValue()));

        loadAllEvents();
        eventsTableView.setItems(events);

        adjustTableColumnWidths();
    }

    private void loadAllEvents() {
        events.setAll(DatabaseConnection.getAllEvents());
        statusLabel.setText("All events loaded (" + events.size() + " events)");
    }

    private void adjustTableColumnWidths() {
        double widthPercentage = 1.0 / eventsTableView.getColumns().size();
        eventsTableView.getColumns().forEach(column ->
                column.prefWidthProperty().bind(eventsTableView.widthProperty().multiply(widthPercentage))
        );
    }

    @FXML
    private void handleAddEvent() {
        Dialog<Event> dialog = new Dialog<>();
        dialog.setTitle("Add New Event");
        dialog.setHeaderText("Enter details for the new event");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        TextField nameField = new TextField();
        nameField.setPromptText("Event Name");

        TextField sellingPriceField = new TextField("0.0");
        sellingPriceField.setPromptText("Selling Price");

        TextField startDateField = new TextField(LocalDate.now().toString());
        startDateField.setPromptText("Start Date (YYYY-MM-DD)");

        TextField endDateField = new TextField(LocalDate.now().toString());
        endDateField.setPromptText("End Date (YYYY-MM-DD)");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Description");

        TextField maxDiscountField = new TextField("0.0");
        maxDiscountField.setPromptText("Max Discount");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Selling Price:"), 0, 1);
        grid.add(sellingPriceField, 1, 1);
        grid.add(new Label("Start Date:"), 0, 2);
        grid.add(startDateField, 1, 2);
        grid.add(new Label("End Date:"), 0, 3);
        grid.add(endDateField, 1, 3);
        grid.add(new Label("Description:"), 0, 4);
        grid.add(descriptionField, 1, 4);
        grid.add(new Label("Max Discount:"), 0, 5);
        grid.add(maxDiscountField, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    double sellingPrice = Double.parseDouble(sellingPriceField.getText());
                    double maxDiscount = Double.parseDouble(maxDiscountField.getText());

                    Event newEvent = new Event(
                            0, // event_id = 0 → INSERT
                            nameField.getText(),
                            sellingPrice,
                            startDateField.getText(),
                            endDateField.getText(),
                            descriptionField.getText(),
                            maxDiscount
                    );

                    return newEvent;
                } catch (NumberFormatException e) {
                    statusLabel.setText("Please enter valid numeric values for selling price and max discount.");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(event -> {
            if (event != null) {
                // Add to the observable list
                events.add(event);
                // Save the new event to the database immediately
                List<Event> newEventList = new ArrayList<>();
                newEventList.add(event);
                DatabaseConnection.pushEventEditsToDatabase(newEventList);
                // Refresh the table to show all events (including the new one with updated ID)
                loadAllEvents();
                statusLabel.setText("New event added: " + event.getName());
            }
        });
    }

    @FXML
    private void handleHeldEvent() {
        Event selected = eventsTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            statusLabel.setText("Event marked as held: " + selected.getName());
        } else {
            statusLabel.setText("Select an event to mark as held.");
        }
    }

    @FXML
    private void handleConfirmedEvent() {
        Event selected = eventsTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            statusLabel.setText("Event confirmed: " + selected.getName());
        } else {
            statusLabel.setText("Select an event to mark as confirmed.");
        }
    }

    @FXML
    private void handleCancelledEvent() {
        Event selected = eventsTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            statusLabel.setText("Event cancelled: " + selected.getName());
        } else {
            statusLabel.setText("Select an event to mark as cancelled.");
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
                DatabaseConnection.pushEventEditsToDatabase(new ArrayList<>(eventsTableView.getItems()));
                loadAllEvents(); // Refresh the table after saving changes
            }
        });
    }

    @FXML
    private void handleBackToMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) eventsTableView.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    public void handleBackToCalendar(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Calendar.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) eventsTableView.getScene().getWindow();
        stage.setScene(scene);
    }
}