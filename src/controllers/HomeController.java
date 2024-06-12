package controllers;

import services.userService.*;
import views.NewPage;

import java.util.Scanner;

public class HomeController {
    public static void load() throws InterruptedException {
        UserService userService = UserService.getInstance();
        boolean isValid = true;
        do {
            Scanner scanner = new Scanner(System.in);
            int choose = Integer.parseInt(scanner.nextLine());
            switch (choose) {
                case 1: {
                    NewPage.displayNewPage();
                    userService.login();
                    isValid = false;
                    break;
                }
                case 2: {
                    userService.register();
                    isValid = false;
                    break;
                }
                case 0: {
                    System.out.println("------Good Bye-----");
                    UserService.currentAccount = null;
                    isValid = false;
                    System.exit(0);
                }
            }
        }while (isValid) ;
    }
}
