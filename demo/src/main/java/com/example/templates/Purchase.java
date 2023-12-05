package com.example.templates;

import java.math.BigDecimal;
import org.json.JSONObject;

public class Purchase {
    private String reference;
    private double price;
    private int number;
    private String type;
    private int supplier;

    public Purchase(){};

    public Purchase(String reference, double price, int number, String type, int supplier){
        this.reference = reference;
        this.price = price;
        this.number = number;
        this.type = type;
        this.supplier = supplier;
    };
    
    public void setReference(String reference){
        this.reference = reference;
    }

    public String getReference(){
        return reference;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return price;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public int getNumber(){
        return number;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setSupplier(int supplier){
        this.supplier = supplier;
    }

    public int getSupplier(){
        return supplier;
    }

    public String JsonToString(){
        JSONObject obj = new JSONObject();

        obj.put("reference", this.reference);
        obj.put("price", this.price);
        obj.put("number", this.number);
        obj.put("type", this.type);
        obj.put("supplier", this.supplier);

        return obj.toString();  
    }

    public String toString(){
        return ("{\nReference: " + reference + "Price: " + price + "\nNumber: " + number + "\nType: " + type + "\nSupplier: " + supplier + "\n}");
    }
}
