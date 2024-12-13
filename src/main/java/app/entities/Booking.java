package app.entities;

import java.awt.print.Book;
import java.time.LocalDate;

public class Booking {

    private int bookingId;
    private String itemName;
    private String email;
    private LocalDate bookingDate;
    private int days;
    private String comment;
    private String bookingStatus;
    private String name;

    public Booking(String itemName, String email, LocalDate bookingDate, int days, String comment, String bookingStatus) {
        this.itemName = itemName;
        this.email = email;
        this.bookingDate = bookingDate;
        this.days = days;
        this.comment = comment;
        this.bookingStatus = bookingStatus;

    }

    public Booking(int bookingId, String itemName, String email, String name, LocalDate bookingDate, int days, String comment, String bookingStatus) {
        this.bookingId = bookingId;
        this.itemName = itemName;
        this.email = email;
        this.bookingDate = bookingDate;
        this.days = days;
        this.comment = comment;
        this.bookingStatus = bookingStatus;
        this.name = name;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemName() {
        return itemName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public int getDays() {
        return days;
    }

    public String getComment() {
        return comment;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    // Setter-metoder
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
