package services.orderService;

import models.Cart;
import models.Order;
import models.Product;
import services.productService.ProductService;
import storages.orderStorage.OrderStorage;
import storages.productStorage.ProductStorage;

import java.util.*;

public class OrderService implements IOrderService {
    Scanner scanner = new Scanner(System.in);
    private static final List<Order> orderList = OrderStorage.getInstance().readFile();
    private static final List<Product> productList = ProductStorage.getInstance().readFile();
    private static OrderService instance;

    private OrderService() {}

    public static OrderService getInstance() {
        if(instance == null){
            synchronized (OrderService.class){
                if(instance == null){
                    instance = new OrderService();
                }
            }
        }
        return instance;
    }

    @Override
    public void viewAllOrder() {
        double total = 0;
        for (Order order : orderList) {

            for (Cart cart : order.getBoughtList()) {
                double singleBillTotal = 0;
                singleBillTotal += cart.getTotalAmount();
                System.out.println("____________________________________***___________________________________________________");
                System.out.printf("|%-12s |%-20s |%-8s |%-10s |%-12s |%-15s |\n", "Thời gian", "Tên sản phẩm", "Số lượng", "Đơn giá", "Tổng cộng", "Tài khoản");

                total += cart.getTotalAmount();
                System.out.printf("|%-12s |%-20s |%-8d |%-10.2f |%-12.2f |%-15s |\n", cart.getNow(), cart.getNameCart(), cart.getQuantity(), cart.getUnitPrice(), cart.getTotalAmount(), cart.getUsername());
                System.out.printf("|%-12s |%-20s |%-8s |%-10s |%-12.2s |%-15s |\n", "", "", "", "", "------", "");
                System.out.printf("|%-12s |%-20s |%-8s |%-10s |%-12.2f |%-15s |\n", "", "", "", "Total", singleBillTotal, "");
                System.out.println("____________________________________***___________________________________________________");
            }
        }
        System.out.println("|-------------|---------------------|---------|----------|--------------|----------------|");
        System.out.printf("|%-12s |%-20s |%-8s |%-10s |%-12.2f |%-15s |\n", "", "", "", "Total", total, "");
        System.out.println("|-------------|---------------------|---------|----------|--------------|----------------|");
    }

    @Override
    public void filterByProductName() {
        System.out.println("Tên sản phẩm: ");
        String name = scanner.nextLine();
        boolean isMatch = false;

        double total = 0;
        int quantityTotal = 0;

        System.out.println("_____________________________________________________________________________");
        System.out.printf("|%-12s |%-20s |%-8s |%-10s |%-12s |%-10s |\n", "Thời gian", "Tên sản phẩm", "Số lượng", "Đơn giá", "Tổng cộng", "Tài khoản");
        System.out.println("|-------------|---------------------|---------|-----------|--------------|----------|");

        for (Order order : orderList) {
            for (Cart cart : order.getBoughtList()) {
                if (cart.getNameCart().toLowerCase().trim().equals(name.toLowerCase())) {
                    isMatch = true;
                    total += cart.getTotalAmount();
                    quantityTotal += cart.getQuantity();
                    System.out.printf("|%-12s |%-20s |%-8d |%-10.2f |%-12.2f |%-10s |\n", cart.getNow(), cart.getNameCart(), cart.getQuantity(), cart.getUnitPrice(), cart.getTotalAmount(), cart.getUsername());
                }
            }
        }
        if (isMatch) {
            System.out.println("|-------------|---------------------|--------------|-----------|--------------|----------|");
            System.out.println("|-------------|---------------------|Tổng số lượng|-----------|Tổng cộng|------------------|");
            System.out.printf("|%-12s |%-20s |%-8s |%-10s |%-12.2f |%-10s |\n", "", "", quantityTotal, "", total, "");
            System.out.println("|-------------|---------------------|--------------|-----------|--------------|----------|");
        }
        if (!isMatch) {
            System.err.println("Không tìm thấy tài khoản");
        }
    }

    @Override
    public void filterByProductType() {
        List<String> categoryList = new ArrayList<>();
        Map<String, String> typeMap = new HashMap<>();
        for (Product product : productList) {
            boolean isExist = false;
            String type = product.getNameProduct();
            for (String s : categoryList) {
                if (s.equals(type)) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                categoryList.add(type);
            }
        }
        System.out.println("Bạn muốn tìm loại sản phẩm nào: ");
        for (String t : categoryList) {
            int number = categoryList.indexOf(t) + 1;
            String key = "" + number;
            typeMap.put(key, t);
            System.out.println(key + ". " + typeMap.get(key));
        }
        System.out.println("Lựa chọn chức năng: ");
        String choose = scanner.nextLine();
        System.out.println("Số thứ tự  Tên sản phẩm    Số lượng  Giá   Loại");
        System.out.println("---------------------------------------------------");
        boolean isMatch = false;
        for (Order order : orderList) {
            for (Cart cart : order.getBoughtList())
                if (cart.getCategory().equals(typeMap.get(choose))) {
                    isMatch = true;
                    String formattedString = String.format("%-7d %-16s %-9d %.2f   %s", cart.getIdCart(), cart.getNameCart(), cart.getQuantity(), cart.getUnitPrice(), cart.getCategory());
                    System.out.println(formattedString);
                }
        }
        if (isMatch) {
            System.out.println("---------------------------------------------------");
        } else {
            System.err.println("Không tìm thấy sản phẩm");
        }
    }

    @Override
    public void filterByUserName() {
        System.out.println("Nhập tài khoản: ");
        String name = scanner.nextLine();
        boolean isMatch = false;

        double total = 0;
        int quantityTotal = 0;

        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.printf("| %-12s | %-20s | %-8s | %-10s | %-12s | %-10s |\n", "Thời gian", "Tên sản phẩm", "Số lượng", "Đơn giá", "Tổng cộng", "Tài khoản");
        System.out.println("|--------------|----------------------|----------|------------|--------------|--------------|");

        for (Order order : orderList) {
            for (Cart cart : order.getBoughtList()) {
                if (cart.getUsername().toLowerCase().trim().contains(name.toLowerCase().trim())) {
                    isMatch = true;
                    total += cart.getTotalAmount();
                    quantityTotal += cart.getQuantity();
                    System.out.printf("| %-12s | %-20s | %-8d | %-10.2f | %-12.2f | %-10s |\n", cart.getNow(), cart.getNameCart(), cart.getQuantity(), cart.getUnitPrice(), cart.getTotalAmount(), cart.getUsername());
                }
            }
        }

        if (isMatch) {
            System.out.println("|--------------|----------------------|----------|------------|--------------|--------------|");
            System.out.println("|                                  Tổng số lượng|                         Tổng cộng            |");
            System.out.printf("|%47d |%30.2f |\n", quantityTotal, total);
            System.out.println("|--------------|----------------------|----------|------------|--------------|--------------|");
        }
        if (!isMatch) {
            System.err.println("Product not found");
        }
    }

    @Override
    public void viewAdminWallet() {
            double total = 0;
            for (Order order : orderList) {
                for (Cart cart: order.getBoughtList()) {
                    total += cart.getTotalAmount();
                }
            }
            System.out.println("Số dư trong ví của bạn: " + total);
    }
}
