package services.productService;

import models.Cart;

import java.util.List;

public interface IProductService {
    void addProduct() throws InterruptedException;
    void editProduct() throws InterruptedException;
    void deleteProduct() throws InterruptedException;
    void findByName() throws InterruptedException;
    void findByCategory() throws InterruptedException;
    void showAllProducts() throws InterruptedException;
    void editWhenBuy(List<Cart> cartList) throws InterruptedException;
    void sortPriceLowToHigh() throws InterruptedException;
    void sortPriceHighToLow() throws InterruptedException;
    void sortById() throws InterruptedException;
}
