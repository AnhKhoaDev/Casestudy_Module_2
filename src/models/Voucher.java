package models;

public class Voucher {
    private String idVoucher;
    private double cost;
    private boolean available;

    public Voucher(){

    }

    public Voucher(String idVoucher, double cost, boolean available) {
        this.idVoucher = idVoucher;
        this.cost = cost;
        this.available = available;
    }

    public String getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(String idVoucher) {
        this.idVoucher = idVoucher;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
