package Electro_Bill;

import java.util.*;
import java.text.SimpleDateFormat;

public class Bill {
    private int Bill_ID;
    private double amount;
    private Date issueDate;
    private BillStatus status;
    private int ElectricMeterNo;
    private double consumedWatt;
    private final double wattPrice;
    private int clientID;
    private Client client;
    private Date deadline;
    private Date payDate;
    private boolean pastDue;

    public Bill(int Bill_ID, int ElectricMeterNo, double consumedWatt, int clientID, Client client) {
        this.Bill_ID = Bill_ID;
        this.amount = 0;
        this.issueDate = new Date();
        this.status = BillStatus.pending;
        this.ElectricMeterNo = ElectricMeterNo;
        this.consumedWatt = consumedWatt;
        this.wattPrice = 10;
        this.clientID = clientID;
        this.client = client;
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueDate);
        calendar.add(Calendar.MONTH, 1);
        this.deadline = calendar.getTime();
        
        this.pastDue = false;
    }

    public int getBill_ID() {
        return Bill_ID;
    }

    public void setBill_ID(int Bill_ID) {
        this.Bill_ID = Bill_ID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public int getElectricMeterNo() {
        return ElectricMeterNo;
    }

    public void setElectricMeterNo(int ElectricMeterNo) {
        this.ElectricMeterNo = ElectricMeterNo;
    }

    public double getConsumedWatt() {
        return consumedWatt;
    }

    public void setConsumedWatt(double consumedWatt) {
        this.consumedWatt = consumedWatt;
    }

    public double getWattPrice() {
        return wattPrice;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
    
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    
    public Date getDeadline() {
        return deadline;
    }

    public boolean isPastDue() {
        return pastDue;
    }

    public void setPastDue(boolean pastDue) {
        this.pastDue = pastDue;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }
    
    public void viewBillDetails() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("Bill ID: " + Bill_ID);
        System.out.println("Amount: $" + amount);
        System.out.println("Issue Date: " + dateFormat.format(issueDate));
        System.out.println("Electric Meter No.: " + ElectricMeterNo);
        System.out.println("Consumed Watts: " + consumedWatt);
        System.out.println("Watt Price: $" + wattPrice);
        System.out.println("Client ID: " + clientID);

        if (client != null) {
            System.out.println("Client: " + client.getFirstName() + " " + client.getLastName());
        } else {
            System.out.println("Client: Unknown");
        }
        
        System.out.println("Payment Deadline: " + dateFormat.format(deadline));
        System.out.println("Past Due: " + (pastDue ? "Yes" : "No"));

        if (payDate != null) {
            System.out.println("Pay Date: " + dateFormat.format(payDate));
        } else {
            System.out.println("Pay Date: Not yet paid");
        }
    }
    
    public String stringViewBillDetails() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String s = ( "\n Bill ID: " + Bill_ID
                   + "\n Amount: $" + amount
                   + "\n Issue Date: " + dateFormat.format(issueDate)
                   + "\n Electric Meter No.: " + ElectricMeterNo
                   + "\n Consumed Watts: " + consumedWatt
                   + "\n Watt Price: $" + wattPrice
                   + "\n Client ID: " + clientID
                   + "\n Payment Deadline: " + dateFormat.format(deadline));
        
        if (client != null) {
            s = (s + "\n Client: " + client.getFirstName() + " " + client.getLastName());
        } else {
            s = (s + "\n Client: Unknown");
        }
                   
                   
        
        if(pastDue){ s = s + "\n Past Due: Yes"; }
        else if(!pastDue && payDate != null){ s = s + "\n Past Due: No"; }
        
        if(payDate != null){s = s + ("\n Pay Date: " + dateFormat.format(payDate));}
        else{s = s + ("\n Pay Date: Not yet paid");}
        return s;
    }
    
    public void markPaid() {
        Date now = new Date();
        this.payDate = now;

        if (this.status == BillStatus.pending) {
            this.status = BillStatus.paid;
            System.out.println("Bill ID: " + this.Bill_ID + " has been marked as paid on " + now);

            if (!now.before(this.deadline)) {
                this.pastDue = true;
                if (this.client != null) {
                    this.client.incrementBillPastTimeCounter();
                    if (this.client.isIsInWhitelist()) {
                        this.client.removeFromWhitelist();
                    }
                }
            } else {
                if (this.client != null) {
                    this.client.incrementBillOnTimeCounter();
                    if (this.client.isIsInBlacklist()) {
                        this.client.removeFromBlacklist();
                    }
                }
            }
        } else {
            System.out.println("Bill ID: " + this.Bill_ID + " is already marked paid.");
        }
    }
    
    public void applyDiscount(Discount discount) {
        Date now = new Date();

        if (this.client != null && discount.getClient() != null && this.client.equals(discount.getClient())) {

            if (!now.before(discount.getStartDate()) && now.before(discount.getEndDate()) && discount.isActivated()) {
                this.amount -= this.amount * (discount.getPercentage() / 100.0);
                System.out.println("Discount applied. New amount: $" + this.amount);
            } else {
                System.out.println("Discount is not applicable, or current date is outside the valid period.");
            }
        } else {
            System.out.println("Discount cannot be applied. Bill's client and discount's client do not match or one is null.");
        }
    }
}
