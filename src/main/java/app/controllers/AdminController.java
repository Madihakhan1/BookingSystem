package app.controllers;

import app.persistence.AdminMapper;
import app.entities.Admin;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class AdminController {

    private AdminMapper adminMapper;

    public AdminController(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public static void addRoutes(Javalin app, ConnectionPool dbConnection) {
        AdminController adminController = new AdminController(new AdminMapper()); // Sørg for at AdminMapper er korrekt initialiseret

        // Login route
        app.post("/login", ctx -> adminController.login(ctx, dbConnection));

        // Admin views
        app.get("/studentview", ctx -> ctx.render("/views/studentview.html"));
        app.get("/equipmentview", ctx -> ctx.render("/views/equipmentview.html"));
        app.get("/bookingoverview", ctx -> ctx.render("/views/bookingoverview.html"));

        // Logout route
        app.post("/logout", adminController::logout);
    }

    public void login(Context ctx, ConnectionPool dbConnection) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            Admin admin = adminMapper.login(email, password, dbConnection);
            if (admin != null) {
                ctx.sessionAttribute("admin", admin); // Gem admin i sessionen
                ctx.redirect("/studentview");         // Redirect til relevant admin dashboard (her studentview)
            } else {
                ctx.attribute("message", "Ugyldige loginoplysninger!");  // Send fejlmeddelelse til Thymeleaf
                ctx.render("/views/index.html");     // Render login-siden med fejlmeddelelsen
            }
        } catch (Exception e) {
            ctx.status(500).result("Noget gik galt: " + e.getMessage());
        }
    }

    public void logout(Context ctx) {
        ctx.sessionAttribute("admin", null); // Fjern admin fra sessionen
        ctx.redirect("/");                   // Redirect til login-siden
    }
}
