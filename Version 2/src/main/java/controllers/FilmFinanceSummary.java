package controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FilmFinanceSummary {
    private final SimpleStringProperty filmTitle;
    private final SimpleDoubleProperty cost;
    private final SimpleIntegerProperty ticketsSold;
    private final SimpleDoubleProperty ticketPrice;
    private final SimpleDoubleProperty revenue;
    private final SimpleDoubleProperty profit;

    public FilmFinanceSummary(String filmTitle, double cost, int ticketsSold, double ticketPrice) {
        this.filmTitle = new SimpleStringProperty(filmTitle);
        this.cost = new SimpleDoubleProperty(cost);
        this.ticketsSold = new SimpleIntegerProperty(ticketsSold);
        this.ticketPrice = new SimpleDoubleProperty(ticketPrice);
        double revenueCalc = ticketPrice * ticketsSold;
        this.revenue = new SimpleDoubleProperty(revenueCalc);
        this.profit = new SimpleDoubleProperty(revenueCalc - cost);
    }

    public String getFilmTitle() { return filmTitle.get(); }
    public double getCost() { return cost.get(); }
    public int getTicketsSold() { return ticketsSold.get(); }
    public double getTicketPrice() { return ticketPrice.get(); }
    public double getRevenue() { return revenue.get(); }
    public double getProfit() { return profit.get(); }
}

