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

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("UPDATE Users SET password = ? WHERE username = ? AND password = ?")) {
            statement.setString(1, newPassword);
            statement.setString(2, username);
            statement.setString(3, oldPassword);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("密码修改成功");
                return true;
            } else {
                System.out.println("用户名或原密码不正确");
            }
        } catch (SQLException e) {
            System.out.println("密码修改失败: " + e.getMessage());
        }

        return false;
    }

    public boolean resetPassword(String username, String newPassword) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("UPDATE Users SET password = ? WHERE username = ?")) {
            statement.setString(1, newPassword);
            statement.setString(2, username);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("密码重置成功");
                return true;
            } else {
                System.out.println("用户名不存在");
            }
        } catch (SQLException e) {
            System.out.println("密码重置失败: " + e.getMessage());
        }

        return false;
    }
}

