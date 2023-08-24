package org.example.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.util.Scanner;

public class MyUserManager {
    private static final String DB_URL = "jdbc:sqlite:users.db";
    private Scanner scanner;
    public MyUserManager(Scanner scanner) {
        this.scanner = scanner;
    }
    public boolean registerUser(String username, String password) {
        // 检查用户名长度
        if (username.length() < 5) {
            System.out.println("用户名长度不能少于5个字符");
            return false;
        }

        // 检查密码长度
        if (password.length() <= 8) {
            System.out.println("密码长度必须大于8个字符");
            return false;
        }

        // 检查密码格式是否满足要求
        if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", password)) {
            System.out.println("密码必须包含大小写字母、数字和特殊字符");
            return false;
        }

        // 读取用户手机号和邮箱
        System.out.print("请输入手机号: ");
       
        String phoneNumber = scanner.nextLine();

        System.out.print("请输入邮箱: ");
        String email = scanner.nextLine();

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("用户名已存在");
                return false;
            }

            // 注册用户
            try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Users (username, password, phone_number, email) VALUES (?, ?, ?, ?)")) {
                insertStatement.setString(1, username);
                insertStatement.setString(2, password);
                insertStatement.setString(3, phoneNumber);
                insertStatement.setString(4, email);
                insertStatement.executeUpdate();
                System.out.println("注册成功");
                return true;
            } catch (SQLException e) {
                System.out.println("注册用户失败: " + e.getMessage());
            }
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
