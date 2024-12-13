package app.controllers;

import app.entities.Booking;
import app.entities.Item;
import app.entities.Student;
import app.exceptions.DatabaseException;
import app.persistence.BookingMapper;
import app.persistence.ConnectionPool;
import app.persistence.ItemMapper;
import app.persistence.StudentMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentController {


    public static void addRoutes(Javalin app, ConnectionPool dbConnection) {
        app.get("/", ctx -> ctx.render("index.html"));
        app.get("/createstudent.html", ctx -> ctx.render("createstudent.html"));

        app.post("/students/create", ctx -> createStudent(ctx, dbConnection));

        app.get("/studentview", ctx -> {
            getAllStudents(ctx, dbConnection);
        });

        app.get("/studentpage", ctx -> {
            showBookingPage(ctx,dbConnection);
        });

    }



    public static void getAllStudents(Context ctx, ConnectionPool dbConnection) {
        try {
            List<Student> students = StudentMapper.getAllStudents(dbConnection);
            ctx.attribute("student", students);
            ctx.render("studentview.html");
        } catch (DatabaseException e) {
            ctx.status(500).result("Fejl ved hentning af studerende: " + e.getMessage());
        }
    }


    public static void createStudent(Context ctx, ConnectionPool dbConnection) {
        String name = ctx.formParam("name");
        String email = ctx.formParam("email");
        String phone = ctx.formParam("phone");
        String password = ctx.formParam("password");

        // Validering af input
        if (name == null || name.isEmpty()) {
            ctx.attribute("message", "Venligst indtast dit navn.");
            ctx.render("createstudent.html");
            return;
        }

        if (email == null || !email.contains("@") || !email.contains(".")) {
            ctx.attribute("message", "Venligst indtast en gyldig e-mail-adresse.");
            ctx.render("createstudent.html");
            return;
        }

        if (password == null || password.length() < 6) {
            ctx.attribute("message", "Kodeordet skal være mindst 6 tegn langt.");
            ctx.render("createstudent.html");
            return;
        }

        try {
            // Opretter studerende
            StudentMapper.createStudent(name, email, phone, password, dbConnection);

            // Sender succesbesked
            ctx.attribute("message", "Din bruger er nu oprettet.");
            ctx.render("createstudent.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", "Fejl ved oprettelse af bruger: " + e.getMessage());
            ctx.render("createstudent.html");
        }

    }

    public static void showBookingPage(Context ctx, ConnectionPool dbConnection) {
        try {
            List<Item> items = ItemMapper.getAllItems(dbConnection);
            ctx.attribute("items", items);
            ctx.render("studentpage.html");
        } catch (Exception e) {
            ctx.status(500).result("Fejl ved hentning af udstyr: " + e.getMessage());
        }
    }


}
