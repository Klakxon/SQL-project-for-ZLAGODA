package org.example;

public class Sale {
    private final String upc, checkNumber;
    private final int productNumber;
    private final double sellingPrice;

    public Sale(String upc, String checkNumber, int productNumber, double sellingPrice) {
        if (upc == null || upc.length() > 12) {
            throw new IllegalArgumentException("Invalid value of upc");
        }
        if (checkNumber == null || checkNumber.length() > 10) {
            throw new IllegalArgumentException("Invalid value of checkNumber");
        }
        if (productNumber <= 0) {
            throw new IllegalArgumentException("Invalid value of productNumber");
        }
        if (sellingPrice <= 0) {
            throw new IllegalArgumentException("Invalid value of sellingPrice");
        }
        this.upc = upc;
        this.checkNumber = checkNumber;
        this.productNumber = productNumber;
        this.sellingPrice = sellingPrice;
    }

    public String getUpc() {
        return upc;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }
}
