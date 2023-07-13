package org.example.ShoppingCart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class cartManager {
    private static final String CART_DB_URL = "jdbc:sqlite:cart.db";
    private static final String GOODS_DB_URL = "jdbc:sqlite:goods.db";

    
    public void displayGoodsInformation() {
        try (Connection connection = DriverManager.getConnection(GOODS_DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT name, category, quantity, price FROM Goods");
             ResultSet resultSet = statement.executeQuery()) {
            System.out.println("商品信息：");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                
                System.out.println("商品名称：" + name);
                System.out.println("商品类别：" + category);
                System.out.println("商品数量：" + quantity);
                System.out.println("商品售价：" + price);
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            System.out.println("显示商品信息失败: " + e.getMessage());
        }
    }

    public boolean addToCart(String product, int quantity) {
        try (Connection connection = DriverManager.getConnection(CART_DB_URL);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Cart (product, quantity) VALUES (?, ?)")) {
            statement.setString(1, product);
            statement.setInt(2, quantity);
            statement.executeUpdate();
            System.out.println("商品已加入购物车");

            return true;
        } catch (SQLException e) {
            System.out.println("添加商品到购物车失败: " + e.getMessage());
        }

        return false;
    }

    public boolean removeFromCart(String product) {
        try (Connection connection = DriverManager.getConnection(CART_DB_URL);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Cart WHERE product = ?")) {
            statement.setString(1, product);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("商品已从购物车中移除");
                return true;
            } else {
                System.out.println("购物车中不存在该商品");
            }
        } catch (SQLException e) {
            System.out.println("从购物车中移除商品失败: " + e.getMessage());
        }

        return false;
    }

    public boolean updateQuantity(String product, int newQuantity) {
        try (Connection connection = DriverManager.getConnection(CART_DB_URL);
             PreparedStatement statement = connection.prepareStatement("UPDATE Cart SET quantity = ? WHERE product = ?")) {
            statement.setInt(1, newQuantity);
            statement.setString(2, product);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("商品数量已更新");
                return true;
            } else {
                System.out.println("购物车中不存在该商品");
            }
        } catch (SQLException e) {
            System.out.println("更新购物车商品数量失败: " + e.getMessage());
        }

        return false;
    }

    public void checkout() {
        // 执行结账操作的逻辑
        System.out.println("结账成功！");
    }

    public void viewPurchaseHistory(String username) {
        try (Connection connection = DriverManager.getConnection(CART_DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Cart WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("购物历史：");
            while (resultSet.next()) {
                String product = resultSet.getString("product");
                int quantity = resultSet.getInt("quantity");

                System.out.println("商品：" + product + "，数量：" + quantity);
            }
        } catch (SQLException e) {
            System.out.println("查看购物历史失败: " + e.getMessage());
        }
    }
}

