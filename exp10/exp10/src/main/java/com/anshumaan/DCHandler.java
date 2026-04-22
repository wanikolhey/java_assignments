package com.anshumaan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DCHandler {
    private final DBConn dbConn;

    public DCHandler(DBConn dbConn) {
        this.dbConn = dbConn;
    }

    public void initializeDatabase() {
        try {
            Connection conn = dbConn.connect();
            createTables(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to initialize database schema.", e);
        }
    }

    private void createTables(Connection conn) throws SQLException {
        try (Statement pragmaStmt = conn.createStatement()) {
            pragmaStmt.execute("PRAGMA foreign_keys = ON");
        }

        String createRestaurant = "CREATE TABLE IF NOT EXISTS Restaurant ("
                + "Id INTEGER PRIMARY KEY, "
                + "Name TEXT NOT NULL, "
                + "Address TEXT NOT NULL"
                + ")";

        String createMenuItem = "CREATE TABLE IF NOT EXISTS MenuItem ("
                + "Id INTEGER PRIMARY KEY, "
                + "Name TEXT NOT NULL, "
                + "Price REAL NOT NULL, "
                + "ResId INTEGER NOT NULL, "
                + "FOREIGN KEY (ResId) REFERENCES Restaurant(Id)"
                + ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createRestaurant);
            stmt.execute(createMenuItem);
        }
    }

    public String insertRestaurant(int id, String name, String address) {
        String sql = "INSERT INTO Restaurant (Id, Name, Address) VALUES (?, ?, ?)";
        try (PreparedStatement ps = dbConn.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, address);
            int rows = ps.executeUpdate();
            return "Inserted Restaurant rows: " + rows;
        } catch (SQLException e) {
            return "Insert Restaurant failed: " + e.getMessage();
        }
    }

    public String selectAllRestaurants() {
        String sql = "SELECT Id, Name, Address FROM Restaurant ORDER BY Id";
        return executeSelect(sql);
    }

    public String updateRestaurantAddress(int id, String newAddress) {
        String sql = "UPDATE Restaurant SET Address = ? WHERE Id = ?";
        try (PreparedStatement ps = dbConn.getConnection().prepareStatement(sql)) {
            ps.setString(1, newAddress);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            return "Updated Restaurant rows: " + rows;
        } catch (SQLException e) {
            return "Update Restaurant failed: " + e.getMessage();
        }
    }

    public String deleteRestaurant(int id) {
        String sql = "DELETE FROM Restaurant WHERE Id = ?";
        try (PreparedStatement ps = dbConn.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return "Deleted Restaurant rows: " + rows;
        } catch (SQLException e) {
            return "Delete Restaurant failed: " + e.getMessage();
        }
    }

    public String insertMenuItem(int id, String name, double price, int resId) {
        String sql = "INSERT INTO MenuItem (Id, Name, Price, ResId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = dbConn.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, price);
            ps.setInt(4, resId);
            int rows = ps.executeUpdate();
            return "Inserted MenuItem rows: " + rows;
        } catch (SQLException e) {
            return "Insert MenuItem failed: " + e.getMessage();
        }
    }

    public String selectAllMenuItems() {
        String sql = "SELECT Id, Name, Price, ResId FROM MenuItem ORDER BY Id";
        return executeSelect(sql);
    }

    public String selectMenuItemsByPrice(double maxPrice) {
        String sql = "SELECT Id, Name, Price, ResId FROM MenuItem WHERE Price <= ? ORDER BY Id";
        try (PreparedStatement ps = dbConn.getConnection().prepareStatement(sql)) {
            ps.setDouble(1, maxPrice);
            try (ResultSet rs = ps.executeQuery()) {
                return resultSetToTable(rs);
            }
        } catch (SQLException e) {
            return "Select MenuItem by price failed: " + e.getMessage();
        }
    }

    public String selectMenuItemsByRestaurantName(String restaurantName) {
        String sql = "SELECT m.Id, m.Name, m.Price, r.Name AS Restaurant "
                + "FROM MenuItem m JOIN Restaurant r ON m.ResId = r.Id "
                + "WHERE r.Name = ? ORDER BY m.Id";
        try (PreparedStatement ps = dbConn.getConnection().prepareStatement(sql)) {
            ps.setString(1, restaurantName);
            try (ResultSet rs = ps.executeQuery()) {
                return resultSetToTable(rs);
            }
        } catch (SQLException e) {
            return "Select MenuItem by Restaurant failed: " + e.getMessage();
        }
    }

    public String updateMenuItemPrice(int id, double newPrice) {
        String sql = "UPDATE MenuItem SET Price = ? WHERE Id = ?";
        try (PreparedStatement ps = dbConn.getConnection().prepareStatement(sql)) {
            ps.setDouble(1, newPrice);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            return "Updated MenuItem rows: " + rows;
        } catch (SQLException e) {
            return "Update MenuItem failed: " + e.getMessage();
        }
    }

    public String deleteMenuItem(int id) {
        String sql = "DELETE FROM MenuItem WHERE Id = ?";
        try (PreparedStatement ps = dbConn.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return "Deleted MenuItem rows: " + rows;
        } catch (SQLException e) {
            return "Delete MenuItem failed: " + e.getMessage();
        }
    }

    private String executeSelect(String sql) {
        try (Statement stmt = dbConn.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return resultSetToTable(rs);
        } catch (SQLException e) {
            return "Select failed: " + e.getMessage();
        }
    }

    private String resultSetToTable(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        StringBuilder builder = new StringBuilder();

        for (int i = 1; i <= columnCount; i++) {
            builder.append(String.format("%-18s", metaData.getColumnLabel(i)));
        }
        builder.append('\n');

        for (int i = 1; i <= columnCount; i++) {
            builder.append("------------------");
        }
        builder.append('\n');

        boolean hasRows = false;
        while (rs.next()) {
            hasRows = true;
            for (int i = 1; i <= columnCount; i++) {
                builder.append(String.format("%-18s", rs.getString(i)));
            }
            builder.append('\n');
        }

        if (!hasRows) {
            builder.append("No records found.\n");
        }

        return builder.toString();
    }
}
