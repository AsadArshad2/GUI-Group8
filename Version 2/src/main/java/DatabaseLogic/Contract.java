package DatabaseLogic;

import javafx.beans.property.*;

public class Contract {
    private IntegerProperty contractId = new SimpleIntegerProperty();
    private IntegerProperty eventId = new SimpleIntegerProperty();
    private IntegerProperty clientId = new SimpleIntegerProperty();
    private StringProperty clientName = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty contractDate = new SimpleStringProperty();

    public Contract(int contractId, int eventId, int clientId, String clientName, String description, String contractDate) {
        this.contractId.set(contractId);
        this.eventId.set(eventId);
        this.clientId.set(clientId);
        this.clientName.set(clientName);
        this.description.set(description);
        this.contractDate.set(contractDate);
    }

    public Contract(int contractId, int eventId, int clientId, String description, String contractDate) {
        this.contractId.set(contractId);
        this.eventId.set(eventId);
        this.clientId.set(clientId);
        this.description.set(description);
        this.contractDate.set(contractDate);
    }

    // Getters
    public int getContractId() { return contractId.get(); }
    public int getEventId() { return eventId.get(); }
    public int getClientId() { return clientId.get(); }
    public String getClientName() { return clientName.get(); }
    public String getDescription() { return description.get(); }
    public String getContractDate() { return contractDate.get(); }

    // Property Getters
    public IntegerProperty getContractIdProperty() { return contractId; }
    public IntegerProperty getEventIdProperty() { return eventId; }
    public IntegerProperty getClientIdProperty() { return clientId; }
    public StringProperty getClientNameProperty() { return clientName; }
    public StringProperty getDescriptionProperty() { return description; }
    public StringProperty getContractDateProperty() { return contractDate; }
}