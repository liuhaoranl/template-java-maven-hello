package org.example;
import java.util.Scanner;
import org.example.User.userOperation; 
import org.example.User.MyUserManager;
import org.example.Administrator.administratorOperation; 
//import org.example.Administrator.MyAdministratorManager;

public class Main{
    
    public static void main(String[] args) {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer();
        databaseInitializer.initializeDatabase();
        Scanner scanner = new Scanner(System.in); 
		System.out.println("欢迎你的到来");
        
		boolean flage = true;
		while(flage) {
			System.out.println("请选择你的身份");
        System.out.println("1.管理员  2.用户  （输入exit退出）");
			String userInput = scanner.nextLine();
			switch (userInput) {
			case "1":
            while(true) {//管理员
                Main.administrator();
                if(userInput.equals("exit")) {
                    break;
                }
    
            }
       
            case "2"://用户
            while(true) {
                Main.user();
                userInput = scanner.nextLine();
                if(userInput.equals("exit")) {
                    break;
                }
            }   
      }
        scanner.close();
        System.out.println("Done."); 
     }
   }

    private static void shoppingMenu() {
        System.out.println("欢迎进入购物系统主页");

        try (Scanner scanner = new Scanner(System.in)) {
            String userInput = "";

            while(true) {
                System.out.println("输入exit 退出");
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
        MyUserManager userManager = new MyUserManager();
        userOperation userOperation = new userOperation(scanner, userManager);
        administratorOperation administratorOperation = new administratorOperation();
        while(true) {
			System.out.println("1.注册  2.登录  3.修改管理员密码  4.重置用户密码 5.列出用户信息 6.删除用户 7.查询用户 （输入exit退出）");
			String userInput = scanner.nextLine();
            String name = scanner.nextLine();
            String newPassword = scanner.nextLine();
            switch (userInput) {
                case "1": // 注册
                    userOperation.registerUser();
                    break;
                case "2": // 登录
                    userOperation.loginUser();
                    break;
                
                    case "3": // 修改管理员密码
                    System.out.println("输入用户名");
                    name = scanner.nextLine();
                    System.out.println("输入新密码");
                    newPassword = scanner.nextLine();
                    administratorOperation.changeAdminPassword(name,newPassword);
                    break;
    
                    case "4": // 重置用户密码
                    System.out.println("输入用户名");
                    name = scanner.nextLine();
                    System.out.println("输入新密码");
                    newPassword = scanner.nextLine();
                    administratorOperation.resetUserPassword(name,newPassword);
                    break;
             
                    case "5": // 列出用户信息
                    administratorOperation.listAllUsers();
                    break;

                    case "6": // 删除用户
                    System.out.println("输入用户名");
                    userInput = scanner.nextLine();
                    administratorOperation.deleteUser(userInput);
                    break;

                    case "7": // 查询用户
                    System.out.println("输入用户名");
                    userInput = scanner.nextLine();
                    administratorOperation.getUserInfo(userInput);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("无效的选项，请重新输入。");
                    break;
          }
        scanner.close();
        System.out.println("Done."); 
     }
    }


    private static void user() {
        System.out.println("欢迎进入购物系统");
        Scanner scanner = new Scanner(System.in);
        MyUserManager userManager = new MyUserManager();
        userOperation userOperation = new userOperation(scanner, userManager);
    
            System.out.println("1.注册  2.登录  3.密码管理  4.购物  （输入exit退出）");
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "1": // 注册
                    userOperation.registerUser();
                    break;
                case "2": // 登录
                    userOperation.loginUser();
                    break;
                
                case "3":
                while(true) {//密码管理
                    userInput = scanner.nextLine();
                    if(userInput.equals("exit")) {
                        break;
                    }
                }
    
                case "4":
                while(true) { //购物
                    Main.shoppingMenu();
                    userInput = scanner.nextLine();
                    if(userInput.equals("exit")) {
                        break;
                    }
                }
             
                case "exit":
                    return;
                default:
                    System.out.println("无效的选项，请重新输入。");
                    break;
          }
    }
    
}