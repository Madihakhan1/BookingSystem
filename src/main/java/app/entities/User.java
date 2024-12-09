package app.entities;

public abstract class User {
    protected String email;
    protected String name;
    protected String phone;
    protected String password;
    protected boolean isAdmin;

    // Konstruktør, der tager email, name, phone og status
    public User(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.isAdmin = false;
    }

    // Konstruktør, der tager email, name, phone og password
    public User(String email, String name, String phone, String password) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.isAdmin = false;
    }

    // Getter og Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter og Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter og Setter for phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Getter og Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter og Setter for isAdmin
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }


}
