package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;

import app.controllers.AdminController;
import app.controllers.BookingController;
import app.controllers.ItemController;
import app.entities.Student;
import app.persistence.ConnectionPool;
import app.persistence.StudentMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;
import app.controllers.StudentController;


import java.sql.Connection;
import java.util.List;


public class Main {

    private static final String USER = "postgres";
    private static final String PASSWORD = "fedebamseabe32";
    private static final String URL = "jdbc:postgresql://165.232.127.225:5432/%s?currentSchema=public";
    private static final String DB = "bookingsystem";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args) {

        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Routing

        StudentController.addRoutes(app, connectionPool);
        AdminController.addRoutes(app, connectionPool);
        ItemController.addRoutes(app, connectionPool);
        BookingController.addRoutes(app,connectionPool);



    }

}