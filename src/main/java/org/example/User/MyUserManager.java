package org.example.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyUserManager {
    private static final String DB_URL = "jdbc:sqlite:users.db";

    public boolean registerUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Users (username, password) VALUES (?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            System.out.println("注册成功");

            return true;
        } catch (SQLException e) {
            System.out.println("注册用户失败: " + e.getMessage());
        }

        return false;
    }

    public boolean login(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                if (password.equals(storedPassword)) {
                    System.out.println("登录成功");
                    return true;
                } else {
                    System.out.println("密码不正确。");
                }
            } else {
                System.out.println("用户名不存在。");
            }
        } catch (SQLException e) {
            System.out.println("登录失败: " + e.getMessage());
        }

        return false;
    }


    public void displayAllUsers() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users")) {
            ResultSet resultSet = statement.executeQuery();
    
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
    
                System.out.println("Username: " + username + ", Password: " + password);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve users: " + e.getMessage());
        }
    }
    
}
