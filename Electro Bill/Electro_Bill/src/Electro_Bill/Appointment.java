package Electro_Bill;

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Appointment implements AppointmentReadOnly{
    private int id;
    private Date dateTime;
    private String address;
    private int clientID;
    private int techID;

    public Appointment(int id, Date dateTime, String address, int clientID, int techID) {
        this.id = id;
        this.dateTime = dateTime;
        this.address = address;
        this.clientID = clientID;
        this.techID = techID;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getTechID() {
        return techID;
    }

    public void setTechID(int techID) {
        this.techID = techID;
    }
    
    @Override
    public void viewAppointmentDetails() {
        System.out.println("Appointment Details:");
        System.out.println("ID: " + id);
        System.out.println("Date: " + dateTime);
        System.out.println("Address: " + address);
        System.out.println("Client ID: " + clientID);
        System.out.println("Technician ID: " + techID);
    }
    
    @Override
    public String stringViewAppointmentDetails() {
        String s = ( "\n ID: " + id
                   + "\n Date: " + dateTime
                   + "\n Address: " + address
                   + "\n Client ID: " + clientID
                   + "\n Technician ID: " + techID);
        return s;
    }
    
    public void editAppointmentDateTime(String newDateInput) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            Date newDate = formatter.parse(newDateInput);
            Date now = new Date();

            if (newDate.after(now)) {
                this.dateTime = newDate;
                System.out.println("Date and Time updated to " + formatter.format(newDate));
            } else {
                System.out.println("Cannot set a past date and time.");
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use the format yyyy-MM-dd.");
        }
    }
}
