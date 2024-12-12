package app.persistence;


import app.entities.Booking;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingMapper {

    public static List<Booking> getBookingsByStudentEmail(String email, ConnectionPool dbConnection) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT booking_id, item_name, email, booking_date, days, comment, booking_status FROM booking WHERE email = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getString("item_name"),
                        rs.getString("email"),
                        rs.getDate("booking_date").toLocalDate(),
                        rs.getInt("days"),
                        rs.getString("comment"),
                        rs.getString("booking_status")
                );
                bookings.add(booking);
            }

        } catch (SQLException e) {
            throw new SQLException("Fejl ved hentning af bookinger: " + e.getMessage(), e);  // Håndter eventuelle fejl
        }

        return bookings;  // Returnér listen af bookinger
    }

    public static void addBooking(Booking booking, ConnectionPool dbConnection) throws SQLException {
        String query = "INSERT INTO booking (item_name, email, booking_date, days, comment, booking_status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the prepared statement parameters from the Booking object
            stmt.setString(1, booking.getItemName());  // Set item_name
            stmt.setString(2, booking.getEmail());  // Set student email
            stmt.setDate(3, java.sql.Date.valueOf(booking.getBookingDate()));  // Convert LocalDate to SQL Date
            stmt.setInt(4, booking.getDays());  // Set number of days for the booking
            stmt.setString(5, booking.getComment());  // Set any comment added for the booking
            stmt.setString(6, booking.getBookingStatus());  // Set booking status (e.g., "Pending")

            // Execute the update to insert the booking into the database
            stmt.executeUpdate();
        }
    }


    public static List<Booking> getAllBookingsWithDetails(ConnectionPool dbConnection) throws DatabaseException {
        String sql = "SELECT b.item_name, b.email, b.booking_date, b.days, b.comment, b.booking_status, " +
                "s.name AS student_name " +
                "FROM booking AS b " +
                "LEFT JOIN student AS s ON b.email = s.email";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            List<Booking> bookings = new ArrayList<>();

            while (rs.next()) {
                // Opret Booking-objekt med detaljer fra databasen
                Booking booking = new Booking(
                        rs.getString("item_name"),               // Udstyr
                        rs.getString("email"),                   // Elevens e-mail
                        rs.getString("student_name"),            // Elevens navn
                        rs.getDate("booking_date").toLocalDate(), // Bookingdato som LocalDate
                        rs.getInt("days"),                       // Antal dage booket
                        rs.getString("comment"),                 // Kommentar
                        rs.getString("booking_status")           // Status
                );
                // Status
                bookings.add(booking);
            }

            return bookings;
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af bookingdetaljer: " + e.getMessage(), e);
        }
    }








}






