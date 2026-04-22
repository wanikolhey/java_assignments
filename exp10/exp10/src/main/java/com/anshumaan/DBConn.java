package com.anshumaan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DBConn {
    private static final String URL = buildDatabaseUrl();

    private Connection conn = null;

    private static String buildDatabaseUrl() {
        List<Path> candidates = List.of(
                Path.of("LAB_10", "lab9.db"),
                Path.of("..", "lab9.db"),
                Path.of("lab9.db")
        );

        for (Path candidate : candidates) {
            if (Files.exists(candidate)) {
                return "jdbc:sqlite:" + candidate;
            }
        }

        return "jdbc:sqlite:" + Path.of("lab9.db");
    }

    public DBConn() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found.");
            e.printStackTrace();
        }
    }

    public Connection connect() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL);
                System.out.println("Connection to SQLite has been established.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to SQLite database.", e);
        }
        return conn;
    }

    public Connection getConnection() {
        if (!isConnected()) {
            connect();
        }
        return conn;
    }

    public boolean isConnected() {
        try {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection to SQLite has been closed.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                conn = null;
            }
        }
    }

    public static void main(String[] args) {
        DBConn dbConn = new DBConn();
        dbConn.connect();
        dbConn.disconnect();
    }
}
