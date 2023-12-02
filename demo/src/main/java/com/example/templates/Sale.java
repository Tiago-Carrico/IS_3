package com.example.templates;

import java.math.BigDecimal;

import org.json.JSONObject;

public class Sale {
    private String reference;
    private BigDecimal price;
    private int number;
    private int supplier;
    private int buyer;

    public Sale(){};

    public Sale(String reference, BigDecimal price, int number, int supplier, int buyer){
        this.reference = reference;
        this.price = price;
        this.number = number;
        this.supplier = supplier;
        this.buyer = buyer;
    }

    public void setReference(String reference){
        this.reference = reference;
    }

    public String getReference(){
        return reference;
    }

    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public BigDecimal getPrice(){
        return price;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public int getNumber(){
        return number;
    }

    public void setSupplier(int supplier){
        this.supplier = supplier;
    }

    public int getSupplier(){
        return supplier;
    }

    public void setBuyer(int buyer){
        this.buyer = buyer;
    }

    public int getBuyer(){
        return buyer;
    }

    public String JsonToString(){
        JSONObject obj = new JSONObject();

        obj.put("reference", this.reference);
        obj.put("price", this.price);
        obj.put("number", this.number);
        obj.put("supplier", this.supplier);
        obj.put("buyer", this.buyer);

        return obj.toString();
    }

    public String toString(){
        return ("{\nReference: " + reference + "\nPrice: " + price + "\nNumber: " + number + "\nSupplier: " + supplier + "\nBuyer: " + buyer + "\n}");
    }

    //Works to create json object and turn to string
    /* 
    public String jsonTest(){
        JSONObject obj = new JSONObject();

        obj.put("reference", "123");
        obj.put("price", "12.99");
        obj.put("type", "black short");
        obj.put("supplier", "456");

        return obj.toString();
    }
    */

}
