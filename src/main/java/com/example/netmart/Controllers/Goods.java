package com.example.netmart.Controllers;

public class Goods {
    private int good_id;
    private String good_name;
    private int quantity;
    private double buying_price;
    private double selling_price;
    private double gross_price;
    private String date;

    public Goods(int good_id, String good_name, int quantity, double buying_price, double selling_price, double gross_price, String date) {
        this.good_id = good_id;
        this.good_name = good_name;
        this.quantity = quantity;
        this.buying_price = buying_price;
        this.selling_price = selling_price;
        this.gross_price = gross_price;
        this.date = date;
    }

    public Integer getGood_id() {
        return good_id;
    }

    public String getGood_name() {
        return good_name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getBuying_price() {
        return buying_price;
    }

    public Double getSelling_price() {
        return selling_price;
    }

    public Double getGross_price() {
        return gross_price;
    }

    public String getDate() {
        return date;
    }


    public void setGood_id(Integer good_id) {
        this.good_id = good_id;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setBuying_price(Double buying_price) {
        this.buying_price = buying_price;
    }

    public void setSelling_price(Double selling_price) {
        this.selling_price = selling_price;
    }

    public void setGross_price(Double gross_price) {
        this.gross_price = gross_price;
    }

    public void setDate_price(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "id = " + good_id + ", good_name = " + good_name + ", quantity = " + quantity + ", buying_price = " + buying_price + ", selling_price = "
                + selling_price
                + ", gross_price = " + gross_price + ", date = "
                + date;
    }

    public String sqlStr() {
        return "good_name = '" + good_name + "', quantity = " + quantity + ", buying_price = "
                + buying_price
                + ", selling_price = " + selling_price + ", gross_price = "
                + gross_price + ", date = '" + date+ "'";
    }


    public static  Goods nullItem(){
        return  new Goods(-1, null, -1, -1, -1, -1, null);
    }
//
    public boolean isNull(){
        return good_name == null || quantity < 0 || buying_price == -1 || selling_price < 0 || gross_price < 0 || date == null;
    }
}