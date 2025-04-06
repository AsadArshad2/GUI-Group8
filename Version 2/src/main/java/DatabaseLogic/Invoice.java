package DatabaseLogic;

import java.util.Date;

public class Invoice {
    private int invoiceID;
    private int clientID;
    private int bookingID;
    private Date date;
    private String cost_description;
    private double total_cost;
    private String status;

    public Invoice(int clientID, int bookingID, Date date, String cost_description, double total_cost, String status) {
        this.clientID = clientID;
        this.bookingID = bookingID;
        this.date = date;
        this.cost_description = cost_description;
        this.total_cost = total_cost;
        this.status = status;
    }
    public int getInvoiceID() { return invoiceID; }
    public void setInvoiceID(int invoiceID) { this.invoiceID = invoiceID; }
    public int getClientID() { return clientID; }
    public void setClientID(int clientID) { this.clientID = clientID; }
    public int getBookingID() { return bookingID; }
    public void setBookingID(int bookingID) { this.bookingID = bookingID; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getCost_description() { return cost_description; }
    public void setCost_description(String cost_description) { this.cost_description = cost_description; }
    public double getTotal_cost() { return total_cost; }
    public void setTotal_cost(double total_cost) { this.total_cost = total_cost; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
