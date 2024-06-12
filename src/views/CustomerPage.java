package views;

import controllers.CustomerController;
import services.userService.UserService;

public class CustomerPage {
    public static void viewCustomer() throws InterruptedException{
        System.out.println("---------"+ UserService.getInstance().getUsername() +"----------");
        System.out.println("1.Xem danh sách sản phẩm");
        System.out.println("2.Tìm sản phẩm theo tên");
        System.out.println("3.Tìm sản phẩm theo loại sản phẩm");
        System.out.println("4.Thêm sản phẩm vào giỏ hàng");
        System.out.println("5.Xem giỏ hàng");
        System.out.println("6.Xem lịch sử mua hàng");
        System.out.println("7.Thay đổi mật khẩu");
        System.out.println("8.Nạp ví");
        System.out.println("9.Đặt hàng");
        System.out.println("10.Xem ví thanh toán");
        System.out.println("0.Thoát chương trình");
        CustomerController.customerController();
    }
}
