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
        String sql = "SELECT name, email, phone FROM student";

        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");

                students.add(new Student(name, email, phone));
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






}
