package DatabaseLogic;

public class Client {
    private int clientID;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;

    public Client(int clientID, String name, String email, String address, String phoneNumber) {
        this.clientID = clientID;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    //just need NAME
    public Client(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getClientID() { return clientID; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setClientID(int clientID) { this.clientID = clientID; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
