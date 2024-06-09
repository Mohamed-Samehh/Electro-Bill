package Electro_Bill;

import java.util.*;

public class Feedback {
    private int id;
    private Date dateTime;
    private String message;
    private double rate;
    private int clientID;

    public Feedback(int id, String message, double rate, int clientID) {
        this.id = id;
        this.dateTime = new Date();
        this.message = message;
        this.rate = rate;
        this.clientID = clientID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    
    public Date getDateTime() {
        return dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
    
    public void viewFeedback() {
        System.out.println("Feedback ID: " + id);
        System.out.println("Date: " + dateTime);
        System.out.println("Rating: " + rate + " out of 5.0");
        System.out.println("Feedback Message: " + message);
        System.out.println("Client ID: " + clientID);
    }
    
    public String stringViewFeedback() {
        String s = ( "\n Feedback ID: " + id
                   + "\n Date: " + dateTime
                   + "\n Rating: " + rate + " out of 5.0"
                   + "\n Feedback Message: " + message
                   + "\n Client ID: " + clientID);
        return s;
    }
}
