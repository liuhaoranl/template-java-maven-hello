package org.example.User;

import java.util.Scanner;

public class userOperation {
    private Scanner scanner;
    private MyUserManager userManager;

    public userOperation(Scanner scanner, MyUserManager userManager) {
        this.scanner = scanner;
        this.userManager = userManager;
    }

    public void registerUser() {
        System.out.println("现在你在用户注册子菜单里.");
        while (true) {
            System.out.print("请输入用户名:");
            String username = this.scanner.nextLine();

            System.out.print("请输入密码:");
            String password = this.scanner.nextLine();

            boolean success = this.userManager.registerUser(username, password);

            if (success) {
                break;
            }
        }
    }

    public void loginUser() {
        System.out.println("现在你在用户登录子菜单里.");
        System.out.print("请输入用户名:");
        String username = this.scanner.nextLine();

        System.out.print("请输入密码:");
        String password = this.scanner.nextLine();

        boolean success = this.userManager.login(username, password);

        if (success) {
            // 登录成功后的处理逻辑
            // 可以在这里执行其他操作，比如显示用户信息或者跳转到其他功能
            System.out.println("登录成功！");
        } else {
            // 登录失败后的处理逻辑
            // 可以在这里进行错误处理，比如显示错误信息或者重新尝试登录
            System.out.println("登录失败，请检查用户名和密码。");
        }
    }
}
