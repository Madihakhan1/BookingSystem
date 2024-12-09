package app.entities;


public class Student extends User {

    // Konstruktør med status og password
    public Student(String email, String name, String phone, String status) {
        super(email, name, phone, status); // Brug superklassen' konstruktor

    }

    // Konstruktør uden password (kun status kan tildeles senere)
    public Student(String email, String name, String phone) {
        super(email, name, phone, null); // Sender null som password, da det ikke er nødvendigt her
    }

}
