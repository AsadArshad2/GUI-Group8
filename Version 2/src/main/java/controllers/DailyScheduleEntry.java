package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DailyScheduleEntry {
    private final StringProperty room;
    private final StringProperty time;
    private final StringProperty activity;
    private final StringProperty bookedBy;
    private final StringProperty configuration;
    private final StringProperty notes;

    public DailyScheduleEntry(String room, String time, String activity, String bookedBy, String configuration, String notes) {
        this.room = new SimpleStringProperty(room);
        this.time = new SimpleStringProperty(time);
        this.activity = new SimpleStringProperty(activity);
        this.bookedBy = new SimpleStringProperty(bookedBy);
        this.configuration = new SimpleStringProperty(configuration);
        this.notes = new SimpleStringProperty(notes);
    }

    public StringProperty roomProperty() {
        return room;
    }

    public StringProperty timeProperty() {
        return time;
    }

    public StringProperty activityProperty() {
        return activity;
    }

    public StringProperty bookedByProperty() {
        return bookedBy;
    }

    public StringProperty configurationProperty() {
        return configuration;
    }

    public StringProperty notesProperty() {
        return notes;
    }

    // Optional getters if needed
    public String getRoom() {
        return room.get();
    }

    public String getTime() {
        return time.get();
    }

    public String getActivity() {
        return activity.get();
    }

    public String getBookedBy() {
        return bookedBy.get();
    }

    public String getConfiguration() {
        return configuration.get();
    }

    public String getNotes() {
        return notes.get();
    }
}
