package app.persistence;

import app.entities.Item;
import io.javalin.Javalin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemMapper {



    public static List<Item> getAllItems(ConnectionPool dbConnection) throws Exception {
        List<Item> items = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection()) {
            String query = "SELECT item_name,description, category, status FROM item";  // Hent de relevante kolonner fra item-tabellen
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    // Opret et Item-objekt for hver post
                    Item item = new Item(
                            rs.getString("item_name"),
                            rs.getString("description"),
                            rs.getString("category"),
                            rs.getString("status"));

                    // Tilføj item til listen
                    items.add(item);
                }
            }
        }
        return items;  // Returner listen af items
    }




}
