package views;

import controllers.HomeController;

import java.util.Scanner;

public class HomePage {
    Scanner scanner = new Scanner(System.in);
    public static void homeView() throws InterruptedException {
        System.out.println("_____K SHOP_____");
        System.out.println("1. Đăng nhập");
        System.out.println("2. Đăng ký");
        System.out.println("0. Thoát chương trình");
        System.out.println("Chức năng bạn chọn là: ");
        HomeController.load();
    }
}
