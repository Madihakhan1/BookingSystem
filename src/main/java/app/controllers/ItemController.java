package app.controllers;

import app.entities.Item;
import app.persistence.ConnectionPool;
import app.persistence.ItemMapper;
import io.javalin.Javalin;

import io.javalin.http.Context;
import java.util.List;

public class ItemController {

    private static final ItemMapper itemMapper = new ItemMapper();

    public static void addRoutes(Javalin app, ConnectionPool dbConnection) {

        app.get("/equipmentview", ctx -> showEquipment(ctx, dbConnection));
    }

    public static void showEquipment(Context ctx, ConnectionPool dbConnection) {
        try {
            // Hent alle tilgængelige udstyr fra ItemMapper
            List<Item> items = ItemMapper.getAllItems(dbConnection);

            // Send udstyr til Thymeleaf
            ctx.attribute("items", items);  // Send data til Thymeleaf
            ctx.render("studentpage.html");  // Render booking-siden med udstyr
        } catch (Exception e) {
            ctx.status(500).result("Fejl ved hentning af udstyr: " + e.getMessage());
        }
    }


}
