package controllers;

import models.Order;
import services.cartService.CartService;
import services.customerService.CustomerService;
import services.productService.ProductService;
import services.userService.UserService;
import views.CustomerPage;
import views.NewPage;
import views.SortProductListMenu;

import java.util.Scanner;

public class CustomerController {
    public static void customerController() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        ProductService productService = ProductService.getInstance();
        CustomerService customerService = CustomerService.getInstance();
        CartService cartService = CartService.getInstance();
        UserService userService = UserService.getInstance();
        boolean isExcept = false;
        do{
            System.out.println("Lựa chọn chức năng: ");
            String choose = scanner.nextLine();
            switch (choose) {
                case "1":{
                    productService.showAllProducts();
                    SortProductListMenu.sortView();
                    break;
                }
                case "2":{
                    isExcept = true;
                    productService.findByName();
                    customerService.enterBackMenu();
                    break;
                }
                case "3":{
                    isExcept = true;
                    productService.findByCategory();
                    customerService.enterBackMenu();
                    break;
                }
                case "4":{
                    cartService.addToCart();
                    customerService.enterBackMenu();
                    break;
                }
                case "5":{
                    cartService.viewMyCart();
                    customerService.enterBackMenu();
                    break;
                }
                case "6":{
                    customerService.viewMyCartListOrder(new Order());
                    customerService.enterBackMenu();
                    break;
                }
                case "7":{
                    userService.changePassword();
                    customerService.enterBackMenu();
                    break;
                }
                case "8":{
                    customerService.addFund();
                    customerService.enterBackMenu();
                    break;
                }
                case "9":{
                    customerService.buyProduct();
                    customerService.enterBackMenu();
                    break;
                }
                case "10":{
                    customerService.viewMyWallet();
                    customerService.enterBackMenu();
                    break;
                }
                case "0":{
                    System.out.println("------Good Bye-----");
                    UserService.currentAccount = null;
                    NewPage.displayNewPage();
                    HomeController.load();
                    break;
                }default:
                    System.err.println("Mời bạn chọn lại chức năng");
            }
        }while(!isExcept);
    }
}
