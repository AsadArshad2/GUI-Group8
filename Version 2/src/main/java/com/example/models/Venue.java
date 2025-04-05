package com.example.models;
import javafx.beans.property.*;

public class Venue {

    public StringProperty venueName = new SimpleStringProperty();
    public StringProperty status = new SimpleStringProperty();
    public IntegerProperty capacity = new SimpleIntegerProperty();

    public Venue(String name, String status, int capacity) {
        this.venueName.set(name);
        this.status.set(status);
        this.capacity.set(capacity);
    }

    public String getVenueName() { return venueName.get(); }
    public void setVenueName(String name) { this.venueName.set(name); } 
    public StringProperty venueNameProperty() { return venueName; }

    public String getStatus() { return status.get(); }
    public void setStatus(String status) { this.status.set(status); }
    public StringProperty statusProperty() { return status; }

    public int getCapacity() { return capacity.get(); }
    public IntegerProperty capacityProperty() { return capacity; }
}
