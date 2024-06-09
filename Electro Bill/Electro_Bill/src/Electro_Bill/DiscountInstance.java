package Electro_Bill;

import java.util.*;

public class DiscountInstance extends Discount {

    public DiscountInstance(int id, double percentage, int clientID, Client client, ArrayList<Observer> observers) {
        super(id, percentage, clientID, client, observers);
    }

    @Override
    public void viewDiscountDetails() {
        System.out.println("Discount ID: " + getId());
        System.out.println("Discount Percentage: " + getPercentage() + "%");
        System.out.println("Valid From: " + getStartDate());
        System.out.println("Valid Until: " + getEndDate());
        System.out.println("Promo Code: " + getPromoCode());
        System.out.println("Client ID: " + getClientID());
        if (getClient() != null) {
            System.out.println("Client: " + getClient().getFirstName() + " " + getClient().getLastName());
        } else {
            System.out.println("You are not whitelisted");
        }
        System.out.println("Active: " + (activated ? "Yes" : "No"));
    }
    
    @Override
    public String stringViewDiscountDetails() {
        String s = ( "\n Discount ID: " + getId()
                   + "\n Discount Percentage: " + getPercentage() + "%"
                   + "\n Valid From: " + getStartDate()
                   + "\n Valid Until: " + getEndDate()
                   + "\n Promo Code: " + getPromoCode()
                   + "\n Active: " + (activated ? "Yes" : "No")
                   + "\n Client ID: " + getClientID());
                   
        if (getClient() != null) {
            s = (s + "\n Client: " + getClient().getFirstName() + " " + getClient().getLastName());
        } else {
            s = (s + "\n Client is not whitelisted, but the discount is still usable if it's still active");
        }
        return s;
    }
    
    @Override
    public void Activate() {
        if (client != null && client.isIsInWhitelist()) {
            System.out.println("Client " + client.getFirstName() + " " + client.getLastName() + " is whitelisted and eligible for the discount.");
            this.activated = true;
        } else {
            System.out.println("Client is not whitelisted. Discount is not activated.");
            this.activated = false;
        }
    }
}