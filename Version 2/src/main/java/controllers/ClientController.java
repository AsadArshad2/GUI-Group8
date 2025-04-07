package controllers;

import DatabaseLogic.Client;
import DatabaseLogic.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientController {

    @FXML private TableView<Client> clientTable;
    @FXML private TableColumn<Client, String> nameColumn;
    @FXML private TableColumn<Client, String> companyNameColumn;
    @FXML private TableColumn<Client, String> emailColumn;
    @FXML private TableColumn<Client, String> telephoneNumberColumn;
    @FXML private TableColumn<Client, String> streetAddressColumn;
    @FXML private TableColumn<Client, String> cityColumn;
    @FXML private TableColumn<Client, String> postcodeColumn;
    @FXML private TableColumn<Client, String> billingNameColumn;
    @FXML private TableColumn<Client, String> billingEmailColumn;
    @FXML private Label statusLabel;

    private ObservableList<Client> clients = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        System.out.println("ClientController: Initializing...");
        System.out.println("ClientController: Stylesheet URL = " + getClass().getResource("/styles/style.css"));

        clientTable.setEditable(true);

        // Set up table columns using property getters
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> event.getRowValue().setName(event.getNewValue()));

        companyNameColumn.setCellValueFactory(cellData -> cellData.getValue().getCompanyNameProperty());
        companyNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        companyNameColumn.setOnEditCommit(event -> event.getRowValue().setCompanyName(event.getNewValue()));

        emailColumn.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(event -> event.getRowValue().setEmail(event.getNewValue()));

        telephoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getTelephoneNumberProperty());
        telephoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        telephoneNumberColumn.setOnEditCommit(event -> event.getRowValue().setTelephoneNumber(event.getNewValue()));

        streetAddressColumn.setCellValueFactory(cellData -> cellData.getValue().getStreetAddressProperty());
        streetAddressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        streetAddressColumn.setOnEditCommit(event -> event.getRowValue().setStreetAddress(event.getNewValue()));

        cityColumn.setCellValueFactory(cellData -> cellData.getValue().getCityProperty());
        cityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        cityColumn.setOnEditCommit(event -> event.getRowValue().setCity(event.getNewValue()));

        postcodeColumn.setCellValueFactory(cellData -> cellData.getValue().getPostcodeProperty());
        postcodeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        postcodeColumn.setOnEditCommit(event -> event.getRowValue().setPostcode(event.getNewValue()));

        billingNameColumn.setCellValueFactory(cellData -> cellData.getValue().getBillingNameProperty());
        billingNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        billingNameColumn.setOnEditCommit(event -> event.getRowValue().setBillingName(event.getNewValue()));

        billingEmailColumn.setCellValueFactory(cellData -> cellData.getValue().getBillingEmailProperty());
        billingEmailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        billingEmailColumn.setOnEditCommit(event -> event.getRowValue().setBillingEmail(event.getNewValue()));

        loadAllClients();
        clientTable.setItems(clients);

        adjustTableColumnWidths();
        System.out.println("ClientController: Initialized successfully.");
    }

    private void loadAllClients() {
        clients.setAll(DatabaseConnection.getAllClients());
        System.out.println("ClientController: Loaded clients = " + clients.size());
        statusLabel.setText("All clients loaded (" + clients.size() + " clients)");
    }

    private void adjustTableColumnWidths() {
        // Dynamically adjust column widths based on content
        for (TableColumn<Client, ?> column : clientTable.getColumns()) {
            // Compute the width based on the header text
            Text headerText = new Text(column.getText());
            double headerWidth = headerText.getBoundsInLocal().getWidth() + 20; // Add padding

            // Compute the maximum width of the content in this column
            double maxContentWidth = headerWidth;
            for (Client client : clients) {
                String content = getContentForColumn(column, client);
                if (content != null) {
                    Text contentText = new Text(content);
                    double contentWidth = contentText.getBoundsInLocal().getWidth() + 20; // Add padding
                    maxContentWidth = Math.max(maxContentWidth, contentWidth);
                }
            }

            // Set the column width to the maximum of header and content width
            column.setPrefWidth(maxContentWidth);
        }

        // Ensure the TableView width accommodates all columns
        double totalWidth = clientTable.getColumns().stream()
                .mapToDouble(TableColumn::getPrefWidth)
                .sum();
        clientTable.setPrefWidth(totalWidth + 30); // Add extra space for borders/scrollbar
    }

    private String getContentForColumn(TableColumn<Client, ?> column, Client client) {
        if (column == nameColumn) return client.getName();
        if (column == companyNameColumn) return client.getCompanyName();
        if (column == emailColumn) return client.getEmail();
        if (column == telephoneNumberColumn) return client.getTelephoneNumber();
        if (column == streetAddressColumn) return client.getStreetAddress();
        if (column == cityColumn) return client.getCity();
        if (column == postcodeColumn) return client.getPostcode();
        if (column == billingNameColumn) return client.getBillingName();
        if (column == billingEmailColumn) return client.getBillingEmail();
        return "";
    }

    @FXML
    private void handleAddClient() {
        Dialog<Client> dialog = new Dialog<>();
        dialog.setTitle("Add New Client");
        dialog.setHeaderText("Enter details for the new client");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        TextField nameField = new TextField();
        nameField.setPromptText("Contact Name");

        TextField companyNameField = new TextField();
        companyNameField.setPromptText("Company Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField telephoneNumberField = new TextField();
        telephoneNumberField.setPromptText("Phone Number");

        TextField streetAddressField = new TextField();
        streetAddressField.setPromptText("Street Address");

        TextField cityField = new TextField();
        cityField.setPromptText("City");

        TextField postcodeField = new TextField();
        postcodeField.setPromptText("Postcode");

        TextField billingNameField = new TextField();
        billingNameField.setPromptText("Billing Name (Optional)");

        TextField billingEmailField = new TextField();
        billingEmailField.setPromptText("Billing Email (Optional)");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Contact Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Company Name:"), 0, 1);
        grid.add(companyNameField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Phone Number:"), 0, 3);
        grid.add(telephoneNumberField, 1, 3);
        grid.add(new Label("Street Address:"), 0, 4);
        grid.add(streetAddressField, 1, 4);
        grid.add(new Label("City:"), 0, 5);
        grid.add(cityField, 1, 5);
        grid.add(new Label("Postcode:"), 0, 6);
        grid.add(postcodeField, 1, 6);
        grid.add(new Label("Billing Name:"), 0, 7);
        grid.add(billingNameField, 1, 7);
        grid.add(new Label("Billing Email:"), 0, 8);
        grid.add(billingEmailField, 1, 8);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                String name = nameField.getText();
                String companyName = companyNameField.getText();
                String email = emailField.getText();
                String telephoneNumber = telephoneNumberField.getText();
                String streetAddress = streetAddressField.getText();
                String city = cityField.getText();
                String postcode = postcodeField.getText();
                String billingName = billingNameField.getText();
                String billingEmail = billingEmailField.getText();

                if (name == null || name.trim().isEmpty() ||
                        companyName == null || companyName.trim().isEmpty() ||
                        email == null || email.trim().isEmpty() ||
                        telephoneNumber == null || telephoneNumber.trim().isEmpty() ||
                        streetAddress == null || streetAddress.trim().isEmpty() ||
                        city == null || city.trim().isEmpty() ||
                        postcode == null || postcode.trim().isEmpty()) {
                    statusLabel.setText("Please fill in all required fields.");
                    return null;
                }

                return new Client(
                        0, // clientID will be set by AUTO_INCREMENT
                        name,
                        email,
                        companyName,
                        telephoneNumber,
                        streetAddress,
                        city,
                        postcode,
                        billingName.isEmpty() ? null : billingName,
                        billingEmail.isEmpty() ? null : billingEmail
                );
            }
            return null;
        });

        dialog.showAndWait().ifPresent(client -> {
            if (client != null) {
                try {
                    // Insert the new client into the database
                    String insertSQL = "INSERT INTO Clients (name, email, company_name, telephone_number, street_address, city, postcode, billing_name, billing_email) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    try (Connection conn = DatabaseConnection.connectToDatabase();
                         PreparedStatement stmt = conn.prepareStatement(insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        stmt.setString(1, client.getName());
                        stmt.setString(2, client.getEmail());
                        stmt.setString(3, client.getCompanyName());
                        stmt.setString(4, client.getTelephoneNumber());
                        stmt.setString(5, client.getStreetAddress());
                        stmt.setString(6, client.getCity());
                        stmt.setString(7, client.getPostcode());
                        if (client.getBillingName() == null) {
                            stmt.setNull(8, java.sql.Types.VARCHAR);
                        } else {
                            stmt.setString(8, client.getBillingName());
                        }
                        if (client.getBillingEmail() == null) {
                            stmt.setNull(9, java.sql.Types.VARCHAR);
                        } else {
                            stmt.setString(9, client.getBillingEmail());
                        }
                        stmt.executeUpdate();

                        // Retrieve the generated client ID
                        ResultSet generatedKeys = stmt.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            client.setClientID(generatedKeys.getInt(1));
                        }
                    }

                    clients.add(client);
                    loadAllClients();
                    statusLabel.setText("New client added: " + client.getName());
                } catch (SQLException e) {
                    System.out.println("Error adding client: " + e.getMessage());
                    statusLabel.setText("Failed to add client: " + e.getMessage());
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
                List<Client> clientsToUpdate = new ArrayList<>();
                for (Client client : clientTable.getItems()) {
                    // Only update clients that already exist in the database (client_id > 0)
                    if (client.getClientID() > 0) {
                        clientsToUpdate.add(client);
                    }
                }

                if (clientsToUpdate.isEmpty()) {
                    statusLabel.setText("No clients to update.");
                    return;
                }

                try {
                    // Update the clients in the database
                    String updateSQL = "UPDATE Clients SET name = ?, email = ?, company_name = ?, telephone_number = ?, " +
                            "street_address = ?, city = ?, postcode = ?, billing_name = ?, billing_email = ? " +
                            "WHERE client_id = ?";
                    try (Connection conn = DatabaseConnection.connectToDatabase();
                         PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
                        for (Client client : clientsToUpdate) {
                            stmt.setString(1, client.getName());
                            stmt.setString(2, client.getEmail());
                            stmt.setString(3, client.getCompanyName());
                            stmt.setString(4, client.getTelephoneNumber());
                            stmt.setString(5, client.getStreetAddress());
                            stmt.setString(6, client.getCity());
                            stmt.setString(7, client.getPostcode());
                            if (client.getBillingName() == null) {
                                stmt.setNull(8, java.sql.Types.VARCHAR);
                            } else {
                                stmt.setString(8, client.getBillingName());
                            }
                            if (client.getBillingEmail() == null) {
                                stmt.setNull(9, java.sql.Types.VARCHAR);
                            } else {
                                stmt.setString(9, client.getBillingEmail());
                            }
                            stmt.setInt(10, client.getClientID());
                            stmt.addBatch();
                        }
                        stmt.executeBatch();
                    }

                    loadAllClients();
                    statusLabel.setText("Clients updated successfully.");
                } catch (SQLException e) {
                    System.out.println("Error updating clients: " + e.getMessage());
                    statusLabel.setText("Error updating clients: " + e.getMessage());
                }
            }
        });
    }

    @FXML
    private void handleDeleteClient() {
        Client selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            statusLabel.setText("Please select a client to delete.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you want to delete the client: " + selected.getName() + "?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Delete the client from the database
                    String deleteSQL = "DELETE FROM Clients WHERE client_id = ?";
                    try (Connection conn = DatabaseConnection.connectToDatabase();
                         PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {
                        stmt.setInt(1, selected.getClientID());
                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected > 0) {
                            clients.remove(selected);
                            loadAllClients();
                            statusLabel.setText("Client deleted: " + selected.getName());
                        } else {
                            statusLabel.setText("Failed to delete client.");
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Error deleting client: " + e.getMessage());
                    statusLabel.setText("Failed to delete client: " + e.getMessage());
                }
            }
        });
    }

    @FXML
    private void handleBackToMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) clientTable.getScene().getWindow();
        stage.setScene(scene);
        System.out.println("Navigating back to main menu...");
    }
}