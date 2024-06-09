package Electro_Bill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class DB {

    private final String userName = "root";
    private final String password = "";
    private final String dbName = "se_db";

    private Connection con;

    public DB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, userName, password);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            System.err.println("DATABASE CONNECTION ERROR: " + e.toString());
        }
    }

    public void addAppointment(Appointment a) {
        String sql = "INSERT INTO appointment (appointmentID, dateTime, address, c_id, Tech_ID) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, getNextId("appointment", "appointmentID"));
            java.util.Date utilDate = a.getDateTime();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pstmt.setDate(2, sqlDate);
            pstmt.setString(3, a.getAddress());
            pstmt.setInt(4, a.getClientID());
            pstmt.setInt(5, a.getTechID());
            pstmt.executeUpdate();
            System.out.println("Appointment added to database");
        } catch (SQLException e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.getMessage());
        }
    }
    
    public Appointment getAppointment(int id) {
        Appointment a = null;
        String sql = "SELECT * FROM appointment WHERE appointmentID = ?";
    
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
        
                if (rs.next()) {
                    a = new Appointment(
                        rs.getInt("appointmentID"),
                        rs.getDate("dateTime"),
                        rs.getString("address"),
                        rs.getInt("c_id"),
                        rs.getInt("Tech_ID")
                    );
                }
            } catch (SQLException e) {
                System.err.println("SQL ERROR: " + e.toString());
            }
            return a;
        }
    
    public void editAppointmentDate(int appointmentId, String newDateInput) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date newDate = formatter.parse(newDateInput);
            Date now = new Date();

            if (newDate.after(now)) {
                String sql = "UPDATE appointment SET dateTime = ? WHERE appointmentID = ?";
                try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                    pstmt.setDate(1, new java.sql.Date(newDate.getTime()));
                    pstmt.setInt(2, appointmentId);
                    int affectedRows = pstmt.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Appointment date updated successfully.");
                    } else {
                        System.out.println("No appointment found with ID: " + appointmentId);
                    }
                }
            } else {
                System.out.println("Cannot set a past date for the appointment.");
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use the format yyyy-MM-dd.");
        } catch (SQLException e) {
            System.err.println("DATABASE UPDATE ERROR: " + e.getMessage());
        }
    }
    
    public String StringEditAppointmentDateCID(int appointmentId, int clientId, String newDateInput) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String s = "";
    try {
        Date newDate = formatter.parse(newDateInput);
        Date now = new Date();

        if (newDate.after(now)) {
            String sql = "UPDATE appointment SET dateTime = ? WHERE appointmentID = ? AND c_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setDate(1, new java.sql.Date(newDate.getTime()));
                pstmt.setInt(2, appointmentId);
                pstmt.setInt(3, clientId);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    s = ("Appointment date updated successfully.");
                } else {
                    s = ("No appointment found with ID: " + appointmentId + " and Client ID: " + clientId);
                }
            }
        } else {
            s = ("Cannot set a past date for the appointment.");
        }
    } catch (ParseException e) {
        s = ("Invalid date format. Please use the format yyyy-MM-dd.");
    } catch (SQLException e) {
        System.err.println("DATABASE UPDATE ERROR: " + e.getMessage());
    }
    return s;
}

    public ArrayList<AppointmentReadOnly> getAllAppointmentsByTID(int ID) {
        ArrayList<AppointmentReadOnly> result = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from appointment where Tech_ID = " + ID);
            while (rs.next()) {
                result.add(new Appointment(
                        rs.getInt("appointmentID"),
                        rs.getDate("dateTime"),
                        rs.getString("address"),
                        rs.getInt("c_id"),
                        rs.getInt("Tech_ID") )
                );
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return result;
    }
    
    public ArrayList<AppointmentReadOnly> getUpcomingAppointmentsByTID(int ID) {
        ArrayList<AppointmentReadOnly> result = new ArrayList<>();
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();

            String query = "SELECT * FROM appointment WHERE Tech_ID = ? AND dateTime > ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, ID);
            pstmt.setObject(2, currentDateTime);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                result.add(new Appointment(
                        rs.getInt("appointmentID"),
                        rs.getDate("dateTime"),
                        rs.getString("address"),
                        rs.getInt("c_id"),
                        rs.getInt("Tech_ID"))
                );
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return result;
    }
    
    public ArrayList<AppointmentReadOnly> getPastAppointmentsByTID(int ID) {
        ArrayList<AppointmentReadOnly> result = new ArrayList<>();
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();

            String query = "SELECT * FROM appointment WHERE Tech_ID = ? AND dateTime < ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, ID);
            pstmt.setObject(2, currentDateTime);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                result.add(new Appointment(
                        rs.getInt("appointmentID"),
                        rs.getDate("dateTime"),
                        rs.getString("address"),
                        rs.getInt("c_id"),
                        rs.getInt("Tech_ID"))
                );
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return result;
    }
    
    public ArrayList<Appointment> getAllAppointmentsByCID(int ID) {
        ArrayList<Appointment> result = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from appointment where c_id = " + ID);
            while (rs.next()) {
                result.add(new Appointment(
                        rs.getInt("appointmentID"),
                        rs.getDate("dateTime"),
                        rs.getString("address"),
                        rs.getInt("c_id"),
                        rs.getInt("Tech_ID") )
                );
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return result;
    }
    
    public ArrayList<Appointment> getUpcomingAppointmentsByCID(int ID) {
        ArrayList<Appointment> result = new ArrayList<>();
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();

            String query = "SELECT * FROM appointment WHERE c_id = ? AND dateTime > ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, ID);
            pstmt.setObject(2, currentDateTime);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                result.add(new Appointment(
                        rs.getInt("appointmentID"),
                        rs.getDate("dateTime"),
                        rs.getString("address"),
                        rs.getInt("c_id"),
                        rs.getInt("Tech_ID"))
                );
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return result;
    }
    
    public ArrayList<Appointment> getPastAppointmentsByCID(int ID) {
        ArrayList<Appointment> result = new ArrayList<>();
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();

            String query = "SELECT * FROM appointment WHERE c_id = ? AND dateTime < ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, ID);
            pstmt.setObject(2, currentDateTime);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                result.add(new Appointment(
                        rs.getInt("appointmentID"),
                        rs.getDate("dateTime"),
                        rs.getString("address"),
                        rs.getInt("c_id"),
                        rs.getInt("Tech_ID"))
                );
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return result;
    }

    
    public void addTechnician(Technician t) {
        String sql = "INSERT INTO technician (technicianID, firstName, lastName, email, password, address, registerDate, salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, getNextId("technician", "technicianID"));
            pstmt.setString(2, t.getFirstName());
            pstmt.setString(3, t.getLastName());
            pstmt.setString(4, t.getEmail());
            pstmt.setString(5, t.getPassword());
            pstmt.setString(6, t.getAddress());
            java.util.Date utilDate = t.getRegisterDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pstmt.setDate(7, sqlDate);
            pstmt.setDouble(8, t.getSalary());
            pstmt.executeUpdate();
            System.out.println("Technician added to database");
        } catch (SQLException e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.getMessage());
        }
    }
    
    public void removeTechnician(int id) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("delete from technician where technicianID = '" + id + "'");
            System.out.println("Technician removed");
        } catch (SQLException e) {
            System.err.println("DATABASE DELETION ERROR: " + e.toString());
        }
    }
    
    public Technician getTechnician(int id) {
        Technician t = null;
        String sql = "SELECT * FROM technician WHERE technicianID = ?";
    
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
        
                if (rs.next()) {
                    ArrayList<AppointmentReadOnly> appoints = getAllAppointmentsByTID(id);
                    t = new Technician(
                        rs.getDouble("salary"),
                        appoints,
                        rs.getInt("technicianID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("address")
                    );
                    t.setRegisterDate(rs.getDate("registerDate"));
                }
            } catch (SQLException e) {
                System.err.println("SQL ERROR: " + e.toString());
            }
            return t;
        }
    
    public ArrayList<Technician> getAllTechnicians() {
        ArrayList<Technician> result = new ArrayList<>();
        String sql = "SELECT * FROM technician";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int technicianID = rs.getInt("technicianID");
                
                ArrayList<AppointmentReadOnly> a = getAllAppointmentsByTID(technicianID);
                
                Technician tech = new Technician(
                    rs.getDouble("salary"),
                    a,
                    technicianID,
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("address")
                );
                tech.setRegisterDate(rs.getDate("registerDate"));
                result.add(tech);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return result;
    }
    
    public void addClient(Client c) {
        String sql = "INSERT INTO client (clientID, firstName, lastName, email, password, address, registerDate, billOnTimeCounter, billPastTimeCounter, isInWhitelist, isInBlacklist, subsEnded) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, getNextId("client", "clientID"));
            pstmt.setString(2, c.getFirstName());
            pstmt.setString(3, c.getLastName());
            pstmt.setString(4, c.getEmail());
            pstmt.setString(5, c.getPassword());
            pstmt.setString(6, c.getAddress());
            java.util.Date utilDate = c.getRegisterDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pstmt.setDate(7, sqlDate);
            pstmt.setInt(8, c.getBillOnTimeCounter());
            pstmt.setInt(9, c.getBillPastTimeCounter());
            pstmt.setBoolean(10, c.isIsInWhitelist());
            pstmt.setBoolean(11, c.isIsInBlacklist());
            pstmt.setBoolean(12, c.isSubsEnded());
            pstmt.executeUpdate();
            System.out.println("Client added to database");
        } catch (SQLException e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.getMessage());
        }
    }
    
    public void removeClient(int id) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("delete from client where clientID  = '" + id + "'");
            System.out.println("Client removed");
        } catch (SQLException e) {
            System.err.println("DATABASE DELETION ERROR: " + e.toString());
        }
    }
    
    public Client getClient(int id) {
            Client c = null;
            String sql = "SELECT * FROM client WHERE clientID = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    ArrayList<Integer> bills = getBillIDsByClientID(id);
                    ArrayList<Integer> complaints = getComplaintIDsByClientID(id);
                    ArrayList<Integer> feedbacks = getFeedbackIDsByClientID(id);
                    ArrayList<Integer> appointments = getAppointmentIDsByClientID(id);
                    ArrayList<Integer> discounts = getDiscountIDsByClientID(id);

                    c = new Client(
                        rs.getInt("billOnTimeCounter"),
                        rs.getInt("billPastTimeCounter"),
                        rs.getBoolean("isInWhitelist"),
                        rs.getBoolean("isInBlacklist"),
                        rs.getBoolean("subsEnded"),
                        bills,
                        complaints,
                        feedbacks,
                        appointments,
                        discounts,
                        rs.getInt("clientID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("address")
                    );
                    c.setRegisterDate(rs.getDate("registerDate"));
                }
            } catch (SQLException e) {
                System.err.println("SQL ERROR: " + e.toString());
            }
            return c;
        }

        public ArrayList<Client> getAllClients() {
        ArrayList<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int clientID = rs.getInt("clientID");

                ArrayList<Integer> bills = getBillIDsByClientID(clientID);
                ArrayList<Integer> complaints = getComplaintIDsByClientID(clientID);
                ArrayList<Integer> feedbacks = getFeedbackIDsByClientID(clientID);
                ArrayList<Integer> appointments = getAppointmentIDsByClientID(clientID);
                ArrayList<Integer> discounts = getDiscountIDsByClientID(clientID);

                Client client = new Client(
                    rs.getInt("billOnTimeCounter"),
                    rs.getInt("billPastTimeCounter"),
                    rs.getBoolean("isInWhitelist"),
                    rs.getBoolean("isInBlacklist"),
                    rs.getBoolean("subsEnded"),
                    bills,
                    complaints,
                    feedbacks,
                    appointments,
                    discounts,
                    clientID,
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("address")
                );
                client.setRegisterDate(rs.getDate("registerDate"));
                clients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return clients;
    }

    
    public void removeFromBlacklist(int clientID) {
        String fetchSql = "SELECT isInBlacklist FROM client WHERE clientID = ?";
        String updateSql = "UPDATE client SET isInBlacklist = false, billPastTimeCounter = 0 WHERE clientID = ? AND isInBlacklist = true";

        try (PreparedStatement fetchStmt = con.prepareStatement(fetchSql);
             PreparedStatement updateStmt = con.prepareStatement(updateSql)) {

            fetchStmt.setInt(1, clientID);
            ResultSet rs = fetchStmt.executeQuery();

            if (rs.next()) {
                boolean isInBlacklist = rs.getBoolean("isInBlacklist");

                if (isInBlacklist) {
                    updateStmt.setInt(1, clientID);
                    int affectedRows = updateStmt.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Client ID " + clientID + " has been removed from the blacklist and their payment record has been reset.");
                    } else {
                        System.out.println("No updates performed. Check if the client ID is correct.");
                    }
                } else {
                    System.out.println("Client ID " + clientID + " is not on the blacklist.");
                }
            } else {
                System.out.println("No client found with ID: " + clientID);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE UPDATE ERROR: " + e.getMessage());
        }
    }
    
    public void addToBlacklist(int clientID) {
        String fetchSql = "SELECT billPastTimeCounter, isInBlacklist, isInWhitelist FROM client WHERE clientID = ?";
        String updateSql = "UPDATE client SET isInBlacklist = ?, billOnTimeCounter = 0 WHERE clientID = ?";

        try (PreparedStatement fetchStmt = con.prepareStatement(fetchSql);
             PreparedStatement updateStmt = con.prepareStatement(updateSql)) {

            fetchStmt.setInt(1, clientID);
            ResultSet rs = fetchStmt.executeQuery();

            if (rs.next()) {
                int billPastTimeCounter = rs.getInt("billPastTimeCounter");
                boolean isInBlacklist = rs.getBoolean("isInBlacklist");
                boolean isInWhitelist = rs.getBoolean("isInWhitelist");

                if (billPastTimeCounter >= 3 && !isInBlacklist && !isInWhitelist) {
                    updateStmt.setBoolean(1, true);
                    updateStmt.setInt(2, clientID);
                    updateStmt.executeUpdate();
                    System.out.println("Client ID " + clientID + " has been added to the blacklist due to repeated late payments.");
                } else {
                    if (isInBlacklist) {
                        System.out.println("Client ID " + clientID + " is already blacklisted.");
                    } else if (isInWhitelist) {
                        System.out.println("Client ID " + clientID + " is whitelisted and cannot be blacklisted.");
                    } else {
                        System.out.println("Client ID " + clientID + " has not reached the threshold (3 late payments) for blacklisting.");
                    }
                }
            } else {
                System.out.println("No client found with ID: " + clientID);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE UPDATE ERROR: " + e.getMessage());
        }
    }
    
    public void addToWhitelist(int clientID) {
        String fetchSql = "SELECT billOnTimeCounter, isInWhitelist, isInBlacklist FROM client WHERE clientID = ?";
        String updateSql = "UPDATE client SET isInWhitelist = ?, billPastTimeCounter = 0 WHERE clientID = ?";

        try (PreparedStatement fetchStmt = con.prepareStatement(fetchSql);
            PreparedStatement updateStmt = con.prepareStatement(updateSql)) {
         
            fetchStmt.setInt(1, clientID);
            ResultSet rs = fetchStmt.executeQuery();

            if (rs.next()) {
                int billOnTimeCounter = rs.getInt("billOnTimeCounter");
                boolean isInWhitelist = rs.getBoolean("isInWhitelist");
                boolean isInBlacklist = rs.getBoolean("isInBlacklist");

                if (!isInWhitelist && billOnTimeCounter >= 5 && !isInBlacklist) {
                    updateStmt.setBoolean(1, true);
                    updateStmt.setInt(2, clientID);
                    updateStmt.executeUpdate();
                    System.out.println("Client ID " + clientID + " has been added to the whitelist successfully.");
                } else {
                    if (isInWhitelist) {
                        System.out.println("Client ID " + clientID + " is already whitelisted.");
                    } else if (isInBlacklist) {
                        System.out.println("Client ID " + clientID + " is blacklisted and cannot be whitelisted.");
                    } else if (billOnTimeCounter < 5) {
                        System.out.println("Client ID " + clientID + " has not reached the threshold (5 on-time payments) for whitelisting.");
                    }
                }
            } else {
                System.out.println("No client found with ID: " + clientID);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE UPDATE ERROR: " + e.getMessage());
        }
    }
    
    public void removeFromWhitelist(int clientID) {
        String fetchSql = "SELECT isInWhitelist FROM client WHERE clientID = ?";
        String updateSql = "UPDATE client SET isInWhitelist = ?, billOnTimeCounter = 0 WHERE clientID = ?";

        try (PreparedStatement fetchStmt = con.prepareStatement(fetchSql);
            PreparedStatement updateStmt = con.prepareStatement(updateSql)) {

            fetchStmt.setInt(1, clientID);
            ResultSet rs = fetchStmt.executeQuery();

            if (rs.next()) {
                boolean isInWhitelist = rs.getBoolean("isInWhitelist");

                if (isInWhitelist) {
                    updateStmt.setBoolean(1, false);
                    updateStmt.setInt(2, clientID);
                    updateStmt.executeUpdate();
                    System.out.println("Client ID " + clientID + " has been removed from the whitelist and their on-time payment record has been reset.");
                } else {
                    System.out.println("Client ID " + clientID + " is not on the whitelist.");
                }
            } else {
                System.out.println("No client found with ID: " + clientID);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE UPDATE ERROR: " + e.getMessage());
        }
    }

    
    public void endSubs(int clientID) {
        String fetchSql = "SELECT billPastTimeCounter, isInBlacklist FROM client WHERE clientID = ?";
        String updateSql = "UPDATE client SET subsEnded = ?, billPastTimeCounter = 0, billOnTimeCounter = 0 WHERE clientID = ?";

        try (PreparedStatement fetchStmt = con.prepareStatement(fetchSql);
            PreparedStatement updateStmt = con.prepareStatement(updateSql)) {
         
            fetchStmt.setInt(1, clientID);
            ResultSet rs = fetchStmt.executeQuery();

            if (rs.next()) {
                int billPastTimeCounter = rs.getInt("billPastTimeCounter");
                boolean isInBlacklist = rs.getBoolean("isInBlacklist");

                if (billPastTimeCounter >= 5 && isInBlacklist) {
                    updateStmt.setBoolean(1, true);
                    updateStmt.setInt(2, clientID);
                    updateStmt.executeUpdate();
                    removeClient(clientID);
                    System.out.println("Subscription for client ID " + clientID + " has been ended due to repeated late payments and being on the blacklist.");
                
                } else {
                    if (!isInBlacklist) {
                        System.out.println("Client ID " + clientID + " is not blacklisted. Subscription cannot be ended.");
                    } else {
                        System.out.println("Client ID " + clientID + " has not reached the threshold (5 late payments) for ending the subscription.");
                    }
                }
            } else {
                System.out.println("No client found with ID: " + clientID);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE UPDATE ERROR: " + e.getMessage());
        }
    }
    
    public void addFeedback(Feedback f) {
        String sql = "INSERT INTO feedback (feedback_ID, dateTime, message, rate, CID) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, getNextId("feedback", "feedback_ID"));
            java.util.Date utilDate = f.getDateTime();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pstmt.setDate(2, sqlDate);
            pstmt.setString(3, f.getMessage());
            pstmt.setDouble(4, f.getRate());
            pstmt.setInt(5, f.getClientID());
            pstmt.executeUpdate();
            System.out.println("Feedback added to database");
        } catch (SQLException e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.getMessage());
        }
    }
    
    public Feedback getFeedback(int id) {
        Feedback f = null;
        String sql = "SELECT * FROM feedback WHERE feedback_ID  = ?";
    
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
        
                if (rs.next()) {
                    f = new Feedback(
                        rs.getInt("feedback_ID"),
                        rs.getString("message"),
                        rs.getDouble("rate"),
                        rs.getInt("CID")
                    );
                    f.setDateTime(rs.getDate("dateTime"));
                }
            } catch (SQLException e) {
                System.err.println("SQL ERROR: " + e.toString());
            }
            return f;
        }
    
    public ArrayList<Feedback> getAllFeedbacksByCID(int ID) {
        ArrayList<Feedback> result = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from feedback where CID = " + ID);
            while (rs.next()) {
                Feedback feed = new Feedback(
                        rs.getInt("feedback_ID"),
                        rs.getString("message"),
                        rs.getDouble("rate"),
                        rs.getInt("CID")
                );
                feed.setDateTime(rs.getDate("dateTime"));
                result.add(feed);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return result;
    }
    
    public ArrayList<Feedback> getAllFeedbacks() {
        ArrayList<Feedback> result = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from feedback");
            while (rs.next()) {
                Feedback feed = new Feedback(
                        rs.getInt("feedback_ID"),
                        rs.getString("message"),
                        rs.getDouble("rate"),
                        rs.getInt("CID")
                );
                feed.setDateTime(rs.getDate("dateTime"));
                result.add(feed);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return result;
    }
    
    public void addComplaint(Complaint c) {
        String sql = "INSERT INTO complaint (complaintID, dateTime, message, C_ID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, getNextId("complaint", "complaintID"));
            java.util.Date utilDate = c.getDateTime();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pstmt.setDate(2, sqlDate);
            pstmt.setString(3, c.getMessage());
            pstmt.setInt(4, c.getClientID());
            pstmt.executeUpdate();
            System.out.println("Complaint added to database");
        } catch (SQLException e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.getMessage());
        }
    }
    
    public Complaint getComplaint(int id) {
        Complaint c = null;
        String sql = "SELECT * FROM complaint WHERE complaintID  = ?";
    
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
        
                if (rs.next()) {
                    c = new Complaint(
                        rs.getInt("complaintID"),
                        rs.getString("message"),
                        rs.getInt("C_ID")
                    );
                    c.setDateTime(rs.getDate("dateTime"));
                }
            } catch (SQLException e) {
                System.err.println("SQL ERROR: " + e.toString());
            }
            return c;
        }
    
    public ArrayList<Complaint> getAllComplaintsByCID(int ID) {
        ArrayList<Complaint> result = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from complaint where C_ID = " + ID);
            while (rs.next()) {
                Complaint comp = new Complaint(
                        rs.getInt("complaintID"),
                        rs.getString("message"),
                        rs.getInt("C_ID")
                );
                comp.setDateTime(rs.getDate("dateTime"));
                result.add(comp);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return result;
    }
    
    public ArrayList<Complaint> getAllComplaints() {
        ArrayList<Complaint> result = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from complaint");
            while (rs.next()) {
                Complaint comp = new Complaint(
                        rs.getInt("complaintID"),
                        rs.getString("message"),
                        rs.getInt("C_ID")
                );
                comp.setDateTime(rs.getDate("dateTime"));
                result.add(comp);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return result;
    }
    
    public void addAdmin(Admin a) {
        String sql = "INSERT INTO admin (adminID, firstName, lastName, email, password, address, registerDate, salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, getNextId("admin", "adminID"));
            pstmt.setString(2, a.getFirstName());
            pstmt.setString(3, a.getLastName());
            pstmt.setString(4, a.getEmail());
            pstmt.setString(5, a.getPassword());
            pstmt.setString(6, a.getAddress());
            java.util.Date utilDate = a.getRegisterDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pstmt.setDate(7, sqlDate);
            pstmt.setDouble(8, a.getSalary());
            pstmt.executeUpdate();
            System.out.println("Admin added to database");
        } catch (SQLException e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.getMessage());
        }
    }
    
    public Admin getAdmin(int id) {
        Admin a = null;
        String sql = "SELECT * FROM admin WHERE adminID = ?";
    
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
        
                if (rs.next()) {
                    ArrayList<Manager> m = getAllManagers();
                    ArrayList<Client> c = getAllClients();
                    ArrayList<Technician> t = getAllTechnicians();
                    ArrayList<Integer> ad = AllAdminIDs();
                
                    a = new Admin(
                        m,
                        c,
                        t,
                        ad,
                        rs.getDouble("salary"),
                        rs.getInt("adminID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("address")
                    );
                    a.setRegisterDate(rs.getDate("registerDate"));
                }
            } catch (SQLException e) {
                System.err.println("SQL ERROR: " + e.toString());
            }
            return a;
        }
    
    public void removeAdmin(int id) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("delete from admin where adminID = '" + id + "'");
            System.out.println("Admin removed");
        } catch (SQLException e) {
            System.err.println("DATABASE DELETION ERROR: " + e.toString());
        }
    }

    
    public void addManager(Manager m) {
        String sql = "INSERT INTO manager (managerID, firstName, lastName, email, password, address, registerDate, salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, getNextId("manager", "managerID"));
            pstmt.setString(2, m.getFirstName());
            pstmt.setString(3, m.getLastName());
            pstmt.setString(4, m.getEmail());
            pstmt.setString(5, m.getPassword());
            pstmt.setString(6, m.getAddress());
            java.util.Date utilDate = m.getRegisterDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pstmt.setDate(7, sqlDate);
            pstmt.setDouble(8, m.getSalary());
            pstmt.executeUpdate();
            System.out.println("Manager added to database");
        } catch (SQLException e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.getMessage());
        }
    }
    
    public void removeManager(int id) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("delete from manager where managerID = '" + id + "'");
            System.out.println("Manager removed");
        } catch (SQLException e) {
            System.err.println("DATABASE DELETION ERROR: " + e.toString());
        }
    }
    
    public Manager getManager(int id) {
        Manager m = null;
        String sql = "SELECT * FROM manager WHERE managerID = ?";
    
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
        
                if (rs.next()) {
                    ArrayList<Bill> b = getAllBills();
                    ArrayList<DiscountInstance> d = getAllDiscounts();
                    ArrayList<Client> c = getAllClients();
                
                    m = new Manager(
                        rs.getDouble("salary"),
                        b,
                        d,
                        c,
                        rs.getInt("managerID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("address")
                    );
                    m.setRegisterDate(rs.getDate("registerDate"));
                }
            } catch (SQLException e) {
                System.err.println("SQL ERROR: " + e.toString());
            }
            return m;
        }
    
    public ArrayList<Manager> getAllManagers() {
        ArrayList<Manager> managers = new ArrayList<>();
        String sql = "SELECT * FROM manager";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ArrayList<Bill> b = getAllBills();
            ArrayList<Client> c = getAllClients();
            ArrayList<DiscountInstance> d = getAllDiscounts();

            while (rs.next()) {
                Manager manager = new Manager(
                    rs.getDouble("salary"),
                    b,
                    d,
                    c,
                    rs.getInt("managerID"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("address")
                );
                manager.setRegisterDate(rs.getDate("registerDate"));
                managers.add(manager);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return managers;
    }
    
    public void addPayment(Payment p) {
        String sql = "INSERT INTO payment (paymentID, cardNo, cardHolder, cvv, amount, clientID, billID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, getNextId("payment", "paymentID"));
            pstmt.setString(2, p.getCardNo());
            pstmt.setString(3, p.getCardHolderName());
            pstmt.setString(4, p.getCvv());
            pstmt.setDouble(5, p.getAmount());
            pstmt.setInt(6, p.getClientID());
            pstmt.setInt(7, p.getBillID());
            pstmt.executeUpdate();
            System.out.println("Payment added to database");
        } catch (SQLException e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.getMessage());
        }
    }
    
    public Payment getPayment(int id) {
        Payment p = null;
        String sql = "SELECT * FROM payment WHERE paymentID = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                p = new Payment(
                    rs.getInt("paymentID"),
                    rs.getString("cardNo"),
                    rs.getString("cardHolder"),
                    rs.getString("cvv"),
                    rs.getDouble("amount"),
                    rs.getInt("clientID"),
                    rs.getInt("billID")
                );
            }
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.toString());
        }
        return p;
    }

    
    public void addDiscount(DiscountInstance d) {
        String sql = "INSERT INTO discount (discountID, percentage, startDate, endDate, clientID, promoCode, activated) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, getNextId("discount", "discountID"));
            pstmt.setDouble(2, d.getPercentage());
            java.util.Date utilDate = d.getStartDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pstmt.setDate(3, sqlDate);
            java.util.Date utilDate2 = d.getEndDate();
            java.sql.Date sqlDate2 = new java.sql.Date(utilDate2.getTime());
            pstmt.setDate(4, sqlDate2);
            pstmt.setInt(5, d.getClientID());
            pstmt.setString(6, d.getPromoCode());
            pstmt.setBoolean(7, d.isActivated());
            pstmt.executeUpdate();
            System.out.println("Discount added to database");
        } catch (SQLException e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.getMessage());
        }
    }
    
    public DiscountInstance getDiscount(int id) {
        DiscountInstance d = null;
        String sql = "SELECT * FROM discount WHERE discountID = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int clientID = rs.getInt("clientID");
                Client client = getClient(clientID);
                double percentage = rs.getDouble("percentage");
                int discountID = rs.getInt("discountID");

                d = new DiscountInstance(
                    discountID,
                    percentage,
                    clientID,
                    client,
                    null
                );
                d.setStartDate(rs.getDate("startDate"));
                d.setEndDate(rs.getDate("endDate"));
                d.setPromoCode(rs.getString("promoCode"));
                d.Activate();
            }
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.toString());
        }
        return d;
    }
    
    public void removeDiscount(int id) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("delete from discount where discountID = '" + id + "'");
            System.out.println("Discount removed");
        } catch (SQLException e) {
            System.err.println("DATABASE DELETION ERROR: " + e.toString());
        }
    }
    
    public ArrayList<DiscountInstance> getAllDiscountsByCID(int id) {
        ArrayList<DiscountInstance> discounts = new ArrayList<>();
        String sql = "SELECT * FROM discount WHERE clientID = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int clientId = rs.getInt("clientID");
                Client client = getClient(clientId);
                DiscountInstance discountInstance = new DiscountInstance(
                    rs.getInt("discountID"),
                    rs.getDouble("percentage"),
                    clientId,
                    client,
                    null
                );
                discountInstance.setStartDate(rs.getDate("startDate"));
                discountInstance.setEndDate(rs.getDate("endDate"));
                discountInstance.setPromoCode(rs.getString("promoCode"));
                discountInstance.Activate();
                discounts.add(discountInstance);
            }
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.toString());
        }
        return discounts;
    }
    
    public ArrayList<DiscountInstance> getAllExpDiscountsByCID(int id) {
        ArrayList<DiscountInstance> expiredDiscounts = new ArrayList<>();
        String sql = "SELECT * FROM discount WHERE clientID = ? AND endDate <= CURDATE()";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int clientId = rs.getInt("clientID");
                Client client = getClient(clientId);
                DiscountInstance discountInstance = new DiscountInstance(
                    rs.getInt("discountID"),
                    rs.getDouble("percentage"),
                    clientId,
                    client,
                    null
                );
                discountInstance.setStartDate(rs.getDate("startDate"));
                discountInstance.setEndDate(rs.getDate("endDate"));
                discountInstance.setPromoCode(rs.getString("promoCode"));
                discountInstance.Activate();
                expiredDiscounts.add(discountInstance);
            }
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.toString());
        }
        return expiredDiscounts;
    }

    public ArrayList<DiscountInstance> getAllActiveDiscountsByCID(int id) {
        ArrayList<DiscountInstance> activeDiscounts = new ArrayList<>();
        String sql = "SELECT * FROM discount WHERE clientID = ? AND endDate > CURDATE()";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int clientId = rs.getInt("clientID");
                Client client = getClient(clientId);
                DiscountInstance discountInstance = new DiscountInstance(
                    rs.getInt("discountID"),
                    rs.getDouble("percentage"),
                    clientId,
                    client,
                    null
                );
                discountInstance.setStartDate(rs.getDate("startDate"));
                discountInstance.setEndDate(rs.getDate("endDate"));
                discountInstance.setPromoCode(rs.getString("promoCode"));
                discountInstance.Activate();
                activeDiscounts.add(discountInstance);
            }
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.toString());
        }
        return activeDiscounts;
    }
    
    public ArrayList<DiscountInstance> getAllDiscounts() {
        ArrayList<DiscountInstance> discounts = new ArrayList<>();
        String sql = "SELECT * FROM discount";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int clientId = rs.getInt("clientID");
                Client client = getClient(clientId);
                DiscountInstance discountInstance = new DiscountInstance(
                    rs.getInt("discountID"),
                    rs.getDouble("percentage"),
                    clientId,
                    client,
                    null
                );
                discountInstance.setStartDate(rs.getDate("startDate"));
                discountInstance.setEndDate(rs.getDate("endDate"));
                discountInstance.setPromoCode(rs.getString("promoCode"));
                discountInstance.Activate();
                discounts.add(discountInstance);
            }
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.toString());
        }
        return discounts;
    }
    
    public ArrayList<DiscountInstance> getAllExpiredDiscounts() {
        ArrayList<DiscountInstance> expiredDiscounts = new ArrayList<>();
        String sql = "SELECT * FROM discount WHERE endDate <= CURDATE()";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int clientId = rs.getInt("clientID");
                Client client = getClient(clientId);
                DiscountInstance discountInstance = new DiscountInstance(
                    rs.getInt("discountID"),
                    rs.getDouble("percentage"),
                    clientId,
                    client,
                    null
                );
                discountInstance.setStartDate(rs.getDate("startDate"));
                discountInstance.setEndDate(rs.getDate("endDate"));
                discountInstance.setPromoCode(rs.getString("promoCode"));
                discountInstance.Activate();
                expiredDiscounts.add(discountInstance);
            }
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.toString());
        }
        return expiredDiscounts;
    }

    public ArrayList<DiscountInstance> getAllActiveDiscounts() {
        ArrayList<DiscountInstance> activeDiscounts = new ArrayList<>();
        String sql = "SELECT * FROM discount WHERE endDate > CURDATE()";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int clientId = rs.getInt("clientID");
                Client client = getClient(clientId);
                DiscountInstance discountInstance = new DiscountInstance(
                    rs.getInt("discountID"),
                    rs.getDouble("percentage"),
                    clientId,
                    client,
                    null
                );
                discountInstance.setStartDate(rs.getDate("startDate"));
                discountInstance.setEndDate(rs.getDate("endDate"));
                discountInstance.setPromoCode(rs.getString("promoCode"));
                discountInstance.Activate();
                activeDiscounts.add(discountInstance);
            }
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.toString());
        }
        return activeDiscounts;
    }
    
    public void activateDiscount(int discountID, int clientID) {
        try {
            DiscountInstance discount = getDiscount(discountID);
            if (discount == null) {
                System.out.println("No discount found with the provided discount ID.");
                return;
            }

            Client client = getClient(clientID);
            if (client == null) {
                System.out.println("Invalid client provided.");
                return;
            }

            if (discount.getClientID() == clientID && client.isIsInWhitelist()) {
                String updateSql = "UPDATE discount SET activated = ? WHERE discountID = ?";
                try (PreparedStatement updateStmt = con.prepareStatement(updateSql)) {
                    updateStmt.setBoolean(1, true);
                    updateStmt.setInt(2, discountID);
                    int affectedRows = updateStmt.executeUpdate();

                    if (affectedRows > 0) {
                        System.out.println("Discount activated in the database for the specified discount ID.");
                    } else {
                        System.out.println("Failed to activate the discount in the database.");
                    }
                }
            } else {
                System.out.println("Client does not match the discount's client or is not whitelisted.");
            }
        } catch (SQLException e) {
            System.err.println("DATABASE OPERATION ERROR: " + e.getMessage());
        }
    }
    
    public void addBill(Bill b) {
        String sql = "INSERT INTO bill (Bill_ID, amount, issueDate, status, ElectricMeterNo, consumedWatt, wattPrice, client_ID, deadline, payDate, pastDue) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            int id = getNextId("bill", "Bill_ID");
            pstmt.setInt(1, id);
            pstmt.setDouble(2, b.getAmount());
            pstmt.setDate(3, new java.sql.Date(b.getIssueDate().getTime()));
            pstmt.setString(4, b.getStatus().name());
            pstmt.setInt(5, b.getElectricMeterNo());
            pstmt.setDouble(6, b.getConsumedWatt());
            pstmt.setDouble(7, b.getWattPrice());
            pstmt.setInt(8, b.getClientID());
            pstmt.setDate(9, new java.sql.Date(b.getDeadline().getTime()));
        
            if (b.getPayDate() != null) {
                pstmt.setDate(10, new java.sql.Date(b.getPayDate().getTime()));
            } else {
                pstmt.setNull(10, java.sql.Types.DATE);
            }

            pstmt.setBoolean(11, b.isPastDue());
            pstmt.executeUpdate();
            System.out.println("Bill added to database successfully.");
            calculateAmount(id);
        } catch (SQLException e) {
            System.err.println("DATABASE INSERTION ERROR: " + e.getMessage());
        }
    }
    
    public Bill getBill(int id) {
    Bill b = null;
    String sql = "SELECT * FROM bill WHERE Bill_ID = ?";

    try (PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int clientID = rs.getInt("client_ID");
            Client client = getClient(clientID);

            b = new Bill(
                rs.getInt("Bill_ID"),
                rs.getInt("ElectricMeterNo"),
                rs.getDouble("consumedWatt"),
                rs.getInt("client_ID"),
                client
            );
            b.setAmount(rs.getDouble("amount"));
            b.setPayDate(rs.getDate("payDate"));
            b.setPastDue(rs.getBoolean("pastDue"));
            b.setDeadline(rs.getDate("deadline"));
            b.setIssueDate(rs.getDate("issueDate"));
        }
    } catch (SQLException e) {
        System.err.println("SQL ERROR: " + e.toString());
    }
    return b;
}
    
    public ArrayList<Bill> getAllBills() {
        ArrayList<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bill";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int clientId = rs.getInt("client_ID");
                Client client = getClient(clientId);
                Bill b = new Bill(
                    rs.getInt("Bill_ID"),
                    rs.getInt("ElectricMeterNo"),
                    rs.getDouble("consumedWatt"),
                    clientId,
                    client
                );
                b.setAmount(rs.getDouble("amount"));
                b.setPayDate(rs.getDate("payDate"));
                b.setPastDue(rs.getBoolean("pastDue"));
                b.setDeadline(rs.getDate("deadline"));
                b.setIssueDate(rs.getDate("issueDate"));
                bills.add(b);
                
            }
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.toString());
        }
        return bills;
    }
    
    public ArrayList<Bill> getAllBillsByCID(int id) {
        ArrayList<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bill WHERE client_ID = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int clientId = rs.getInt("client_ID");
                Client client = getClient(clientId);
                Bill b = new Bill(
                    rs.getInt("Bill_ID"),
                    rs.getInt("ElectricMeterNo"),
                    rs.getDouble("consumedWatt"),
                    clientId,
                    client
                );
                b.setAmount(rs.getDouble("amount"));
                b.setPayDate(rs.getDate("payDate"));
                b.setPastDue(rs.getBoolean("pastDue"));
                b.setDeadline(rs.getDate("deadline"));
                b.setIssueDate(rs.getDate("issueDate"));
                bills.add(b);
            }
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.toString());
        }
        return bills;
    }
    
    public ArrayList<Bill> getAllUnpaidBillsByCID(int id) {
        ArrayList<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bill WHERE client_ID = ? AND status = 'pending'";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int clientId = rs.getInt("client_ID");
                Client client = getClient(clientId);
                Bill b = new Bill(
                    rs.getInt("Bill_ID"),
                    rs.getInt("ElectricMeterNo"),
                    rs.getDouble("consumedWatt"),
                    clientId,
                    client
                );
                b.setAmount(rs.getDouble("amount"));
                b.setPayDate(rs.getDate("payDate"));
                b.setPastDue(rs.getBoolean("pastDue"));
                b.setDeadline(rs.getDate("deadline"));
                b.setIssueDate(rs.getDate("issueDate"));
                bills.add(b);
            }
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.toString());
        }
        return bills;
    }
    
    public ArrayList<Bill> getAllPaidBillsByCID(int id) {
        ArrayList<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bill WHERE client_ID = ? AND status = 'paid'";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int clientId = rs.getInt("client_ID");
                Client client = getClient(clientId);
                Bill b = new Bill(
                    rs.getInt("Bill_ID"),
                    rs.getInt("ElectricMeterNo"),
                    rs.getDouble("consumedWatt"),
                    clientId,
                    client
                );
                b.setAmount(rs.getDouble("amount"));
                b.setPayDate(rs.getDate("payDate"));
                b.setPastDue(rs.getBoolean("pastDue"));
                b.setDeadline(rs.getDate("deadline"));
                b.setIssueDate(rs.getDate("issueDate"));
                bills.add(b);
            }
        } catch (SQLException e) {
            System.err.println("SQL ERROR: " + e.toString());
        }
        return bills;
    }
    
    public void calculateAmount(int billId) {
        String fetchSql = "SELECT consumedWatt, wattPrice FROM bill WHERE Bill_ID = ?";
        String updateSql = "UPDATE bill SET amount = ? WHERE Bill_ID = ?";

        try (PreparedStatement fetchStmt = con.prepareStatement(fetchSql);
             PreparedStatement updateStmt = con.prepareStatement(updateSql)) {

            fetchStmt.setInt(1, billId);
            ResultSet rs = fetchStmt.executeQuery();

            if (rs.next()) {
                double consumedWatt = rs.getDouble("consumedWatt");
                double wattPrice = rs.getDouble("wattPrice");
                double newAmount = consumedWatt * wattPrice;

                updateStmt.setDouble(1, newAmount);
                updateStmt.setInt(2, billId);
                int affectedRows = updateStmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Bill amount updated successfully for Bill ID: " + billId);
                    System.out.println("New Amount: $" + newAmount);
                } else {
                    System.out.println("No changes were made. Check if the Bill ID is correct.");
                }
            } else {
                System.out.println("No bill found with ID: " + billId);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE UPDATE ERROR: " + e.getMessage());
        }
    }
    
    public void markBillPaid(int billID) {
        String fetchSql = "SELECT status, deadline, client_ID FROM bill WHERE Bill_ID = ?";
        String updateSql = "UPDATE bill SET status = ?, payDate = CURRENT_DATE(), pastDue = ? WHERE Bill_ID = ? AND status = ?";

        try (PreparedStatement fetchStmt = con.prepareStatement(fetchSql);
             PreparedStatement updateStmt = con.prepareStatement(updateSql)) {

            fetchStmt.setInt(1, billID);
            ResultSet rs = fetchStmt.executeQuery();
            Date now = new java.util.Date();

            if (rs.next()) {
                String currentStatus = rs.getString("status");
                java.sql.Date deadline = rs.getDate("deadline");
                int clientID = rs.getInt("client_ID");

                if (BillStatus.pending.name().equalsIgnoreCase(currentStatus)) {
                    boolean isPastDue = (deadline != null && now.after(deadline));

                    updateStmt.setString(1, BillStatus.paid.name());
                    updateStmt.setBoolean(2, isPastDue);
                    updateStmt.setInt(3, billID);
                    updateStmt.setString(4, BillStatus.pending.name());
                    int affectedRows = updateStmt.executeUpdate();

                    if (affectedRows > 0) {
                        System.out.println("Bill ID: " + billID + " has been marked as paid on " + now);
                        updateClientBillCounters(clientID, isPastDue);
                    } else {
                        System.out.println("No updates performed. Check if the bill ID is correct or the bill is already paid.");
                    }
                } else {
                    System.out.println("Bill ID: " + billID + " is already marked paid.");
                }
            } else {
                System.out.println("No bill found with ID: " + billID);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE UPDATE ERROR: " + e.getMessage());
        }
    }
    
    public String stringMarkBillPaid(int billID) {
        String fetchSql = "SELECT status, deadline, client_ID FROM bill WHERE Bill_ID = ?";
        String updateSql = "UPDATE bill SET status = ?, payDate = CURRENT_DATE(), pastDue = ? WHERE Bill_ID = ? AND status = ?";
        String s = "";
        
        try (PreparedStatement fetchStmt = con.prepareStatement(fetchSql);
             PreparedStatement updateStmt = con.prepareStatement(updateSql)) {

            fetchStmt.setInt(1, billID);
            ResultSet rs = fetchStmt.executeQuery();
            Date now = new java.util.Date();

            if (rs.next()) {
                String currentStatus = rs.getString("status");
                java.sql.Date deadline = rs.getDate("deadline");
                int clientID = rs.getInt("client_ID");

                if (BillStatus.pending.name().equalsIgnoreCase(currentStatus)) {
                    boolean isPastDue = (deadline != null && now.after(deadline));

                    updateStmt.setString(1, BillStatus.paid.name());
                    updateStmt.setBoolean(2, isPastDue);
                    updateStmt.setInt(3, billID);
                    updateStmt.setString(4, BillStatus.pending.name());
                    int affectedRows = updateStmt.executeUpdate();

                    if (affectedRows > 0) {
                        s = ("Bill ID: " + billID + " has been marked as paid on " + now);
                        updateClientBillCounters(clientID, isPastDue);
                    } else {
                        s = ("No updates performed. Check if the bill ID is correct or the bill is already paid.");
                    }
                } else {
                    s = ("Bill ID: " + billID + " is already marked paid.");
                }
            } else {
                s = ("No bill found with ID: " + billID);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE UPDATE ERROR: " + e.getMessage());
        }
        return s;
    }
    
    private void updateClientBillCounters(int clientID, boolean isPastDue) {
        try {
            if (isPastDue) {
                String sql = "UPDATE client SET billPastTimeCounter = billPastTimeCounter + 1 WHERE clientID = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, clientID);
                stmt.executeUpdate();
                System.out.println("Incremented past due counter for Client ID: " + clientID);
                removeFromWhitelist(clientID);
                addToBlacklist(clientID);
                endSubs(clientID);
            } else {
                String sql = "UPDATE client SET billOnTimeCounter = billOnTimeCounter + 1 WHERE clientID = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, clientID);
                stmt.executeUpdate();
                System.out.println("Incremented on-time payment counter for Client ID: " + clientID);
                removeFromBlacklist(clientID);
                addToWhitelist(clientID);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE UPDATE ERROR: " + e.getMessage());
        }
    }
    
    public void applyDiscountToBill(int billID, int discountID) {
        String fetchBillSql = "SELECT amount, client_ID FROM bill WHERE Bill_ID = ?";
        String fetchDiscountSql = "SELECT percentage, startDate, endDate, clientID, activated FROM discount WHERE discountID = ?";
        String updateBillSql = "UPDATE bill SET amount = ? WHERE Bill_ID = ?";

        try (PreparedStatement fetchBillStmt = con.prepareStatement(fetchBillSql);
             PreparedStatement fetchDiscountStmt = con.prepareStatement(fetchDiscountSql);
             PreparedStatement updateBillStmt = con.prepareStatement(updateBillSql)) {

            fetchBillStmt.setInt(1, billID);
            ResultSet billRs = fetchBillStmt.executeQuery();

            if (billRs.next()) {
                double billAmount = billRs.getDouble("amount");
                int billClientID = billRs.getInt("client_ID");

                fetchDiscountStmt.setInt(1, discountID);
                ResultSet discountRs = fetchDiscountStmt.executeQuery();

                if (discountRs.next()) {
                    double discountPercentage = discountRs.getDouble("percentage");
                    Date discountStartDate = discountRs.getDate("startDate");
                    Date discountEndDate = discountRs.getDate("endDate");
                    int discountClientID = discountRs.getInt("clientID");
                    boolean isActivated = discountRs.getBoolean("activated");

                    Date now = new Date();

                    if (isActivated && billClientID == discountClientID && !now.before(discountStartDate) && now.before(discountEndDate)) {
                        double newAmount = billAmount - (billAmount * (discountPercentage / 100.0));

                        updateBillStmt.setDouble(1, newAmount);
                        updateBillStmt.setInt(2, billID);
                        updateBillStmt.executeUpdate();

                        System.out.println("Discount applied. New amount: $" + newAmount);
                        removeDiscount(discountID);
                    } else {
                        System.out.println("Discount is not applicable. Current date is outside the valid period, clients do not match, or the discount is not activated.");
                    }
                } else {
                    System.out.println("No discount found with ID: " + discountID);
                }
            } else {
                System.out.println("No bill found with ID: " + billID);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE ERROR: " + e.getMessage());
        }
    }
    
    
    public String stringApplyDiscountToBill(int billID, int discountID) {
        String fetchBillSql = "SELECT amount, client_ID FROM bill WHERE Bill_ID = ?";
        String fetchDiscountSql = "SELECT percentage, startDate, endDate, clientID, activated FROM discount WHERE discountID = ?";
        String updateBillSql = "UPDATE bill SET amount = ? WHERE Bill_ID = ?";
        String s = "";

        try (PreparedStatement fetchBillStmt = con.prepareStatement(fetchBillSql);
             PreparedStatement fetchDiscountStmt = con.prepareStatement(fetchDiscountSql);
             PreparedStatement updateBillStmt = con.prepareStatement(updateBillSql)) {

            fetchBillStmt.setInt(1, billID);
            ResultSet billRs = fetchBillStmt.executeQuery();

            if (billRs.next()) {
                double billAmount = billRs.getDouble("amount");
                int billClientID = billRs.getInt("client_ID");

                fetchDiscountStmt.setInt(1, discountID);
                ResultSet discountRs = fetchDiscountStmt.executeQuery();

                if (discountRs.next()) {
                    double discountPercentage = discountRs.getDouble("percentage");
                    Date discountStartDate = discountRs.getDate("startDate");
                    Date discountEndDate = discountRs.getDate("endDate");
                    int discountClientID = discountRs.getInt("clientID");
                    boolean isActivated = discountRs.getBoolean("activated");

                    Date now = new Date();

                    if (isActivated && billClientID == discountClientID && !now.before(discountStartDate) && now.before(discountEndDate)) {
                        double newAmount = billAmount - (billAmount * (discountPercentage / 100.0));

                        updateBillStmt.setDouble(1, newAmount);
                        updateBillStmt.setInt(2, billID);
                        updateBillStmt.executeUpdate();

                        s = ("Discount applied. New amount: $" + newAmount);
                        removeDiscount(discountID);
                    } else {
                        s = ("Discount is not applicable. Current date is outside the valid period, clients do not match, or the discount is not activated.");
                    }
                } else {
                    s = ("No discount found with ID: " + discountID);
                }
            } else {
                s = ("No bill found with ID: " + billID);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE ERROR: " + e.getMessage());
        }
        return s;
    }
    
    
    public ArrayList<Integer> AllAdminIDs() {
        ArrayList<Integer> adminIDs = new ArrayList<>();
        String sql = "SELECT adminID FROM admin ORDER BY adminID ASC";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                adminIDs.add(rs.getInt("adminID"));
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.getMessage());
        }
        return adminIDs;
    }

    
    public ArrayList<Integer> getBillIDsByClientID(int clientID) {
        ArrayList<Integer> billIDs = new ArrayList<>();
        String sql = "SELECT Bill_ID FROM bill WHERE client_ID = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, clientID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                billIDs.add(rs.getInt("Bill_ID"));
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.getMessage());
        }
        return billIDs;
    }
    
    public ArrayList<Integer> getAppointmentIDsByClientID(int clientID) {
        ArrayList<Integer> appointmentIDs = new ArrayList<>();
        String sql = "SELECT appointmentID FROM appointment WHERE c_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, clientID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                appointmentIDs.add(rs.getInt("appointmentID"));
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.getMessage());
        }
        return appointmentIDs;
    }
    
    public ArrayList<Integer> getDiscountIDsByClientID(int clientID) {
        ArrayList<Integer> discountIDs = new ArrayList<>();
        String sql = "SELECT discountID FROM discount WHERE clientID = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, clientID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                discountIDs.add(rs.getInt("discountID"));
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.getMessage());
        }
        return discountIDs;
    }

    public ArrayList<Integer> getComplaintIDsByClientID(int clientID) {
        ArrayList<Integer> complaintIDs = new ArrayList<>();
        String sql = "SELECT complaintID FROM complaint WHERE C_ID = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, clientID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                complaintIDs.add(rs.getInt("complaintID"));
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.getMessage());
        }
        return complaintIDs;
    }

    public ArrayList<Integer> getFeedbackIDsByClientID(int clientID) {
        ArrayList<Integer> feedbackIDs = new ArrayList<>();
        String sql = "SELECT feedback_ID FROM feedback WHERE CID = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, clientID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                feedbackIDs.add(rs.getInt("feedback_ID"));
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.getMessage());
        }
        return feedbackIDs;
    }
    
    public ArrayList<Client> getAllBlacklistedClients() {
        ArrayList<Client> blacklistedClients = new ArrayList<>();
        String sql = "SELECT * FROM client WHERE isInBlacklist = true";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int clientID = rs.getInt("clientID");
                ArrayList<Integer> bills = getBillIDsByClientID(clientID);
                ArrayList<Integer> complaints = getComplaintIDsByClientID(clientID);
                ArrayList<Integer> feedbacks = getFeedbackIDsByClientID(clientID);
                ArrayList<Integer> appointments = getAppointmentIDsByClientID(clientID);
                ArrayList<Integer> discounts = getDiscountIDsByClientID(clientID);

                Client client = new Client(
                    rs.getInt("billOnTimeCounter"),
                    rs.getInt("billPastTimeCounter"),
                    rs.getBoolean("isInWhitelist"),
                    rs.getBoolean("isInBlacklist"),
                    rs.getBoolean("subsEnded"),
                    bills,
                    complaints,
                    feedbacks,
                    appointments,
                    discounts,
                    clientID,
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("address")
                );
                client.setRegisterDate(rs.getDate("registerDate"));
                blacklistedClients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return blacklistedClients;
    }
    
    public ArrayList<Client> getAllWhitelistedClients() {
        ArrayList<Client> whitelistedClients = new ArrayList<>();
        String sql = "SELECT * FROM client WHERE isInWhitelist = true";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int clientID = rs.getInt("clientID");
                ArrayList<Integer> bills = getBillIDsByClientID(clientID);
                ArrayList<Integer> complaints = getComplaintIDsByClientID(clientID);
                ArrayList<Integer> feedbacks = getFeedbackIDsByClientID(clientID);
                ArrayList<Integer> appointments = getAppointmentIDsByClientID(clientID);
                ArrayList<Integer> discounts = getDiscountIDsByClientID(clientID);

                Client client = new Client(
                    rs.getInt("billOnTimeCounter"),
                    rs.getInt("billPastTimeCounter"),
                    rs.getBoolean("isInWhitelist"),
                    rs.getBoolean("isInBlacklist"),
                    rs.getBoolean("subsEnded"),
                    bills,
                    complaints,
                    feedbacks,
                    appointments,
                    discounts,
                    clientID,
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("address")
                );
                client.setRegisterDate(rs.getDate("registerDate"));
                whitelistedClients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return whitelistedClients;
    }
    
    public ArrayList<Bill> getAllPastDueBills() {
        ArrayList<Bill> pastDueBills = new ArrayList<>();
        String sql = "SELECT * FROM bill WHERE pastDue = 1 AND status = 'paid'";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int clientID = rs.getInt("client_ID");
                Client client = getClient(clientID);

                Bill bill = new Bill(
                    rs.getInt("Bill_ID"),
                    rs.getInt("ElectricMeterNo"),
                    rs.getDouble("consumedWatt"),
                    clientID,
                    client
                );

                bill.setAmount(rs.getDouble("amount"));
                bill.setPayDate(rs.getDate("payDate"));
                bill.setPastDue(rs.getBoolean("pastDue"));
                bill.setDeadline(rs.getDate("deadline"));
                bill.setIssueDate(rs.getDate("issueDate"));
                pastDueBills.add(bill);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return pastDueBills;
    }
    
    public ArrayList<Bill> getAllPastDueBillsByCID(int clientID) {
        ArrayList<Bill> pastDueBills = new ArrayList<>();
        String sql = "SELECT * FROM bill WHERE pastDue = 1 AND client_ID = ? AND status = 'paid'";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, clientID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Client client = getClient(clientID);

                Bill bill = new Bill(
                    rs.getInt("Bill_ID"),
                    rs.getInt("ElectricMeterNo"),
                    rs.getDouble("consumedWatt"),
                    clientID,
                    client
                );

                bill.setAmount(rs.getDouble("amount"));
                bill.setPayDate(rs.getDate("payDate"));
                bill.setPastDue(rs.getBoolean("pastDue"));
                bill.setDeadline(rs.getDate("deadline"));
                bill.setIssueDate(rs.getDate("issueDate"));
                pastDueBills.add(bill);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return pastDueBills;
    }

    
    public ArrayList<Bill> getAllOnTimeBills() {
        ArrayList<Bill> pastDueBills = new ArrayList<>();
        String sql = "SELECT * FROM bill WHERE pastDue = 0 AND status = 'paid'";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int clientID = rs.getInt("client_ID");
                Client client = getClient(clientID);

                Bill bill = new Bill(
                    rs.getInt("Bill_ID"),
                    rs.getInt("ElectricMeterNo"),
                    rs.getDouble("consumedWatt"),
                    clientID,
                    client
                );

                bill.setAmount(rs.getDouble("amount"));
                bill.setPayDate(rs.getDate("payDate"));
                bill.setPastDue(rs.getBoolean("pastDue"));
                bill.setDeadline(rs.getDate("deadline"));
                bill.setIssueDate(rs.getDate("issueDate"));
                pastDueBills.add(bill);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return pastDueBills;
    }
    
    public ArrayList<Bill> getAllOnTimeBillsByCID(int clientID) {
        ArrayList<Bill> onTimeBills = new ArrayList<>();
        String sql = "SELECT * FROM bill WHERE pastDue = 0 AND client_ID = ? AND status = 'paid'";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, clientID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Client client = getClient(clientID);

                Bill bill = new Bill(
                    rs.getInt("Bill_ID"),
                    rs.getInt("ElectricMeterNo"),
                    rs.getDouble("consumedWatt"),
                    clientID,
                    client
                );

                bill.setAmount(rs.getDouble("amount"));
                bill.setPayDate(rs.getDate("payDate"));
                bill.setPastDue(rs.getBoolean("pastDue"));
                bill.setDeadline(rs.getDate("deadline"));
                bill.setIssueDate(rs.getDate("issueDate"));
                onTimeBills.add(bill);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return onTimeBills;
    }


    public ArrayList<Bill> getAllUnpaidBills() {
        ArrayList<Bill> unpaidBills = new ArrayList<>();
        String sql = "SELECT * FROM bill WHERE status = 'pending'";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int clientID = rs.getInt("client_ID");
                Client client = getClient(clientID);

                Bill bill = new Bill(
                    rs.getInt("Bill_ID"),
                    rs.getInt("ElectricMeterNo"),
                    rs.getDouble("consumedWatt"),
                    clientID,
                    client
                );

                bill.setAmount(rs.getDouble("amount"));
                bill.setPayDate(rs.getDate("payDate"));
                bill.setPastDue(rs.getBoolean("pastDue"));
                bill.setDeadline(rs.getDate("deadline"));
                bill.setIssueDate(rs.getDate("issueDate"));
                unpaidBills.add(bill);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return unpaidBills;
    }

    public ArrayList<Bill> getAllPaidBills() {
        ArrayList<Bill> paidBills = new ArrayList<>();
        String sql = "SELECT * FROM bill WHERE status = 'paid'";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int clientID = rs.getInt("client_ID");
                Client client = getClient(clientID);

                Bill bill = new Bill(
                    rs.getInt("Bill_ID"),
                    rs.getInt("ElectricMeterNo"),
                    rs.getDouble("consumedWatt"),
                    clientID,
                    client
                );

                bill.setAmount(rs.getDouble("amount"));
                bill.setPayDate(rs.getDate("payDate"));
                bill.setPastDue(rs.getBoolean("pastDue"));
                bill.setDeadline(rs.getDate("deadline"));
                bill.setIssueDate(rs.getDate("issueDate"));
                paidBills.add(bill);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return paidBills;
    }
    
    public int getNextId(String tableName, String idColumn) throws SQLException {
        String sql = "SELECT MAX(" + idColumn + ") FROM " + tableName;
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) + 1;
            } else {
                return 1;
            }
        }
    }
    
    public int getTechnicianIdByAddress(String address) {
        String sql = "SELECT technicianID FROM technician WHERE address = ?";
        int id = 0;
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, address);
            ResultSet rs = stmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                int technicianId = rs.getInt("technicianID");
                id = technicianId;
                found = true;
            }

            if (!found) {
                System.out.println("No technician found with the address: " + address);
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
        }
        return id;
    }
    
    public Client getLastClient() throws SQLException {
        String sql = "SELECT * FROM client ORDER BY clientID DESC LIMIT 1";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ArrayList<Integer> bills = getBillIDsByClientID(rs.getInt("clientID"));
                ArrayList<Integer> complaints = getComplaintIDsByClientID(rs.getInt("clientID"));
                ArrayList<Integer> feedbacks = getFeedbackIDsByClientID(rs.getInt("clientID"));
                ArrayList<Integer> appointments = getAppointmentIDsByClientID(rs.getInt("clientID"));
                ArrayList<Integer> discounts = getDiscountIDsByClientID(rs.getInt("clientID"));

                Client client = new Client(
                    rs.getInt("billOnTimeCounter"),
                    rs.getInt("billPastTimeCounter"),
                    rs.getBoolean("isInWhitelist"),
                    rs.getBoolean("isInBlacklist"),
                    rs.getBoolean("subsEnded"),
                    bills,
                    complaints,
                    feedbacks,
                    appointments,
                    discounts,
                    rs.getInt("clientID"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("address")
                );
                client.setRegisterDate(rs.getDate("registerDate"));
                return client;
            }
        }
        return null;
    }
    
    public Technician getLastTechnician() throws SQLException {
    String sql = "SELECT * FROM technician ORDER BY technicianID DESC LIMIT 1";
    try (PreparedStatement stmt = con.prepareStatement(sql)) {
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            ArrayList<AppointmentReadOnly> appointments = getAllAppointmentsByTID(rs.getInt("technicianID"));

            Technician tech = new Technician(
                rs.getDouble("salary"),
                appointments,
                rs.getInt("technicianID"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("address")
            );
            tech.setRegisterDate(rs.getDate("registerDate"));
            return tech;
        }
    }
    return null;
}
    
    public Payment getLastPayment() throws SQLException {
        String sql = "SELECT * FROM payment ORDER BY paymentID DESC LIMIT 1";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Payment(
                    rs.getInt("paymentID"),
                    rs.getString("cardNo"),
                    rs.getString("cardHolder"),
                    rs.getString("cvv"),
                    rs.getDouble("amount"),
                    rs.getInt("clientID"),
                    rs.getInt("billID")
                );
            }
        }
        return null;
    }

    public Integer getClientIDbyEmail(String email) throws SQLException {
        String sql = "SELECT clientID FROM client WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("clientID");
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
            throw e;
        }
    }
    
    public Admin getLastAdmin() throws SQLException {
        String sql = "SELECT * FROM admin ORDER BY adminID DESC LIMIT 1";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ArrayList<Manager> m = getAllManagers();
                ArrayList<Client> c = getAllClients();
                ArrayList<Technician> t = getAllTechnicians();
                ArrayList<Integer> ad = AllAdminIDs();

                Admin a = new Admin(
                    m,
                    c,
                    t,
                    ad,
                    rs.getDouble("salary"),
                    rs.getInt("adminID"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("address")
                );
                a.setRegisterDate(rs.getDate("registerDate"));
                return a;
            }
        }
        return null;
    }
    
    public Manager getLastManager() throws SQLException {
        String sql = "SELECT * FROM manager ORDER BY managerID DESC LIMIT 1";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ArrayList<Bill> b = getAllBills();
                ArrayList<DiscountInstance> d = getAllDiscounts();
                ArrayList<Client> c = getAllClients();

                Manager m = new Manager(
                    rs.getDouble("salary"),
                    b,
                    d,
                    c,
                    rs.getInt("managerID"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("address")
                );
                m.setRegisterDate(rs.getDate("registerDate"));
                return m;
                    }
                } catch (SQLException e) {
                    System.err.println("SQL ERROR: " + e.toString());
                }
        return null;
    }
    
    public Integer getTechIDbyEmail(String email) throws SQLException {
        String sql = "SELECT technicianID FROM technician WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("technicianID");
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
            throw e;
        }
    }

    public Integer getManagerIDbyEmail(String email) throws SQLException {
        String sql = "SELECT managerID FROM manager WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("managerID");
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
            throw e;
        }
    }
    
    public Integer getAdminIDbyEmail(String email) throws SQLException {
        String sql = "SELECT adminID FROM admin WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("adminID");
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("DATABASE QUERY ERROR: " + e.toString());
            throw e;
        }
    }
}
