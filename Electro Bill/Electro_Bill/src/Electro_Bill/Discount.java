package Electro_Bill;

import java.util.*;

public abstract class Discount implements Subject{
    int id;
    double percentage;
    Date startDate;
    Date endDate;
    int clientID;
    Client client;
    private ArrayList<Observer> observers;
    private String promoCode;
    boolean activated;

    public Discount(int id, double percentage, int clientID, Client client, ArrayList<Observer> observers) {
        this.id = id;
        this.percentage = percentage;
        this.startDate = new Date();
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH, 1);
        this.endDate = calendar.getTime();
        
        this.clientID = clientID;
        
        if (client != null && client.isIsInWhitelist()) {
            this.client = client;
            this.activated = true;
        } else {
            this.client = null;
            this.activated = false;
        }
        
        this.observers = observers;
        this.promoCode = generatePromoCode(5);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
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
        if (client != null && client.isIsInWhitelist()) {
            this.client = client;
            System.out.println("Client " + client.getFirstName() + " " + client.getLastName() + " is now eligible for the discount.");
        } else {
            System.out.println("Discount cannot be assigned. Client must be on the whitelist.");
        }
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<Observer> observers) {
        this.observers = observers;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }
    
    public String getPromoCode() {
        return promoCode;
    }
    
    public void addPromocode(){ 
    updateAll(getPromoCode());        
    }
    
    public boolean isActivated() {
        return activated;
    }
    
    @Override
    public void addObserver(Observer obser) {              
        observers.add(obser);
    }

    @Override
    public void removeObserver(Observer obser) {
        observers.remove(obser);
    }

    @Override
    public void updateAll(String promoCode) {        
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(promoCode);
        }
    }
    
    public abstract void Activate();
    
    public abstract void viewDiscountDetails();
    
    public abstract String stringViewDiscountDetails();
    
    
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private String generatePromoCode(int length) {
        StringBuilder builder = new StringBuilder();
        Random rand = new Random();
        while (length-- != 0) {
            int character = (int)(rand.nextDouble() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
