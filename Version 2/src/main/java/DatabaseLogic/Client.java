package DatabaseLogic;

import javafx.beans.property.*;

public class Client {
    private IntegerProperty clientID = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty companyName = new SimpleStringProperty();
    private StringProperty telephoneNumber = new SimpleStringProperty();
    private StringProperty streetAddress = new SimpleStringProperty();
    private StringProperty city = new SimpleStringProperty();
    private StringProperty postcode = new SimpleStringProperty();
    private StringProperty billingName = new SimpleStringProperty();
    private StringProperty billingEmail = new SimpleStringProperty();

    // Full constructor with all fields
    public Client(int clientID, String name, String email, String companyName, String telephoneNumber,
                  String streetAddress, String city, String postcode, String billingName, String billingEmail) {
        this.clientID.set(clientID);
        this.name.set(name);
        this.email.set(email);
        this.companyName.set(companyName);
        this.telephoneNumber.set(telephoneNumber);
        this.streetAddress.set(streetAddress);
        this.city.set(city);
        this.postcode.set(postcode);
        this.billingName.set(billingName);
        this.billingEmail.set(billingEmail);
    }

    // Constructor with just name (for display purposes)
    public Client(String name) {
        this.name.set(name);
    }

    @Override
    public String toString() {
        return name.get();
    }

    // Getters
    public int getClientID() { return clientID.get(); }
    public String getName() { return name.get(); }
    public String getEmail() { return email.get(); }
    public String getCompanyName() { return companyName.get(); }
    public String getTelephoneNumber() { return telephoneNumber.get(); }
    public String getStreetAddress() { return streetAddress.get(); }
    public String getCity() { return city.get(); }
    public String getPostcode() { return postcode.get(); }
    public String getBillingName() { return billingName.get(); }
    public String getBillingEmail() { return billingEmail.get(); }

    // Setters
    public void setClientID(int clientID) { this.clientID.set(clientID); }
    public void setName(String name) { this.name.set(name); }
    public void setEmail(String email) { this.email.set(email); }
    public void setCompanyName(String companyName) { this.companyName.set(companyName); }
    public void setTelephoneNumber(String telephoneNumber) { this.telephoneNumber.set(telephoneNumber); }
    public void setStreetAddress(String streetAddress) { this.streetAddress.set(streetAddress); }
    public void setCity(String city) { this.city.set(city); }
    public void setPostcode(String postcode) { this.postcode.set(postcode); }
    public void setBillingName(String billingName) { this.billingName.set(billingName); }
    public void setBillingEmail(String billingEmail) { this.billingEmail.set(billingEmail); }

    // Property Getters
    public IntegerProperty getClientIDProperty() { return clientID; }
    public StringProperty getNameProperty() { return name; }
    public StringProperty getEmailProperty() { return email; }
    public StringProperty getCompanyNameProperty() { return companyName; }
    public StringProperty getTelephoneNumberProperty() { return telephoneNumber; }
    public StringProperty getStreetAddressProperty() { return streetAddress; }
    public StringProperty getCityProperty() { return city; }
    public StringProperty getPostcodeProperty() { return postcode; }
    public StringProperty getBillingNameProperty() { return billingName; }
    public StringProperty getBillingEmailProperty() { return billingEmail; }
}