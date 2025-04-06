package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class FinancesController {

    @FXML private TextField filmTitleField;
    @FXML private TextField filmCostField;
    @FXML private TextField ticketSalesField;
    @FXML private TextField ticketPriceField;

    @FXML private BarChart<String, Number> costChart;
    @FXML private BarChart<String, Number> salesChart;
    @FXML private TextArea financeOutput;

    @FXML private TableView<FilmFinanceSummary> filmSummaryTable;
    @FXML private TableColumn<FilmFinanceSummary, String> filmTitleCol;
    @FXML private TableColumn<FilmFinanceSummary, Number> costCol;
    @FXML private TableColumn<FilmFinanceSummary, Number> seatsSoldCol;
    @FXML private TableColumn<FilmFinanceSummary, Number> priceCol;
    @FXML private TableColumn<FilmFinanceSummary, Number> revenueCol;
    @FXML private TableColumn<FilmFinanceSummary, Number> profitCol;

    private final ObservableList<FilmFinanceSummary> summaryData = FXCollections.observableArrayList();

    private final Map<String, Double> filmCosts = new HashMap<>();
    private final Map<String, Double> filmRevenue = new HashMap<>();
    private final Map<String, Integer> filmSeatsSold = new HashMap<>();
    private final Map<String, Double> filmTicketPrice = new HashMap<>();

    @FXML
    public void initialize() {
        costChart.setTitle("Film Costs");
        salesChart.setTitle("Ticket Revenue");

        filmTitleCol.setCellValueFactory(new PropertyValueFactory<>("filmTitle"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
        seatsSoldCol.setCellValueFactory(new PropertyValueFactory<>("ticketsSold"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        revenueCol.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        profitCol.setCellValueFactory(new PropertyValueFactory<>("profit"));
        filmSummaryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        filmSummaryTable.setPlaceholder(new Label("No content available"));

        filmSummaryTable.setItems(summaryData);
    }

    @FXML
    private void handleLogCost() {
        String title = filmTitleField.getText().trim();
        String costStr = filmCostField.getText().trim();

        if (title.isEmpty() || costStr.isEmpty()) {
            financeOutput.setText("Please enter both film title and cost.");
            return;
        }

        try {
            double cost = Double.parseDouble(costStr);
            filmCosts.put(title, cost);
            updateCostChart();
            updateSummary(title);
            financeOutput.setText("Logged cost of £" + cost + " for film: " + title);
        } catch (NumberFormatException e) {
            financeOutput.setText("Invalid cost. Please enter a number.");
        }
    }

    @FXML
    private void handleLogSales() {
        String title = filmTitleField.getText().trim();
        String seatsStr = ticketSalesField.getText().trim();
        String priceStr = ticketPriceField.getText().trim();

        if (title.isEmpty() || seatsStr.isEmpty() || priceStr.isEmpty()) {
            financeOutput.setText("Please enter film title, ticket sales and price.");
            return;
        }

        try {
            int seats = Integer.parseInt(seatsStr);
            double price = Double.parseDouble(priceStr);
            double revenue = seats * price;

            filmRevenue.put(title, revenue);
            filmSeatsSold.put(title, seats);
            filmTicketPrice.put(title, price);
            updateSalesChart();
            updateSummary(title);
            financeOutput.setText("Logged revenue of £" + revenue + " for film: " + title);
        } catch (NumberFormatException e) {
            financeOutput.setText("Invalid ticket sales or price. Use numbers only.");
        }
    }

    private void updateSummary(String title) {
        double cost = filmCosts.getOrDefault(title, 0.0);
        int seats = filmSeatsSold.getOrDefault(title, 0);
        double price = filmTicketPrice.getOrDefault(title, 0.0);
        double revenue = seats * price;

        // Remove old summary if exists
        summaryData.removeIf(s -> s.getFilmTitle().equals(title));

        summaryData.add(new FilmFinanceSummary(title, cost, seats, price));
    }

    private void updateCostChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Film Costs");
        for (Map.Entry<String, Double> entry : filmCosts.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        costChart.setData(FXCollections.observableArrayList(series));
    }

    private void updateSalesChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ticket Revenue");
        for (Map.Entry<String, Double> entry : filmRevenue.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        salesChart.setData(FXCollections.observableArrayList(series));
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