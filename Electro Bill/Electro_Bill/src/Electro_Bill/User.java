package Electro_Bill;

import java.util.*;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private Date registerDate;

    public User(int id, String firstName, String lastName, String email, String password, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.registerDate = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (isValidEmail(email)) {
            this.email = email;
            System.out.println("Email updated successfully to: " + email);
        } else {
            System.out.println("Invalid email format. Email not updated.");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (isValidPassword(password)) {
            this.password = password;
            System.out.println("Password updated successfully.");
        } else {
            System.out.println("Invalid password. Password not updated.");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }
    
    private boolean isValidEmail(String email) {
        return email != null && !email.isEmpty() && email.contains("@");
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 5;
    }
}
