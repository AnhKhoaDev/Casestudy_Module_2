package controllers;

import services.adminService.AdminService;
import services.orderService.OrderService;
import views.AdminPage;
import views.NewPage;

import java.util.Scanner;

public class OrderController {
    public static void orderController() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        OrderService orderService = OrderService.getInstance();
        AdminService adminService = AdminService.getInstance();
        boolean isExpect = false;
        do {
            System.out.println("Lựa chọn chức năng: ");
            String choose = scanner.nextLine();
            switch (choose) {
                case "1": {
                    isExpect = true;
                    orderService.viewAllOrder();
                    adminService.enterBackMenu();
                    break;
                }
                case "2": {
                    isExpect = true;
                    orderService.filterByProductName();
                    adminService.enterBackMenu();
                    break;
                }
                case "3": {
                    isExpect = true;
                    orderService.filterByProductType();
                    adminService.enterBackMenu();
                    break;
                }
                case "4": {
                    isExpect = true;
                    orderService.filterByUserName();
                    adminService.enterBackMenu();
                    break;
                }
                case "0": {
                    NewPage.displayNewPage();
                    AdminPage.viewAdminPage();
                    break;
                }
                default:
                    System.out.println("Mời bạn chọn lại chức năng");
            }
        }while (!isExpect);
    }
}
