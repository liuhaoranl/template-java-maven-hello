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
        System.out.print("请输入商品编号: ");
        int id = Integer.parseInt(this.scanner.nextLine());

        System.out.print("请输入商品名称: ");
        String name = this.scanner.nextLine();

        System.out.print("请输入生产厂家: ");
        String manufacturer = this.scanner.nextLine();

        System.out.print("请输入生产日期: ");
        String productionDate = this.scanner.nextLine();

        System.out.print("请输入型号: ");
        String model = this.scanner.nextLine();

        System.out.print("请输入进货价: ");
        double cost = Double.parseDouble(this.scanner.nextLine());

        System.out.print("请输入零售价格: ");
        double price = Double.parseDouble(this.scanner.nextLine());

        System.out.print("请输入商品数量: ");
        int quantity = Integer.parseInt(this.scanner.nextLine());

        try (Connection connection = DriverManager.getConnection(GOODS_DB_URL);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Goods (id, name, manufacturer, production_date, model, cost, price, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, manufacturer);
            statement.setString(4, productionDate);
            statement.setString(5, model);
            statement.setDouble(6, cost);
            statement.setDouble(7, price);
            statement.setInt(8, quantity);
            statement.executeUpdate();
            System.out.println("商品信息添加成功");
        } catch (SQLException e) {
            System.out.println("商品信息添加失败: " + e.getMessage());
        }
    }

    public void modifyGoodsInformation() {
        System.out.println("现在你在修改商品信息子菜单里.");
        System.out.print("请输入要修改的商品编号: ");
        int id = Integer.parseInt(this.scanner.nextLine());

        System.out.println("请选择要修改的内容:");
        System.out.println("1. 商品名称");
        System.out.println("2. 生产厂家");
        System.out.println("3. 生产日期");
        System.out.println("4. 型号");
        System.out.println("5. 进货价");
        System.out.println("6. 零售价格");
        System.out.println("7. 商品数量");
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
                columnName = "manufacturer";
                break;
            case "3":
                columnName = "production_date";
                break;
            case "4":
                columnName = "model";
                break;
            case "5":
                columnName = "cost";
                break;
            case "6":
                columnName = "price";
                break;
            case "7":
                columnName = "quantity";
                break;
            default:
                System.out.println("无效的选项");
                return;
        }

        try (Connection connection = DriverManager.getConnection(GOODS_DB_URL);
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Goods SET " + columnName + " = ? WHERE id = ?")) {
            if (columnName.equals("quantity") || columnName.equals("id")) {
                statement.setInt(1, Integer.parseInt(newValue));
            } else {
                statement.setString(1, newValue);
            }
            statement.setInt(2, id);
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
        System.out.print("请输入要删除的商品编号: ");
        int id = Integer.parseInt(this.scanner.nextLine());

        try (Connection connection = DriverManager.getConnection(GOODS_DB_URL);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Goods WHERE id = ?")) {
            statement.setInt(1, id);
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
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Goods WHERE name LIKE ? OR manufacturer LIKE ?")) {
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String manufacturer = resultSet.getString("manufacturer");
                String productionDate = resultSet.getString("production_date");
                String model = resultSet.getString("model");
                double cost = resultSet.getDouble("cost");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");

                System.out.println("商品编号: " + id);
                System.out.println("商品名称: " + name);
                System.out.println("生产厂家: " + manufacturer);
                System.out.println("生产日期: " + productionDate);
                System.out.println("型号: " + model);
                System.out.println("进货价: " + cost);
                System.out.println("零售价格: " + price);
                System.out.println("商品数量: " + quantity);
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            System.out.println("商品信息查询失败: " + e.getMessage());
        }
    }

    public void listAllGoodsInformation() {
        System.out.println("现在你在列出所有商品信息子菜单里.");

        try (Connection connection = DriverManager.getConnection(GOODS_DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Goods");
             ResultSet resultSet = statement.executeQuery()) {
        
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String manufacturer = resultSet.getString("manufacturer");
                String productionDate = resultSet.getString("production_date");
                String model = resultSet.getString("model");
                double cost = resultSet.getDouble("cost");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");

                System.out.println("商品编号: " + id);
                System.out.println("商品名称: " + name);
                System.out.println("生产厂家: " + manufacturer);
                System.out.println("生产日期: " + productionDate);
                System.out.println("型号: " + model);
                System.out.println("进货价: " + cost);
                System.out.println("零售价格: " + price);
                System.out.println("商品数量: " + quantity);
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            System.out.println("商品信息查询失败: " + e.getMessage());
        }
    }
  
}