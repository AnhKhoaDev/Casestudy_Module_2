package services.cartService;

import models.Cart;
import models.Product;
import services.integerCheckService.IntegerCheckService;
import services.productService.ProductService;
import services.userService.UserService;
import storages.cartStorage.CartStorage;
import storages.productStorage.ProductStorage;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CartService implements ICartService {
    Scanner scanner = new Scanner(System.in);
    private static final List<Cart> cartList = CartStorage.getInstance().readFile();
    private static final List<Product> productList = ProductStorage.getInstance().readFile();
    private static CartService instance;

    private CartService() {}

    public static CartService getInstance() {
        if(instance == null){
            synchronized (CartService.class){
                if(instance == null){
                    instance = new CartService();
                }
            }
        }
        return instance;
    }



    @Override
    public void addToCart() throws InterruptedException {
        ProductService productService = ProductService.getInstance();
        productService.showAllProducts();
        System.out.println("Enter id of product");
        String id = scanner.nextLine();
        boolean isExist = false;
        if (IntegerCheckService.getInstance().integerCheck(id)) {
            for (Product product : productList) {
                if (product.getIdProduct() == Integer.parseInt(id)) {
                    isExist = true;
                    boolean quantityCheck = false;
                    do {
                        boolean isOverZero = false;
                        do {
                            System.out.println("Nhập số lượng muốn thêm: ");
                            String quantity = scanner.nextLine();
                            if (IntegerCheckService.getInstance().integerCheck(quantity)) {
                                if (Integer.parseInt(quantity) > 0) {
                                    isOverZero = true;
                                    if (Integer.parseInt(quantity) <= product.getQuantity()) {
                                        quantityCheck = true;
                                        Cart cartProduct = new Cart(Integer.parseInt(id), product.getNameProduct(), Integer.parseInt(quantity), product.getPrice(), product.getCategory(), UserService.currentAccount);
                                        cartList.add(cartProduct);
                                        CartStorage.getInstance().writeFile(cartList);

                                        System.out.println("Sản phẩm đã được thêm vào giỏ hàng");
                                        CartService.getInstance().viewMyCart();
                                        break;
                                    } else {
                                        System.out.println("Số lượng không thể lớn hơn cửa hàng");
                                    }
                                } else {
                                    System.err.println("Số lượng phải lớn hơn 0, hãy nhập lại");
                                }
                            }
                        } while (!isOverZero);
                    } while (!quantityCheck);
                }
            }
        }
        if (!isExist) {
            System.err.println("Sản phẩm không được tìm thấy");
            addToCart();
        }
    }

    @Override
    public double getTotal() {
        double total = 0;
        for (Cart cart : cartList) {
            if (cart.getUsername().equalsIgnoreCase(UserService.currentAccount)) {
                total += cart.getTotalAmount();
            }
        }
        return total;
    }

    @Override
    public List<Cart> getMyCart() {
        List<Cart> myCartList = new ArrayList<>();
        String now = LocalTime.now().toString();
        for (Cart c : cartList) {
            if (c.getUsername().equals(UserService.currentAccount)) {
                Cart cart = new Cart(now, c.getIdCart(), c.getNameCart(), c.getQuantity(), c.getUnitPrice(), c.getCategory(), c.getUsername());
                myCartList.add(cart);
            }
        }
        return myCartList;
    }

    @Override
    public void viewMyCart() {
        double total = 0;
        int count = 1;
        System.out.println("Số thứ tư  Tên sản phẩm    Loại    Số lượng Đơn giá  Tổng cộng");
        System.out.println("-----------------------------------------------------------------");
        for (Cart cart : cartList) {
            if (cart.getUsername().equalsIgnoreCase(UserService.currentAccount)) {
                String formattedString = String.format("%-7d %-16s %-7s %-9d %.2f %.2f", count++, cart.getNameCart(), cart.getCategory(), cart.getQuantity(), cart.getUnitPrice(), cart.getTotalAmount());
                System.out.println(formattedString);
                total += cart.getTotalAmount();
            }
        }
        System.out.printf("Total:%57.2f", total);
        System.out.println(" ");
    }

    public static void main(String[] args) {
        CartService.getInstance().viewMyCart();
    }
}
