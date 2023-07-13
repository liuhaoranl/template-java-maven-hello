package org.example.Goods;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class goodsOperation {
    private static final String GOODS_DB_URL = "jdbc:sqlite:goods.db";
    private Scanner scanner;

    public goodsOperation(Scanner scanner) {
        this.scanner = scanner;
    }

    public void addGoodsInformation() {
        System.out.println("现在你在添加商品信息子菜单里.");
        System.out.print("请输入商品名称: ");
        String name = this.scanner.nextLine();

        System.out.print("请输入商品类别: ");
        String category = this.scanner.nextLine();

        System.out.print("请输入商品数量: ");
        int quantity = Integer.parseInt(this.scanner.nextLine());

        System.out.print("请输入商品成本: ");
        double cost = Double.parseDouble(this.scanner.nextLine());

        System.out.print("请输入商品售价: ");
        double price = Double.parseDouble(this.scanner.nextLine());

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

    public void modifyGoodsInformation() {
        System.out.println("现在你在修改商品信息子菜单里.");
        System.out.print("请输入要修改的商品名称: ");
        String name = this.scanner.nextLine();

        System.out.println("请选择要修改的内容:");
        System.out.println("1. 商品名称");
        System.out.println("2. 商品类别");
        System.out.println("3. 商品成本");
        System.out.println("4. 商品数量");
        System.out.println("5. 商品售价");
        System.out.print("请选择选项: ");
        String option = this.scanner.nextLine();

        System.out.print("请输入新的值: ");
        String newValue = this.scanner.nextLine();

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
                     "UPDATE Goods SET " + columnName + " = ? WHERE name = ?")) {
            statement.setString(1, newValue);
            statement.setString(2, name);
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

    public void deleteGoodsInformation() {
        System.out.println("现在你在删除商品信息子菜单里.");
        System.out.print("请输入要删除的商品关键字: ");
        String keyword = this.scanner.nextLine();

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

    public void queryGoodsInformation() {
        System.out.println("现在你在查询商品信息子菜单里.");
        System.out.print("请输入要查询的商品关键字: ");
        String keyword = this.scanner.nextLine();

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
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            System.out.println("商品信息查询失败: " + e.getMessage());
        }
    }

    public void listAllGoodsInformation() {
        System.out.println("现在你在列出所有商品信息子菜单里.");

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
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            System.out.println("商品信息查询失败: " + e.getMessage());
        }
    }
}
