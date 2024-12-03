package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;


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

       /* app.get("/users", ctx -> UserController.showUserList(ctx, connectionPool));
        app.post("/login", ctx -> UserController.login(ctx, connectionPool));
        */





    }

}