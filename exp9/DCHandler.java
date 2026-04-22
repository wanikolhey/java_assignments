package LAB_9;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DCHandler extends Thread {
    private final DBConn dbconn;

    public DCHandler(DBConn dbConn) {
        this.dbconn = dbConn;
    }

    @Override
    public void run() {
        try {
            Connection conn = dbconn.connect();
            createTables(conn);
            clearTables(conn);
            insertRestaurants(conn);
            insertMenuItems(conn);

            System.out.println("\n1) Menu items where price <= 100");
            printQueryResult(conn,
                    "SELECT Id, Name, Price, ResId FROM MenuItem WHERE Price <= 100 ORDER BY Id");

            System.out.println("\n2) Menu items available in Cafe Java");
            printQueryResult(conn,
                    "SELECT m.Id, m.Name, m.Price, r.Name AS RestaurantName "
                            + "FROM MenuItem m JOIN Restaurant r ON m.ResId = r.Id "
                            + "WHERE r.Name = 'Cafe Java' ORDER BY m.Id");

            System.out.println("\n3) Update menu item prices <= 100 to 200");
            updateMenuItemPrices(conn);
            printQueryResult(conn,
                    "SELECT Id, Name, Price, ResId FROM MenuItem ORDER BY Id");

            System.out.println("\n4) Delete menu items where name starts with P");
            deleteMenuItemsStartingWithP(conn);
            printQueryResult(conn,
                    "SELECT Id, Name, Price, ResId FROM MenuItem ORDER BY Id");
        } catch (SQLException e) {
            throw new RuntimeException("Error while performing CRUD operations.", e);
        } finally {
            dbconn.disconnect();
        }
    }

    private void createTables(Connection conn) throws SQLException {
        try (Statement pragmaStmt = conn.createStatement()) {
            pragmaStmt.execute("PRAGMA foreign_keys = ON");
        }

        String createRestaurant = "CREATE TABLE IF NOT EXISTS Restaurant ("
            + "Id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Name VARCHAR(100) NOT NULL, "
                + "Address VARCHAR(255) NOT NULL"
                + ")";

        String createMenuItem = "CREATE TABLE IF NOT EXISTS MenuItem ("
            + "Id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Name VARCHAR(100) NOT NULL, "
                + "Price DECIMAL(10,2) NOT NULL, "
            + "ResId INTEGER NOT NULL, "
                + "FOREIGN KEY (ResId) REFERENCES Restaurant(Id)"
                + ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createRestaurant);
            stmt.execute(createMenuItem);
            System.out.println("Tables are ready.");
        }
    }

    private void clearTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM MenuItem");
            stmt.executeUpdate("DELETE FROM Restaurant");
            stmt.executeUpdate("DELETE FROM sqlite_sequence WHERE name='MenuItem'");
            stmt.executeUpdate("DELETE FROM sqlite_sequence WHERE name='Restaurant'");
            System.out.println("Old data cleared.");
        }
    }

    private void insertRestaurants(Connection conn) throws SQLException {
        String insertSql = "INSERT INTO Restaurant (Id, Name, Address) VALUES (?, ?, ?)";

        String[][] restaurants = {
                {"1", "Cafe Java", "Aundh, Pune"},
                {"2", "Spice Route", "Baner, Pune"},
                {"3", "Tasty Bowl", "Kothrud, Pune"},
                {"4", "Urban Bites", "Wakad, Pune"},
                {"5", "Food Hub", "Shivajinagar, Pune"},
                {"6", "Royal Dine", "Koregaon Park, Pune"},
                {"7", "Green Leaf", "Hinjewadi, Pune"},
                {"8", "Cozy Corner", "Viman Nagar, Pune"},
                {"9", "Street Eats", "Camp, Pune"},
                {"10", "Sunrise Cafe", "Pimpri, Pune"}
        };

        try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
            for (String[] restaurant : restaurants) {
                ps.setInt(1, Integer.parseInt(restaurant[0]));
                ps.setString(2, restaurant[1]);
                ps.setString(3, restaurant[2]);
                ps.addBatch();
            }
            ps.executeBatch();
            System.out.println("Inserted 10 records into Restaurant.");
        }

        printQueryResult(conn, "SELECT Id, Name, Address FROM Restaurant ORDER BY Id");
    }

    private void insertMenuItems(Connection conn) throws SQLException {
        String insertSql = "INSERT INTO MenuItem (Id, Name, Price, ResId) VALUES (?, ?, ?, ?)";

        Object[][] menuItems = {
                {1, "Pasta", 90.00, 1},
                {2, "Burger", 120.00, 1},
                {3, "Pizza", 95.00, 2},
                {4, "Sandwich", 80.00, 3},
                {5, "Paratha", 70.00, 4},
                {6, "Noodles", 140.00, 5},
                {7, "Pav Bhaji", 100.00, 6},
                {8, "Salad", 60.00, 7},
                {9, "Paneer Roll", 85.00, 8},
                {10, "Coffee", 50.00, 1}
        };

        try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
            for (Object[] menuItem : menuItems) {
                ps.setInt(1, (int) menuItem[0]);
                ps.setString(2, (String) menuItem[1]);
                ps.setDouble(3, (double) menuItem[2]);
                ps.setInt(4, (int) menuItem[3]);
                ps.addBatch();
            }
            ps.executeBatch();
            System.out.println("Inserted 10 records into MenuItem.");
        }

        printQueryResult(conn, "SELECT Id, Name, Price, ResId FROM MenuItem ORDER BY Id");
    }

    private void updateMenuItemPrices(Connection conn) throws SQLException {
        String updateSql = "UPDATE MenuItem SET Price = 200 WHERE Price <= 100";
        try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
            int rows = ps.executeUpdate();
            System.out.println("Updated rows: " + rows);
        }
    }

    private void deleteMenuItemsStartingWithP(Connection conn) throws SQLException {
        String deleteSql = "DELETE FROM MenuItem WHERE Name LIKE 'P%'";
        try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
            int rows = ps.executeUpdate();
            System.out.println("Deleted rows: " + rows);
        }
    }

    private void printQueryResult(Connection conn, String sql) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            printResultSetAsTable(rs);
        }
    }

    private void printResultSetAsTable(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            System.out.printf("%-18s", metaData.getColumnLabel(i));
        }
        System.out.println();

        for (int i = 1; i <= columnCount; i++) {
            System.out.print("------------------");
        }
        System.out.println();

        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-18s", rs.getString(i));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        DBConn dbConn = new DBConn();
        DCHandler dcHandler = new DCHandler(dbConn);
        dcHandler.run();
    }
}
