package DatabaseLogic;

import javafx.beans.property.*;

public class Invoice {
    private IntegerProperty invoiceId = new SimpleIntegerProperty();
    private IntegerProperty clientId = new SimpleIntegerProperty();
    private StringProperty clientName = new SimpleStringProperty();
    private StringProperty date = new SimpleStringProperty();
    private StringProperty costDescription = new SimpleStringProperty();
    private DoubleProperty totalCost = new SimpleDoubleProperty();
    private StringProperty paidStatus = new SimpleStringProperty();

    public Invoice(int invoiceId, int clientId, String clientName, String date, String costDescription, double totalCost, String paidStatus) {
        this.invoiceId.set(invoiceId);
        this.clientId.set(clientId);
        this.clientName.set(clientName);
        this.date.set(date);
        this.costDescription.set(costDescription);
        this.totalCost.set(totalCost);
        this.paidStatus.set(paidStatus);
    }

    public Invoice(int invoiceId, int clientId, String date, String costDescription, double totalCost, String paidStatus) {
        this.invoiceId.set(invoiceId);
        this.clientId.set(clientId);
        this.date.set(date);
        this.costDescription.set(costDescription);
        this.totalCost.set(totalCost);
        this.paidStatus.set(paidStatus);
    }

    // Getters
    public int getInvoiceId() { return invoiceId.get(); }
    public int getClientId() { return clientId.get(); }
    public String getClientName() { return clientName.get(); }
    public String getDate() { return date.get(); }
    public String getCostDescription() { return costDescription.get(); }
    public double getTotalCost() { return totalCost.get(); }
    public String getPaidStatus() { return paidStatus.get(); }

    // Property Getters
    public IntegerProperty getInvoiceIdProperty() { return invoiceId; }
    public IntegerProperty getClientIdProperty() { return clientId; }
    public StringProperty getClientNameProperty() { return clientName; }
    public StringProperty getDateProperty() { return date; }
    public StringProperty getCostDescriptionProperty() { return costDescription; }
    public DoubleProperty getTotalCostProperty() { return totalCost; }
    public StringProperty getPaidStatusProperty() { return paidStatus; }
}