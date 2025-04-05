package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ContractController {

    @FXML private TextField clientNameField;
    @FXML private TextField eventNameField;
    @FXML private DatePicker eventDatePicker;
    @FXML private TextField venueCostField;
    @FXML private TextArea contractDetailsField;

    @FXML
    private void handleCreateContract(ActionEvent event) {
        String clientName = clientNameField.getText().trim();
        String eventName = eventNameField.getText().trim();
        String venueCost = venueCostField.getText().trim();
        String contractDetails = contractDetailsField.getText().trim();

        if (clientName.isEmpty() || eventName.isEmpty() || venueCost.isEmpty()) {
            contractDetailsField.setText("Please fill in all required fields.");
            return;
        }

        try {
            double cost = Double.parseDouble(venueCost);
            // Here you can process saving the contract (e.g., database or file storage)
            contractDetailsField.setText("Contract for " + eventName + " created with " + clientName + ". Venue cost: £" + cost);
        } catch (NumberFormatException e) {
            contractDetailsField.setText("Invalid cost entered.");
        }
    }

    @FXML
    private void handleBackToMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) clientNameField.getScene().getWindow();
        stage.setScene(scene);
    }
}
