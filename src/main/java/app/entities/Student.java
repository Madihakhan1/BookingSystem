package app.entities;

public class Student extends User {

    private String password;

    // Konstruktør med status og password
    public Student(String email, String name, String phone, String status, String password) {
        super(email, name, phone, status);
        this.password = password;
    }


    public Student(String email, String name, String status) {
            super(email, name, null, status);
        }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
