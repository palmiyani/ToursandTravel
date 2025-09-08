import java.io.*;
import java.sql.*;

public class Booking {
    private static String customerID;
    private static String paymentID;
    private static File ticketFile;

    public Booking(String customerID, String paymentID) {
        this.customerID = customerID;
        this.paymentID = paymentID;
        File ticketFile =   new File(
            "C:\\Users\\DELL\\Desktop\\LJ Uni\\SEM-2\\Java Practice\\toursandtravel\\src\\" + customerID+ ".txt");
        this.ticketFile = ticketFile;
    }

    public static void storeTicketFile(Connection con) {
        String sql = "INSERT INTO bookingdetails(customerID, paymentID, ticketFile) VALUES (?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(sql);
             FileReader fr = new FileReader(ticketFile)) {
             
            pst.setString(1, customerID);
            pst.setString(2, paymentID);
            pst.setCharacterStream(3, fr, (int)ticketFile.length());
            
            int r = pst.executeUpdate();
            if (r > 0) {
                System.out.println("Ticket file stored successfully in the database.");
            } else {
                System.out.println("Failed to store the ticket file.");
            }
        } catch (SQLException | IOException e) {
            System.out.println("Error storing ticket file.");
            e.printStackTrace();
        }
    }

    public static void retrieveTicketFile(Connection con, String customerID) {
        String sql = "SELECT ticketFile FROM bookingdetails WHERE customerID = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, customerID);
            try (ResultSet rst = pst.executeQuery()) {
                if (rst.next()) {
                    Clob ticketClob = rst.getClob("ticketFile");
                    try (Reader r = ticketClob.getCharacterStream();
                         FileWriter fw = new FileWriter(customerID + "_retrieved.txt")) {
                         
                        int i;
                        while ((i = r.read()) != -1) {
                            fw.write((char) i);
                        }
                        System.out.println("Ticket file retrieved and stored as " + customerID + "_retrieved.txt");
                    }
                } else {
                    System.out.println("No ticket found for customer ID: " + customerID);
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println("Error retrieving ticket file.");
            e.printStackTrace();
        }
    }
}
