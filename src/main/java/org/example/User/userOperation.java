package org.example.User;
import java.util.Scanner;

public class userOperation {
    private String username;
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
            System.out.print("请输入用户名(用户名长度不少于5个字符): ");
            String username = scanner.nextLine();

            System.out.print("请输入密码(密码长度大于8个字符，必须是大小写字母、数字和标点符号的组合): ");

            String password = scanner.nextLine();

            boolean success = userManager.registerUser(username, password);

            if (success) {
                break;
            }
        }
    }

    public boolean loginUser() {
        System.out.println("***用户登录***");
        System.out.print("请输入用户名: ");
        String username = scanner.nextLine();

        System.out.print("请输入密码: ");
        String password = scanner.nextLine();

        boolean success = userManager.login(username, password);

        if (success) {
            this.username = username;
            System.out.println("登录成功！");
        } else {
            System.out.println("登录失败，请检查用户名和密码。");
        }

        return success;
    }

    public void changePassword() {
        System.out.println("***修改密码***");
        System.out.print("请输入用户名: ");
        String username = scanner.nextLine();

        System.out.print("请输入原密码: ");
        String oldPassword = scanner.nextLine();

        System.out.print("请输入新密码: ");
        String newPassword = scanner.nextLine();

        boolean success = userManager.changePassword(username, oldPassword, newPassword);

        if (success) {
            System.out.println("密码修改成功！");
        } else {
            System.out.println("密码修改失败，请检查用户名和原密码。");
        }
    }

    public void resetPassword() {
        System.out.println("***重置密码***");
        System.out.print("请输入用户名: ");
        String username = scanner.nextLine();

        System.out.print("请输入新密码: ");
        String newPassword = scanner.nextLine();

        boolean success = userManager.resetPassword(username, newPassword);

        if (success) {
            System.out.println("密码重置成功！");
        } else {
            System.out.println("密码重置失败，请检查用户名。");
        }
    }
}
