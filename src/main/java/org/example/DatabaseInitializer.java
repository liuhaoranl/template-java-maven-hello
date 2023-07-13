package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String DB_URL = "jdbc:sqlite:users.db";

    public void initUserDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)";
            statement.executeUpdate(createTableQuery);
            System.out.println("Database initialized successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to initialize database: " + e.getMessage());
        }
    }

    private static final String ADMIN_DB_URL = "jdbc:sqlite:admin.db";

    public void initAdminDatabase() {
        try (Connection connection = DriverManager.getConnection(ADMIN_DB_URL);
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)";
            statement.executeUpdate(createTableQuery);
            System.out.println("Database initialized successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to initialize database: " + e.getMessage());
        }
    }

    private static final String GOODS_DB_URL = "jdbc:sqlite:goods.db";

    public void initGoodsDatabase() {
        try (Connection connection = DriverManager.getConnection(GOODS_DB_URL);
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)";
            statement.executeUpdate(createTableQuery);
            System.out.println("Database initialized successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to initialize database: " + e.getMessage());
        }
    }

    private static final String CART_DB_URL = "jdbc:sqlite:cart.db";

    public void initCartDatabase() {
        try (Connection connection = DriverManager.getConnection(CART_DB_URL);
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)";
            statement.executeUpdate(createTableQuery);
            System.out.println("Database initialized successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to initialize database: " + e.getMessage());
        }
    }
}
