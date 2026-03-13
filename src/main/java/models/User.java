package models;

public class User {
    private String name;
    private String email;
    private String number;
    private String hashedPassword;

    public User(String name, String email, String number, String hashedPassword) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.hashedPassword = this.hashMethod(hashedPassword);
    }


    private String hashMethod(String password){

        return new StringBuilder(password).reverse().toString();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = this.hashMethod(hashedPassword);
    }
}
