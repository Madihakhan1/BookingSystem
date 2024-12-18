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

        app.get("/login", ctx -> ctx.render("login.html"));
        app.post("/login", ctx -> doLogin(ctx, dbConnection));

        app.get("/adminpage", ctx -> adminPage(ctx));

        app.get("/logout", ctx -> doLogout(ctx));

        app.get("/admin/add-student", ctx -> {
            ctx.render("add-student.html");
        });

        app.post("/admin/add-student", ctx -> addStudent(ctx, dbConnection));
        app.get("/admin/studentview", ctx -> showStudents(ctx, dbConnection));

        app.get("/bookingoverview", ctx -> showBookingOverview(ctx, dbConnection));

        app.post("/admin/update-booking-status", ctx -> updateBookingStatus(ctx, dbConnection));
        app.post("/admin/delete-booking", ctx -> deleteBooking(ctx, dbConnection));

    }

    public static void doLogin(Context ctx, ConnectionPool dbConnection) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            if (email.endsWith("@cph.com")) {
                Admin admin = adminMapper.login(email, password, dbConnection);

                if (admin != null) {
                    ctx.sessionAttribute("admin", admin);
                    ctx.redirect("/adminpage");
                } else {
                    ctx.attribute("message", "Ugyldige loginoplysninger for admin!");
                    ctx.render("login.html");
                }
            } else {
                Student student = studentMapper.login(email, password, dbConnection);

                if (student != null) {
                    ctx.sessionAttribute("student", student);
                    ctx.sessionAttribute("email",email);
                    ctx.redirect("/studentpage");
                } else {
                    ctx.attribute("message", "Ugyldige loginoplysninger for studerende!");
                    ctx.render("login.html");
                }
            }
        } catch (Exception e) {
            ctx.status(500).result("Noget gik galt: " + e.getMessage());
        }
    }



    // Logout metode
    public static void doLogout(Context ctx) {
        ctx.sessionAttribute("admin", null);
        ctx.redirect("/login");
    }

    public static void adminPage(Context ctx) {
        Admin adminSession = ctx.sessionAttribute("admin");

        if (adminSession == null) {
            ctx.redirect("/login");
        } else {
            ctx.attribute("adminName", adminSession.getName());
            ctx.render("adminpage.html");
        }
    }


    public static void addStudent(io.javalin.http.Context ctx, ConnectionPool dbConnection) {
        String name = ctx.formParam("name");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        if (name == null || email == null || password == null ||
                name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            ctx.attribute("message", "Navn, email og password skal udfyldes.");
            ctx.render("add-student.html");
            return;
        }

        try {
            Student student = new Student(email,name,password);
            StudentMapper.addStudent(student, dbConnection);
            ctx.attribute("message", "Studerende er blevet tilføjet!");
            ctx.render("add-student.html");
        } catch (Exception e) {
            ctx.status(500).result("Fejl ved tilføjelse af studerende: " + e.getMessage());
        }
    }

    public static void showStudents(Context ctx, ConnectionPool dbConnection) {
        try {
            List<Student> students = StudentMapper.getAllStudents(dbConnection);

            if (students != null && !students.isEmpty()) {
                ctx.attribute("students", students);
                ctx.render("studentview.html");
            } else {
                ctx.attribute("message", "Ingen studerende fundet.");
                ctx.render("studentview.html");
            }
        } catch (Exception e) {
            ctx.status(500).result("Fejl ved hentning af studerende: " + e.getMessage());
        }
    }

    public static void showBookingOverview(Context ctx, ConnectionPool dbConnection) {
        try {
            List<Booking> bookings = BookingMapper.getAllBookingsWithDetails(dbConnection);

            ctx.attribute("bookings", bookings);
            ctx.render("bookingoverview.html");
        } catch (Exception e) {
            ctx.status(500).result("Fejl ved hentning af bookingoversigt: " + e.getMessage());
        }
    }

    public static void updateBookingStatus(Context ctx, ConnectionPool dbConnection) {
        int bookingId = Integer.parseInt(ctx.formParam("bookingId"));
        String newStatus = ctx.formParam("status");

        try {
            BookingMapper.updateBookingStatus(bookingId, newStatus, dbConnection);
            ctx.redirect("/bookingoverview");
        } catch (Exception e) {
            ctx.status(500).result("Fejl ved opdatering af bookingstatus: " + e.getMessage());
        }
    }

    private static void deleteBooking(Context ctx, ConnectionPool connectionPool) {
        try {
            int bookingId = Integer.parseInt(ctx.formParam("bookingId"));
            BookingMapper.deleteBooking(bookingId, connectionPool);
            ctx.redirect("/bookingoverview");
        } catch (DatabaseException | NumberFormatException e) {
            ctx.status(500).result("Fejl ved sletning af booking: " + e.getMessage());
        }
    }


}


