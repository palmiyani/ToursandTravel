import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class LoginSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/toursandtravel";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static HashMap<String, String> userDatabase = new HashMap<>();

    private static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void loadUserData() {
        String sql = "SELECT user_id, password FROM logininfo";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String userID = rs.getString("user_id");
                String password = rs.getString("password");
                userDatabase.put(userID, password);
            }
            System.out.println("User data loaded successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean authenticate(String userID, String password) {
        if (userDatabase.containsKey(userID)) {
            return userDatabase.get(userID).equals(password);
        }
        return false;
    }

    public static void addUSER(Connection con, String username, String passwordHash) throws SQLException{
        String query = "INSERT INTO logininfo (user_id, password) VALUES (?, ?)";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, passwordHash);
            int rows = pstmt.executeUpdate();
            if (rows>0) {
                System.out.println("Succesfully added the user");
            } else{
                System.out.println("Failed to add the user");

            }
    }
    public static void deleteUSER(Connection con, String username) throws SQLException{
        String adminName = "admin";
        if (username.toLowerCase().equals(adminName)) {
            System.out.println("Admin cannot be deleted");
        } else{
            String query = "DELETE FROM logininfo WHERE user_id = ?";
    
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, username);
                int rows = pstmt.executeUpdate();
                if (rows>0) {
                    System.out.println("Succesfully deleted the user");
                } else{
                    System.out.println("Failed to deleted the user");
    
                }
        }
    }
}
