package DatabaseLogic;

import javafx.beans.property.*;

public class Seat {
    private IntegerProperty seatID = new SimpleIntegerProperty();
    private IntegerProperty roomID = new SimpleIntegerProperty();
    private StringProperty row = new SimpleStringProperty();
    private StringProperty number = new SimpleStringProperty();
    private StringProperty type = new SimpleStringProperty();

    // Constructor
    public Seat(int seatID, int roomID, String row, String number, String type) {
        this.seatID.set(seatID);
        this.roomID.set(roomID);
        this.row.set(row);
        this.number.set(number);
        this.type.set(type);
    }

    // Getters
    public int getSeatID() { return seatID.get(); }
    public int getRoomID() { return roomID.get(); }
    public String getRow() { return row.get(); }
    public String getNumber() { return number.get(); }
    public String getType() { return type.get(); }

    // Setters
    public void setSeatID(int seatID) { this.seatID.set(seatID); }
    public void setRoomID(int roomID) { this.roomID.set(roomID); }
    public void setRow(String row) { this.row.set(row); }
    public void setNumber(String number) { this.number.set(number); }
    public void setType(String type) { this.type.set(type); }

    // Property getters for JavaFX binding
    public IntegerProperty seatIDProperty() { return seatID; }
    public IntegerProperty roomIDProperty() { return roomID; }
    public StringProperty rowProperty() { return row; }
    public StringProperty numberProperty() { return number; }
    public StringProperty typeProperty() { return type; }
}