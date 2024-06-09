package Electro_Bill;

import java.util.*;

public class Client extends User implements Observer {
    private int billOnTimeCounter;
    private int billPastTimeCounter;
    private boolean isInWhitelist;
    private boolean isInBlacklist;
    private boolean subsEnded;
    private ArrayList<Integer> billsID;
    private ArrayList<Integer> complaintsID;
    private ArrayList<Integer> feedbacksID;
    private ArrayList<Integer> appointmentsID;
    private ArrayList<Integer> discountsID;

    public Client(int billOnTimeCounter, int billPastTimeCounter, boolean isInWhitelist, boolean isInBlacklist, boolean subsEnded, ArrayList<Integer> bills, ArrayList<Integer> complaints, ArrayList<Integer> feedbacks, ArrayList<Integer> appointments, ArrayList<Integer> discounts, int id, String firstName, String lastName, String email, String password, String address) {
        super(id, firstName, lastName, email, password, address);
        this.billOnTimeCounter = billOnTimeCounter;
        this.billPastTimeCounter = billPastTimeCounter;
        this.isInWhitelist = isInWhitelist;
        this.isInBlacklist = isInBlacklist;
        this.subsEnded = subsEnded;
        this.billsID = bills;
        this.complaintsID = complaints;
        this.feedbacksID = feedbacks;
        this.appointmentsID = appointments;
        this.discountsID = discounts;
    }

    public int getBillOnTimeCounter() {
        return billOnTimeCounter;
    }

    public void setBillOnTimeCounter(int billOnTimeCounter) {
        this.billOnTimeCounter = billOnTimeCounter;
    }

    public int getBillPastTimeCounter() {
        return billPastTimeCounter;
    }

    public void setBillPastTimeCounter(int billPastTimeCounter) {
        this.billPastTimeCounter = billPastTimeCounter;
    }

    public boolean isIsInWhitelist() {
        return isInWhitelist;
    }

    public void setIsInWhitelist(boolean isInWhitelist) {
        this.isInWhitelist = isInWhitelist;
    }

    public boolean isIsInBlacklist() {
        return isInBlacklist;
    }

    public void setIsInBlacklist(boolean isInBlacklist) {
        this.isInBlacklist = isInBlacklist;
    }

    public boolean isSubsEnded() {
        return subsEnded;
    }

    public void setSubsEnded(boolean subsEnded) {
        this.subsEnded = subsEnded;
    }

    public ArrayList<Integer> getBillsID() {
        return billsID;
    }

    public void setBillsID(ArrayList<Integer> billsID) {
        this.billsID = billsID;
    }

    public ArrayList<Integer> getComplaintsID() {
        return complaintsID;
    }

    public void setComplaintsID(ArrayList<Integer> complaintsID) {
        this.complaintsID = complaintsID;
    }

    public ArrayList<Integer> getFeedbacksID() {
        return feedbacksID;
    }

    public void setFeedbacksID(ArrayList<Integer> feedbacksID) {
        this.feedbacksID = feedbacksID;
    }

    public ArrayList<Integer> getAppointmentsID() {
        return appointmentsID;
    }

    public void setAppointmentsID(ArrayList<Integer> appointmentsID) {
        this.appointmentsID = appointmentsID;
    }

    public ArrayList<Integer> getDiscountsID() {
        return discountsID;
    }

    public void setDiscountsID(ArrayList<Integer> discountsID) {
        this.discountsID = discountsID;
    }

    
    
    public void incrementBillPastTimeCounter() {
        this.billPastTimeCounter++;
    }
    
    public void incrementBillOnTimeCounter() {
        this.billOnTimeCounter++;
    }
    
    public void addToBlacklist() {
        if (this.billPastTimeCounter >= 3 && !this.isInBlacklist && !this.isInWhitelist) {
            this.isInBlacklist = true;
            this.billOnTimeCounter = 0;
            System.out.println("Client " + getFirstName() + " " + getLastName() + " has been added to the blacklist due to repeated late payments.");
        } else {
            if (this.isInBlacklist) {
                System.out.println("Client " + getFirstName() + " " + getLastName() + " is already blacklisted.");
            } else if (this.isInWhitelist) {
                System.out.println("Client " + getFirstName() + " " + getLastName() + " is whitelisted and cannot be blacklisted.");
            } else {
                System.out.println("Client " + getFirstName() + " " + getLastName() + " has not reached the threshold (3 late payments) for blacklisting.");
            }
        }
    }
    
    public void addToWhitelist() {
    if (this.billOnTimeCounter >= 5 && !this.isInWhitelist && !this.isInBlacklist) {
        this.isInWhitelist = true;
        this.billPastTimeCounter = 0;
        System.out.println("Client " + getFirstName() + " " + getLastName() + " has been added to the whitelist successfully.");
    } else {
        if (this.isInWhitelist) {
            System.out.println("Client " + getFirstName() + " " + getLastName() + " is already whitelisted.");
        } else if (this.isInBlacklist) {
            System.out.println("Client " + getFirstName() + " " + getLastName() + " is blacklisted and cannot be whitelisted.");
        } else {
            System.out.println("Client " + getFirstName() + " " + getLastName() + " has not reached the threshold (5 on-time payments) for whitelisting.");
        }
    }
}
    
    public void endSubs() {
        if (this.billPastTimeCounter >= 5 && this.isInBlacklist) {
            this.subsEnded = true;
            this.billPastTimeCounter = 0;
            this.billOnTimeCounter = 0;
            System.out.println("Subscription for client " + getFirstName() + " " + getLastName() + " has been ended due to repeated late payments and being on the blacklist.");
        } else {
            if (!this.isInBlacklist) {
                System.out.println("Client " + getFirstName() + " " + getLastName() + " is not blacklisted. Subscription cannot be ended.");
            } else {
                System.out.println("Client " + getFirstName() + " " + getLastName() + " has not reached the threshold (5 late payments) for ending the subscription.");
            }
        }
    }
    
    public void removeFromBlacklist() {
        if (this.isInBlacklist) {
            this.isInBlacklist = false;
            this.billPastTimeCounter = 0;

            System.out.println("Client " + getFirstName() + " " + getLastName() + 
                               " has been removed from the blacklist and their payment record has been reset.");
        } else {
            System.out.println("Client " + getFirstName() + " " + getLastName() + " is not on the blacklist.");
        }
    }
    
    public void removeFromWhitelist() {
    if (this.isInWhitelist) {
        this.isInWhitelist = false;
        this.billOnTimeCounter = 0;

        System.out.println("Client " + getFirstName() + " " + getLastName() + 
                           " has been removed from the whitelist and their on-time payment record has been reset.");
    } else {
        System.out.println("Client " + getFirstName() + " " + getLastName() + " is not on the whitelist.");
    }
}
    
    public void viewClientDetails() {
        System.out.println("Client ID: " + getId());
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Email: " + getEmail());
        System.out.println("Address: " + getAddress());
        System.out.println("Registration Date: " + getRegisterDate());
        System.out.println("Bill Payments On Time: " + getBillOnTimeCounter());
        System.out.println("Bill Payments Past Time: " + getBillPastTimeCounter());
        System.out.println("Is in Whitelist: " + (isIsInWhitelist() ? "Yes" : "No"));
        System.out.println("Is in Blacklist: " + (isIsInBlacklist() ? "Yes" : "No"));
        System.out.println("Subscription Ended: " + (isSubsEnded() ? "Yes" : "No"));
        System.out.println("Number of Bills: " + (billsID != null ? billsID.size() : 0));
        System.out.println("Number of Appointments: " + (appointmentsID != null ? appointmentsID.size() : 0));
        System.out.println("Number of Feedbacks: " + (feedbacksID != null ? feedbacksID.size() : 0));
        System.out.println("Number of Complaints: " + (complaintsID != null ? complaintsID.size() : 0));
        System.out.println("Number of Discounts: " + (discountsID != null ? discountsID.size() : 0));
    }
    
    public String stringViewClientDetails() {
        String s = ( "\n Client ID: " + getId()
                   + "\n Name: " + getFirstName() + " " + getLastName()
                   + "\n Email: " + getEmail()
                   + "\n Address: " + getAddress()
                   + "\n Registration Date: " + getRegisterDate()
                   + "\n Bill Payments On Time: " + getBillOnTimeCounter()
                   + "\n Bill Payments Past Time: " + getBillPastTimeCounter()
                   + ("\n Is in Whitelist: " + (isIsInWhitelist() ? "Yes" : "No"))
                   + ("\n Is in Blacklist: " + (isIsInBlacklist() ? "Yes" : "No"))
                   + ("\n Subscription Ended: " + (isSubsEnded() ? "Yes" : "No"))
                   + ("\n Number of Bills: " + (billsID != null ? billsID.size() : 0))
                   + ("\n Number of Appointments: " + (appointmentsID != null ? appointmentsID.size() : 0))
                   + ("\n Number of Feedbacks: " + (feedbacksID != null ? feedbacksID.size() : 0))
                   + ("\n Number of Complaints: " + (complaintsID != null ? complaintsID.size() : 0))
                   + ("\n Number of Discounts: " + (discountsID != null ? discountsID.size() : 0)));
        return s;
    }


    
    @Override
    public void update(String s) {
        System.out.println("You got new promocode " + s);
    }
}
