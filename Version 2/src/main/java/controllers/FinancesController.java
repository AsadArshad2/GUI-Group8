package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class FinancesController {
    @FXML private TextField filmTitleField;
    @FXML private TextField filmCostField;
    @FXML private TextField ticketSalesField;
    @FXML private TextArea financeOutput;

    @FXML
    private void handleLogCost() {
        String title = filmTitleField.getText();
        String cost = filmCostField.getText();
        financeOutput.appendText("Logged Film Cost → " + title + ": £" + cost + "\n");
    }

    @FXML
    private void handleRetrieveSales() {
        String title = ticketSalesField.getText();
        financeOutput.appendText("Retrieved ticket sales for " + title + ": £" + ((int)(Math.random() * 2000) + 500) + "\n");
    }

    @FXML
    private void handleBackToMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) filmTitleField.getScene().getWindow();
        stage.setScene(scene);
    }
}
