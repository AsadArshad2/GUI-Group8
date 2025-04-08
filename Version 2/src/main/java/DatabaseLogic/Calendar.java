package DatabaseLogic;

import javafx.beans.property.*;

public class Calendar {
    private IntegerProperty bookingID = new SimpleIntegerProperty();
    private StringProperty date = new SimpleStringProperty();
    private StringProperty startTime = new SimpleStringProperty();
    private StringProperty endTime = new SimpleStringProperty();
    private DoubleProperty totalCost = new SimpleDoubleProperty();
    private StringProperty status = new SimpleStringProperty();

    private IntegerProperty eventID = new SimpleIntegerProperty();
    private StringProperty eventName = new SimpleStringProperty();
    private DoubleProperty sellingPrice = new SimpleDoubleProperty();
    private StringProperty eventStart = new SimpleStringProperty();
    private StringProperty eventEnd = new SimpleStringProperty();
    private StringProperty eventDescription = new SimpleStringProperty();
    private DoubleProperty maxDiscount = new SimpleDoubleProperty();

    private IntegerProperty roomID = new SimpleIntegerProperty();
    private StringProperty roomName = new SimpleStringProperty();
    private IntegerProperty capacity = new SimpleIntegerProperty();
    private StringProperty layouts = new SimpleStringProperty();

    public Calendar(int bookingID, String date, String startTime, String endTime, double totalCost, String status,
                    int eventID, String eventName, double sellingPrice, String eventStart, String eventEnd, String eventDescription, double maxDiscount,
                    int roomID, String roomName, int capacity, String layouts) {
        this.bookingID.set(bookingID);
        this.date.set(date);
        this.startTime.set(startTime);
        this.endTime.set(endTime);
        this.totalCost.set(totalCost);
        this.status.set(status);

        this.eventID.set(eventID);
        this.eventName.set(eventName);
        this.sellingPrice.set(sellingPrice);
        this.eventStart.set(eventStart);
        this.eventEnd.set(eventEnd);
        this.eventDescription.set(eventDescription);
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
    public String getStatus() { return status.get(); }

    public int getEventID() { return eventID.get(); }
    public String getEventName() { return eventName.get(); }
    public double getSellingPrice() { return sellingPrice.get(); }
    public String getEventStart() { return eventStart.get(); }
    public String getEventEnd() { return eventEnd.get(); }
    public String getEventDescription() { return eventDescription.get(); }
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
    public void setStatus(String status) { this.status.set(status); }

    public void setEventID(int eventID) { this.eventID.set(eventID); }
    public void setEventName(String eventName) { this.eventName.set(eventName); }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice.set(sellingPrice); }
    public void setEventStart(String eventStart) { this.eventStart.set(eventStart); }
    public void setEventEnd(String eventEnd) { this.eventEnd.set(eventEnd); }
    public void setEventDescription(String eventDescription) { this.eventDescription.set(eventDescription); }
    public void setMaxDiscount(double maxDiscount) { this.maxDiscount.set(maxDiscount); }

    public void setRoomID(int roomID) { this.roomID.set(roomID); }
    public void setRoomName(String roomName) { this.roomName.set(roomName); }
    public void setCapacity(int capacity) { this.capacity.set(capacity); }
    public void setLayouts(String layouts) { this.layouts.set(layouts); }

    // Property getters
    public IntegerProperty bookingIDProperty() { return bookingID; }
    public StringProperty dateProperty() { return date; }
    public StringProperty startTimeProperty() { return startTime; }
    public StringProperty endTimeProperty() { return endTime; }
    public DoubleProperty totalCostProperty() { return totalCost; }
    public StringProperty statusProperty() { return status; }

    public IntegerProperty eventIDProperty() { return eventID; }
    public StringProperty eventNameProperty() { return eventName; }
    public DoubleProperty sellingPriceProperty() { return sellingPrice; }
    public StringProperty eventStartProperty() { return eventStart; }
    public StringProperty eventEndProperty() { return eventEnd; }
    public StringProperty eventDescriptionProperty() { return eventDescription; }
    public DoubleProperty maxDiscountProperty() { return maxDiscount; }

    public IntegerProperty roomIDProperty() { return roomID; }
    public StringProperty roomNameProperty() { return roomName; }
    public IntegerProperty capacityProperty() { return capacity; }
    public StringProperty layoutsProperty() { return layouts; }
}