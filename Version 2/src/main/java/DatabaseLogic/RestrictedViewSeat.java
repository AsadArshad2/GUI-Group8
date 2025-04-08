package DatabaseLogic;

public class RestrictedViewSeat {
    private int seatID;
    private int roomID;
    private String row;
    private String number;
    private String type;

    public RestrictedViewSeat(int seatID, int roomID, String row, String number, String type) {
        this.seatID = seatID;
        this.roomID = roomID;
        this.row = row;
        this.number = number;
        this.type = type;
    }
    public int getSeatID() { return seatID; }
    public int getRoomID() { return roomID; }
    public String getRow() { return row; }
    public String getNumber() { return number; }
    public String getType() { return type; }
    public void setSeatID(int seatID) { this.seatID = seatID; }
    public void setRoomID(int roomID) { this.roomID = roomID; }
    public void setRow(String row) { this.row = row; }
    public void setNumber(String number) { this.number = number; }
    public void setType(String type) { this.type = type; }
}
