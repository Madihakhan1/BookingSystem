package app.controllers;

import app.entities.Admin;
import app.entities.Booking;
import app.entities.Student;
import app.exceptions.DatabaseException;
import app.persistence.AdminMapper;
import app.persistence.BookingMapper;
import app.persistence.ConnectionPool;
import app.persistence.StudentMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;


public class AdminController {

    private static final AdminMapper adminMapper = new AdminMapper();
    private static final StudentMapper studentMapper = new StudentMapper();



    public static void addRoutes(Javalin app, ConnectionPool dbConnection) {

        // Login route
        app.get("/login", ctx -> ctx.render("login.html"));
        app.post("/login", ctx -> doLogin(ctx, dbConnection));  // Calls doLogin method

        app.get("/adminpage", ctx -> adminPage(ctx));  // Calls adminPage method

        app.get("/logout", ctx -> doLogout(ctx));  // Calls doLogout method

        app.get("/admin/add-student", ctx -> {
            ctx.render("add-student.html");  // Render skabelonen
        });

        app.post("/admin/add-student", ctx -> addStudent(ctx, dbConnection));
        app.get("/admin/studentview", ctx -> showStudents(ctx, dbConnection));

        app.get("/bookingoverview", ctx -> showBookingOverview(ctx, dbConnection));

    }

    public static void doLogin(Context ctx, ConnectionPool dbConnection) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            // Tjek om email ender på "@cph.com" for admin
            if (email.endsWith("@cph.com")) {
                // Håndter admin login
                Admin admin = adminMapper.login(email, password, dbConnection);

                if (admin != null) {
                    ctx.sessionAttribute("admin", admin);  // Gem admin objekt i sessionen
                    ctx.redirect("/adminpage");  // Redirect til admin-dashboardet
                } else {
                    ctx.attribute("message", "Ugyldige loginoplysninger for admin!");
                    ctx.render("login.html");  // Render login-siden med fejl
                }
            } else {
                // Håndter student login
                Student student = studentMapper.login(email, password, dbConnection);

                if (student != null) {
                    ctx.sessionAttribute("student", student);  // Gem student objekt i sessionen
                    ctx.redirect("/studentpage");  // Redirect til student-dashboardet
                } else {
                    ctx.attribute("message", "Ugyldige loginoplysninger for studerende!");
                    ctx.render("login.html");  // Render login-siden med fejl
                }
            }
        } catch (Exception e) {
            ctx.status(500).result("Noget gik galt: " + e.getMessage());  // Fejl ved database eller login
        }
    }



    // Logout metode
    public static void doLogout(Context ctx) {
        ctx.sessionAttribute("admin", null);  // Fjern admin session
        ctx.redirect("/login");  // Redirect til login siden
    }

    public static void adminPage(Context ctx) {
        // Hent admin session-attributten som et Admin-objekt
        Admin adminSession = ctx.sessionAttribute("admin");

        // Tjek om admin-sessionen er null
        if (adminSession == null) {
            ctx.redirect("/login");
        } else {
            // Hvis sessionen er gyldig, send admin-information til Thymeleaf
            ctx.attribute("adminName", adminSession.getName()); // Brug getName() fra User-klassen
            ctx.render("adminpage.html"); // Render admin-dashboardet
        }
    }


    public static void addStudent(io.javalin.http.Context ctx, ConnectionPool dbConnection) {
        // Hent data fra formularen
        String name = ctx.formParam("name");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        // Validering: Sørg for at alle felter er udfyldt
        if (name == null || email == null || password == null || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            ctx.attribute("message", "Alle felter skal udfyldes.");
            ctx.render("add-student.html");
            return;
        }

        // Opret en studerende og gem den i databasen via mapper
        try {
            Student student = new Student(name, email, password);  // Opret studerende med name, email og password
            StudentMapper.addStudent(student, dbConnection);  // Gem studerende i databasen
            ctx.attribute("message", "Studerende er blevet tilføjet!");  // Send succesbesked
            ctx.render("add-student.html");  // Render formularen igen med succesbesked
        } catch (Exception e) {
            ctx.status(500).result("Fejl ved tilføjelse af studerende: " + e.getMessage());
        }
    }

    public static void showStudents(Context ctx, ConnectionPool dbConnection) {
        try {
            // Hent alle studerende fra databasen via den eksisterende getAllStudents metode
            List<Student> students = StudentMapper.getAllStudents(dbConnection);

            // Hvis studerende findes, send dem til Thymeleaf
            if (students != null && !students.isEmpty()) {
                ctx.attribute("students", students);
                ctx.render("studentview.html");  // Render studentview.html
            } else {
                // Hvis ingen studerende er fundet, vis en besked
                ctx.attribute("message", "Ingen studerende fundet.");
                ctx.render("studentview.html");
            }
        } catch (Exception e) {
            ctx.status(500).result("Fejl ved hentning af studerende: " + e.getMessage());
        }
    }

    public static void showBookingOverview(Context ctx, ConnectionPool dbConnection) {
        try {
            // Hent alle bookinger fra databasen
            List<Booking> bookings = BookingMapper.getAllBookingsWithDetails(dbConnection);

            // Send bookinger til Thymeleaf-skabelonen
            ctx.attribute("bookings", bookings);
            ctx.render("bookingoverview.html");
        } catch (Exception e) {
            // Hvis der opstår fejl, vis en passende besked
            ctx.status(500).result("Fejl ved hentning af bookingoversigt: " + e.getMessage());
        }
    }


}


