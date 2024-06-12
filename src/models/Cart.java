package models;

public class Cart {
    private String now;
    private int idCart;
    private String nameCart;
    private int quantity;
    private double unitPrice;
    private double totalAmount;
    private String category;
    private String username;

    public Cart(int id, String name, int quantity, Double price, String category, String username) {}

    public Cart(String now, int idCart, String nameCart, int quantity, double unitPrice, String category, String username) {
        this.now = now;
        this.idCart = idCart;
        this.nameCart = nameCart;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = this.quantity * this.unitPrice;
        this.category = category;
        this.username = username;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public String getNameCart() {
        return nameCart;
    }

    public void setNameCart(String nameCart) {
        this.nameCart = nameCart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
