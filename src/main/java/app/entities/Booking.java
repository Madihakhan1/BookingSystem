package app.entities;

import java.time.LocalDate;

import java.time.LocalDate;

public class Booking {

    private int bookingId;
    private String itemName;
    private String email;
    private LocalDate bookingDate;
    private int days;
    private String comment;
    private String bookingStatus;

    // Konstruktor (uden bookingId, da det er auto-genereret)
    public Booking(String itemName, String email, LocalDate bookingDate, int days, String comment, String bookingStatus) {
        this.itemName = itemName;
        this.email = email;
        this.bookingDate = bookingDate;
        this.days = days;
        this.comment = comment;
        this.bookingStatus = bookingStatus;
    }

    // Getter-metoder
    public int getBookingId() {
        return bookingId;
    }

    public String getItem_name() {
        return itemName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBooking_date() {
        return bookingDate;
    }

    public int getDays() {
        return days;
    }

    public String getComment() {
        return comment;
    }

    public String getBooking_status() {
        return bookingStatus;
    }

    // Setter-metoder
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setItem_name(String itemName) {
        this.itemName = itemName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBooking_date(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setBooking_status(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
