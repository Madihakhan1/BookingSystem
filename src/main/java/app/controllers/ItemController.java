package app.controllers;

import app.entities.Item;
import app.persistence.ConnectionPool;
import app.persistence.ItemMapper;
import io.javalin.Javalin;

import io.javalin.http.Context;
import java.util.List;

public class ItemController {


    public static void addRoutes(Javalin app, ConnectionPool dbConnection) {

        app.get("/equipmentview", ctx -> showEquipment(ctx, dbConnection));
    }

    public static void showEquipment(Context ctx, ConnectionPool dbConnection) {
        try {
            List<Item> items = ItemMapper.getAllItems(dbConnection);

            ctx.attribute("items", items);
            ctx.render("equipmentview.html");
        } catch (Exception e) {
            ctx.status(500).result("Fejl ved hentning af udstyr: " + e.getMessage());
        }
    }


}
