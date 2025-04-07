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
    private StringProperty status = new SimpleStringProperty();
    private StringProperty clientName = new SimpleStringProperty();
    private StringProperty eventName = new SimpleStringProperty();
    private StringProperty roomName = new SimpleStringProperty();

    // Full constructor
    public Booking(int bookingID, int clientID, int eventID, int roomID, String date, String startTime, String endTime, double totalCost, String status) {
        this.bookingID.set(bookingID);
        this.clientID.set(clientID);
        this.eventID.set(eventID);
        this.roomID.set(roomID);
        this.date.set(date);
        this.startTime.set(startTime);
        this.endTime.set(endTime);
        this.totalCost.set(totalCost);
        this.status.set(status);
        this.roomName.set(""); // Initialize roomName as empty; will be set later
    }

    // Constructor for Bookings Page
    public Booking(int bookingID, String clientName, String eventName, String roomName, String date, String startTime, String endTime, double totalCost, String status) {
        this.bookingID.set(bookingID);
        this.clientName.set(clientName);
        this.eventName.set(eventName);
        this.roomName.set(roomName);
        this.date.set(date);
        this.startTime.set(startTime);
        this.endTime.set(endTime);
        this.totalCost.set(totalCost);
        this.status.set(status);
    }

    public LocalTime getStartTimeAsLocalTime() {
        return LocalTime.parse(startTime.get());
    }

    public LocalTime getEndTimeAsLocalTime() {
        return LocalTime.parse(endTime.get());
    }

    // Getters
    public int getBookingID() { return bookingID.get(); }
    public int getClientID() { return clientID.get(); }
    public int getEventID() { return eventID.get(); }
    public int getRoomID() { return roomID.get(); }
    public String getDate() { return date.get(); }
    public String getStartTime() { return startTime.get(); }
    public String getEndTime() { return endTime.get(); }
    public double getTotalCost() { return totalCost.get(); }
    public String getStatus() { return status.get(); }
    public String getClientName() { return clientName.get(); }
    public String getEventName() { return eventName.get(); }
    public String getRoomName() { return roomName.get(); }

    // Setters
    public void setBookingID(int bookingID) { this.bookingID.set(bookingID); }
    public void setClientID(int clientID) { this.clientID.set(clientID); }
    public void setEventID(int eventID) { this.eventID.set(eventID); }
    public void setRoomID(int roomID) { this.roomID.set(roomID); }
    public void setDate(String date) { this.date.set(date); }
    public void setStartTime(String startTime) { this.startTime.set(startTime); }
    public void setEndTime(String endTime) { this.endTime.set(endTime); }
    public void setTotalCost(double totalCost) { this.totalCost.set(totalCost); }
    public void setStatus(String status) { this.status.set(status); }
    public void setClientName(String clientName) { this.clientName.set(clientName); }
    public void setEventName(String eventName) { this.eventName.set(eventName); }
    public void setRoomName(String roomName) { this.roomName.set(roomName); }

    // Property getters
    public IntegerProperty getBookingIDProperty() { return bookingID; }
    public IntegerProperty getClientIDProperty() { return clientID; }
    public IntegerProperty getEventIDProperty() { return eventID; }
    public IntegerProperty getRoomIDProperty() { return roomID; }
    public StringProperty getDateProperty() { return date; }
    public StringProperty getStartTimeProperty() { return startTime; }
    public StringProperty getEndTimeProperty() { return endTime; }
    public DoubleProperty getTotalCostProperty() { return totalCost; }
    public StringProperty getStatusProperty() { return status; }
    public StringProperty getClientNameProperty() { return clientName; }
    public StringProperty getEventNameProperty() { return eventName; }
    public StringProperty getRoomNameProperty() { return roomName; }
}