package controllers;

import models.Product;
import services.adminService.AdminService;
import services.orderService.OrderService;
import services.productService.ProductService;
import services.userService.UserService;
import services.voucherService.VoucherService;
import views.OrderPage;

import java.util.Scanner;

public class AdminController {
    public static void adminController() throws InterruptedException{
        Scanner sc = new Scanner(System.in);
        ProductService productService =  ProductService.getInstance();
        VoucherService voucherService =  VoucherService.getInstance();
        AdminService adminService =  AdminService.getInstance();
        OrderService orderService = OrderService.getInstance();
        System.out.println("Lựa chọn chức năng: ");
        int choose = Integer.parseInt(sc.nextLine());
        switch (choose) {
            case 1:{
                productService.addProduct();
                adminService.enterBackMenu();
                break;
            }
            case 2:{
                productService.editProduct();
                adminService.enterBackMenu();
                break;
            }
            case 3:{
                productService.deleteProduct();
                adminService.enterBackMenu();
                break;
            }
            case 4:{
                productService.findByName();
                adminService.enterBackMenu();
                break;
            }
            case 5:{
                productService.findByCategory();
                adminService.enterBackMenu();
                break;
            }
            case 6:{
                productService.showAllProducts();
                adminService.enterBackMenu();
                break;
            }
            case 7:{
                voucherService.addVoucher();
                adminService.enterBackMenu();
                break;
            }
            case 8:{
                OrderPage.orderPage();
                adminService.enterBackMenu();
                break;
            }
            case 9:{
                orderService.viewAdminWallet();
                adminService.enterBackMenu();
                break;
            }
            case 0:{
                UserService.currentAccount = null;
                System.out.println("------Good Bye-----");
                HomeController.load();
            }
        }
    }
}
