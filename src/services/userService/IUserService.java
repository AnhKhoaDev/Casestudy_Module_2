package services.userService;

import models.User;

public interface IUserService {
    void register() throws InterruptedException;
    void login() throws InterruptedException;
    void changePassword()throws InterruptedException;
    String getUsername();
}
