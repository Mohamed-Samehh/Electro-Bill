package Electro_Bill;

import java.util.*;

public class Technician extends User{
    private double salary;
    private ArrayList<AppointmentReadOnly> appointments;

    public Technician(double salary, ArrayList<AppointmentReadOnly> appointments, int id, String firstName, String lastName, String email, String password, String address) {
        super(id, firstName, lastName, email, password, address);
        this.salary = salary;
        this.appointments = appointments;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public ArrayList<AppointmentReadOnly> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<AppointmentReadOnly> appointments) {
        this.appointments = appointments;
    }
    
    public void viewTechnicianDetails() {
        System.out.println("Technician ID: " + getId());
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Email: " + getEmail());
        System.out.println("Address: " + getAddress());
        System.out.println("Registered On: " + getRegisterDate());
        System.out.println("Salary: $" + salary);
        System.out.println("Number of Appointments: " + (appointments != null ? appointments.size() : 0));
    }
    
    public String stringViewTechnicianDetails() {
        String s = ( "\n Technician ID: " + getId()
                   + "\n Name: " + getFirstName() + " " + getLastName()
                   + "\n Email: " + getEmail()
                   + "\n Address: " + getAddress()
                   + "\n Registered On: " + getRegisterDate()
                   + "\n Salary: $" + salary
                   + "\n Number of Appointments: " + (appointments != null ? appointments.size() : 0));
        return s;
    }
}
