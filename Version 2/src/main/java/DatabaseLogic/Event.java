package DatabaseLogic;

import javafx.beans.property.*;

import java.util.Date;

public class Event {
    private IntegerProperty eventId = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private DoubleProperty sellingPrice= new SimpleDoubleProperty();
    private StringProperty startDate = new SimpleStringProperty();
    private StringProperty endDate = new SimpleStringProperty();
    private StringProperty eventDescription = new SimpleStringProperty();
    private DoubleProperty maxDiscount= new SimpleDoubleProperty();

    //full constructor
    public Event(int eventId, String name, double sellingPrice, String startDate, String endDate, String eventDescription, double maxDiscount) {
        this.eventId.set(eventId);
        this.name.set(name);
        this.sellingPrice.set(sellingPrice);
        this.startDate.set(startDate);
        this.endDate.set(endDate);
        this.eventDescription.set(eventDescription);
        this.maxDiscount.set(maxDiscount);
        }

    public int getEventId() { return eventId.get(); }
    public void setEventId(int eventId) { this.eventId.set(eventId); }
    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }
    public double getSellingPrice() { return sellingPrice.get(); }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice.set(sellingPrice); }
    public String getStartDate() { return startDate.get(); }
    public void setStartDate(String startDate) { this.startDate.set(startDate); }
    public String getEndDate() { return endDate.get(); }
    public void setEndDate(String endDate) { this.endDate.set(endDate); }
    public String getEventDescription() { return eventDescription.get(); }
    public void setEventDescription(String eventDescription) { this.eventDescription.set(eventDescription); }
    public double getMaxDiscount() { return maxDiscount.get(); }
    public void setMaxDiscount(double maxDiscount) { this.maxDiscount.set(maxDiscount); }

    public IntegerProperty eventIdProperty() { return eventId; }
    public StringProperty nameProperty() { return name; }
    public DoubleProperty sellingPriceProperty() { return sellingPrice; }
    public StringProperty startDateProperty() { return startDate; }
    public StringProperty endDateProperty() { return endDate; }
    public StringProperty eventDescriptionProperty() { return eventDescription; }
    public DoubleProperty maxDiscountProperty() { return maxDiscount; }

    public Event(String name){
        this.name.set(name);
        }

    @Override
    public String toString() {
        return name.toString();
    }
}
