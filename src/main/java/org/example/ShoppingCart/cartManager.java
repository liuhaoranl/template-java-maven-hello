package org.example.ShoppingCart;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class cartManager {
    private static final String CART_DB_URL = "jdbc:sqlite:cart.db";
    private static final String GOODS_DB_URL = "jdbc:sqlite:goods.db";
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

    
    private double getProductPrice(String product) {//获取单价
        double price = 0.0;
    
        try (Connection connection = DriverManager.getConnection(GOODS_DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT price FROM Goods WHERE name = ?")) {
            statement.setString(1, product);
            ResultSet resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                price = resultSet.getDouble("price");
            } else {
                System.out.println("找不到商品：" + product);
            }
    
        } catch (SQLException e) {
            System.out.println("获取商品价格失败: " + e.getMessage());
        }
    
        return price;
    }


   

    public void updateGoodsQuantities() {
        try (Connection connection = DriverManager.getConnection(GOODS_DB_URL)) {
            // 获取购物车中的商品信息
            try (PreparedStatement cartStatement = connection.prepareStatement("SELECT product, quantity FROM Cart");
                 ResultSet cartResultSet = cartStatement.executeQuery()) {

                while (cartResultSet.next()) {
                    String product = cartResultSet.getString("product");
                    int cartQuantity = cartResultSet.getInt("quantity");

                    // 查询商品表中的数量
                    try (PreparedStatement goodsStatement = connection.prepareStatement("SELECT quantity FROM Goods WHERE name = ?")) {
                        goodsStatement.setString(1, product);
                        ResultSet goodsResultSet = goodsStatement.executeQuery();

                        if (goodsResultSet.next()) {
                            int currentQuantity = goodsResultSet.getInt("quantity");
                            int newQuantity = currentQuantity - cartQuantity;

                            // 更新商品数量
                            try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE Goods SET quantity = ? WHERE name = ?")) {
                                updateStatement.setInt(1, newQuantity);
                                updateStatement.setString(2, product);
                                updateStatement.executeUpdate();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("更新商品数量失败: " + e.getMessage());
        }
    }


    private double calculateTotalCost() {//计算总价
        double totalCost = 0.0;
    
        try (Connection connection = DriverManager.getConnection(CART_DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT product, quantity FROM Cart");
             ResultSet resultSet = statement.executeQuery()) {
    
            while (resultSet.next()) {
                String product = resultSet.getString("product");
                int quantity = resultSet.getInt("quantity");
    
                double price = getProductPrice(product); 
                totalCost += price * quantity;
            }
    
        } catch (SQLException e) {
            System.out.println("计算总消费金额失败: " + e.getMessage());
        }
    
        return totalCost;
    }


    public void checkout() {//结账
        double totalCost = calculateTotalCost(); 
        System.out.println("总消费金额：" + totalCost);
        updateGoodsQuantities();
        System.out.println("结账成功！");
    }


    public void viewPurchaseHistory(String username) {
        try (Connection connection = DriverManager.getConnection(CART_DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT C.*, G.price FROM Cart C JOIN Goods G ON C.product = G.name WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
    
            System.out.println("购物历史：");
            double totalCost = 0.0;
    
            while (resultSet.next()) {
                String product = resultSet.getString("product");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                double cost = price * quantity;
                totalCost += cost;
    
                System.out.println("商品：" + product + "，数量：" + quantity + "，单价：" + price + "，花费：" + cost);
            }
    
            System.out.println("总花费：" + totalCost);
        } catch (SQLException e) {
            System.out.println("查看购物历史失败: " + e.getMessage());
        }
    }
} 