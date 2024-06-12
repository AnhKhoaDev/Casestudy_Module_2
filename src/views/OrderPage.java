package views;

import controllers.OrderController;
import services.orderService.OrderService;

public class OrderPage {
    public static void orderPage() throws InterruptedException {
        System.out.println("---------Trang đặt hàng----------");
        System.out.println("1.Xem lịch sử đặt hàng");
        System.out.println("2.Lọc theo tên sản phẩm");
        System.out.println("3.Lọc theo sản phẩm");
        System.out.println("4.Xem lịch sử tài khoản");
        System.out.println("0. Thoát khỏi chương trình");
        OrderController.orderController();
    }
}
