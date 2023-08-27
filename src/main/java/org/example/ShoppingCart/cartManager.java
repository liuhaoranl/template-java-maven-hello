package org.example.ShoppingCart;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class cartManager {
    private static final String CART_DB_URL = "jdbc:sqlite:cart.db";
    private static final String GOODS_DB_URL = "jdbc:sqlite:goods.db";
    private static final String PURCHASE_HISTORY_DB_URL = "jdbc:sqlite:purchase_history.db";
    private Scanner scanner;

    
    public void displayGoodsInformation() {
        try (Connection connection = DriverManager.getConnection(GOODS_DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT name, model, quantity, price FROM Goods");
             ResultSet resultSet = statement.executeQuery()) {
            System.out.println("商品信息：");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String model = resultSet.getString("model");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                
                System.out.println("商品名称：" + name);
                System.out.println("商品类别：" + model);
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
        System.out.println("警告：你正在尝试从购物车中移除商品 " + product);
        System.out.print("确认是否继续移除操作？ (输入 'yes' 或 'no'): ");
        String confirmation = scanner.nextLine();
    
        if (confirmation.equalsIgnoreCase("yes")) {
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
        } else {
            System.out.println("移除操作已取消");
        }
    
        return false;
    }
    

    public boolean updateQuantity(String product, int newQuantity) {//修改购物车中的商品数量
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
            System.out.println("修改购物车商品数量失败: " + e.getMessage());
        }

        return false;
    }

    public String getPurchasedItemsFromCart() {
        StringBuilder items = new StringBuilder();
        try (Connection connection = DriverManager.getConnection(CART_DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT product, quantity FROM Cart");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String product = resultSet.getString("product");
                int quantity = resultSet.getInt("quantity");
                items.append(product).append(" (数量: ").append(quantity).append(")").append(", ");
            }
        } catch (SQLException e) {
            System.out.println("获取购买商品清单失败: " + e.getMessage());
        }
        return items.toString();
    }


    private void savePurchaseHistory(String username, Statement cartStatement) {
        try (Connection connection = DriverManager.getConnection(PURCHASE_HISTORY_DB_URL);
             PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO PurchaseHistory (username, purchased_items, quantity) VALUES (?, ?, ?)")) {
            ResultSet cartResultSet = cartStatement.executeQuery("SELECT * FROM Cart");
            while (cartResultSet.next()) {
                String product = cartResultSet.getString("product");
                int quantity = cartResultSet.getInt("quantity");
    
                statement.setString(1, username);
                statement.setString(2, product);
                statement.setInt(3, quantity);
                statement.executeUpdate();
            }
    
            System.out.println("购买历史已保存！");
        } catch (SQLException e) {
            System.out.println("保存购买历史失败: " + e.getMessage());
        }
    }


    public void checkout(String username) {
    
        // 创建连接和购物车Statement
        try (Connection cartConnection = DriverManager.getConnection(CART_DB_URL);
             Statement cartStatement = cartConnection.createStatement()) {
    
            // 更新Goods数据库中商品数量
            try (Connection connection = DriverManager.getConnection(GOODS_DB_URL);
                 Statement goodsStatement = connection.createStatement()) {
    
                ResultSet cartResultSet = cartStatement.executeQuery("SELECT * FROM Cart");
                while (cartResultSet.next()) {
                    String product = cartResultSet.getString("product");
                    int quantity = cartResultSet.getInt("quantity");
    
                    // 更新商品数量
                    String updateGoodsQuery = "UPDATE Goods SET quantity = quantity - " + quantity + " WHERE name = '" + product + "'";
                    goodsStatement.executeUpdate(updateGoodsQuery);
                }
    
                // 清空购物车
                cartStatement.executeUpdate("DELETE FROM Cart");
    
                System.out.println("付款成功！感谢您的购买！");
    
                // 在这里保存购物历史
                savePurchaseHistory(username,cartStatement);
            } catch (SQLException e) {
                System.out.println("结账过程中出现错误: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("创建购物车连接失败: " + e.getMessage());
        }
    }
    
    
    public void viewPurchaseHistory() {
        try (Connection connection = DriverManager.getConnection(PURCHASE_HISTORY_DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM PurchaseHistory")) {
            ResultSet resultSet = statement.executeQuery();
    
            while (resultSet.next()) {
              
                String username = resultSet.getString("username");
                String purchased_items = resultSet.getString("purchased_items");
                String quantity = resultSet.getString("quantity");

    
                System.out.println("用户名： " + username + ", 商品： " + purchased_items+ ", 数量： " + quantity);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve users: " + e.getMessage());
        }
    }
   
} 