package app.controllers;

import app.entities.Admin;
import app.exceptions.DatabaseException;
import app.persistence.AdminMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class AdminController {

    private static final AdminMapper adminMapper = new AdminMapper();



    public static void addRoutes(Javalin app, ConnectionPool dbConnection) {

        // Login route
        app.get("/login", ctx -> ctx.render("login.html"));
        app.post("/login", ctx -> doLogin(ctx, dbConnection));  // Calls doLogin method

        // Admin page route
        app.get("/adminpage", ctx -> adminPage(ctx));  // Calls adminPage method

        // Logout route
        app.get("/logout", ctx -> doLogout(ctx));  // Calls doLogout method
    }

    public static void doLogin(Context ctx, ConnectionPool dbConnection) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            // Brug adminMapper til at tjekke login
            Admin admin = adminMapper.login(email, password, dbConnection);

            if (admin != null) {
                ctx.sessionAttribute("admin", admin);  // Gem admin objekt i sessionen
                ctx.redirect("/adminpage");  // Redirect til admin-dashboardet
            } else {
                // Hvis admin ikke findes eller login mislykkes, send fejlmeddelelse til Thymeleaf
                ctx.attribute("message", "Ugyldige loginoplysninger!");  // Fejlmeddelelse
                ctx.render("index.html");  // Render login-siden med fejl
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


}