package DatabaseLogic;

import java.util.Date;

public class Event {
    private int eventId;
    private String name;
    private double sellingPrice;
    private Date startDate;
    private Date endDate;
    private String eventDescription;
    private double maxDiscount;

    //full constructor
    public Event(int eventId, String name, double sellingPrice, Date startDate, Date endDate, String eventDescription, double maxDiscount) {
        this.eventId = eventId;
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventDescription = eventDescription;
        this.maxDiscount = maxDiscount;
    }

    //just need name
    public Event(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }


    public int getEventId() { return eventId; }
    public String getName() { return name; }
    public double getSellingPrice() { return sellingPrice; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public String getEventDescription() { return eventDescription; }
    public double getMaxDiscount() { return maxDiscount; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public void setName(String name) { this.name = name; }
    public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public void setEventDescription(String eventDescription) { this.eventDescription = eventDescription; }
    public void setMaxDiscount(double maxDiscount) { this.maxDiscount = maxDiscount; }
}
