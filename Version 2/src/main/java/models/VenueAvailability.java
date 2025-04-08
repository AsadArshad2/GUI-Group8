package models;

public class VenueAvailability {
    private String timeSlot;
    private String venue;
    private String status;

    public VenueAvailability(String timeSlot, String venue, String status) {
        this.timeSlot = timeSlot;
        this.venue = venue;
        this.status = status;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public String getVenue() {
        return venue;
    }

    public String getStatus() {
        return status;
    }
}
