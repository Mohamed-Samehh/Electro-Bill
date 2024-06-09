package Electro_Bill;

import java.util.*;

public class Admin extends User {
    private ArrayList<Manager> managers;
    private ArrayList<Client> clients;
    private ArrayList<Technician> technicians;
    private ArrayList<Integer> AdminID;
    private double salary;

    public Admin(ArrayList<Manager> managers, ArrayList<Client> clients, ArrayList<Technician> technicians, ArrayList<Integer> AdminID, double salary, int id, String firstName, String lastName, String email, String password, String address) {
        super(id, firstName, lastName, email, password, address);
        this.managers = managers;
        this.clients = clients;
        this.technicians = technicians;
        this.AdminID = AdminID;
        this.salary = salary;
    }

    public ArrayList<Manager> getManagers() {
        return managers;
    }

    public void setManagers(ArrayList<Manager> managers) {
        this.managers = managers;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public ArrayList<Technician> getTechnicians() {
        return technicians;
    }

    public void setTechnicians(ArrayList<Technician> technicians) {
        this.technicians = technicians;
    }

    public ArrayList<Integer> getAdminID() {
        return AdminID;
    }

    public void setAdminID(ArrayList<Integer> AdminID) {
        this.AdminID = AdminID;
    }

    
    
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    public void viewAdminDetails() {
    System.out.println("Admin ID: " + getId());
    System.out.println("Name: " + getFirstName() + " " + getLastName());
    System.out.println("Email: " + getEmail());
    System.out.println("Address: " + getAddress());
    System.out.println("Registered on: " + getRegisterDate());
    System.out.println("Salary: $" + getSalary());

    System.out.println("Managers:");
        if (managers != null && !managers.isEmpty()) {
            for (Manager manager : managers) {
                System.out.println(" - " + manager.getFirstName() + " " + manager.getLastName() + "  ID: " + manager.getId());
            }
        } else {
            System.out.println("No managers assigned.");
        }

        System.out.println("Clients:");
        if (clients != null && !clients.isEmpty()) {
            for (Client client : clients) {
                System.out.println(" - " + client.getFirstName() + " " + client.getLastName() + "  ID: " + client.getId());
            }
        } else {
            System.out.println("No clients assigned.");
        }

        System.out.println("Technicians:");
        if (technicians != null && !technicians.isEmpty()) {
            for (Technician technician : technicians) {
                System.out.println(" - " + technician.getFirstName() + " " + technician.getLastName() + "  ID: " + technician.getId());
            }
        } else {
            System.out.println("No technicians assigned.");
        }
        
        System.out.println("Admin IDs:");
        if (AdminID != null && !AdminID.isEmpty()) {
            for (Integer adminId : AdminID) {
                System.out.println(" - Admin ID: " + adminId);
            }
        } else {
            System.out.println("No admins.");
        }
    }

    
    public String stringViewAdminDetails() {
        String s = ("\n Admin ID: " + getId()
                   + "\n Name: " + getFirstName() + " " + getLastName()
                   + "\n Email: " + getEmail()
                   + "\n Address: " + getAddress()
                   + "\n Registered on: " + getRegisterDate()
                   + "\n Salary: $" + getSalary());

        s += "\n\n Managers:";
        if (managers != null && !managers.isEmpty()) {
            for (Manager manager : managers) {
                s += ("\n - " + manager.getFirstName() + " " + manager.getLastName() + "  ID: " + manager.getId());
            }
        } else {
            s += "\n No managers assigned.";
        }

        s += "\n\n Clients:";
        if (clients != null && !clients.isEmpty()) {
            for (Client client : clients) {
                s += ("\n - " + client.getFirstName() + " " + client.getLastName() + "  ID: " + client.getId());
            }
        } else {
            s += "\n No clients assigned.";
        }

        s += "\n\n Technicians:";
        if (technicians != null && !technicians.isEmpty()) {
            for (Technician technician : technicians) {
                s += ("\n - " + technician.getFirstName() + " " + technician.getLastName() + "  ID: " + technician.getId());
            }
        } else {
            s += "\n No technicians assigned.";
        }
        
        s += "\n\n Admin IDs:";
        if (AdminID != null && !AdminID.isEmpty()) {
            for (Integer adminId : AdminID) {
                s += ("\n - Admin ID: " + adminId);
            }
        } else {
            s += "\n No admins.";
        }

        return s;
    }

}
