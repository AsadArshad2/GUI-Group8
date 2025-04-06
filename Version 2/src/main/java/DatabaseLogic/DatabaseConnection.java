package DatabaseLogic;

import controllers.BookingController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
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
                        rs.getString("address"),
                        rs.getString("telephone_number")
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
             PreparedStatement p = conn.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                Room room = new Room(
                        rs.getInt("room_id"),
                        rs.getString("name"),
                        rs.getInt("capacity"),
                        rs.getString("layouts")
                );
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
        String sql = "SELECT c.name as client_name, e.name as event_name, b.date, b.start_time, b.end_time, b.total_cost, b.configuration_details, b.status, b.booking_id, b.client_id, b.event_id, b.room_id FROM Bookings b JOIN Clients c ON b.client_id = c.client_id JOIN Event_details e ON b.event_id = e.event_id";

        try (Connection conn = connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Create a new Booking object that includes client and event names
                Booking booking = new Booking(
                        rs.getInt("booking_ID"),
                        rs.getString("client_name"),
                        rs.getString("event_name"),
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
            System.err.println("Error in fetching bookings: " + e.getMessage());
            e.printStackTrace();
        }
        return bookings;
    }
}