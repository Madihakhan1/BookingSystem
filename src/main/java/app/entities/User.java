package app.entities;

public abstract class User {
    protected String email;
    protected String name;
    protected String phone;
    protected String password;
    protected boolean isAdmin;
    protected String status;


    public User(String email, String name, String status) {
        this.email = email;
        this.name = name;
        this.status = status;
        this.isAdmin = false;
    }

    public User(String email, String name, String phone, String password) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.isAdmin = false;
    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getStatus() {
        return this.status;
    }



}
