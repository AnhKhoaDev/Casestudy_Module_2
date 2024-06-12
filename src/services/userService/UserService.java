package services.userService;

import controllers.HomeController;
import models.User;
import storages.userStorage.UserStorage;
import views.AdminPage;
import views.CustomerPage;
import views.NewPage;

import java.util.List;
import java.util.Scanner;

public class UserService implements IUserService {
    Scanner scanner = new Scanner(System.in);
    private static final List<User> userList = UserStorage.getInstance().readFile();
    public static String currentAccount;
    private static UserService instance;

    private UserService() {}

    public static UserService getInstance() {
        if(instance == null){
            synchronized (UserService.class){
                if(instance == null){
                    instance = new UserService();
                }
            }
        }
        return instance;
    }



    @Override
    public void register() throws InterruptedException {
        System.out.println("-------Đăng ký------");
        System.out.println("Tài khoản: ");
        boolean userNameExists = false;

        String userName;

        String USERNAME_REGEX = "^(?=.*[A-Z])(?=.*\\d).{6,}$";
        boolean isMatch = false;
        do {
            System.out.println("\033[34m" + "Tên người dùng phải có ít nhất 6 ký tự, bao gồm ít nhất 1 chữ số và 1 chữ in hoa." + "\033[0m");
            userName = scanner.nextLine();
            if (userName.matches(USERNAME_REGEX)) {
                isMatch = true;
            } else {
                System.err.println("Tên người dùng không hợp lệ.");
            }
        } while (!isMatch);
        for (User a : userList) {
            if (a.getUserName().equals(userName)) {
                userNameExists = true;
                break;
            }
        }
        if (userNameExists) {
            System.err.println("Tên này đã có người dùng. Vui lòng nhập tên người dùng khác.");
            userNameExists = false;
        }
        String exceptPassword = "";
        boolean isExcept = false;
        do {
            System.out.println("Mật khẩu: ");
            String inputPassword = scanner.nextLine();
            String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d).{6,}$";
            if (inputPassword.matches(PASSWORD_REGEX)) {
                isExcept = true;
                exceptPassword = inputPassword;
            } else {
                System.err.println("Mật khẩu không hợp lệ.");
            }
            System.out.println("\033[34m" + "Mật khẩu phải có ít nhất 6 ký tự, trong đó có ít nhất 1 chữ số và 1 chữ in hoa." + "\033[0m");
        }while(!isExcept);

        System.out.println("Tên: ");
        String firstName = scanner.nextLine();
        System.out.println("Họ: ");
        String lastName = scanner.nextLine();
        boolean isValid = false;
        String phoneNumber;

        do {
            String PHONE_REGEX = "^0\\d{9,}$";
            System.out.println("Số điện thoại phải có ít nhất 10 số và bắt đầu bằng 0");
            phoneNumber = scanner.nextLine();
            if (phoneNumber.matches(PHONE_REGEX)) {
                isValid = true;
            } else {
                System.err.println("Số điên thoại không hợp lệ.");
            }
        }while(!isValid);
        String role = "customer";
        double wallet = 0;

        User newUser = new User(userName, exceptPassword, firstName, lastName, phoneNumber, role, wallet);
        userList.add(newUser);
        UserStorage.getInstance().writeFile(userList);
        System.out.println("Tài khoản đăng ký thành công!");
        HomeController.load();
    }

    @Override
    public void login() throws InterruptedException {
        boolean isSuceesed = false;
        System.out.println("-----Log in------");
        System.out.println("Tài khoản: ");
        String inputUserName = scanner.nextLine();
        System.out.println("Mật khẩu: ");
        String inputPassword = scanner.nextLine();
        for (User a : userList) {
            if (a.getUserName().equals(inputUserName) && a.getPassword().equals(inputPassword)) {
                isSuceesed = true;
                currentAccount = inputUserName;
                if(a.getRole().equalsIgnoreCase("admin")){
                    System.out.println("Đăng nập thành công.");
                    NewPage.displayNewPage();
                    AdminPage.viewAdminPage();
                    break;
                }else if(a.getRole().equalsIgnoreCase("customer")){
                    System.out.println("Đăng nhập thành công");
                    NewPage.displayNewPage();
                    CustomerPage.viewCustomer();
                    break;
                }
            }
        }if(!isSuceesed){
            System.err.println("Tài khoản hoặc mật khẩu không đúng.");
            System.out.println();
            login();
        }
    }

    @Override
    public void changePassword() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        boolean isMatching = false;
        do {
            System.out.println("Mật khẩu hiện tại: ");
            String currentPassword = scanner.nextLine();

            for (User a : userList) {
                if (currentAccount.equals(a.getUserName()) && a.getPassword().equals(currentPassword)) {
                    System.out.println("Mật khẩu mới: ");
                    String newPassword = scanner.nextLine();
                    a.setPassword(newPassword);
                    isMatching = true;
                    System.out.println("Mật khẩu đã được thay đổi");
                    break;
                }
            }
            if (!isMatching) {
                System.out.println("Mật khẩu không đúng, vui lòng nhập lại!");
            }
            UserStorage.getInstance().writeFile(userList);
        } while (!isMatching);
    }

    @Override
    public String getUsername() {
        StringBuilder outputName = new StringBuilder();
        for(User user : userList){
            if(currentAccount.equals(user.getUserName())){
                outputName.append(user.getFirstName()).append(user.getLastName());
            }
        }
        return outputName.toString();
    }
}
