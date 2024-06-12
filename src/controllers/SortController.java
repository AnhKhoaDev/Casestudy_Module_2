package controllers;

import services.customerService.CustomerService;
import services.productService.ProductService;
import views.CustomerPage;

import java.util.Scanner;

public class SortController {
    public static void sortController() throws InterruptedException{
        Scanner scanner = new Scanner(System.in);
        ProductService productService = ProductService.getInstance();
        CustomerService customerService = CustomerService.getInstance();
        System.out.println("Lựa chọn chức năng: ");
        String choose = scanner.nextLine();
        switch (choose) {
            case "1":{
                productService.sortPriceLowToHigh();
                customerService.enterBackMenu();
                break;
            }
            case "2":{
                productService.sortPriceHighToLow();
                customerService.enterBackMenu();
                break;
            }
            case "3":{
                productService.findByCategory();
                customerService.enterBackMenu();
                break;
            }
            case "4":{
                CustomerPage.viewCustomer();
                break;
            }
        }
    }
}
