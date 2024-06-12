package views;

import controllers.SortController;

public class SortProductListMenu {
    public static void sortView() throws InterruptedException {
        System.out.println("________________________________________________");
        System.out.println("1.Sắp xếp theo giá : Tăng dần");
        System.out.println("2.Sắp xếp theo giá: Giảm dần");
        System.out.println("3. Sắp xếp theo danh mục");
        System.out.println("4. Trở về trang chủ");
        SortController.sortController();
    }
}
