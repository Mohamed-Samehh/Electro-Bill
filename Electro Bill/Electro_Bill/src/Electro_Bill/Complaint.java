package Electro_Bill;

import java.util.*;

public class Complaint {
    private int id;
    private Date dateTime;
    private String message;
    private int clientID;

    public Complaint(int id, String message, int client) {
        this.id = id;
        this.dateTime = new Date();
        this.message = message;
        this.clientID = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
    
    public void viewComplaint() {
        System.out.println("Complaint ID: " + id);
        System.out.println("Date and Time: " + dateTime);
        System.out.println("Complaint Message: " + message);
        System.out.println("Client ID: " + clientID);
    }
    
    public String stringViewComplaint() {
        String s = ( "\n Complaint ID: " + id
                   + "\n Date and Time: " + dateTime
                   + "\n Complaint Message: " + message
                   + "\n Client ID: " + clientID);
        return s;
    }
}
