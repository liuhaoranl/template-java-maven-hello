package org.example;

import java.util.Scanner;
import org.example.User.userOperation;
import org.example.User.MyUserManager;
import org.example.Administrator.administratorOperation;
import org.example.Goods.goodsOperation;
public class Main {
    public static void main(String[] args) {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer();
        databaseInitializer.initUserDatabase();
        databaseInitializer.initAdminDatabase();
        databaseInitializer.initGoodsDatabase();
        Scanner scanner = new Scanner(System.in);
        System.out.println("欢迎你的到来");

        boolean flag = true;
        while (flag) {
            System.out.println("请选择你的身份");
            System.out.println("1.管理员  2.用户  3.返回");

            String userInput = scanner.nextLine();
            switch (userInput) {
                case "1":
                  // 管理员
                    Main.administrator();
                    break;
    
                case "2": // 用户
                    Main.user();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("无效的选项，请重新输入。");
                    break;
            }
        }
    }


    private static void shoppingMenu() {
        System.out.println("欢迎进入购物系统主页");

        try (Scanner scanner = new Scanner(System.in)) {
            String userInput = "";

            while (true) {
                System.out.println("输入exit退出");
                System.out.print("菜单");
                userInput = scanner.nextLine();
                if (userInput.equals("exit")) {
                    break;
                }
                System.out.println("");
            }
        }
    }
    private static void administrator() {
        System.out.println("欢迎进入购物系统");
        Scanner scanner = new Scanner(System.in);
        administratorOperation administratorOperation = new administratorOperation();
    
        while (true) {
            System.out.println("1.注册  2.登录  3.密码管理  4.客户管理  5.商品管理  6.返回");
            String userInput = scanner.nextLine();
    
            switch (userInput) {
                case "1": // 注册
                    administratorOperation.initializeAdminAccount();
                    break;
                case "2": // 登录
                    loginAdmin(scanner, administratorOperation);
                    break;
                case "3": // 密码管理
                    managePassword(scanner, administratorOperation);
                    break;
                case "4": // 客户管理
                    manageUser(scanner, administratorOperation);
                    break;
                case "5": // 商品管理
                    manageGoods(scanner);
                    break;
                case "6":
                    return;
                default:
                    System.out.println("无效的选项，请重新输入。");
                    break;
            }
        }
    }
    
    private static void loginAdmin(Scanner scanner, administratorOperation administratorOperation) {
        System.out.println("输入用户名");
        String name = scanner.nextLine();
        System.out.println("输入密码");
        String password = scanner.nextLine();
        administratorOperation.loginAdmin(name, password);
    }
    
    private static void managePassword(Scanner scanner, administratorOperation administratorOperation) {
        System.out.println("输入用户名");
        String name = scanner.nextLine();
        System.out.println("输入新密码");
        String newPassword = scanner.nextLine();
        administratorOperation.changeAdminPassword(name, newPassword);
    }
    
    private static void manageUser(Scanner scanner, administratorOperation administratorOperation) {
        System.out.println("1.列出用户信息  2.重置用户密码  3.删除用户");
        String userInput = scanner.nextLine();
    
        switch (userInput) {
            case "1":
                administratorOperation.displayAllUsers();
                break;
            case "2":
                resetUserPassword(scanner, administratorOperation);
                break;
            case "3":
                System.out.println("输入用户名");
                userInput = scanner.nextLine();
                administratorOperation.deleteUser(userInput);
                break;
            default:
                System.out.println("无效的选项，请重新输入。");
                break;
        }
    }
    
    private static void resetUserPassword(Scanner scanner, administratorOperation administratorOperation) {
        System.out.println("输入用户名");
        String name = scanner.nextLine();
        System.out.println("输入新密码");
        String newPassword = scanner.nextLine();
        administratorOperation.resetUserPassword(name, newPassword);
    }
    
    private static void manageGoods(Scanner scanner) {
        goodsOperation goodsOperation = new goodsOperation(scanner);
    
        while (true) {
            System.out.println("1.添加商品信息  2.修改商品信息  3.删除商品信息  4.查询商品信息  5.列出所有商品信息  6.返回");
            String userInput = scanner.nextLine();
    
            switch (userInput) {
                case "1":
                    goodsOperation.addGoodsInformation();
                    break;
                case "2":
                    goodsOperation.modifyGoodsInformation();
                    break;
                case "3":
                    goodsOperation.deleteGoodsInformation();
                    break;
                case "4":
                    goodsOperation.queryGoodsInformation();
                    break;
                case "5":
                    goodsOperation.listAllGoodsInformation();
                    break;
                case "6":
                    return;
                default:
                    System.out.println("无效的选项，请重新输入。");
                    break;
            }
        }
    }

    


    private static void user() {
        System.out.println("欢迎进入购物系统");
        Scanner scanner = new Scanner(System.in);
        MyUserManager userManager = new MyUserManager();
        userOperation userOperation = new userOperation(scanner, userManager);

        System.out.println("1.注册  2.登录  3.密码管理  4.购物  5.返回");
        String userInput = scanner.nextLine();

        switch (userInput) {
            case "1": // 注册
                userOperation.registerUser();
                break;
            case "2": // 登录
                userOperation.loginUser();
                break;
            case "3": // 密码管理
                userInput = scanner.nextLine();
                break;
            case "4": // 购物
                Main.shoppingMenu();
                userInput = scanner.nextLine();
                break;
            case "5":
                return;
            default:
                System.out.println("无效的选项，请重新输入。");
                break;
        }
    }


   
}
