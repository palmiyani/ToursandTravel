import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDetails {
    private String customerID;
    private String name;
    private String email;
    private long phone;
    private String address;

    public CustomerDetails(String customerID, String name, String email, long phone, String address) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public long getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void addCustomer(Connection conn) {
        String sql = "INSERT INTO customerdetails (customerID, name, email, phone, address) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerID);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setLong(4, phone);
            pstmt.setString(5, address);
            pstmt.executeUpdate();
            System.out.println("Customer added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding customer.");
            e.printStackTrace();
        }
    }

    public void updateCustomer(Connection conn) {
        String sql = "UPDATE customerdetails SET name = ?, email = ?, phone = ?, address = ? WHERE customerID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setLong(3, phone);
            pstmt.setString(4, address);
            pstmt.setString(5, customerID);
            pstmt.executeUpdate();
            System.out.println("Customer updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating customer.");
            e.printStackTrace();
        }
    }

    public void deleteCustomer(Connection conn) {
        String sql = "DELETE FROM customerdetails WHERE customerID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerID);
            pstmt.executeUpdate();
            System.out.println("Customer deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting customer.");
            e.printStackTrace();
        }
    }

    public static void viewCustomerDetails(Connection conn) {
        String sql = "SELECT customerID, name FROM customerdetails";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String customerID = rs.getString("customerID");
                String name = rs.getString("name");
                System.out.println("Customer ID: " + customerID + ", Name: " + name);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching customer details.");
            e.printStackTrace();
        }
    }

    public static CustomerDetails getCustomerById(String customerID, Connection conn) throws SQLException {
        String query = "SELECT * FROM customerdetails WHERE customerID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, customerID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new CustomerDetails(
                        rs.getString("customerID"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getLong("phone"),
                        rs.getString("address")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "CustomerDetails{" +
                "customerID='" + customerID + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", address='" + address + '\'' +
                '}';
    }
}
