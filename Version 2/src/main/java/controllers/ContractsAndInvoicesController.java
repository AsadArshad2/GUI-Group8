package controllers;

import DatabaseLogic.Client;
import DatabaseLogic.Contract;
import DatabaseLogic.DatabaseConnection;
import DatabaseLogic.Event;
import DatabaseLogic.Invoice;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;
import java.util.List;

public class ContractsAndInvoicesController {

    @FXML private TableView<Contract> contractsTable;
    @FXML private TableColumn<Contract, String> clientNameColumn;
    @FXML private TableColumn<Contract, String> descriptionColumn;
    @FXML private TableColumn<Contract, String> contractDateColumn;

    @FXML private TableView<Invoice> invoicesTable;
    @FXML private TableColumn<Invoice, String> invoiceClientNameColumn;
    @FXML private TableColumn<Invoice, String> invoiceDateColumn;
    @FXML private TableColumn<Invoice, String> costDescriptionColumn;
    @FXML private TableColumn<Invoice, Double> totalCostColumn;
    @FXML private TableColumn<Invoice, String> paidStatusColumn;

    // Removed @FXML private ComboBox<Client> clientSelector;

    @FXML
    public void initialize() {
        System.out.println("ContractsAndInvoicesController: Initializing...");
        System.out.println("ContractsAndInvoicesController: Stylesheet URL = " + getClass().getResource("/styles/style.css"));

        // Clear any existing columns in the Contracts table to prevent extra columns
        contractsTable.getColumns().clear();

        // Initialize Contracts Table with exactly three columns
        clientNameColumn.setCellValueFactory(cellData -> cellData.getValue().getClientNameProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        contractDateColumn.setCellValueFactory(cellData -> cellData.getValue().getContractDateProperty());

        // Add the columns to the table
        contractsTable.getColumns().addAll(clientNameColumn, descriptionColumn, contractDateColumn);

        // Set column resize policy to ensure only defined columns are used
        contractsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Debug: Log the number of columns
        System.out.println("Contracts Table Columns: " + contractsTable.getColumns().size());

        // Initialize Invoices Table
        invoiceClientNameColumn.setCellValueFactory(cellData -> cellData.getValue().getClientNameProperty());
        invoiceDateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        costDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getCostDescriptionProperty());
        totalCostColumn.setCellValueFactory(cellData -> cellData.getValue().getTotalCostProperty().asObject());
        paidStatusColumn.setCellValueFactory(cellData -> cellData.getValue().getPaidStatusProperty());

        // Removed client selector population
        // List<Client> clients = DatabaseConnection.getAllClients();
        // clientSelector.setItems(FXCollections.observableArrayList(clients));

        // Load data
        loadContracts();
        loadInvoices();
    }

    private void loadContracts() {
        List<Contract> contracts = DatabaseConnection.getAllContractsWithClientName();
        contractsTable.setItems(FXCollections.observableArrayList(contracts));
        contractsTable.refresh(); // Force a refresh to ensure the UI updates

        // Debug: Log the number of columns after loading data
        System.out.println("Contracts Table Columns after loading data: " + contractsTable.getColumns().size());
    }

    private void loadInvoices() {
        List<Invoice> invoices = DatabaseConnection.getAllInvoicesWithClientName();
        invoicesTable.setItems(FXCollections.observableArrayList(invoices));
    }

    @FXML
    private void createContract() {
        Dialog<Contract> dialog = new Dialog<>();
        dialog.setTitle("Create New Contract");
        dialog.setHeaderText("Enter details for the new contract");

        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        // Fetch clients and events for dropdowns
        List<Client> clients = DatabaseConnection.getAllClients();
        ComboBox<Client> clientComboBox = new ComboBox<>(FXCollections.observableArrayList(clients));
        clientComboBox.setPromptText("Select Client");

        List<Event> events = DatabaseConnection.getAllEvents();
        ComboBox<Event> eventComboBox = new ComboBox<>(FXCollections.observableArrayList(events));
        eventComboBox.setPromptText("Select Event");

        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Description");
        descriptionField.setPrefRowCount(3);

        TextField contractDateField = new TextField();
        contractDateField.setPromptText("Contract Date (YYYY-MM-DD)");
        contractDateField.setText(LocalDate.now().toString()); // Default to today

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Event:"), 0, 0);
        grid.add(eventComboBox, 1, 0);
        grid.add(new Label("Client:"), 0, 1);
        grid.add(clientComboBox, 1, 1);
        grid.add(new Label("Description:"), 0, 2);
        grid.add(descriptionField, 1, 2);
        grid.add(new Label("Contract Date:"), 0, 3);
        grid.add(contractDateField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                try {
                    Event selectedEvent = eventComboBox.getSelectionModel().getSelectedItem();
                    Client selectedClient = clientComboBox.getSelectionModel().getSelectedItem();
                    String description = descriptionField.getText();
                    String contractDate = contractDateField.getText();

                    if (selectedEvent == null || selectedClient == null ||
                            description == null || description.trim().isEmpty() ||
                            contractDate == null || contractDate.trim().isEmpty()) {
                        throw new IllegalArgumentException("All fields must be filled.");
                    }

                    // Validate date format (YYYY-MM-DD)
                    LocalDate.parse(contractDate); // Will throw exception if format is invalid

                    return new Contract(0, selectedEvent.getEventId(), selectedClient.getClientID(), description, contractDate);
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText(null);
                    alert.setContentText("Please ensure all fields are filled correctly: " + e.getMessage());
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(contract -> {
            try {
                DatabaseConnection.createContract(
                        contract.getEventId(),
                        contract.getClientId(),
                        contract.getDescription(),
                        contract.getContractDate()
                );
                loadContracts(); // Refresh the table
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Contract created successfully.");
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to create contract: " + e.getMessage());
                alert.showAndWait();
            }
        });
    }

    @FXML
    private void generateInvoice() {
        try {
            DatabaseConnection.generateInvoicesForAllUninvoicedBookings();
            loadInvoices(); // Refresh the table
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invoices Generated");
            alert.setHeaderText(null);
            alert.setContentText("Invoices have been generated for all uninvoiced bookings.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to generate invoices: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void goBackToMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) contractsTable.getScene().getWindow();
        stage.setScene(scene);
        System.out.println("Navigating back to main menu...");
    }
}