package app.persistence;

import app.entities.Student;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentMapper {


    private Connection connection;


    public static List<Student> getAllStudents(ConnectionPool pool) throws DatabaseException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT name, email, phone FROM student"; // SQL-forespørgsel for at hente alle studerende

        try (Connection connection = pool.getConnection(); // Hent forbindelse fra ConnectionPool
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            // Iterere gennem resultaterne og oprette Student-objekter
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");

                // Oprette et Student-objekt og tilføje det til listen
                students.add(new Student(name, email, phone));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af studerende: " + e.getMessage(), e);
        }

        return students;
    }



}
