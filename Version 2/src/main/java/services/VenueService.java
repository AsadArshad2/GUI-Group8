package services;

import com.example.models.Venue;
import java.util.List;
import java.util.ArrayList;

public class VenueService {

    private List<Venue> allVenues;

    public VenueService() {
        // Initializing venues (this can be replaced with database calls)
        allVenues = new ArrayList<>();
        allVenues.add(new Venue("Main Hall", "Available", 370));
        allVenues.add(new Venue("Small Hall", "Available", 100));
        allVenues.add(new Venue("Rehearsal Space", "Occupied", 30));
    }

    public List<Venue> checkAvailability(String date) {
        // Simulate checking availability. Replace with actual logic.
        List<Venue> availableVenues = new ArrayList<>();
        for (Venue venue : allVenues) {
            if (venue.getStatus().equals("Available")) {  // ✅ Fixed
                availableVenues.add(venue);
            }
        }

        return availableVenues;
    }

    public List<String> getAllVenueNames() {
        List<String> venueNames = new ArrayList<>();
        for (Venue venue : allVenues) {
            venueNames.add(venue.getVenueName());
        }
        return venueNames;
    }

    public boolean reserveVenue(Venue venue, String venueName) {
        // Simulate the reservation process
        if (venue.getStatus().equals("Available")) {
            venue.status.set("Reserved");
            return true;
        }
        return false;
    }
}
