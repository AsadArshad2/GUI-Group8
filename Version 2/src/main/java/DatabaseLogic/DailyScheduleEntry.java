package DatabaseLogic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class DailyScheduleEntry {
    private final String room;
    private final String time;
    private final String activity;
    private final String bookedBy;
    private final String configuration;
    private final String notes;

    public DailyScheduleEntry(String room, String time, String activity, String bookedBy, String configuration, String notes) {
        this.room = room;
        this.time = time;
        this.activity = activity;
        this.bookedBy = bookedBy;
        this.configuration = configuration;
        this.notes = notes;
    }

    public String getRoom() { return room; }
    public String getTime() { return time; }
    public String getActivity() { return activity; }
    public String getBookedBy() { return bookedBy; }
    public String getConfiguration() { return configuration; }
    public String getNotes() { return notes; }

    public StringProperty roomProperty() { return new SimpleStringProperty(room); }
    public StringProperty timeProperty() { return new SimpleStringProperty(time); }
    public StringProperty activityProperty() { return new SimpleStringProperty(activity); }
    public StringProperty bookedByProperty() { return new SimpleStringProperty(bookedBy); }
    public StringProperty configurationProperty() { return new SimpleStringProperty(configuration); }
    public StringProperty notesProperty() { return new SimpleStringProperty(notes); }
}