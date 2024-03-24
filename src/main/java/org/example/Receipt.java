package org.example;

import java.util.Date;

public class Receipt {
    private final String id, idEmployee, idCard;
    private final GoodInShop arrOfGoods[];
    private final Date date;
    private final double total;
    private final double vat;

    public Receipt(String id, String idEmployee, String idCard,
                   Date date, double total, double vat, GoodInShop[] arrOfGoods) {
        if (id == null || id.length() > 10) {
            throw new IllegalArgumentException("Invalid value of id");
        }
        if (idEmployee == null || idEmployee.length() > 10) {
            throw new IllegalArgumentException("Invalid value of idEmployee");
        }
        if (idCard != null && idCard.length() > 13) {
            throw new IllegalArgumentException("Invalid value of idCard");
        }
        if (date == null) {
            throw new IllegalArgumentException("Invalid value of date");
        }
        if (total <= 0) {
            throw new IllegalArgumentException("Invalid value of total");
        }
        if (vat <= 0) {
            throw new IllegalArgumentException("Invalid value of vat");
        }
        this.id = id;
        this.idEmployee = idEmployee;
        this.idCard = idCard;
        this.date = date;
        this.total = total;
        this.vat = vat;
        this.arrOfGoods = arrOfGoods;
    }

    public String getId() {
        return id;
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public String getIdCard() {
        return idCard;
    }

    public GoodInShop[] getArrOfGoods() {
        return arrOfGoods;
    }

    public Date getDate() {
        return date;
    }

    public double getTotal() {
        return total;
    }

    public double getVat() {
        return vat;
    }
}
