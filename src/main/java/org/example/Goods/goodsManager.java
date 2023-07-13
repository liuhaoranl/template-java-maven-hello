package org.example.Goods;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class goodsManager {
    private static final String GOODS_DB_URL = "jdbc:sqlite:goods.db";

    public void addGoods(String name, String category, int quantity, double cost, double price) {
        try (Connection connection = DriverManager.getConnection(GOODS_DB_URL);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Goods (name, category, quantity, cost, price) VALUES (?, ?, ?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, category);
            statement.setInt(3, quantity);
            statement.setDouble(4, cost);
            statement.setDouble(5, price);
            statement.executeUpdate();
            System.out.println("商品信息添加成功");
        } catch (SQLException e) {
            System.out.println("商品信息添加失败: " + e.getMessage());
        }
    }

    public void updateGoods(String name, String option, String newValue) {
        String columnName;
        switch (option) {
            case "1":
                columnName = "name";
                break;
            case "2":
                columnName = "category";
                break;
            case "3":
                columnName = "cost";
                break;
            case "4":
                columnName = "quantity";
                break;
            case "5":
                columnName = "price";
                break;
            default:
                System.out.println("无效的选项");
                return;
        }

        try (Connection connection = DriverManager.getConnection(GOODS_DB_URL);
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Goods SET " + columnName + " = ? WHERE name LIKE ?")) {
            statement.setString(1, newValue);
            statement.setString(2, "%" + name + "%");
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("商品信息修改成功");
            } else {
                System.out.println("未找到匹配的商品");
            }
        } catch (SQLException e) {
            System.out.println("商品信息修改失败: " + e.getMessage());
        }
    }

    public void deleteGoods(String keyword) {
        try (Connection connection = DriverManager.getConnection(GOODS_DB_URL);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Goods WHERE name LIKE ?")) {
            statement.setString(1, "%" + keyword + "%");
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("商品信息删除成功");
            } else {
                System.out.println("未找到匹配的商品");
            }
        } catch (SQLException e) {
            System.out.println("商品信息删除失败: " + e.getMessage());
        }
    }

    public void queryGoods(String keyword) {
        try (Connection connection = DriverManager.getConnection(GOODS_DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Goods WHERE name LIKE ?")) {
            statement.setString(1, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                int quantity = resultSet.getInt("quantity");
                double cost = resultSet.getDouble("cost");
                double price = resultSet.getDouble("price");

                System.out.println("商品名称: " + name);
                System.out.println("商品类别: " + category);
                System.out.println("商品数量: " + quantity);
                System.out.println("商品成本: " + cost);
                System.out.println("商品售价: " + price);
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("商品信息查询失败: " + e.getMessage());
        }
    }

    public void listAllGoods() {
        try (Connection connection = DriverManager.getConnection(GOODS_DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Goods")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                int quantity = resultSet.getInt("quantity");
                double cost = resultSet.getDouble("cost");
                double price = resultSet.getDouble("price");

                System.out.println("商品名称: " + name);
                System.out.println("商品类别: " + category);
                System.out.println("商品数量: " + quantity);
                System.out.println("商品成本: " + cost);
                System.out.println("商品售价: " + price);
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("列出所有商品信息失败: " + e.getMessage());
        }
    }
}

