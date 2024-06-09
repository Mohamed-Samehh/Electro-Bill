package Electro_Bill;

public interface Subject {
    public void addObserver(Observer obser);        
   
    public void removeObserver(Observer obser);       
 
    public void updateAll(String s);
}
