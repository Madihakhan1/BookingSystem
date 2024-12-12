package app.entities;

public class Student extends User {

    private String password; // Tilføj password som felt

    // Konstruktør med status og password
    public Student(String email, String name, String phone, String status, String password) {
        super(email, name, phone, status); // Brug superklassens konstruktor
        this.password = password; // Initialiser password
    }


    public Student(String email, String name, String status) {
            super(email, name, null, status); // Sørg for at sætte status korrekt i User-konstruktøren
        }



    // Getter for password
    public String getPassword() {
        return password;
    }

    // Eventuelt en setter, hvis nødvendigt
    public void setPassword(String password) {
        this.password = password;
    }
}
