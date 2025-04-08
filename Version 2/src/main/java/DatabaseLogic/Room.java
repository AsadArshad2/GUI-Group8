package DatabaseLogic;

import javafx.beans.property.*;

public class Room {
    private IntegerProperty roomID = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty capacity = new SimpleIntegerProperty();
    private StringProperty layouts = new SimpleStringProperty();

    public Room(int roomID, String name, int capacity, String layouts) {
        this.roomID.set(roomID);
        this.name.set(name);
        this.capacity.set(capacity);
        this.layouts.set(layouts);
    }

    // Getters
    public int getRoomID() { return roomID.get(); }
    public String getName() { return name.get(); }
    public int getCapacity() { return capacity.get(); }
    public String getLayouts() { return layouts.get(); }

    // Setters
    public void setRoomID(int roomID) { this.roomID.set(roomID); }
    public void setName(String name) { this.name.set(name); }
    public void setCapacity(int capacity) { this.capacity.set(capacity); }
    public void setLayouts(String layouts) { this.layouts.set(layouts); }

    // Property Getters
    public IntegerProperty getRoomIDProperty() { return roomID; }
    public StringProperty getNameProperty() { return name; }
    public IntegerProperty getCapacityProperty() { return capacity; }
    public StringProperty getLayoutsProperty() { return layouts; }

    @Override
    public String toString() {
        return name.get();
    }
}