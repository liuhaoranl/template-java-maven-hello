package org.example.Administrator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class administratorOperation {
    private static final String DB_URL = "jdbc:sqlite:users.db";

   
    public boolean changeAdminPassword(String username, String newPassword) {//修改密码
        try (Connection connection = DriverManager.getConnection(DB_URL);
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

    public List<String> listAllUsers() {//列出用户信息
        List<String> userList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT username FROM Users")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                userList.add(username);
            }
        } catch (SQLException e) {
            System.out.println("列出用户信息失败: " + e.getMessage());
        }

        return userList;
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

    public String getUserInfo(String username) {//查询用户信息
        String userInfo = null;

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userId = resultSet.getString("id");
                String userPassword = resultSet.getString("password");
                userInfo = "用户ID: " + userId + ", 用户名: " + username + ", 密码: " + userPassword;
            } else {
                System.out.println("用户名不存在");
            }
        } catch (SQLException e) {
            System.out.println("查询用户信息失败: " + e.getMessage());
        }

        return userInfo;
    }
}







