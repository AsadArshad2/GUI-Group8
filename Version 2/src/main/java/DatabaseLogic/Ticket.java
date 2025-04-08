package DatabaseLogic;

import javafx.beans.property.*;
import java.util.Date;

public class Ticket {
    private IntegerProperty ticketID = new SimpleIntegerProperty();
    private IntegerProperty eventID = new SimpleIntegerProperty();
    private ObjectProperty<Date> saleDate = new SimpleObjectProperty<>();
    private IntegerProperty numberOfSeats = new SimpleIntegerProperty();
    private DoubleProperty sellingPrice = new SimpleDoubleProperty();

    public Ticket(int ticketID, int eventID, Date saleDate, int numberOfSeats, double sellingPrice) {
        this.ticketID.set(ticketID);
        this.eventID.set(eventID);
        this.saleDate.set(saleDate);
        this.numberOfSeats.set(numberOfSeats);
        this.sellingPrice.set(sellingPrice);
    }

    // Getters
    public int getTicketID() { return ticketID.get(); }
    public int getEventID() { return eventID.get(); }
    public Date getSaleDate() { return saleDate.get(); }
    public int getNumberOfSeats() { return numberOfSeats.get(); }
    public double getSellingPrice() { return sellingPrice.get(); }

    // Setters
    public void setTicketID(int ticketID) { this.ticketID.set(ticketID); }
    public void setEventID(int eventID) { this.eventID.set(eventID); }
    public void setSaleDate(Date saleDate) { this.saleDate.set(saleDate); }
    public void setNumberOfSeats(int numberOfSeats) { this.numberOfSeats.set(numberOfSeats); }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice.set(sellingPrice); }

    // Property getters for JavaFX binding
    public IntegerProperty ticketIDProperty() { return ticketID; }
    public IntegerProperty eventIDProperty() { return eventID; }
    public ObjectProperty<Date> saleDateProperty() { return saleDate; }
    public IntegerProperty numberOfSeatsProperty() { return numberOfSeats; }
    public DoubleProperty sellingPriceProperty() { return sellingPrice; }
}