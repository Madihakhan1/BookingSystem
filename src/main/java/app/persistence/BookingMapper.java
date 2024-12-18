package app.persistence;


import app.entities.Booking;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingMapper {



    public static void addBooking(Booking booking, ConnectionPool dbConnection) throws SQLException {
        String query = "INSERT INTO booking (item_name, email, booking_date, days, comment, booking_status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, booking.getItemName());
            stmt.setString(2, booking.getEmail());
            stmt.setDate(3, java.sql.Date.valueOf(booking.getBookingDate()));
            stmt.setInt(4, booking.getDays());
            stmt.setString(5, booking.getComment());
            stmt.setString(6, booking.getBookingStatus());


            stmt.executeUpdate();
        }
    }


    public static List<Booking> getAllBookingsWithDetails(ConnectionPool dbConnection) throws DatabaseException {
        String sql = "SELECT b.booking_id,b.item_name, b.email, b.booking_date, b.days, b.comment, b.booking_status, " +
                "s.name AS student_name " +
                "FROM booking AS b " +
                "LEFT JOIN student AS s ON b.email = s.email";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            List<Booking> bookings = new ArrayList<>();

            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getString("item_name"),
                        rs.getString("email"),
                        rs.getString("student_name"),
                        rs.getDate("booking_date").toLocalDate(),
                        rs.getInt("days"),
                        rs.getString("comment"),
                        rs.getString("booking_status")
                );
                bookings.add(booking);
            }

            return bookings;
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af bookingdetaljer: " + e.getMessage(), e);
        }
    }


    public static void updateBookingStatus(int bookingId, String status, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE booking SET booking_status = ? WHERE booking_id = ?";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, status);
            ps.setInt(2, bookingId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af bookingstatus");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i opdatering af bookingstatus: " + e.getMessage(), e);
        }
    }

    public static void deleteBooking(int bookingId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "DELETE FROM booking WHERE booking_id = ?";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, bookingId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved sletning af booking");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved sletning af booking: " + e.getMessage(), e);
        }
    }








}






