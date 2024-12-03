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

    public static void addRoutes(Javalin app, ConnectionPool dbConnection)
    {
        app.get("/", ctx -> showAllStudents(ctx,dbConnection));


    }

    // Controller metode til at vise listen af studerende
    public static void showAllStudents(Context ctx, ConnectionPool dbConnection) {
        try {
            // Opret en instans af StudentMapper og kald getAllStudents
            StudentMapper studentMapper = new StudentMapper();
            List<Student> students = studentMapper.getAllStudents(dbConnection); // Hent alle studerende

            // Gem listen i Thymeleaf-request attribut og render siden
            ctx.attribute("student", students);
            ctx.render("studentview.html");
        } catch (DatabaseException e) {
            // Håndter fejl
            ctx.status(500).result("Fejl ved hentning af studerende: " + e.getMessage());
        }
    }

}
