package app.persistence;

import app.entities.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMapper {

    public static Admin login(String email, String password, ConnectionPool dbConnection) throws Exception {
        try (Connection conn = dbConnection.getConnection()) {
            String query = "SELECT * FROM admin WHERE email = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, email);
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
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
        }
        return null;
    }

    }

