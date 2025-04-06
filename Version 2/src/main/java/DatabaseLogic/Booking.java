package DatabaseLogic;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import javafx.beans.property.*;

public class Booking {
    private IntegerProperty bookingID = new SimpleIntegerProperty();
    private IntegerProperty clientID = new SimpleIntegerProperty();
    private IntegerProperty eventID = new SimpleIntegerProperty();
    private IntegerProperty roomID = new SimpleIntegerProperty();
    private StringProperty date = new SimpleStringProperty();
    private StringProperty startTime = new SimpleStringProperty();
    private StringProperty endTime = new SimpleStringProperty();
    private DoubleProperty totalCost = new SimpleDoubleProperty();
    private StringProperty configurationDetails  = new SimpleStringProperty();
    private StringProperty status = new SimpleStringProperty();
    private StringProperty clientName = new SimpleStringProperty();  // New property
    private StringProperty eventName = new SimpleStringProperty();

    //full constructor
    public Booking(int bookingID, int clientID, int eventID, int roomID, String date, String startTime, String endTime, double totalCost, String configurationDetails, String status) {
        this.bookingID.set(bookingID);
        this.clientID.set(clientID);
        this.eventID.set(eventID);
        this.roomID.set(roomID);
        this.date.set(date);
        this.startTime.set(startTime);
        this.endTime.set(endTime);
        this.totalCost.set(totalCost);
        this.configurationDetails.set(configurationDetails);
        this.status.set(status);
    }

    //Constructor for bookings Page
    public Booking(int bookingID, String clientName, String eventName, String date, String startTime, String endTime, double totalCost, String configurationDetails, String status) {
        this.bookingID.set(bookingID);
        this.clientName.set(clientName);
        this.eventName.set(eventName);
        this.date.set(date);
        this.startTime.set(startTime);
        this.endTime.set(endTime);
        this.totalCost.set(totalCost);
        this.configurationDetails.set(configurationDetails);
        this.status.set(status);
    }

    public LocalTime getStartTimeAsLocalTime() {
        return LocalTime.parse(startTime.get());
    }

    public LocalTime getEndTimeAsLocalTime() {
        return LocalTime.parse(endTime.get());
    }

    public int getBookingID() { return bookingID.get(); }
    public int getClientID() { return clientID.get(); }
    public int getEventID() { return eventID.get(); }
    public int getRoomID() { return roomID.get(); }
    public String getDate() { return date.get(); }
    public String getStartTime() { return startTime.get(); }
    public String getEndTime() { return endTime.get(); }
    public double getTotalCost() { return totalCost.get(); }
    public String getConfigurationDetails() { return configurationDetails.get(); }
    public String getStatus() { return status.get(); }
    public String getClientName() { return clientName.get(); }
    public String getEventName() { return eventName.get(); }

    public void setBookingID(int bookingID) { this.bookingID.set(bookingID); }
    public void setClientID(int clientID) { this.clientID.set(clientID); }
    public void setEventID(int eventID) { this.eventID.set(eventID); }
    public void setRoomID(int roomID) { this.roomID.set(roomID); }
    public void setDate(String date) { this.date.set(date); }
    public void setStartTime(String startTime) { this.startTime.set(startTime); }
    public void setEndTime(String endTime) { this.endTime.set(endTime); }
    public void setTotalCost(double totalCost) { this.totalCost.set(totalCost); }
    public void setConfigurationDetails(String configurationDetails) { this.configurationDetails.set(configurationDetails); }
    public void setStatus(String status) { this.status.set(status); }
    public void setClientName(String clientName) { this.clientName.set(clientName); }
    public void setEventName(String eventName) { this.eventName.set(eventName); }

    public IntegerProperty getBookingIDProperty() { return bookingID; }
    public IntegerProperty getClientIDProperty() { return clientID; }
    public IntegerProperty getEventIDProperty() { return eventID; }
    public IntegerProperty getRoomIDProperty() { return roomID; }
    public StringProperty getDateProperty() { return date; }
    public StringProperty getStartTimeProperty() { return startTime; }
    public StringProperty getEndTimeProperty() { return endTime; }
    public DoubleProperty getTotalCostProperty() { return totalCost; }
    public StringProperty getConfigurationDetailsProperty() { return configurationDetails; }
    public StringProperty getStatusProperty() { return status; }
    public StringProperty getClientNameProperty() { return clientName; }
    public StringProperty getEventNameProperty() { return eventName; }
}
