package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class MenuController {

    private void switchScene(String fxml, ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/" + fxml));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void goToContract(ActionEvent event) throws Exception {
        switchScene("Contract.fxml", event);
    }
    @FXML
    private void goToInterTeam(ActionEvent event) throws Exception {
        switchScene("InterTeamView.fxml", event);
    }

    @FXML
    private void goToFinances(ActionEvent event) throws Exception {
        switchScene("Finances.fxml", event);
    }

    @FXML
    private void goToReviews(ActionEvent event) throws Exception {
        switchScene("Reviews.fxml", event);
    }

    @FXML
    private void goToDailyPlanner(ActionEvent event) throws Exception {
        switchScene("DailyPlanner.fxml", event);
    }

    @FXML
    private void goToSeating(ActionEvent event) throws Exception {
        switchScene("Seating.fxml", event);
    }

    @FXML
    private void goToCalendar(ActionEvent event) throws Exception {
        switchScene("Calendar.fxml", event);
    }

    @FXML
    private void goToUsageTracker(ActionEvent event) throws Exception {
        switchScene("UsageTracker.fxml", event);
    }

    @FXML
    private void goToSystemSettings(ActionEvent event) throws Exception {
        switchScene("SystemSettings.fxml", event);
    }
}
