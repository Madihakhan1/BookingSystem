package app.controllers;

import app.entities.Item;
import app.persistence.ConnectionPool;
import app.persistence.ItemMapper;
import io.javalin.Javalin;

import java.util.List;

public class ItemController {

    private static final ItemMapper itemMapper = new ItemMapper();

    public static void addRoutes(Javalin app, ConnectionPool dbConnection) {
        app.get("/equipmentview", ctx -> showEquipment(ctx, dbConnection));
    }

    public static void showEquipment(io.javalin.http.Context ctx, ConnectionPool dbConnection) {
        try {
            // Brug ItemMapper til at hente alle udstyr
            List<Item> items = ItemMapper.getAllItems(dbConnection);

            // Hvis udstyr findes, send dem til Thymeleaf
            if (items != null && !items.isEmpty()) {
                ctx.attribute("items", items);  // Send items til Thymeleaf-siden
                ctx.render("equipmentview.html");    // Render equipment-siden
            } else {
                // Hvis der ikke er noget udstyr, vis en besked
                ctx.attribute("message", "Ingen udstyr tilgængeligt.");
                ctx.render("equipmentview.html");
            }
        } catch (Exception e) {
            ctx.status(500).result("Noget gik galt: " + e.getMessage());
        }
    }
}
