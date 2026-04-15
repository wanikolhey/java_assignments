package exp7;

import java.sql.*;

public class RestaurantSystem {
    // Database credentials - Update 'root' and 'password' as per your local setup
    static final String DB_URL = "jdbc:mysql://localhost:3306/FoodiesDB?createDatabaseIfNotExist=true";
    static final String USER = "root";
    static final String PASS = "Wanii@0246";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            System.out.println("--- Setting up Database and Tables ---");
            setupDatabase(stmt);

            System.out.println("\n--- 1. Inserting 10 Records into each table ---");
            insertRecords(stmt);

            System.out.println("\n--- 2. MenuItem table where price <= 100 ---");
            displayQueryResult(stmt, "SELECT * FROM MenuItem WHERE Price <= 100");

            System.out.println("\n--- 3. MenuItems available in 'Cafe Java' ---");
            displayQueryResult(stmt, "SELECT m.Id, m.Name, m.Price, m.ResId FROM MenuItem m " +
                                     "JOIN Restaurant r ON m.ResId = r.Id WHERE r.Name = 'Cafe Java'");

            System.out.println("\n--- 4. Updating Price <= 100 to 200 ---");
            int updatedCount = stmt.executeUpdate("UPDATE MenuItem SET Price = 200 WHERE Price <= 100");
            System.out.println("Rows updated: " + updatedCount);
            displayQueryResult(stmt, "SELECT * FROM MenuItem");

            System.out.println("\n--- 5. Deleting MenuItems starting with 'P' ---");
            int deletedCount = stmt.executeUpdate("DELETE FROM MenuItem WHERE Name LIKE 'P%'");
            System.out.println("Rows deleted: " + deletedCount);
            displayQueryResult(stmt, "SELECT * FROM MenuItem");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setupDatabase(Statement stmt) throws SQLException {
        // Drop tables if they exist to avoid 'Table already exists' errors during testing
        stmt.executeUpdate("DROP TABLE IF EXISTS MenuItem");
        stmt.executeUpdate("DROP TABLE IF EXISTS Restaurant");

        // Create Restaurant Table
        stmt.executeUpdate("CREATE TABLE Restaurant (" +
                "Id INT PRIMARY KEY, " +
                "Name VARCHAR(100), " +
                "Address VARCHAR(255))");

        // Create MenuItem Table
        stmt.executeUpdate("CREATE TABLE MenuItem (" +
                "Id INT PRIMARY KEY, " +
                "Name VARCHAR(100), " +
                "Price DOUBLE, " +
                "ResId INT, " +
                "FOREIGN KEY (ResId) REFERENCES Restaurant(Id))");
        
        System.out.println("Tables created successfully.");
    }

    private static void insertRecords(Statement stmt) throws SQLException {
        // Inserting 10 Restaurants
        String[] restNames = {"Cafe Java", "Pasta Palace", "Burger Hub", "Pizza Hut", "Taco Bell", 
                              "Sushi Zen", "Spicy Thai", "Green Salad", "Diner 77", "Wrap It"};
        for (int i = 1; i <= 10; i++) {
            stmt.executeUpdate("INSERT INTO Restaurant VALUES (" + i + ", '" + restNames[i-1] + "', 'Location " + i + "')");
        }

        // Inserting 10 MenuItems
        // Note: Cafe Java (ID 1) gets specific items for the query test
        String[] itemNames = {"Pasta", "Pizza", "Coffee", "Paneer", "Tea", "Burger", "Pancakes", "Sushi", "Taco", "Fries"};
        double[] prices = {120, 250, 40, 180, 30, 90, 150, 300, 75, 50};
        for (int i = 1; i <= 10; i++) {
            int resId = (i <= 3) ? 1 : i; // First 3 items assigned to 'Cafe Java'
            stmt.executeUpdate("INSERT INTO MenuItem VALUES (" + i + ", '" + itemNames[i-1] + "', " + prices[i-1] + ", " + resId + ")");
        }
        System.out.println("10 records inserted into Restaurant and MenuItem.");
    }

    private static void displayQueryResult(Statement stmt, String query) throws SQLException {
        ResultSet rs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        // Print header
        for (int i = 1; i <= columnsNumber; i++) {
            System.out.print(String.format("%-15s", rsmd.getColumnName(i)));
        }
        System.out.println("\n------------------------------------------------------------");

        // Print rows
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                System.out.print(String.format("%-15s", rs.getString(i)));
            }
            System.out.println();
        }
    }
}