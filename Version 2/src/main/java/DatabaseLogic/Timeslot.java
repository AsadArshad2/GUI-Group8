package DatabaseLogic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Timeslot {
    private StringProperty startTime = new SimpleStringProperty();
    private StringProperty endTime = new SimpleStringProperty();
    private StringProperty rateType = new SimpleStringProperty(); // e.g., "Hourly", "Evening", "Daily"
    private double cost; // Cost in pounds (excluding VAT)

    public Timeslot(String startTime, String endTime, String rateType, double cost) {
        this.startTime.set(startTime);
        this.endTime.set(endTime);
        this.rateType.set(rateType);
        this.cost = cost;
    }

    // Getters
    public String getStartTime() { return startTime.get(); }
    public String getEndTime() { return endTime.get(); }
    public String getRateType() { return rateType.get(); }
    public double getCost() { return cost; }

    // Setters
    public void setStartTime(String startTime) { this.startTime.set(startTime); }
    public void setEndTime(String endTime) { this.endTime.set(endTime); }
    public void setRateType(String rateType) { this.rateType.set(rateType); }
    public void setCost(double cost) { this.cost = cost; }

    // Property Getters
    public StringProperty startTimeProperty() { return startTime; }
    public StringProperty endTimeProperty() { return endTime; }
    public StringProperty rateTypeProperty() { return rateType; }

    @Override
    public String toString() {
        return rateType.get() + ": " + startTime.get() + " - " + endTime.get() + " (£" + cost + " + VAT)";
    }
}