
package org.example.Goods;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class goodsManager {
    private static final String GOODS_DB_URL = "jdbc:sqlite:goods.db";

    public void addGoods(int id, String name, String manufacturer, String productionDate, String model, double cost, double price, int quantity) {
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

    public void updateGoods(int id, String option, String newValue) {
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


    public void deleteGoods(int id) {
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

    public void queryGoods(int id) {
        try (Connection connection = DriverManager.getConnection(GOODS_DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Goods WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
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
                System.out.println("数量: " + quantity);
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
            System.out.println("数量: " + quantity);
            System.out.println("-------------------------");
        }
    } catch (SQLException e) {
        System.out.println("列出所有商品信息失败: " + e.getMessage());
    }
}

}