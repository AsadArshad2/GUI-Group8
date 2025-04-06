package DatabaseLogic;

public class Room {
    private int roomID;
    private String Name;
    private int capacity;
    private String layout;

    public Room(int roomID, String Name, int capacity, String layout) {
        this.roomID = roomID;
        this.Name = Name;
        this.capacity = capacity;
        this.layout = layout;
    }

    public int getRoomID() {return roomID;}
    public String getName() {return Name;}
    public int getCapacity() {return capacity;}
    public String getLayout() {return layout;}
    public void setRoomID(int roomID) {this.roomID = roomID;}
    public void setName(String Name) {this.Name = Name;}
    public void setCapacity(int capacity) {this.capacity = capacity;}
    public void setLayout(String layout) {this.layout = layout;}
}
