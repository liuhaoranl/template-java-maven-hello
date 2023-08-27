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

    public boolean initializeAdminAccount() {//管理员账号初始化
        try (Connection connection = DriverManager.getConnection(ADMIN_DB_URL);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Administrators (username TEXT PRIMARY KEY, password TEXT)");
    
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Administrators WHERE username = 'admin'");
            if (!resultSet.next()) {
                statement.executeUpdate("INSERT INTO Administrators (username, password) VALUES ('admin', 'ynuinfo#777')");
                return true;
            } 
        } catch (SQLException e) {
            System.out.println("管理员账号初始化失败: " + e.getMessage());
        }
    
        return false;
    }
    
    public boolean loginAdmin(String username, String password) {//管理员登录
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
    
    public boolean changeAdminPassword(String username, String newPassword) {//管理员密码修改
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
    

    public boolean resetUserPassword(String username, String newPassword) {//重置用户密码
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

    public boolean deleteUser(String username) {//删除用户信息
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Users WHERE username = ?")) {
            statement.setString(1, username);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("用户信息删除成功");
                return true;
            } else {
                System.out.println("用户名不存在");
            }
        } catch (SQLException e) {
            System.out.println("删除用户信息失败: " + e.getMessage());
        }

        return false;
    }

    public Optional<String> getUserInfo(String username) {//查询用户信息
        String userInfo = null;
    
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                String userPassword = resultSet.getString("password");
                userInfo = "用户名: " + username + ", 密码: " + userPassword;
            } else {
                System.out.println("用户名不存在");
            }
        } catch (SQLException e) {
            System.out.println("查询用户信息失败: " + e.getMessage());
        }
    
        return Optional.ofNullable(userInfo);
    }
}