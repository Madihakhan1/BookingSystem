package app.persistence;

import app.entities.Student;
import app.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    private static final String USER = "postgres";
    private static final String PASSWORD = "fedebamseabe32";
    private static final String URL = "jdbc:postgresql://165.232.127.225:5432/%s?currentSchema=test";
    private static final String DB = "bookingsystem";

    private static ConnectionPool dbConnection;

    @BeforeAll
    static void setUpClass() {
        dbConnection = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

        try (Connection connection = dbConnection.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DROP TABLE IF EXISTS test.student");
                stmt.execute("CREATE TABLE test.student (" +
                        "email VARCHAR(255) PRIMARY KEY, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "status VARCHAR(50), " +
                        "phone VARCHAR(50), " +
                        "password VARCHAR(255) NOT NULL" +
                        ")");
            }
        } catch (SQLException e) {
            fail("Database setup failed: " + e.getMessage());
        }
    }

    @BeforeEach
    void setUp() {
        try (Connection connection = dbConnection.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DELETE FROM student");

                stmt.execute("INSERT INTO student (email, name, status, phone, password) VALUES " +
                        "('student1@example.com', 'Alice', 'Active', '12345678', 'password1'), " +
                        "('student2@example.com', 'Bob', 'Inactive', '87654321', 'password2'), " +
                        "('student3@example.com', 'Charlie', 'Active', '11223344', 'password3')");
            }
        } catch (SQLException e) {
            fail("Database initialization failed: " + e.getMessage());
        }
    }


    @Test
    void testGetAllStudents() throws DatabaseException {
        List<Student> students = StudentMapper.getAllStudents(dbConnection);
        assertEquals(3, students.size());
        assertEquals("Alice", students.get(0).getName());
        assertEquals("Bob", students.get(1).getName());
        assertEquals("Charlie", students.get(2).getName());
    }

    @Test
    void testCreateStudent() throws DatabaseException {
        StudentMapper.createStudent("Dave", "student4@example.com", "55667788", "password4", dbConnection);
        List<Student> students = StudentMapper.getAllStudents(dbConnection);
        assertEquals(4, students.size());
        assertEquals("Dave", students.get(3).getName());
    }

    @Test
    void testAddStudent() throws Exception {
        Student student = new Student("student5@example.com", "Eve", "12345678", "Active", "securepassword");

        StudentMapper.addStudent(student, dbConnection);

        List<Student> students = StudentMapper.getAllStudents(dbConnection);
        assertEquals(4, students.size());
        assertEquals("Eve", students.get(3).getName());
    }




    @Test
    void testLoginFailure() {
        assertThrows(DatabaseException.class, () -> {
            StudentMapper.login("student1@example.com", "wrongpassword", dbConnection);
        });
    }

}
