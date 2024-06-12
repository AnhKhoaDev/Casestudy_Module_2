package services.cartService;

import models.Cart;

import java.util.List;

public interface ICartService {
    void addToCart() throws InterruptedException;
    double  getTotal();
    List<Cart> getMyCart();
    void viewMyCart();
}
