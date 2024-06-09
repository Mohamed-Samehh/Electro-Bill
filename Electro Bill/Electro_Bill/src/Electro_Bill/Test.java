package Electro_Bill;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {
//
    public static void main(String[] args) throws ParseException, SQLException {
//        DB db = new DB();
        
//        ArrayList<Complaint> complaints = db.getAllComplaints();
//        for(Complaint complaint:complaints){
//            complaint.viewComplaint();
//        }
        
//        ArrayList<Feedback> feedbacks = db.getAllFeedbacks();
//        for(Feedback feedback:feedbacks){
//            feedback.viewFeedback();
//        }

//        Date date = new Date();
//        Appointment a = new Appointment(10, date, "Maadi", 1, 2);
//        db.addAppointment(a);
//        db.getLastManager().viewManagerDetails();
//        db.getLastTechnician().viewTechnicianDetails();
//        db.getLastAdmin().viewAdminDetails();
//        Technician t = new Technician(100, null, 3, "Ahmed", "Mohamed", "Am@gmail.com", "12345", "maadi");
//db.addTechnician(t);
//System.out.println(db.getTechnicianIdByAddress("tEEBa"));
//System.out.println(db.getTechnicianIdByAddress("mAadi"));
        
//        ArrayList<Bill> bills = db.getAllPaidBillsByCID(1);
//        for (Bill bo : bills) {
//            bo.viewBillDetails();
//}
        
//          Payment p = new Payment(20, "71333", "Omar", "344", 344, 1, 1);
//          db.addPayment(p);
//            System.out.println(db.getPayment(1).viewPaymentDetails());


//        System.out.println(db.getBill(1).getAmount());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = sdf.parse("2024-09-11");
//        
//        Appointment a1 = new Appointment(0, date, "Maadi", 1, 1);
//        db.addAppointment(a1);
//        Appointment a2 = new Appointment(0, date, "Maadi", 1, 1);
//        db.addAppointment(a2);
//        Appointment a3 = new Appointment(0, date, "Maadi", 1, 1);
//        db.addAppointment(a3);
//        Appointment a4 = new Appointment(0, date, "Maadi", 1, 1);
//        db.addAppointment(a4);
//        Appointment a5 = new Appointment(0, date, "Maadi", 1, 1);
//        db.addAppointment(a5);
//        Appointment a6 = new Appointment(0, date, "Maadi", 1, 1);
//        db.addAppointment(a6);
//        
//        Feedback f = new Feedback(3, "I hate you", 3.3, 2);
//        db.addFeedback(f);

//ArrayList<Feedback> feed = db.getAllFeedbacksByCID(1);
//
//for (Feedback app : feed) {
//            app.viewFeedback();
//}

//ArrayList<Bill> b = db.getAllBillsByCID(4);
//int x = 2;
//for (Bill app : b) {
//            db.markBillPaid(x);
//            app.viewBillDetails();
//            System.out.println("");
//            x += 1;
//}

//Client c = new Client(0, 0, false,false, false, null, null, null, null, null, 4, "Mohamed", "Sameh", "wyy", "sss", "maadi");
//        db.addClient(c);
//        c.setPassword("12344");

//Bill b1 = new Bill(20, 0, 100.22, 1, null);
//db.addBill(b1);
//Bill b2 = new Bill(20, 0, 100.22, 1, null);
//db.addBill(b2);
//Bill b3 = new Bill(20, 0, 100.22, 1, null);
//db.addBill(b3);
//Bill b4 = new Bill(20, 0, 100.22, 1, null);
//db.addBill(b4);
//Bill b5 = new Bill(20, 0, 100.22, 1, null);
//db.addBill(b5);
//Bill b6 = new Bill(20, 0, 100.22, 1, null);
//db.addBill(b6);
//System.out.println(db.getDiscount(1).stringViewDiscountDetails());
//db.addBill(b);
//db.calculateAmount(20);
//
//DiscountInstance d = new DiscountInstance(10, 30, 1, null, null);
//db.addDiscount(d);
//db.getDiscount(2).viewDiscountDetails();
//
//db.applyDiscountToBill(20, 10);
//        
//db.getDiscount(10).viewDiscountDetails();

//db.getBill(1).markPaid();
//
//DiscountInstance d = new DiscountInstance(1, 15, 1, null, null);
//db.addDiscount(d);

//DiscountInstance d = new DiscountInstance(4, 15, 1, null, null);
//db.addDiscount(d);
//
//DiscountInstance dd = new DiscountInstance(6, 15, 1, null, null);
//db.addDiscount(dd);
//DiscountInstance d2 = new DiscountInstance(7, 15, 2, null, null);
//db.addDiscount(d2);
//
//db.getDiscount(7).viewDiscountDetails();
//DiscountInstance d2 = new DiscountInstance(11, 15, 1, null, null);
//db.addDiscount(d2);
//db.activateDiscount(11, 1);
//db.applyDiscountToBill(1, 8);

//Client cl = db.getClient(15);
//ArrayList<Integer> discounts = cl.getDiscountsID();
//for (Integer discount : discounts) {
//    System.out.println(discount);
//}

//db.getDiscount(13).viewDiscountDetails();

//DiscountInstance d = new DiscountInstance(1, 15, 1, null, null);
//db.addDiscount(d);
//db.calculateAmount(1);
//db.activateDiscount(1,1);
//db.applyDiscountToBill(1,1);

//db.getBill(1).viewBillDetails();
//Manager m = new Manager(100, null, null, null, 1, "Mohamed", "Sameh", "Mohamed@gmail.com", "12345", "Maadi");
//db.addManager(m);

//Feedback f = new Feedback(2, "Nice", 10, 1);
//db.addFeedback(f);
//db.getFeedback(2).viewFeedback();

//Complaint c = new Complaint(1, "Bad Service", 1);
//db.addComplaint(c);
//db.getComplaint(1).viewComplaint();



//db.getClient(1).viewClientDetails();


//db.editAppointmentDate(4, "2023-6-5");
//ArrayList<Complaint> c = db.getAllComplaintsByCID(2);
//for (Complaint app : c) {
//            app.viewComplaint();
//}

//Complaint c = new Complaint(3, "I hate you tany", 2);
//        db.addComplaint(c);

//Technician t = new Technician(100, null, 3, "Ahmed", "Mohamed", "Am@gmail.com", "12345", "Maadi");
//db.addTechnician(t);
//ArrayList<AppointmentReadOnly> app = db.getTechnician(3).getAppointments();
//for (AppointmentReadOnly one:app){
//    one.viewAppointmentDetails();   
//}

//ArrayList <Appointment> apps = db.getAllAppointmentsByCID(3);
//int x=4;
//for (Appointment app : apps) {
//            db.updateAppointmentDT(x, date);
//            x+=1;
//}

//DiscountInstance d = new DiscountInstance(3, 15, 4, null, null);
//d.viewDiscountDetails();

//Client c = new Client(0, 0, false,false, false, null, null, null, null, null, null, 4, firstName, lastName, email, password, address);
//        db.addClient(c);
//        db.updateClientWlist(true, 3);
//ArrayList <DiscountInstance> dis = db.getAllDiscounts();

//Admin a = new Admin(null, null, null, 100, 1, "Mohamed", "Sameh", "moha@gmail.com", "12345", "Maadi");
//db.addAdmin(a);
//db.getAdmin(1).viewAdminDetails();

//for (DiscountInstance di : dis) {
//    
//            di.viewDiscountDetails();       
//}

//DiscountInstance d2 = new DiscountInstance(2, 15, 1, null, null);
//db.addDiscount(d2);

//Bill b = new Bill(12, 1, 100.22, 1, null);
//db.addBill(b);
//db.addBill(b);
//db.addBill(b);
//db.addBill(b);
//db.addBill(b);
//db.addBill(b);
//db.activateDiscount(2, 1);
//db.applyDiscountToBill(3, 2);
//db.markBillPaid(12);
//Client c = new Client(0, 0, false,false, false, null, null, null, null, null, 2, "Mohamed", "Sameh", "moha@gmail.com", "12345", "Maadi");
//db.addClient(c);
//db.getClient(2).viewClientDetails();
//
//Client cc = new Client(0, 0, false,true, false, null, null, null, null, null, 3, "Mohamed", "Sameh", "moha@gmail.com", "12345", "Maadi");
//db.addClient(cc);
//db.getClient(3).viewClientDetails();

//ArrayList<Client> bb = db.getAllClients();
//for (Client one : bb) {
//            one.viewClientDetails();
//            System.out.println("");
//}

//db.getManager(1).viewPastDue();

//db.calculateAmount(16);


//DiscountInstance d = new DiscountInstance(5, 15, 4, null, null);
//db.addDiscount(d);
//db.getDiscount(5);
//db.applyDiscountToBill(16, 5);

//Bill s = db.getBill(1);
//s.viewBillDetails();
//db.updateBillAmountAutomatically(1);
//
//db.removeFromWhitelist(3);
//db.addToBlacklist(3);
//db.markBillPaid(1);
//db.addTechnician(t);
//t.viewTechnicianDetails();

//ArrayList<Technician> ps = db.getAllTechnicians();
//for (Technician app : ps) {
//            app.viewTechnicianDetails();
//}


//db.getAppointment(4).viewAppointmentDetails();
        
//
//        Complaint c = new Complaint(1, date, "Bad Service", 2);
//        db.addComplaint(c);
//        db.getComplaint(1).viewComplaint();
    }
}
