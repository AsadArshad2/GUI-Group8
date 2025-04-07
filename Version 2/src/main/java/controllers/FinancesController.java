package controllers;

import DatabaseLogic.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Tooltip;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FinancesController {

    @FXML private BarChart<String, Number> venueProfitsChart;
    @FXML private BarChart<String, Number> salesChart;
    @FXML private TextArea financeOutput;

    @FXML private TableView<VenueProfitSummary> venueSummaryTable;
    @FXML private TableColumn<VenueProfitSummary, String> venueNameCol;
    @FXML private TableColumn<VenueProfitSummary, Number> totalProfitCol;
    @FXML private TableColumn<VenueProfitSummary, Number> seatsSoldCol;
    @FXML private TableColumn<VenueProfitSummary, Number> priceCol;
    @FXML private TableColumn<VenueProfitSummary, Number> revenueCol;
    @FXML private TableColumn<VenueProfitSummary, Number> profitCol;

    private final ObservableList<VenueProfitSummary> summaryData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set chart titles
        venueProfitsChart.setTitle("Venue Profits");
        salesChart.setTitle("Ticket Revenue");

        // Style the charts for better visibility
        venueProfitsChart.setLegendVisible(false);
        salesChart.setLegendVisible(false);
        venueProfitsChart.setBarGap(5);
        salesChart.setBarGap(5);

        // Initialize table columns
        venueNameCol.setCellValueFactory(new PropertyValueFactory<>("venueName"));
        totalProfitCol.setCellValueFactory(new PropertyValueFactory<>("totalProfit"));
        seatsSoldCol.setCellValueFactory(new PropertyValueFactory<>("ticketsSold"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        revenueCol.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        profitCol.setCellValueFactory(new PropertyValueFactory<>("profit"));
        venueSummaryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        venueSummaryTable.setPlaceholder(new Label("No content available"));

        // Set table data
        venueSummaryTable.setItems(summaryData);

        // Load venue profits
        loadVenueProfits();
    }

    private void loadVenueProfits() {
        // Fetch venue profits from the database (now excludes cancelled bookings)
        List<VenueProfitSummary> venueProfits = DatabaseConnection.getVenueProfits();

        // Clear existing data to prevent duplicates
        summaryData.clear();
        venueProfitsChart.getData().clear();
        salesChart.getData().clear();

        // Use a Set to ensure uniqueness based on venue name
        Set<String> seenVenues = new HashSet<>();
        for (VenueProfitSummary summary : venueProfits) {
            if (seenVenues.add(summary.getVenueName())) {
                summaryData.add(summary);
            } else {
                System.out.println("Duplicate venue found and skipped: " + summary.getVenueName());
            }
        }

        // Sort summaryData by totalProfit in descending order
        summaryData.sort((s1, s2) -> Double.compare(s2.getTotalProfit(), s1.getTotalProfit()));

        // Update the venue profits chart
        XYChart.Series<String, Number> profitSeries = new XYChart.Series<>();
        profitSeries.setName("Venue Profits");
        for (VenueProfitSummary summary : summaryData) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(summary.getVenueName(), summary.getTotalProfit());
            Tooltip tooltip = new Tooltip(summary.getVenueName() + ": £" + String.format("%.2f", summary.getTotalProfit()));
            profitSeries.getData().add(data);
            data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode != null) {
                    Tooltip.install(newNode, tooltip);
                }
            });
        }
        venueProfitsChart.setData(FXCollections.observableArrayList(profitSeries));

        // Update the ticket revenue chart (still empty as per your data)
        XYChart.Series<String, Number> revenueSeries = new XYChart.Series<>();
        revenueSeries.setName("Ticket Revenue");
        salesChart.setData(FXCollections.observableArrayList(revenueSeries));

        // Update the output area with total venue profit
        double totalVenueProfit = summaryData.stream().mapToDouble(VenueProfitSummary::getTotalProfit).sum();
        financeOutput.setText("Total Venue Profits: £" + String.format("%.2f", totalVenueProfit));

        // Debug: Log the number of entries
        System.out.println("Number of entries in summaryData: " + summaryData.size());
        System.out.println("Number of bars in venueProfitsChart: " + profitSeries.getData().size());
        System.out.println("Number of bars in salesChart: " + revenueSeries.getData().size());
    }

    @FXML
    private void handleBackToMenu() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        Stage stage = (Stage) financeOutput.getScene().getWindow();
        stage.setScene(scene);
    }
}