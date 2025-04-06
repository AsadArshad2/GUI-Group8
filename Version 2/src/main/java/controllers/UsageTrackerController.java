package controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class UsageTrackerController {

    @FXML
    private BarChart<String, Number> usageBarChart;

    @FXML
    private CategoryAxis monthAxis;

    @FXML
    private NumberAxis usageAxis;

    @FXML
    public void initialize() {
        monthAxis.setLabel("Month");
        usageAxis.setLabel("Usage (Hours)");

        XYChart.Series<String, Number> currentYear = new XYChart.Series<>();
        currentYear.setName("2025");

        // Example data – replace with database query or real stats
        currentYear.getData().add(new XYChart.Data<>("Jan", 120));
        currentYear.getData().add(new XYChart.Data<>("Feb", 98));
        currentYear.getData().add(new XYChart.Data<>("Mar", 134));
        currentYear.getData().add(new XYChart.Data<>("Apr", 160));
        currentYear.getData().add(new XYChart.Data<>("May", 80));

        usageBarChart.getData().add(currentYear);
    }

    @FXML
    private void goToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
