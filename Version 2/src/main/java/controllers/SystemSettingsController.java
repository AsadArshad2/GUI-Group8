package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class SystemSettingsController {

    @FXML
    private TextField usernameField;

    @FXML
    private CheckBox bookingAlertToggle;

    @FXML
    private CheckBox contractAlertToggle;

    @FXML
    private void handleAddUser() {
        String username = usernameField.getText();
        if (username.isEmpty()) {
            showAlert("Please enter a username.");
        } else {
            // TODO: Add logic to insert user into your system/database
            showAlert("User '" + username + "' added successfully.");
            usernameField.clear();
        }
    }

    @FXML
    private void handleBackup() {
        // TODO: Add real backup logic
        showAlert("System data backup completed.");
    }

    @FXML
    private void handleRestore() {
        // TODO: Add real restore logic
        showAlert("System data restored from backup.");
    }

    @FXML
    private void handleSaveAlerts() {
        boolean bookingAlerts = bookingAlertToggle.isSelected();
        boolean contractAlerts = contractAlertToggle.isSelected();

        // TODO: Save these preferences to config or DB
        showAlert("Alert preferences saved:\n"
                + "Booking Alerts: " + bookingAlerts + "\n"
                + "Contract Alerts: " + contractAlerts);
    }

    @FXML
    private void goToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("System Settings");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
