package services.customerService;

import models.Order;

public interface ICustomerService {
    void addFund();
    void buyProduct() throws InterruptedException;
    void viewMyWallet();
    void viewMyHistory();
    void viewMyCartListOrder(Order order);
    void enterBackMenu() throws InterruptedException;
}
