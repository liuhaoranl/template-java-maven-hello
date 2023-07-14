package org.example.User;

import java.util.Scanner;

public class userOperation {
    private String username; // Change field name to lowercase "u"
    private Scanner scanner;
    private MyUserManager userManager;

    public userOperation(Scanner scanner, MyUserManager userManager) {
        this.scanner = scanner;
        this.userManager = userManager;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void registerUser() {
        System.out.println("***用户注册***");
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

    public boolean loginUser() {
        System.out.println("***用户登录***");
        System.out.print("请输入用户名:");
        String username = this.scanner.nextLine();

        System.out.print("请输入密码:");
        String password = this.scanner.nextLine();

        boolean success = this.userManager.login(username, password);

        if (success) {
            this.username = username; // Save the logged-in username to the instance variable
            System.out.println("登录成功！");
        } else {
            System.out.println("登录失败，请检查用户名和密码。");
        }

        return success; // Return the success flag
    }

    public void changePassword() {
        System.out.println("***修改密码***");
        System.out.print("请输入用户名:");
        String username = this.scanner.nextLine();

        System.out.print("请输入原密码:");
        String oldPassword = this.scanner.nextLine();

        System.out.print("请输入新密码:");
        String newPassword = this.scanner.nextLine();

        boolean success = this.userManager.changePassword(username, oldPassword, newPassword);

        if (success) {
            System.out.println("密码修改成功！");
        } else {
            System.out.println("密码修改失败，请检查用户名和原密码。");
        }
    }

    public void resetPassword() {
        System.out.println("***重置密码***");
        System.out.print("请输入用户名:");
        String username = this.scanner.nextLine();

        System.out.print("请输入新密码:");
        String newPassword = this.scanner.nextLine();

        boolean success = this.userManager.resetPassword(username, newPassword);

        if (success) {
            System.out.println("密码重置成功！");
        } else {
            System.out.println("密码重置失败，请检查用户名。");
        }
    }
}
