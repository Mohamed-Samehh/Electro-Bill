package Electro_Bill;

import java.util.*;

public class Manager extends User {
    private double salary;
    private ArrayList<Bill> bills;
    private ArrayList<DiscountInstance> discounts;
    private ArrayList<Client> clients;

    public Manager(double salary, ArrayList<Bill> bills, ArrayList<DiscountInstance> discounts, ArrayList<Client> clients, int id, String firstName, String lastName, String email, String password, String address) {
        super(id, firstName, lastName, email, password, address);
        this.salary = salary;
        this.bills = bills;
        this.discounts = discounts;
        this.clients = clients;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public ArrayList<Bill> getBills() {
        return bills;
    }

    public void setBills(ArrayList<Bill> bills) {
        this.bills = bills;
    }

    public ArrayList<DiscountInstance> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(ArrayList<DiscountInstance> discounts) {
        this.discounts = discounts;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }
    
     public void viewPastDue() {
        System.out.println("Listing all past-due bills:");
        for (Bill bill : bills) {
            if (bill.isPastDue()) {
                bill.viewBillDetails();
                System.out.println("");
            }
        }
    }
     
     public void viewUnpaidBills() {
        System.out.println("Listing all unpaid bills:");
        for (Bill bill : bills) {
            if (bill.getPayDate() == null) {
                bill.viewBillDetails();
                System.out.println("");
            }
        }
    }
     
     public void viewPaidBills() {
        System.out.println("Listing all paid bills:");
        for (Bill bill : bills) {
            if (bill.getPayDate() != null) {
                bill.viewBillDetails();
                System.out.println("");
            }
        }
    }
     
     public void viewBlacklist() {
        System.out.println("Listing all blacklisted clients:");
        for (Client client : clients) {
            if (client.isIsInBlacklist()) {
                client.viewClientDetails();
                System.out.println("");
            }
        }
    }
     
     public void viewWhitelist() {
        System.out.println("Listing all whitelisted clients:");
        for (Client client : clients) {
            if (client.isIsInWhitelist()) {
                client.viewClientDetails();
                System.out.println("");
            }
        }
    }
     
     public void viewManagerDetails() {
        System.out.println("Manager ID: " + getId());
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Email: " + getEmail());
        System.out.println("Address: " + getAddress());
        System.out.println("Registered On: " + getRegisterDate());
        System.out.println("Salary: $" + salary);
        System.out.println("Number of Managed Bills: " + (bills != null ? bills.size() : 0));
        System.out.println("Number of Managed Discounts: " + (discounts != null ? discounts.size() : 0));
        System.out.println("Number of Managed Clients: " + (clients != null ? clients.size() : 0));
    }
     
     public String stringViewManagerDetails() {
        String s = ( "\n Manager ID: " + getId()
                   + "\n Name: " + getFirstName() + " " + getLastName()
                   + "\n Email: " + getEmail()
                   + "\n Address: " + getAddress()
                   + "\n Registered On: " + getRegisterDate()
                   + "\n Salary: $" + salary
                   + "\n Number of Managed Bills: " + (bills != null ? bills.size() : 0)
                   + "\n Number of Managed Discounts: " + (discounts != null ? discounts.size() : 0)
                   + "\n Number of Managed Clients: " + (clients != null ? clients.size() : 0));
        return s;
    }
}
