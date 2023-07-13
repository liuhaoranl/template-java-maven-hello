package org.example.ShoppingCart;

import java.util.Scanner;

public class cartOperation {
    private Scanner scanner;
    private cartManager cartManager;

    public cartOperation(Scanner scanner, cartManager cartManager) {
        this.scanner = scanner;
        this.cartManager = cartManager;
    }

    public void displayGoodsInformation() {
        System.out.println("***显示商品信息***");
        cartManager.displayGoodsInformation();
    }

    public void addToCart() {
        System.out.println("***将商品加入购物车***");
        System.out.print("请输入商品名称：");
        String product = scanner.nextLine();

        System.out.print("请输入商品数量：");
        int quantity = Integer.parseInt(scanner.nextLine());

        boolean success = cartManager.addToCart(product, quantity);

        if (success) {
            System.out.println("商品已成功加入购物车！");
        } else {
            System.out.println("添加商品到购物车失败。");
        }
    }

    public void removeFromCart() {
        System.out.println("***从购物车中移除商品***");
        System.out.print("请输入商品名称：");
        String product = scanner.nextLine();

        boolean success = cartManager.removeFromCart(product);

        if (success) {
            System.out.println("商品已成功从购物车中移除！");
        } else {
            System.out.println("从购物车中移除商品失败。");
        }
    }

    public void updateQuantity() {
        System.out.println("***修改购物车中的商品数量***");
        System.out.print("请输入商品名称：");
        String product = scanner.nextLine();

        System.out.print("请输入新的商品数量：");
        int newQuantity = Integer.parseInt(scanner.nextLine());

        boolean success = cartManager.updateQuantity(product, newQuantity);

        if (success) {
            System.out.println("商品数量已成功更新！");
        } else {
            System.out.println("更新购物车中的商品数量失败。");
        }
    }

    public void checkout() {
        System.out.println("***结账***");
        cartManager.checkout();
    }

    public void viewPurchaseHistory(String username) {
        System.out.println("***查看购物历史***");
        cartManager.viewPurchaseHistory(username);
    }
}
