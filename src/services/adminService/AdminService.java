package services.adminService;

import services.userService.UserService;
import storages.userStorage.UserStorage;
import views.AdminPage;
import views.NewPage;

import java.util.Scanner;

public class AdminService implements IAdminService {
    private static AdminService instance;

    private AdminService() {}

    public static AdminService getInstance() {
        if(instance == null){
            synchronized (AdminService.class){
                if(instance == null){
                    instance = new AdminService();
                }
            }
        }
        return instance;
    }

    @Override
    public void enterBackMenu() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhấn phím enter để quay lại trang menu");
        sc.nextLine();
        NewPage.displayNewPage();
        AdminPage.viewAdminPage();
    }
}
