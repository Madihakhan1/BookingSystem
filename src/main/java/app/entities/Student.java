package app.entities;


    public class Student extends User {

        private String password;
        private String status;

        public Student(String email, String name, String phone, String status, String password) {
            super(email, name, phone, status);
            this.password = password;
            this.status = status;
        }

        public Student(String email, String name, String password) {
            super(email, name);
            this.password = password;
            this.status = "active"; // default status
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
}
