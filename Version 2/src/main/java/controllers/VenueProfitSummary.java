package controllers;

import javafx.beans.property.*;

public class VenueProfitSummary {
    private StringProperty venueName;
    private DoubleProperty totalProfit;
    private IntegerProperty ticketsSold;
    private DoubleProperty ticketPrice;
    private DoubleProperty revenue;
    private DoubleProperty profit;

    public VenueProfitSummary(String venueName, double totalProfit) {
        this.venueName = new SimpleStringProperty(venueName);
        this.totalProfit = new SimpleDoubleProperty(totalProfit);
        this.ticketsSold = new SimpleIntegerProperty(0); // Default to 0 since no ticket data
        this.ticketPrice = new SimpleDoubleProperty(0.0); // Default to 0.0
        this.revenue = new SimpleDoubleProperty(0.0); // Default to 0.0
        this.profit = new SimpleDoubleProperty(totalProfit); // Profit is just venue profit since no ticket data
    }

    // Getters
    public String getVenueName() { return venueName.get(); }
    public double getTotalProfit() { return totalProfit.get(); }
    public int getTicketsSold() { return ticketsSold.get(); }
    public double getTicketPrice() { return ticketPrice.get(); }
    public double getRevenue() { return revenue.get(); }
    public double getProfit() { return profit.get(); }

    // Property Getters
    public StringProperty getVenueNameProperty() { return venueName; }
    public DoubleProperty getTotalProfitProperty() { return totalProfit; }
    public IntegerProperty getTicketsSoldProperty() { return ticketsSold; }
    public DoubleProperty getTicketPriceProperty() { return ticketPrice; }
    public DoubleProperty getRevenueProperty() { return revenue; }
    public DoubleProperty getProfitProperty() { return profit; }
}