import java.sql.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Random;
import java.util.Date;

public class Main {

    static LinkedList<CustomerDetails> customerList = new LinkedList<>();
    public static Connection conn;
    public static LoginSystem loginSystem = new LoginSystem();

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/toursandtravel", "root", "");
        Ticket ticket = new Ticket();

        loginSystem.loadUserData();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Tour and Travel Management System");

        System.out.print("Enter your User ID: ");
        String userID = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        Timestamp loginTimestamp = new Timestamp(new Date().getTime());
        if (!loginSystem.authenticate(userID, password)) {
            System.out.println("Invalid User ID or password. Please try again.");
        } else {
            String query = "INSERT INTO login_logs (user_id, time) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userID);
            pstmt.setTimestamp(2, loginTimestamp);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Login successful! Welcome, User ID: " + userID);
                boolean startProgram = true;
                while (startProgram) {
                    System.out.println("Choose from the following options (0 to exit): ");
                    System.out.println("1. Customer Details");
                    System.out.println("2. View Packages");
                    System.out.println("3. View Payments");
                    System.out.println("4. View Ticket");
                    System.out.println("5. Settings");

                    System.out.print("Enter the number: ");
                    int choice = sc.nextInt();
                    switch (choice) {
                        case 0:
                            startProgram = false;
                            break;
                        case 1:
                            userDetails();
                            break;
                        case 2:
                            viewPackages(null);
                            break;
                        case 3:
                            Payment.displayPaymentStatus(conn);
                            break;
                        case 4:
                            ticket.viewTickets();
                            break;
                        case 5:
                            LoginSystem loginSystem = new LoginSystem();
                            System.out.println("Choose from the following USER Settings:-");
                            System.out.println("1. Add User");
                            System.out.println("2. Remove User");
                            System.out.print("Enter the choice: ");
                            int MAINSETTING_CHOICE = sc.nextInt();
                            sc.nextLine();
                            switch (MAINSETTING_CHOICE) {
                                case 1:
                                    System.out.println("Enter the user id: ");
                                    String userName = sc.nextLine();
                                    System.out.println("Enter the password: ");
                                    String userPassword = sc.nextLine();
                                    loginSystem.addUSER(conn, userName, userPassword);
                                    break;

                                case 2:
                                    System.out.println("Enter the user id to delete: ");
                                    userName = sc.nextLine();
                                    loginSystem.deleteUSER(conn, userName);
                                    break;

                                default:
                                    break;
                            }
                            break;
                        default:
                            throw new InvalidInput("Invalid Input");
                    }
                }
            } else {
                System.out.println("Failed to add the user");

            }
        }

    }

    /*
     * -------------------------- USER DETAILS START ------------------------>
     */
    public static void userDetails() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose from the following Customer Settings:-");
        System.out.println("1. ADD Customer");
        System.out.println("2. UPDATE Customer");
        System.out.println("3. DELETE Customer");
        System.out.println("4. VIEW Customer");
        System.out.print("Enter the choice: ");
        int USER_CHOICE = sc.nextInt();
        switch (USER_CHOICE) {
            case 1:
                userDetailFunctionsADD();
                break;
            case 2:
                userDetailFunctionsUPDATE();
                break;
            case 3:
                userDetailFunctionsDELETE();
                break;
            case 4:
                userDetailFunctionsVIEW();
                break;
            default:
                throw new InvalidInput("Invalid Input");
        }
    }

    public static void userDetailFunctionsADD() throws Exception {
        Scanner sc = new Scanner(System.in);

        String customerID = CustomerIDGenerator();
        System.out.println("Customer ID: " + customerID);
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();
        while (!isValidName(name)) {
            System.out.print("Enter valid Customer Name: ");
            name = sc.nextLine();
        }

        System.out.print("Enter Customer's Email: ");
        String email = sc.nextLine();
        while (!isValidEmail(email)) {
            System.out.print("Enter valid Customer's Email: ");
            email = sc.nextLine();
        }

        System.out.print("Enter Customer's Phone: ");
        Long phone = sc.nextLong();
        while (!isValidPhoneNumber(phone)) {
            System.out.print("Enter valid Customer's Phone: ");
            phone = sc.nextLong();
        }

        sc.nextLine();
        System.out.print("Enter Customer's Address: ");
        String address = sc.nextLine();

        CustomerDetails customerDetails = new CustomerDetails(customerID, name, email, phone, address);
        customerDetails.addCustomer(conn);
        customerList.add(customerDetails);
        System.out.println("Do you want to add package(yes/no):");
        String packageUserDeatilFunction = sc.nextLine();
        if (packageUserDeatilFunction.equalsIgnoreCase("yes")) {
            viewPackages(customerID);
        } else {
            return;
        }
    }

    public static void userDetailFunctionsUPDATE() throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Customer ID to update: ");
        String customerID = sc.nextLine();

        for (CustomerDetails customer : customerList) {
            if (customer.getCustomerID().equals(customerID)) {
                System.out.print("Enter new Customer Name: ");
                String name = sc.nextLine();
                while (!isValidName(name)) {
                    System.out.print("Enter valid Customer Name: ");
                    name = sc.nextLine();
                }

                System.out.print("Enter new Customer's Email: ");
                String email = sc.nextLine();
                while (!isValidEmail(email)) {
                    System.out.print("Enter valid Customer's Email: ");
                    email = sc.nextLine();
                }

                System.out.print("Enter new Customer's Phone: ");
                Long phone = sc.nextLong();
                while (!isValidPhoneNumber(phone)) {
                    System.out.print("Enter valid Customer's Phone: ");
                    phone = sc.nextLong();
                }

                sc.nextLine();
                System.out.print("Enter new Customer's Address: ");
                String address = sc.nextLine();

                customer.updateCustomer(conn);
                break;
            }
        }
    }

    public static void userDetailFunctionsDELETE() throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Customer ID to delete: ");
        String customerID = sc.nextLine();

        customerList.removeIf(customer -> customer.getCustomerID().equals(customerID));
        CustomerDetails customerToDelete = new CustomerDetails(customerID, null, null, 0, null);
        customerToDelete.deleteCustomer(conn);
    }

    public static void userDetailFunctionsVIEW() {
        try {

            if (conn == null || conn.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/toursandtravel", "root", "");
            }

            CustomerDetails.viewCustomerDetails(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * --------------------------- Package Details start ----------------->
     */

    public static void viewPackages(String customerIDinput) throws Exception {
        PackageDetails.viewPackages();

        Scanner sc = new Scanner(System.in);

        System.out.print("Do you want to select the package (Yes/No): ");
        String askPackageSelection = sc.nextLine();
        if (askPackageSelection.equalsIgnoreCase("yes")) {
            System.out.print("Enter the Package ID to select: ");
            String selectedPackageID = sc.nextLine();

            PackageDetails selectedPackage = PackageDetails.getPackageById(selectedPackageID);
            if (selectedPackage != null) {
                System.out.println("You selected: " + selectedPackage.getPackageName());
                System.out.println("Package Price: " + selectedPackage.getPrice());

                String customerID;
                if (customerIDinput == null) {
                    System.out.print("Enter Customer ID: ");
                    customerID = sc.nextLine();
                } else {
                    customerID = customerIDinput;
                }

                Payment payment = Payment.createPayment(Main.conn, customerID, selectedPackage.getPrice());

                if (payment.isPaymentStatus()) {
                    System.out.println("Payment successful! Payment ID: " + payment.getPaymentID());

                    Ticket.generateTicket(customerID, selectedPackage.getPackageID(), payment.getPaymentID(),
                            payment.getAmount(), payment.isPaymentStatus());
                } else {
                    System.out.println("Payment failed.");
                }
            } else {
                System.out.println("Invalid Package ID.");
            }
        } else if (askPackageSelection.equalsIgnoreCase("no")) {
            return;
        } else {
            System.out.println("Invalid Input!!!");
            viewPackages(customerIDinput);
        }
    }

    /*
     * --------------------------- Other Methods ------------------------->
     */
    public static boolean isValidEmail(String email) {

        if (email == null || email.isEmpty()) {
            return false;
        }

        int atIndex = email.indexOf('@');
        if (atIndex == -1 || email.indexOf('@', atIndex + 1) != -1) {
            return false;
        }

        String namePart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex + 1);

        if (namePart.isEmpty() || domainPart.isEmpty()) {
            return false;
        }

        if (domainPart.indexOf('.') == -1) {
            return false;
        }

        if (domainPart.startsWith(".") || domainPart.endsWith(".")) {
            return false;
        }
        if (email.length() > 254) {
            return false;
        }
        return true;
    }

    public static boolean isValidPhoneNumber(long phoneNumber) {
        return Long.toString(phoneNumber).length() == 10;
    }

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    private static final String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";

    public static String CustomerIDGenerator() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(8);

        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(ALPHABETS.length());
            sb.append(ALPHABETS.charAt(index));
        }
        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(DIGITS.length());
            sb.append(DIGITS.charAt(index));
        }
        int lastDigitIndex = random.nextInt(ALPHABETS.length());
        sb.append(ALPHABETS.charAt(lastDigitIndex));
        return sb.toString();
    }

    public static class InvalidInput extends Exception {
        public InvalidInput(String message) {
            super(message);
        }
    }
}
