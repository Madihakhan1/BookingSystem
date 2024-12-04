package app.controllers;

import app.entities.Student;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.StudentMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class StudentController {

    private StudentMapper studentMapper;

    public static void addRoutes(Javalin app, ConnectionPool dbConnection) {
        app.get("/", ctx -> ctx.render("index.html"));
        app.get("/createstudent.html", ctx -> ctx.render("createstudent.html"));

        app.post("/students/create", ctx -> createStudent(ctx, dbConnection));

        app.get("/students", ctx -> showAllStudents(ctx, dbConnection));


    }

    public static void showAllStudents(Context ctx, ConnectionPool dbConnection) {
        try {
            StudentMapper studentMapper = new StudentMapper();
            List<Student> students = studentMapper.getAllStudents(dbConnection); // Hent alle studerende

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
            ctx.render("createstudent.html");  // Vis succesmeddelelse på samme side
        } catch (DatabaseException e) {
            ctx.attribute("message", "Fejl ved oprettelse af bruger: " + e.getMessage());
            ctx.render("createstudent.html");
        }

    }
}
