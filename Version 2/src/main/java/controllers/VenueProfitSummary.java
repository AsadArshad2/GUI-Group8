package controllers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VenueProfitSummary {
    private final StringProperty venueName = new SimpleStringProperty();
    private final DoubleProperty totalProfit = new SimpleDoubleProperty();
    private final IntegerProperty ticketsSold = new SimpleIntegerProperty();
    private final DoubleProperty ticketPrice = new SimpleDoubleProperty();
    private final DoubleProperty revenue = new SimpleDoubleProperty();
    private final DoubleProperty profit = new SimpleDoubleProperty();

    public VenueProfitSummary(String venueName, double totalProfit, int ticketsSold, double ticketPrice, double revenue, double profit) {
        this.venueName.set(venueName);
        this.totalProfit.set(totalProfit);
        this.ticketsSold.set(ticketsSold);
        this.ticketPrice.set(ticketPrice);
        this.revenue.set(revenue);
        this.profit.set(profit);
    }

    public VenueProfitSummary(String venueName, double totalProfit) {
        this.venueName.set(venueName);
        this.totalProfit.set(totalProfit);
        this.ticketsSold.set(0);    // Explicitly set to 0
        this.ticketPrice.set(0.0);  // Explicitly set to 0.0
        this.revenue.set(0.0);      // Explicitly set to 0.0
        this.profit.set(totalProfit); // Profit defaults to totalProfit if no ticket revenue
    }

    // Getters for properties
    public String getVenueName() { return venueName.get(); }
    public double getTotalProfit() { return totalProfit.get(); }
    public int getTicketsSold() { return ticketsSold.get(); }
    public double getTicketPrice() { return ticketPrice.get(); }
    public double getRevenue() { return revenue.get(); }
    public double getProfit() { return profit.get(); }

    // Property getters for JavaFX binding
    public StringProperty venueNameProperty() { return venueName; }
    public DoubleProperty totalProfitProperty() { return totalProfit; }
    public IntegerProperty ticketsSoldProperty() { return ticketsSold; }
    public DoubleProperty ticketPriceProperty() { return ticketPrice; }
    public DoubleProperty revenueProperty() { return revenue; }
    public DoubleProperty profitProperty() { return profit; }
}