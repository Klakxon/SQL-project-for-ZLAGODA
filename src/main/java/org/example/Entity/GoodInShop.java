package org.example.Entity;

public class GoodInShop {
    private final String upc;
    private final int idGood;
    private double price;
    private int amount;
    private boolean isPromotional;

    public GoodInShop(String upc, int idGood, double price, int amount, boolean isPromotional) {
        if (upc == null || upc.length() > 12) {
            throw new IllegalArgumentException("Invalid value of upc");
        }
        if (idGood < 0) {
            throw new IllegalArgumentException("Invalid value of idGood");
        }
        if (price <= 0.0) {
            throw new IllegalArgumentException("Invalid value of price");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Invalid value of amount");
        }
        this.upc = upc;
        this.idGood = idGood;
        this.price = price;
        this.amount = amount;
        this.isPromotional = isPromotional;
    }

    public String getUpc() {
        return upc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isPromotional() {
        return isPromotional;
    }

    public void setPromotional(boolean promotional) {
        isPromotional = promotional;
    }

    public int getIdGood() {
        return idGood;
    }
}
