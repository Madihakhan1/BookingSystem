package app.controllers;

import app.entities.Booking;
import app.persistence.BookingMapper;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.javalin.Javalin;
import io.javalin.http.Context;


public class BookingController {

    public static void addRoutes(Javalin app, ConnectionPool dbConnection) {

        app.post("/studentpage", ctx -> doBookEquipment(ctx,dbConnection));

    }


    public static void doBookEquipment(Context ctx, ConnectionPool dbConnection) {
        try {
            String itemName = ctx.formParam("itemName");
            String email = ctx.sessionAttribute("email");
            LocalDate bookingDate = LocalDate.parse(ctx.formParam("bookingDate"));
            int days = Integer.parseInt(ctx.formParam("days"));
            String comment = ctx.formParam("comment");
            String bookingStatus = "Booked";

            Booking booking = new Booking(itemName, email, bookingDate, days, comment, bookingStatus);

            BookingMapper.addBooking(booking, dbConnection);

            ctx.attribute("message", "Booking blev gennemført succesfuldt!");
            ctx.render("studentpage.html");

        } catch (Exception e) {
            ctx.status(500).result("Fejl ved booking: " + e.getMessage());
        }
    }






}
