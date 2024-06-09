package Electro_Bill;

public class Payment{
    private int ID;
    private String cardNo;
    private String cardHolderName;
    private String cvv;
    private double amount;
    private int clientID;
    private int billID;

    public Payment(int ID, String cardNo, String cardHolderName, String cvv, double amount, int clientID, int billID) {
        this.ID = ID;
        this.cardNo = cardNo;
        this.cardHolderName = cardHolderName;
        this.cvv = cvv;
        this.amount = amount;
        this.clientID = clientID;
        this.billID = billID;
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public String viewPaymentDetails() {
        return "Payment ID: " + ID +
               "\n Card Number: " + cardNo +
               "\n Card Holder Name: " + cardHolderName +
               "\n CVV: " + cvv +
               "\n Amount: " + amount +
               "\n Client ID: " + clientID +
               "\n Bill ID: " + billID;
    }
}
