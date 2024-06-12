package views;

import controllers.AdminController;

public class AdminPage {
    public static void viewAdminPage() throws InterruptedException{
        System.out.println("---------Trang Admin----------");
        System.out.println("1.Thêm sản phẩm");
        System.out.println("2.Cập nhật sản phẩm");
        System.out.println("3.Xoá sản phẩm");
        System.out.println("4.Tìm sản phẩm theo tên");
        System.out.println("5.Tìm sản phẩm theo loại");
        System.out.println("6.Xem danh sách sản phẩm");
        System.out.println("7.Tạo mã giảm giá");
        System.out.println("8.Xem lịch sử");
        System.out.println("9.Xem danh sách thanh toán");
        System.out.println("0.Thoát chương trình");
        AdminController.adminController();
    }
}
