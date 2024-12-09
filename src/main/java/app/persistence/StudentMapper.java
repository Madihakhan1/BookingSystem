package app.persistence;

import app.entities.Student;
import app.exceptions.DatabaseException;

import javax.naming.Context;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentMapper {


    private Connection connection;


    public static List<Student> getAllStudents(ConnectionPool pool) throws DatabaseException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT name, email, status FROM student"; // Hent status også

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String status = rs.getString("status");

                students.add(new Student(email, name, status)); // Send status til Student
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af studerende: " + e.getMessage(), e);
        }

        return students;
    }

    public static void createStudent(String name, String email, String phone, String password, ConnectionPool pool) throws DatabaseException {
        String sql = "INSERT INTO student (name, email, phone, password) VALUES (?, ?, ?, ?)";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, password);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved oprettelse af studerende: " + e.getMessage(), e);
        }
    }


    // Metode til at gemme en studerende i databasen
    public static void addStudent(Student student, ConnectionPool dbConnection) throws Exception {
        // Korrigeret query for at inkludere name, email og password
        String query = "INSERT INTO student (email,name, password) VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                // Indsæt de værdier, der skal gemmes
                stmt.setString(1, student.getEmail());     // Sæt email
                stmt.setString(2, student.getName());      // Sæt navn
                stmt.setString(3, student.getPassword());  // Sæt password
                stmt.executeUpdate();  // Udfør indsættelsen
            }
        }
    }
}