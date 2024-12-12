package app.persistence;

import app.entities.Item;
import io.javalin.Javalin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemMapper {


    public static List<Item> getAllItems(ConnectionPool dbConnection) throws Exception {
        List<Item> items = new ArrayList<>();
        String query = "SELECT item_name, description, category, status, item_id, location FROM item";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String itemName = rs.getString("item_name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                String status = rs.getString("status");


                items.add(new Item(itemName, description, category, status));
            }

        } catch (SQLException e) {
            throw new Exception("Fejl ved hentning af udstyr: " + e.getMessage(), e);
        }

        return items;
    }





}
