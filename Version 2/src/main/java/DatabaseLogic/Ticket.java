package DatabaseLogic;

import java.util.Date;

public class Ticket {
    private int ticketID;
    private int eventID;
    private Date saleDate;
    private int numberOfSeats;
    private double sellingPrice;

    public Ticket(int ticketID, int eventID, Date saleDate, int numberOfSeats, double sellingPrice) {
        this.ticketID = ticketID;
        this.eventID = eventID;
        this.saleDate = saleDate;
        this.numberOfSeats = numberOfSeats;
        this.sellingPrice = sellingPrice;
    }

    public int getTicketID() { return ticketID; }
    public int getEventID() { return eventID; }
    public Date getSaleDate() { return saleDate; }
    public int getNumberOfSeats() { return numberOfSeats; }
    public double getSellingPrice() { return sellingPrice; }
    public void setTicketID(int ticketID) { this.ticketID = ticketID; }
    public void setEventID(int eventID) { this.eventID = eventID; }
    public void setSaleDate(Date saleDate) { this.saleDate = saleDate; }
    public void setNumberOfSeats(int numberOfSeats) { this.numberOfSeats = numberOfSeats; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }
}
