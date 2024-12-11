package app.controllers;

import app.entities.Booking;
import app.persistence.BookingMapper;
import app.persistence.ConnectionPool;

import java.time.LocalDate;

import io.javalin.Javalin;
import io.javalin.http.Context;


public class BookingController {

    public static void addRoutes(Javalin app, ConnectionPool dbConnection) {

        app.get("/studentpage", ctx -> {
            // Du kan vælge at sende en meddelelse om succesfuld login, hvis ønsket
            ctx.render("studentpage.html");
        });

    }


    public static void doBookEquipment(Context ctx, ConnectionPool dbConnection) {
        try {
            // Få fat i de nødvendige oplysninger fra formularen
            String itemName = ctx.formParam("itemName");
            String email = ctx.sessionAttribute("studentEmail"); // Hent studentens email fra sessionen
            LocalDate bookingDate = LocalDate.parse(ctx.formParam("bookingDate")); // Opret LocalDate fra formparametrene
            int days = Integer.parseInt(ctx.formParam("days"));
            String comment = ctx.formParam("comment");
            String bookingStatus = "Pending"; // Booking status er som standard "Pending"

            // Opret et nyt Booking-objekt uden bookingId, da det genereres af databasen
            Booking booking = new Booking(itemName, email, bookingDate, days, comment, bookingStatus);

            // Gem booking i databasen
            BookingMapper.addBooking(booking, dbConnection);

            // Vis succesmeddelelse og render studentpage med besked
            ctx.attribute("message", "Booking blev gennemført succesfuldt!");
            ctx.render("studentpage.html");

        } catch (Exception e) {
            // Hvis der er fejl ved booking, returner fejlmeddelelse
            ctx.status(500).result("Fejl ved booking: " + e.getMessage());
        }
    }



}
