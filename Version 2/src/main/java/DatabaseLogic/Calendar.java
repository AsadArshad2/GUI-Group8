package DatabaseLogic;

import javafx.beans.property.*;

public class Calendar {
    // Existing Booking fields
    private IntegerProperty bookingID = new SimpleIntegerProperty();
    private StringProperty date = new SimpleStringProperty();
    private StringProperty startTime = new SimpleStringProperty();
    private StringProperty endTime = new SimpleStringProperty();
    private DoubleProperty totalCost = new SimpleDoubleProperty();
    private StringProperty configurationDetails = new SimpleStringProperty();
    private StringProperty status = new SimpleStringProperty();

    // New Event fields
    private IntegerProperty eventID = new SimpleIntegerProperty();
    private StringProperty eventName = new SimpleStringProperty();
    private DoubleProperty sellingPrice = new SimpleDoubleProperty();
    private StringProperty eventStart = new SimpleStringProperty();
    private StringProperty eventEnd = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private DoubleProperty maxDiscount = new SimpleDoubleProperty();

    // New Room fields
    private IntegerProperty roomID = new SimpleIntegerProperty();
    private StringProperty roomName = new SimpleStringProperty();
    private IntegerProperty capacity = new SimpleIntegerProperty();
    private StringProperty layouts = new SimpleStringProperty();

    public Calendar(int bookingID, String date, String startTime, String endTime, double totalCost,
                    String configurationDetails, String status,
                    int eventID, String eventName, double sellingPrice, String eventStart,
                    String eventEnd, String description, double maxDiscount,
                    int roomID, String roomName, int capacity, String layouts) {
        this.bookingID.set(bookingID);
        this.date.set(date);
        this.startTime.set(startTime);
        this.endTime.set(endTime);
        this.totalCost.set(totalCost);
        this.configurationDetails.set(configurationDetails);
        this.status.set(status);

        this.eventID.set(eventID);
        this.eventName.set(eventName);
        this.sellingPrice.set(sellingPrice);
        this.eventStart.set(eventStart);
        this.eventEnd.set(eventEnd);
        this.description.set(description);
        this.maxDiscount.set(maxDiscount);

        this.roomID.set(roomID);
        this.roomName.set(roomName);
        this.capacity.set(capacity);
        this.layouts.set(layouts);
    }

    // Getters
    public int getBookingID() { return bookingID.get(); }
    public String getDate() { return date.get(); }
    public String getStartTime() { return startTime.get(); }
    public String getEndTime() { return endTime.get(); }
    public double getTotalCost() { return totalCost.get(); }
    public String getConfigurationDetails() { return configurationDetails.get(); }
    public String getStatus() { return status.get(); }

    public int getEventID() { return eventID.get(); }
    public String getEventName() { return eventName.get(); }
    public double getSellingPrice() { return sellingPrice.get(); }
    public String getEventStart() { return eventStart.get(); }
    public String getEventEnd() { return eventEnd.get(); }
    public String getDescription() { return description.get(); }
    public double getMaxDiscount() { return maxDiscount.get(); }

    public int getRoomID() { return roomID.get(); }
    public String getRoomName() { return roomName.get(); }
    public int getCapacity() { return capacity.get(); }
    public String getLayouts() { return layouts.get(); }

    // Setters
    public void setBookingID(int bookingID) { this.bookingID.set(bookingID); }
    public void setDate(String date) { this.date.set(date); }
    public void setStartTime(String startTime) { this.startTime.set(startTime); }
    public void setEndTime(String endTime) { this.endTime.set(endTime); }
    public void setTotalCost(double totalCost) { this.totalCost.set(totalCost); }
    public void setConfigurationDetails(String configurationDetails) { this.configurationDetails.set(configurationDetails); }
    public void setStatus(String status) { this.status.set(status); }

    public void setEventID(int eventID) { this.eventID.set(eventID); }
    public void setEventName(String eventName) { this.eventName.set(eventName); }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice.set(sellingPrice); }
    public void setEventStart(String eventStart) { this.eventStart.set(eventStart); }
    public void setEventEnd(String eventEnd) { this.eventEnd.set(eventEnd); }
    public void setDescription(String description) { this.description.set(description); }
    public void setMaxDiscount(double maxDiscount) { this.maxDiscount.set(maxDiscount); }

    public void setRoomID(int roomID) { this.roomID.set(roomID); }
    public void setRoomName(String roomName) { this.roomName.set(roomName); }
    public void setCapacity(int capacity) { this.capacity.set(capacity); }
    public void setLayouts(String layouts) { this.layouts.set(layouts); }

    // Property getters
    public IntegerProperty getBookingIDProperty() { return bookingID; }
    public StringProperty getDateProperty() { return date; }
    public StringProperty getStartTimeProperty() { return startTime; }
    public StringProperty getEndTimeProperty() { return endTime; }
    public DoubleProperty getTotalCostProperty() { return totalCost; }
    public StringProperty getConfigurationDetailsProperty() { return configurationDetails; }
    public StringProperty getStatusProperty() { return status; }

    public IntegerProperty getEventIDProperty() { return eventID; }
    public StringProperty getEventNameProperty() { return eventName; }
    public DoubleProperty getSellingPriceProperty() { return sellingPrice; }
    public StringProperty getEventStartProperty() { return eventStart; }
    public StringProperty getEventEndProperty() { return eventEnd; }
    public StringProperty getDescriptionProperty() { return description; }
    public DoubleProperty getMaxDiscountProperty() { return maxDiscount; }

    public IntegerProperty getRoomIDProperty() { return roomID; }
    public StringProperty getRoomNameProperty() { return roomName; }
    public IntegerProperty getCapacityProperty() { return capacity; }
    public StringProperty getLayoutsProperty() { return layouts; }
}