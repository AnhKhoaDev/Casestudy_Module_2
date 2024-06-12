package services.customerService;

import models.Cart;
import models.Order;
import models.User;
import models.Voucher;
import services.cartService.CartService;
import services.productService.ProductService;
import services.userService.UserService;
import services.voucherService.VoucherService;
import storages.cartStorage.CartStorage;
import storages.orderStorage.OrderStorage;
import storages.userStorage.UserStorage;
import storages.voucherStorage.VoucherStorage;
import views.CustomerPage;
import views.NewPage;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class CustomerService implements ICustomerService {
    Scanner scanner = new Scanner(System.in);
    private static final List<Voucher> voucherList = VoucherStorage.getInstance().readFile();
    private static final List<User> userList = UserStorage.getInstance().readFile();
    private static final List<Order> orderList = OrderStorage.getInstance().readFile();
    private static CustomerService instance;

    private CustomerService() {}

    public static CustomerService getInstance() {
        if(instance == null){
            synchronized (CustomerService.class){
                if(instance == null){
                    instance = new CustomerService();
                }
            }
        }
        return instance;
    }

    @Override
    public void addFund() {
        UserService.getInstance();
        String currentAccount = UserService.currentAccount;
        boolean isMatching = false;
        do {
            System.out.println("Nhập mã giảm giá: ");
            String idVoucher = scanner.nextLine();

            for (Voucher voucher : voucherList) {
                if (voucher.equals(voucher.getIdVoucher()) && voucher.isAvailable()) {
                    for (User user : userList) {
                        if (currentAccount.equals(user.getUserName())){
                            user.setWallet(user.getWallet() + voucher.getCost());
                            System.out.println("Ví của bạn đã được nạp thêm: " + voucher.getCost() + "\n"
                                + "Số dư hiện tại trong ví của bạn là: " + user.getWallet());
                            VoucherService.getInstance().disableVoucher(idVoucher);
                            break;
                        }
                    }
                    isMatching = true;
                    break;
                }
            }
            if (!isMatching) {
                System.out.println("Mã voucher không có sẵn hoặc không tồn tại");
                isMatching = false;
            }
            UserStorage.getInstance().writeFile(userList);
        }while (!isMatching);
    }

    @Override
    public void buyProduct() throws InterruptedException {
        User currentAccount = null;
        for (User user : userList) {
            if (user.getUserName().equals(UserService.currentAccount)) {
                currentAccount = user;
            }
        }
        boolean isExist = false;
        if (CartService.getInstance().getMyCart().size() > 0) {
            isExist = true;
        }
        if (isExist) {
            String choice;
            System.out.println("-----Giỏ hàng-----");
            CartService.getInstance().viewMyCart();
            System.out.println("Bạn có muốn mua tất cả sản phẩm trong giỏ hàng ?");
            System.out.println("1.Yes");
            System.out.println("2. No");
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    if (CartService.getInstance().getTotal() < currentAccount.getWallet()) {
                        String now = LocalTime.now().toString();
                        String currentName = currentAccount.getUserName();
                        List<Cart> myCartList = CartService.getInstance().getMyCart();

                        Order order = new Order(now, currentName, myCartList);
                        orderList.add(order);
                        OrderStorage.getInstance().writeFile(orderList);

                        ProductService.getInstance().editWhenBuy(myCartList);
                        System.out.println("Đặt hàng thành công, cảm ơn quý khách!");
                        CustomerService.getInstance().viewMyCartListOrder(order);

                        currentAccount.setWallet(currentAccount.getWallet() - CartService.getInstance().getTotal());
                        UserStorage.getInstance().writeFile(userList);

                        myCartList.clear();
                        CartStorage.getInstance().writeFile(myCartList);

                    } else {
                        System.err.println("Số dư trong ví của quý khách không đủ. Vui lòng nạp thêm tiền vào ví");
                    }
            }
        } else {
            System.out.println("Không có sản phẩm nào trong giỏ hàng.");
        }
    }

    @Override
    public void viewMyWallet() {
        User currentAccount = null;
        for (User user : userList) {
            if (user.getUserName().equals(UserService.currentAccount)) {
                currentAccount = user;
            }
        }
        assert currentAccount != null;
        System.out.println("Số dư trong ví của bạn hiện là:  " + currentAccount.getWallet() + " VNĐ.");
    }

    @Override
    public void viewMyHistory() {
        int count = 1;
        double total = 0;

        System.out.println("___________________________________________________________________");
        System.out.printf("|%-12s |%-8s |%-20s |%-10s |%-10s |%-12s |\n", "Thời gian", "Số thứ tự", "Tên sản phẩm", "Số lượng", "Đơn giá", "Tổng cộng");
        System.out.println("|-------------|---------|---------------------|-----------|----------|-------------|");

        for (Order order : orderList) {
            List<Cart> boughtList = order.getBoughtList();
            double billTotal = 0; // Tổng giá trị của từng hóa đơn
            for (Cart cart : boughtList) {
                if (cart.getUsername().equals(UserService.currentAccount)) {
                    double itemTotal = cart.getQuantity() * cart.getUnitPrice();
                    System.out.printf("|%-12s |%-8d |%-20s |%-10d |%-10.2f |%-12.2f |\n", order.getNow(), count++, cart.getNameCart(), cart.getQuantity(), cart.getUnitPrice(), itemTotal);
                    total += itemTotal;
                    billTotal += itemTotal;
                }
            }
        }
        System.out.println("|-------------|---------|---------------------|-----------|----------|-------------|");
        System.out.printf("|%-12s |%-8s |%-20s |%-10s |%-10s |%.2f |\n", "", "Tổng cổng", "", "", "", total);
        System.out.println("|___________________________________________________________________________|");
    }

    @Override
    public void viewMyCartListOrder(Order order) {
        int count = 1;
        double total = 0;
        System.out.println("___________________________________________________________________");
        System.out.printf("|%-12s |%-8s |%-20s |%-10s |%-10s |%-10s |\n", "Thời gian", "Số thứ tự", "Tên sản phẩm", "Số lượng", "Đơn giá", "Tổng cộng");
        for (Cart cart : CartService.getInstance().getMyCart()) {
            System.out.println("|-------------|---------|---------------------|-----------|----------|----------|");
            double itemTotal = cart.getQuantity() * cart.getUnitPrice();
            System.out.printf("|%-12s |%-8d |%-20s |%-10d |%-10.2f |%-10.2f |\n", order.getNow(), count++, cart.getNameCart(), cart.getQuantity(), cart.getUnitPrice(), itemTotal);
            total += itemTotal;
        }

        System.out.println("|-------------|---------|---------------------|-----------|----------|----------|");
        System.out.printf("|%-12s |%-8s |%-20s |%-10s |%-10s |%.2f |\n", "", "Tổng cộng", "", "", "", total);
        System.out.println("|__________________________________________________________________|");

    }

    @Override
    public void enterBackMenu() throws InterruptedException {
        boolean isExcept = false;
        System.out.println("Press enter key to back to menu page");
        scanner.nextLine();
        NewPage.displayNewPage();
        CustomerPage.viewCustomer();
    }
}
