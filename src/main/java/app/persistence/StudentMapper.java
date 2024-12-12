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
        String sql = "SELECT name, email, status FROM student";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String status = rs.getString("status");

                students.add(new Student(email, name, status));
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
        String query = "INSERT INTO student (email, name, phone, status, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, student.getEmail());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getPhone());
            stmt.setString(4, student.getStatus());
            stmt.setString(5, student.getPassword());
            stmt.executeUpdate();
        }
    }

    public static Student login(String email, String password, ConnectionPool dbConnection) throws DatabaseException {
        Student student = null;
        String query = "SELECT email, name, status FROM student WHERE email = ? AND password = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                student = new Student(
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("status")
                );
            } else {
                throw new DatabaseException("Invalid email or password.");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved login for studerende: " + e.getMessage(), e);
        }

        return student;
    }


}