package models;

import java.util.List;

public class Order {
    private String now;
    private String userName;
    private List<Cart> boughtList;

    public Order(){

    }

    public Order(String now, String userName, List<Cart> boughtList) {
        this.now = now;
        this.userName = userName;
        this.boughtList = boughtList;
    }

    public String getNow() {
        return now;
    }

    public String getUserName() {
        return userName;
    }

    public List<Cart> getBoughtList() {
        return boughtList;
    }
}
