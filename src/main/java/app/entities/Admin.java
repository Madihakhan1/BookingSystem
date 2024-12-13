package app.entities;

public class Admin extends User {

    public Admin(String email, String name, String phone, String password) {
        super(email, name, phone, password);
        this.isAdmin = true;
    }


}
