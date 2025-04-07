package DatabaseLogic;

import controllers.BookingController;
import controllers.VenueProfitSummary;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseConnection {
    //way to connect to the database
    public static Connection connectToDatabase() throws SQLException {
        String url = "jdbc:mysql://sst-stuproj.city.ac.uk:3306/in2033t08";
        String user = "in2033t08_a";
        String password = "1rHVxHi7gR8";
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    public static List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM Event_details";
        try (Connection conn = connectToDatabase();
             PreparedStatement p = conn.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                Event event = new Event(
                        rs.getInt("event_id"),
                        rs.getString("name"),
                        rs.getDouble("selling_price"),
                        rs.getString("start_date"),
                        rs.getString("end_date"),
                        rs.getString("description"),
                        rs.getDouble("max_discount")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            System.err.println("Error in fetching events: " + e.getMessage());
            e.printStackTrace();
        }
        return events;
    }

    public static List<Calendar> getAllCalendarBookings() {
        List<Calendar> calendarList = new ArrayList<>();
        String sql = """
        SELECT 
            b.booking_id,
            b.date,
            b.start_time,
            b.end_time,
            b.total_cost,
            b.configuration_details,
            b.status,

            e.event_id,
            e.name AS event_name,
            e.selling_price,
            e.start_date AS event_start,
            e.end_date AS event_end,
            e.description,
            e.max_discount,

            r.room_id,
            r.name AS room_name,
            r.capacity,
            r.layouts
        FROM Bookings b
        LEFT JOIN Event_details e ON b.event_id = e.event_id
        LEFT JOIN Rooms r ON b.room_id = r.room_id
        """;

        try (Connection conn = connectToDatabase();
             PreparedStatement p = conn.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                Calendar calendar = new Calendar(
                        rs.getInt("booking_id"),
                        rs.getString("date"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getDouble("total_cost"),
                        rs.getString("configuration_details"),
                        rs.getString("status"),

                        rs.getInt("event_id"),
                        rs.getString("event_name"),
                        rs.getDouble("selling_price"),
                        rs.getString("event_start"),
                        rs.getString("event_end"),
                        rs.getString("description"),
                        rs.getDouble("max_discount"),

                        rs.getInt("room_id"),
                        rs.getString("room_name"),
                        rs.getInt("capacity"),
                        rs.getString("layouts")
                );
                calendarList.add(calendar);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching calendar bookings: " + e.getMessage());
            e.printStackTrace();
        }

        return calendarList;
    }

    public static List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM Bookings";
        try (Connection conn = connectToDatabase();
             PreparedStatement p = conn.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("client_id"),
                        rs.getInt("event_id"),
                        rs.getInt("room_id"),
                        rs.getString("date"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getDouble("total_cost"),
                        rs.getString("configuration_details"),
                        rs.getString("status")
                );
                bookings.add(booking);
            }
        } catch (SQLException e) {
            System.err.println("Error in fetching venues: " + e.getMessage());
            e.printStackTrace();
        }
        return bookings;
    }

    public static List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM Clients";
        try (Connection conn = connectToDatabase();
             PreparedStatement p = conn.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                Client client = new Client(
                        rs.getInt("client_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("company_name"),
                        rs.getString("telephone_number"),
                        rs.getString("street_address"),
                        rs.getString("city"),
                        rs.getString("postcode"),
                        rs.getString("billing_name"),
                        rs.getString("billing_email")
                );
                clients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("Error in fetching clients: " + e.getMessage());
            e.printStackTrace();
        }
        return clients;
    }

    public static List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM Rooms";
        try (Connection conn = connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Room room = new Room(
                        rs.getInt("room_id"),
                        rs.getString("name"),
                        rs.getInt("capacity"),
                        rs.getString("layouts")
                );
                System.out.println("Loaded room: ID=" + room.getRoomID() + ", Name=" + room.getName());
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.err.println("Error in fetching rooms: " + e.getMessage());
            e.printStackTrace();
        }
        return rooms;
    }

    public static List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM Bookings";
        try (Connection conn = connectToDatabase();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Ensure time strings are in "HH:mm:ss" format
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                if (startTime != null && startTime.length() == 5) {
                    startTime = startTime + ":00";
                }
                if (endTime != null && endTime.length() == 5) {
                    endTime = endTime + ":00";
                }

                Booking booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("client_id"),
                        rs.getInt("event_id"),
                        rs.getInt("room_id"),
                        rs.getString("date"),
                        startTime,
                        endTime,
                        rs.getDouble("total_cost"),
                        rs.getString("configuration_details"),
                        rs.getString("status")
                );
                booking.setClientName(getClientNameById(booking.getClientID()));
                booking.setEventName(getEventNameById(booking.getEventID()));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching bookings: " + e.getMessage());
        }
        return bookings;
    }

    public static String getClientNameById(int clientId) {
        String query = "SELECT name FROM Clients WHERE client_id = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching client name for client_id " + clientId + ": " + e.getMessage());
        }
        return "";
    }

    public static String getEventNameById(int eventId) {
        String query = "SELECT name FROM Event_details WHERE event_id = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching event name for event_id " + eventId + ": " + e.getMessage());
        }
        return "";
    }

    public static void pushEventEditsToDatabase(List<Event> events) {
        String updateSQL = "UPDATE Event_details SET name = ?, selling_price = ?, start_date = ?, end_date = ?, description = ?, max_discount = ? WHERE event_id = ?";
        String insertSQL = "INSERT INTO Event_details (name, selling_price, start_date, end_date, description, max_discount) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connectToDatabase();
             PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {

            for (Event e : events) {
                if (e.getEventId() > 0) {
                    // Update existing event
                    updateStmt.setString(1, e.getName());
                    updateStmt.setDouble(2, e.getSellingPrice());
                    updateStmt.setString(3, e.getStartDate());
                    updateStmt.setString(4, e.getEndDate());
                    updateStmt.setString(5, e.getEventDescription());
                    updateStmt.setDouble(6, e.getMaxDiscount());
                    updateStmt.setInt(7, e.getEventId());
                    updateStmt.addBatch();
                } else {
                    // Insert new event
                    insertStmt.setString(1, e.getName());
                    insertStmt.setDouble(2, e.getSellingPrice());
                    insertStmt.setString(3, e.getStartDate());
                    insertStmt.setString(4, e.getEndDate());
                    insertStmt.setString(5, e.getEventDescription());
                    insertStmt.setDouble(6, e.getMaxDiscount());
                    insertStmt.addBatch();
                }
            }

            updateStmt.executeBatch();
            insertStmt.executeBatch();

            System.out.println("All event changes pushed to database.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to push event changes: " + e.getMessage());
        }
    }

    public static void pushCalendarEditsToDatabase(List<Calendar> calendarBookings) {
        String updateSQL = "UPDATE Bookings SET start_time = ?, end_time = ?, total_cost = ?, " +
                "status = ? WHERE booking_id = ?";

        String updateRoomsSQL = "UPDATE Rooms SET capacity = ? WHERE room_id = ?";

        String updateEventsSQL = "UPDATE Event_details SET name = ? WHERE event_id = ?";

        try (Connection conn = connectToDatabase();
             PreparedStatement updateBookingsStmt = conn.prepareStatement(updateSQL);
             PreparedStatement updateRoomsStmt = conn.prepareStatement(updateRoomsSQL);
             PreparedStatement updateEventsStmt = conn.prepareStatement(updateEventsSQL)) {

            for (Calendar c : calendarBookings) {
                if (c.getBookingID() > 0) {
                    // Update Bookings table
                    updateBookingsStmt.setString(1, c.getStartTime());
                    updateBookingsStmt.setString(2, c.getEndTime());
                    updateBookingsStmt.setDouble(3, c.getTotalCost());
                    updateBookingsStmt.setString(4, c.getStatus());
                    updateBookingsStmt.setInt(5, c.getBookingID());
                    updateBookingsStmt.addBatch();

                    // Update Rooms table
                    updateRoomsStmt.setInt(1, c.getCapacity());
                    updateRoomsStmt.setInt(2, c.getRoomID());
                    updateRoomsStmt.addBatch();

                    // Update Event_details table
                    updateEventsStmt.setString(1, c.getEventName());
                    updateEventsStmt.setInt(2, c.getEventID());
                    updateEventsStmt.addBatch();
                }
            }

            updateBookingsStmt.executeBatch();
            updateRoomsStmt.executeBatch();
            updateEventsStmt.executeBatch();

            System.out.println("All changes pushed to database.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to push changes: " + e.getMessage());
        }
    }

    public static void pushBookingEditsToDatabase(List<Booking> bookings) {
        String insertSQL = "INSERT INTO Bookings (client_id, event_id, room_id, date, start_time, end_time, total_cost, configuration_details, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String updateSQL = "UPDATE Bookings SET client_id = ?, event_id = ?, room_id = ?, date = ?, start_time = ?, end_time = ?, total_cost = ?, configuration_details = ?, status = ? WHERE booking_id = ?";
        String checkOverlapSQL = "SELECT COUNT(*) FROM Bookings WHERE room_id = ? AND date = ? AND booking_id != ? AND status != 'cancelled' AND " +
                "((start_time < ? AND (end_time > ? OR end_time = '00:00:00')) OR " +
                "(start_time < ? AND (end_time > ? OR end_time = '00:00:00')) OR " +
                "(start_time >= ? AND (end_time <= ? OR end_time = '00:00:00')))";

        try (Connection conn = connectToDatabase()) {
            conn.setAutoCommit(false);
            try (PreparedStatement checkStmt = conn.prepareStatement(checkOverlapSQL);
                 PreparedStatement insertStmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {

                for (Booking booking : bookings) {
                    int roomId = booking.getRoomID();
                    if (roomId <= 0) {
                        throw new SQLException("Invalid room_id " + roomId + " for booking. Room ID must be greater than 0.");
                    }

                    String startTime = booking.getStartTime();
                    String endTime = booking.getEndTime();
                    if (startTime != null && startTime.length() == 5) {
                        startTime = startTime + ":00";
                    }
                    if (endTime != null && endTime.length() == 5) {
                        endTime = endTime + ":00";
                    }

                    // Check for overlaps, excluding the current booking if it's an update and ignoring cancelled bookings
                    checkStmt.setInt(1, booking.getRoomID());
                    checkStmt.setString(2, booking.getDate());
                    checkStmt.setInt(3, booking.getBookingID()); // Exclude the current booking
                    checkStmt.setString(4, endTime);
                    checkStmt.setString(5, startTime);
                    checkStmt.setString(6, endTime);
                    checkStmt.setString(7, startTime);
                    checkStmt.setString(8, startTime);
                    checkStmt.setString(9, endTime);

                    ResultSet rs = checkStmt.executeQuery();
                    rs.next();
                    int overlapCount = rs.getInt(1);
                    if (overlapCount > 0) {
                        throw new SQLException("Cannot add booking: timeslot " + booking.getStartTime() + " - " + booking.getEndTime() + " overlaps with an existing booking.");
                    }

                    // Determine if this is an insert (new booking) or update (existing booking)
                    if (booking.getBookingID() == 0) {
                        // Insert new booking
                        insertStmt.setInt(1, booking.getClientID());
                        if (booking.getEventID() == 0) {
                            insertStmt.setNull(2, java.sql.Types.INTEGER);
                        } else {
                            insertStmt.setInt(2, booking.getEventID());
                        }
                        insertStmt.setInt(3, booking.getRoomID());
                        insertStmt.setString(4, booking.getDate());
                        insertStmt.setString(5, startTime);
                        insertStmt.setString(6, endTime);
                        insertStmt.setDouble(7, booking.getTotalCost());
                        insertStmt.setString(8, booking.getConfigurationDetails());
                        insertStmt.setString(9, booking.getStatus());
                        insertStmt.executeUpdate();

                        ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            booking.setBookingID(generatedKeys.getInt(1));
                        }
                    } else {
                        // Update existing booking
                        updateStmt.setInt(1, booking.getClientID());
                        if (booking.getEventID() == 0) {
                            updateStmt.setNull(2, java.sql.Types.INTEGER);
                        } else {
                            updateStmt.setInt(2, booking.getEventID());
                        }
                        updateStmt.setInt(3, booking.getRoomID());
                        updateStmt.setString(4, booking.getDate());
                        updateStmt.setString(5, startTime);
                        updateStmt.setString(6, endTime);
                        updateStmt.setDouble(7, booking.getTotalCost());
                        updateStmt.setString(8, booking.getConfigurationDetails());
                        updateStmt.setString(9, booking.getStatus());
                        updateStmt.setInt(10, booking.getBookingID());
                        updateStmt.executeUpdate();
                    }
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save bookings: " + e.getMessage(), e);
        }
    }

    public static List<Timeslot> getAvailableTimeslots(String venueType, String venueName, LocalDate date) {
        List<Timeslot> availableTimeslots = new ArrayList<>();

        List<Booking> bookingsOnDate = getBookings().stream()
                .filter(b -> {
                    try {
                        return LocalDate.parse(b.getDate()).equals(date);
                    } catch (Exception e) {
                        System.out.println("Warning: Invalid date format for booking ID " + b.getBookingID() + ": " + b.getDate());
                        return false;
                    }
                })
                .filter(b -> {
                    if (venueType.equals("Venue")) {
                        return true;
                    } else if (venueType.equals("Performance Space") || venueType.equals("Room")) {
                        int roomId = b.getRoomID();
                        if (roomId <= 0) {
                            System.out.println("Warning: Skipping booking with invalid room_id " + roomId + " for booking ID " + b.getBookingID());
                            return false;
                        }
                        String roomName = getRoomNameById(roomId);
                        if (roomName.isEmpty()) {
                            System.out.println("Warning: Skipping booking with room_id " + roomId + " because room name is empty for booking ID " + b.getBookingID());
                            return false;
                        }
                        boolean matches = roomName.toLowerCase().equals(venueName.toLowerCase());
                        System.out.println("Checking booking for room_id " + roomId + " (Room: " + roomName + ") against venueName " + venueName + ": " + matches);
                        return matches;
                    }
                    return false;
                })
                .collect(Collectors.toList());

        System.out.println("DatabaseConnection: Bookings on " + date + " for " + venueType + " (" + venueName + "): " + bookingsOnDate.size());
        bookingsOnDate.forEach(b -> System.out.println("Booking: " + b.getEventID() + " - Room ID: " + b.getRoomID() + " - Start: " + b.getStartTime() + " - End: " + b.getEndTime() + " - Status: " + b.getStatus()));

        List<Timeslot> possibleTimeslots = new ArrayList<>();
        if (venueType.equals("Venue")) {
            possibleTimeslots.add(new Timeslot("17:00", "00:00", "Evening", date.getDayOfWeek().getValue() >= 5 ? 6750 : 6250));
            possibleTimeslots.add(new Timeslot("10:00", "00:00", "Full Day", date.getDayOfWeek().getValue() >= 5 ? 9500 : 8500));
        } else if (venueType.equals("Performance Space")) {
            if (venueName.equals("Main Hall")) {
                if (date.getDayOfWeek().getValue() <= 5) { // Monday to Friday
                    for (int startHour = 10; startHour <= 14; startHour++) {
                        String start = String.format("%02d:00", startHour);
                        String end = String.format("%02d:00", startHour + 3);
                        possibleTimeslots.add(new Timeslot(start, end, "Hourly (3h)", 325 * 3));
                    }
                }
                possibleTimeslots.add(new Timeslot("17:00", "00:00", "Evening", date.getDayOfWeek().getValue() >= 5 ? 2200 : 1850));
                possibleTimeslots.add(new Timeslot("10:00", "00:00", "Daily", date.getDayOfWeek().getValue() >= 5 ? 4200 : 3800));
            } else if (venueName.equals("Small Hall")) {
                if (date.getDayOfWeek().getValue() <= 5) {
                    for (int startHour = 10; startHour <= 14; startHour++) {
                        String start = String.format("%02d:00", startHour);
                        String end = String.format("%02d:00", startHour + 3);
                        possibleTimeslots.add(new Timeslot(start, end, "Hourly (3h)", 225 * 3));
                    }
                }
                possibleTimeslots.add(new Timeslot("17:00", "00:00", "Evening", date.getDayOfWeek().getValue() >= 5 ? 1300 : 950));
                possibleTimeslots.add(new Timeslot("10:00", "00:00", "Daily", date.getDayOfWeek().getValue() >= 5 ? 2500 : 2200));
            } else if (venueName.equals("Rehearsal Space")) {
                if (date.getDayOfWeek().getValue() <= 5) {
                    for (int startHour = 10; startHour <= 14; startHour++) {
                        String start = String.format("%02d:00", startHour);
                        String end = String.format("%02d:00", startHour + 3);
                        possibleTimeslots.add(new Timeslot(start, end, "Hourly (3h)", 60 * 3));
                    }
                }
                possibleTimeslots.add(new Timeslot("10:00", "17:00", "Daily", date.getDayOfWeek().getValue() >= 6 ? 340 : 240));
                possibleTimeslots.add(new Timeslot("10:00", "23:00", "Daily (Extended)", date.getDayOfWeek().getValue() >= 6 ? 500 : 450));
            }
        } else if (venueType.equals("Room")) {
            double hourlyRate = 0, morningAfternoonRate = 0, allDayRate = 0;
            switch (venueName) {
                case "The Green Room":
                    hourlyRate = 25;
                    morningAfternoonRate = 75;
                    allDayRate = 130;
                    break;
                case "Brontë Boardroom":
                    hourlyRate = 40;
                    morningAfternoonRate = 120;
                    allDayRate = 200;
                    break;
                case "Dickens Den":
                    hourlyRate = 30;
                    morningAfternoonRate = 90;
                    allDayRate = 150;
                    break;
                case "Poe Parlor":
                    hourlyRate = 35;
                    morningAfternoonRate = 100;
                    allDayRate = 170;
                    break;
                case "Globe Room":
                    hourlyRate = 50;
                    morningAfternoonRate = 150;
                    allDayRate = 250;
                    break;
                case "Chekhov Chamber":
                    hourlyRate = 38;
                    morningAfternoonRate = 110;
                    allDayRate = 180;
                    break;
            }
            for (int startHour = 10; startHour <= 16; startHour++) {
                String start = String.format("%02d:00", startHour);
                String end = String.format("%02d:00", startHour + 1);
                possibleTimeslots.add(new Timeslot(start, end, "Hourly", hourlyRate));
            }
            possibleTimeslots.add(new Timeslot("10:00", "13:00", "Morning", morningAfternoonRate));
            possibleTimeslots.add(new Timeslot("13:00", "17:00", "Afternoon", morningAfternoonRate));
            possibleTimeslots.add(new Timeslot("10:00", "17:00", "All Day", allDayRate));
        }

        for (Timeslot slot : possibleTimeslots) {
            boolean isAvailable = true;
            LocalTime slotStart = LocalTime.parse(slot.getStartTime() + ":00");
            LocalTime slotEnd = slot.getEndTime().equals("00:00") ? LocalTime.MAX : LocalTime.parse(slot.getEndTime() + ":00");

            for (Booking booking : bookingsOnDate) {
                // Ignore bookings with status 'cancelled'
                if (booking.getStatus().equals("cancelled")) {
                    continue;
                }
                LocalTime bookingStart;
                LocalTime bookingEnd;
                try {
                    bookingStart = LocalTime.parse(booking.getStartTime());
                    bookingEnd = booking.getEndTime().equals("00:00:00") ? LocalTime.MAX : LocalTime.parse(booking.getEndTime());
                } catch (Exception e) {
                    System.out.println("Warning: Invalid time format for booking ID " + booking.getBookingID() + ": Start " + booking.getStartTime() + ", End " + booking.getEndTime());
                    continue;
                }

                System.out.println("Comparing timeslot " + slot.getRateType() + " (" + slot.getStartTime() + " - " + slot.getEndTime() + ") with booking: " + booking.getStartTime() + " - " + booking.getEndTime());
                System.out.println("Slot Start: " + slotStart + ", Slot End: " + slotEnd + ", Booking Start: " + bookingStart + ", Booking End: " + bookingEnd);
                System.out.println("Condition 1 (bookingStart.isBefore(slotEnd)): " + bookingStart.isBefore(slotEnd));
                System.out.println("Condition 2 (bookingEnd.isAfter(slotStart)): " + bookingEnd.isAfter(slotStart));

                if (bookingStart.isBefore(slotEnd) && bookingEnd.isAfter(slotStart)) {
                    isAvailable = false;
                    System.out.println("Timeslot " + slot.getRateType() + " (" + slot.getStartTime() + " - " + slot.getEndTime() + ") overlaps with booking: " + booking.getStartTime() + " - " + booking.getEndTime());
                    break;
                }
            }

            if (isAvailable) {
                availableTimeslots.add(slot);
                System.out.println("Timeslot available: " + slot.getRateType() + " (" + slot.getStartTime() + " - " + slot.getEndTime() + ")");
            }
        }

        System.out.println("DatabaseConnection: Available timeslots for " + venueType + " (" + venueName + ") on " + date + " = " + availableTimeslots.size());
        return availableTimeslots;
    }

    public static String getRoomNameById(int roomId) {
        String query = "SELECT name FROM Rooms WHERE room_id = ?";
        try (Connection conn = connectToDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String roomName = rs.getString("name");
                if (roomName == null || roomName.trim().isEmpty()) {
                    System.out.println("Warning: Room name is empty for room_id " + roomId);
                    return "";
                }
                return roomName;
            } else {
                System.out.println("Warning: No room found for room_id " + roomId);
                return "";
            }
        } catch (SQLException e) {
            System.out.println("Error fetching room name for room_id " + roomId + ": " + e.getMessage());
            return "";
        }
    }

    public static void generateInvoiceForClient(int clientId) {
        String query = "SELECT b.booking_id, b.date, b.start_time, b.end_time, b.total_cost, r.name AS venue_name " +
                "FROM Bookings b " +
                "JOIN Rooms r ON b.room_id = r.room_id " +
                "WHERE b.client_id = ?";
        double totalCost = 0.0;
        StringBuilder costDescriptionBuilder = new StringBuilder();

        try (Connection conn = connectToDatabase();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                String date = rs.getString("date");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                double cost = rs.getDouble("total_cost");
                String venueName = rs.getString("venue_name");

                totalCost += cost;
                costDescriptionBuilder.append(String.format("Booking #%d on %s from %s to %s: %s - £%.2f\n",
                        bookingId, date, startTime, endTime, venueName, cost));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating invoice for client: " + e.getMessage());
        }

        String insertSQL = "INSERT INTO Invoices (client_id, date, cost_description, total_cost, paid_status) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connectToDatabase();
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setInt(1, clientId);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setString(3, costDescriptionBuilder.toString());
            stmt.setDouble(4, totalCost);
            stmt.setString(5, "unpaid");
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error generating invoice: " + e.getMessage());
        }
    }

    public static List<Contract> getAllContracts() {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT * FROM Contracts";
        try (Connection conn = connectToDatabase();
             PreparedStatement p = conn.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                Contract contract = new Contract(
                        rs.getInt("contract_id"),
                        rs.getInt("event_id"),
                        rs.getInt("client_id"),
                        rs.getString("description"),
                        rs.getString("contract_date")
                );
                contracts.add(contract);
            }
        } catch (SQLException e) {
            System.err.println("Error in fetching contracts: " + e.getMessage());
            e.printStackTrace();
        }
        return contracts;
    }

    public static List<Contract> getContractInfo() {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT c.*, cl.name AS client_name " +
                "FROM Contracts c " +
                "JOIN Clients cl ON c.client_id = cl.client_id";
        try (Connection conn = connectToDatabase();
             PreparedStatement p = conn.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                Contract contract = new Contract(
                        rs.getInt("contract_id"),
                        rs.getInt("event_id"),
                        rs.getInt("client_id"),
                        rs.getString("client_name"),
                        rs.getString("description"),
                        rs.getString("contract_date")
                );
                contracts.add(contract);
            }
        } catch (SQLException e) {
            System.err.println("Error in fetching contracts: " + e.getMessage());
            e.printStackTrace();
        }
        return contracts;
    }

    public static List<Contract> getAllContractsWithClientName() {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT c.*, cl.name AS client_name " +
                "FROM Contracts c " +
                "JOIN Clients cl ON c.client_id = cl.client_id";
        try (Connection conn = connectToDatabase();
             PreparedStatement p = conn.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                Contract contract = new Contract(
                        rs.getInt("contract_id"),
                        rs.getInt("event_id"),
                        rs.getInt("client_id"),
                        rs.getString("client_name"),
                        rs.getString("description"),
                        rs.getString("contract_date")
                );
                contracts.add(contract);
            }
        } catch (SQLException e) {
            System.err.println("Error in fetching contracts with client name: " + e.getMessage());
            e.printStackTrace();
        }
        return contracts;
    }

    public static List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoices";
        try (Connection conn = connectToDatabase();
             PreparedStatement p = conn.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                Invoice invoice = new Invoice(
                        rs.getInt("invoice_id"),
                        rs.getInt("client_id"),
                        rs.getString("date"),
                        rs.getString("cost_description"),
                        rs.getDouble("total_cost"),
                        rs.getString("paid_status")
                );
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            System.err.println("Error in fetching invoices: " + e.getMessage());
            e.printStackTrace();
        }
        return invoices;
    }

    public static List<Invoice> getAllInvoicesWithClientName() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT i.*, c.name AS client_name " +
                "FROM Invoices i " +
                "JOIN Clients c ON i.client_id = c.client_id";
        try (Connection conn = connectToDatabase();
             PreparedStatement p = conn.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                Invoice invoice = new Invoice(
                        rs.getInt("invoice_id"),
                        rs.getInt("client_id"),
                        rs.getString("client_name"),
                        rs.getString("date"),
                        rs.getString("cost_description"),
                        rs.getDouble("total_cost"),
                        rs.getString("paid_status")
                );
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            System.err.println("Error in fetching invoices with client name: " + e.getMessage());
            e.printStackTrace();
        }
        return invoices;
    }

    public static List<VenueProfitSummary> getVenueProfits() {
        List<VenueProfitSummary> venueProfits = new ArrayList<>();
        String sql = "SELECT r.name AS venue_name, SUM(b.total_cost) AS total_profit " +
                "FROM Bookings b " +
                "JOIN Rooms r ON b.room_id = r.room_id " +
                "GROUP BY r.name";
        try (Connection conn = connectToDatabase();
             PreparedStatement p = conn.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                String venueName = rs.getString("venue_name");
                double totalProfit = rs.getDouble("total_profit");
                venueProfits.add(new VenueProfitSummary(venueName, totalProfit));
            }
        } catch (SQLException e) {
            System.err.println("Error in fetching venue profits: " + e.getMessage());
            e.printStackTrace();
        }
        return venueProfits;
    }

    public static void createContract(int eventId, int clientId, String description, String contractDate) {
        String insertSQL = "INSERT INTO Contracts (event_id, client_id, description, contract_date) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection conn = connectToDatabase();
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setInt(1, eventId);
            stmt.setInt(2, clientId);
            stmt.setString(3, description);
            stmt.setString(4, contractDate);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating contract: " + e.getMessage());
        }
    }

}