package app.persistence;


import app.entities.Booking;
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
        // Excluding booking_id from the insert as it's auto-generated
        String query = "INSERT INTO booking (item_name, email, booking_date, days, comment, booking_status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the prepared statement parameters from the Booking object
            stmt.setString(1, booking.getItem_name());  // Set item_name
            stmt.setString(2, booking.getEmail());  // Set student email
            stmt.setDate(3, java.sql.Date.valueOf(booking.getBooking_date()));  // Convert LocalDate to SQL Date
            stmt.setInt(4, booking.getDays());  // Set number of days for the booking
            stmt.setString(5, booking.getComment());  // Set any comment added for the booking
            stmt.setString(6, booking.getBooking_status());  // Set booking status (e.g., "Pending")

            // Execute the update to insert the booking into the database
            stmt.executeUpdate();
        }
    }


}






