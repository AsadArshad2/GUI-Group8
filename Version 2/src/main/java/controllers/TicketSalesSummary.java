package controllers;

public class TicketSalesSummary {
    private final String venueName;
    private final int totalSeatsSold;
    private final double totalRevenue;

    public TicketSalesSummary(String venueName, int totalSeatsSold, double totalRevenue) {
        this.venueName = venueName;
        this.totalSeatsSold = totalSeatsSold;
        this.totalRevenue = totalRevenue;
    }

    public String getVenueName() { return venueName; }
    public int getTotalSeatsSold() { return totalSeatsSold; }
    public double getTotalRevenue() { return totalRevenue; }
}