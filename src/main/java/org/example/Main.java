package org.example;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
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
        while(true) {
			System.out.println("1.登录  2.密码管理  3.客户管理  4.商品管理 （输入exit退出）");
			String userInput = scanner.nextLine();
			switch (userInput) {
			case "1":
            while(true) {//登录
                System.out.println("请输入你的账号");
                System.out.println("请输入你的密码");
                System.out.println("登录成功");
                userInput = scanner.nextLine();
                if(userInput.equals("exit")) {
                    break;
                }
            }
       
            case "2"://密码管理
            System.out.println("选择 ");
            System.out.println("1.修改自身密码  2.重置用户密码 ");
            while(true) {
                if(userInput.equals("1"))
                System.out.println("修改自身密码");
                if(userInput.equals("2"))
                System.out.println("重置用户密码");
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

    private static void user() {
        System.out.println("欢迎进入购物系统");
        Scanner scanner = new Scanner(System.in);
        while(true) {
			System.out.println("1.注册  2.登录  3.密码管理  4.购物  （输入exit退出）");
			String userInput = scanner.nextLine();
			switch (userInput) {
			case "1":
            while(true) {//注册
                System.out.println("请输入你的账号");
                System.out.println("请输入你的密码");
                System.out.println("注册成功");
                userInput = scanner.nextLine();
                if(userInput.equals("exit")) {
                    break;
                }
            }
       
            case "2"://登录
            while(true) {
                System.out.println("请输入你的账号");
                System.out.println("请输入你的密码");
                userInput = scanner.nextLine();
                if(userInput.equals("exit")) {
                    break;
                }
            } 
            
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
      }
      
        scanner.close();
        System.out.println("Done."); 
     }
}

}