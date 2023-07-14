package org.example.Administrator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class administratorOperation {
    private static final String DB_URL = "jdbc:sqlite:users.db";
    private static final String ADMIN_DB_URL = "jdbc:sqlite:admin.db";

    // 初始化管理员账号
    public boolean initializeAdminAccount() {
        try (Connection connection = DriverManager.getConnection(ADMIN_DB_URL);
             Statement statement = connection.createStatement()) {
            // 创建Administrators表
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Administrators (username TEXT PRIMARY KEY, password TEXT)");

            // 检查管理员账号是否存在
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Administrators WHERE username = 'admin'");
            if (!resultSet.next()) {
                // 初始化管理员账号
                statement.executeUpdate("INSERT INTO Administrators (username, password) VALUES ('admin', 'ynuinfo#777')");
                System.out.println("管理员账号初始化成功");
                return true;
            } else {
                System.out.println("管理员账号已存在");
            }
        } catch (SQLException e) {
            System.out.println("管理员账号初始化失败: " + e.getMessage());
        }

        return false;
    }

    // 管理员登录
    public boolean loginAdmin(String username, String password) {
        try (Connection connection = DriverManager.getConnection(ADMIN_DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Administrators WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                if (password.equals(storedPassword)) {
                    System.out.println("管理员登录成功");
                    return true;
                } else {
                    System.out.println("管理员密码不正确");
                }
            } else {
                System.out.println("管理员用户名不存在");
            }
        } catch (SQLException e) {
            System.out.println("管理员登录失败: " + e.getMessage());
        }

        return false;
    }

    // 修改管理员密码
    public boolean changeAdminPassword(String username, String newPassword) {
        try (Connection connection = DriverManager.getConnection(ADMIN_DB_URL);
             PreparedStatement statement = connection.prepareStatement("UPDATE Administrators SET password = ? WHERE username = ?")) {
            statement.setString(1, newPassword);
            statement.setString(2, username);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("管理员密码修改成功");
                return true;
            } else {
                System.out.println("管理员用户名不存在");
            }
        } catch (SQLException e) {
            System.out.println("管理员密码修改失败: " + e.getMessage());
        }

        return false;
    }

    // 重置用户密码
    public boolean resetUserPassword(String username, String newPassword) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("UPDATE Users SET password = ? WHERE username = ?")) {
            statement.setString(1, newPassword);
            statement.setString(2, username);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("用户密码重置成功");
                return true;
            } else {
                System.out.println("用户名不存在");
            }
        } catch (SQLException e) {
            System.out.println("用户密码重置失败: " + e.getMessage());
        }

        return false;
    }

    // 列出所有客户信息
    public void displayAllUsers() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                System.out.println("用户名：" + username + "，密码：" + password);
            }
        } catch (SQLException e) {
            System.out.println("获取用户信息失败: " + e.getMessage());
        }
    }

    // 删除客户
    public boolean deleteUser(int customerId) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Users WHERE id = ?")) {
            statement.setInt(1, customerId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("用户信息删除成功");
                return true;
            } else {
                System.out.println("用户ID不存在");
            }
        } catch (SQLException e) {
            System.out.println("删除用户信息失败: " + e.getMessage());
        }

        return false;
    }

    // 查询客户信息
    public Optional<String> getUserInfo(int customerId) {
        String customerInfo = null;

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE id = ?")) {
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                customerInfo = "用户名: " + username + "，密码: " + password;
            } else {
                System.out.println("用户ID不存在");
            }
        } catch (SQLException e) {
            System.out.println("查询用户信息失败: " + e.getMessage());
        }

        return Optional.ofNullable(customerInfo);
    }
}
