package app.entities;

public class Student extends User {

    private String password;

    public Student(String email, String name, String phone, String password, String status) {
        super(email, name, phone, status);
        this.password = password;
    }


    public Student(String email, String name, String status) {
            super(email, name, status);
        }


    public Student(String email, String name, String password,String status) {
        super(email, name, password, status);
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
