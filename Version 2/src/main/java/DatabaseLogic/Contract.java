package DatabaseLogic;

import java.util.Date;

public class Contract {
    private int contractID;
    private int eventID;
    private int clientID;
    private String description;
    private Date contractDate;

    public Contract(int contractID, int eventID, int clientID, String description, Date contractDate) {
        this.contractID = contractID;
        this.eventID = eventID;
        this.clientID = clientID;
        this.description = description;
        this.contractDate = contractDate;
    }
    public int getContractID() { return contractID; }
    public int getEventID() { return eventID; }
    public int getClientID() { return clientID; }
    public String getDescription() { return description; }
    public Date getContractDate() { return contractDate; }
    public void setContractID(int contractID) { this.contractID = contractID; }
    public void setEventID(int eventID) { this.eventID = eventID; }
    public void setClientID(int clientID) { this.clientID = clientID; }
    public void setDescription(String description) { this.description = description; }
    public void setContractDate(Date contractDate) { this.contractDate = contractDate; }
}
