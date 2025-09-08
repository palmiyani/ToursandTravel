import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;

public class Ticket {

    public static void generateTicket(String customerID, String packageID, String paymentID, double amount, boolean paymentStatus) throws Exception {
        try {
            CustomerDetails customer = CustomerDetails.getCustomerById(customerID, Main.conn);
            if (customer == null) {
                System.out.println("Customer not found.");
                return;
            }

            PackageDetails packageDetails = PackageDetails.getPackageById(packageID);
            if (packageDetails == null) {
                System.out.println("Package not found.");
                return;
            }

            String filename = customerID + ".txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                writer.println("=================================================");
                writer.println("Ticket Details:");
                writer.println("=================================================");
                writer.println("----+-+-+-+-+-+-+-+-+-+--- Customer Details ----+-+-+-+-+-+-+-+-+-+---");
                writer.println("Customer ID: " + customer.getCustomerID());
                writer.println("Name: " + customer.getName());
                writer.println("Email: " + customer.getEmail());
                writer.println("Phone: " + customer.getPhone());
                writer.println("Address: " + customer.getAddress());
                writer.println("----+-+-+-+-+-+-+-+-+-+--- Package Details ----+-+-+-+-+-+-+-+-+-+---");
                writer.println("Package ID: " + packageDetails.getPackageID());
                writer.println("Package Name: " + packageDetails.getPackageName());
                writer.println("Package Price: " + packageDetails.getPrice());
                writer.println("----+-+-+-+-+-+-+-+-+-+--- Payment Details ----+-+-+-+-+-+-+-+-+-+---");
                writer.println("Payment ID: " + paymentID);
                writer.println("Total Amount: " + amount);
                writer.println("Status: " + (paymentStatus ? "Successful" : "Failed"));
                System.out.println("Ticket generated: " + filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewTickets() {
        File folder = new File("."); 
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files != null && files.length > 0) {
            System.out.println("Available Tickets:");
            for (File file : files) {
                System.out.println(file.getName());
            }
        } else {
            System.out.println("No tickets found.");
        }
    }
}
