package app.entities;

public class Student extends User {

    public Student(String email, String name, String phone) {
        super(email, name, phone);
        this.isAdmin = false;
    }

    public Student(String email, String name, String phone, String password) {
        super(email, name, phone, password);
        this.isAdmin = false;
    }

}
