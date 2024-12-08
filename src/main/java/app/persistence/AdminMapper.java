package app.persistence;

import app.entities.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminMapper {

    public Admin login(String email, String password, ConnectionPool dbConnection) throws Exception {
        String sql = "SELECT * FROM admin WHERE email = ? AND password = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Admin(
                            rs.getString("email"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("password")
                    );
                }
            }
        }
        return null; // Hvis login fejler
    }
}
