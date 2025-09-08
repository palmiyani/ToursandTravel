import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Payment {

    private String paymentID;
    private String customerID;
    private double amount;
    private boolean paymentStatus;

    public Payment(String paymentID, String customerID, double amount, boolean paymentStatus) {
        this.paymentID = paymentID;
        this.customerID = customerID;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }

    public static String generatePaymentID() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(10);
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (int i = 0; i < 10; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }

    public static Payment createPayment(Connection conn, String customerID, double packagePrice) {
        Scanner sc = new Scanner(System.in);
        String paymentID = generatePaymentID();

       
        double amount = packagePrice + (packagePrice * 0.18);
        System.out.println("Total amount (inc 18% GST): "+amount);
        System.out.print("Confirm payment? (yes/no): ");
        String confirmation = sc.nextLine();
        boolean paymentStatus = confirmation.equalsIgnoreCase("yes");
        Payment payment = new Payment(paymentID, customerID, amount, paymentStatus);
        payment.addPayment(conn);

        if (paymentStatus) {
            System.out.println("Payment successful!");
        } else {
            System.out.println("Payment not completed.");
        }

        return payment;
    }

    public void addPayment(Connection conn) {
        String sql = "INSERT INTO payment (paymentID, customerID, amount, paymentStatus) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.paymentID);
            pstmt.setString(2, this.customerID);
            pstmt.setDouble(3, this.amount);
            pstmt.setBoolean(4, this.paymentStatus);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding payment.");
            e.printStackTrace();
        }
    }

    public static void viewPaymentDetails(Connection conn) {
        String sql = "SELECT paymentID, customerID, amount FROM payment";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String paymentID = rs.getString("paymentID");
                String customerID = rs.getString("customerID");
                String name = rs.getString("name");
                System.out.println("Payment ID: " + paymentID + "Customer ID: " + customerID + ", Name: " + name);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching customer details.");
            e.printStackTrace();
        }
    }

    public static void displayPaymentStatus(Connection conn) {
        try {
           
            String paidCustomersQuery = "SELECT c.customerID, c.name FROM customerdetails c JOIN payment p ON c.customerID = p.customerID WHERE p.paymentStatus = TRUE";
        
            String unpaidCustomersQuery = "SELECT c.customerID, c.name FROM customerdetails c LEFT JOIN payment p ON c.customerID = p.customerID WHERE p.customerID IS NULL";

            System.out.println("Customers who have made payments:");
            try (PreparedStatement paidStmt = conn.prepareStatement(paidCustomersQuery);
                 ResultSet paidRs = paidStmt.executeQuery()) {

                while (paidRs.next()) {
                    String customerID = paidRs.getString("customerID");
                    String name = paidRs.getString("name");
                    System.out.println("Customer ID: " + customerID + ", Name: " + name);
                }
            }

            System.out.println("\nCustomers who haven't made any payments:");
            try (PreparedStatement unpaidStmt = conn.prepareStatement(unpaidCustomersQuery);
                 ResultSet unpaidRs = unpaidStmt.executeQuery()) {

                while (unpaidRs.next()) {
                    String customerID = unpaidRs.getString("customerID");
                    String name = unpaidRs.getString("name");
                    System.out.println("Customer ID: " + customerID + ", Name: " + name);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching payment status.");
            e.printStackTrace();
        }
    }
    public String getPaymentID() {
        return paymentID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }
}
