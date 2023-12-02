package com.example.templates;

import java.math.BigDecimal;
import org.json.JSONObject;

public class Purchase {
    private BigDecimal price;
    private int number;
    private String type;
    private int supplier;

    public Purchase(){};

    public Purchase(BigDecimal price, int number, String type, int supplier){
        this.price = price;
        this.number = number;
        this.type = type;
        this.supplier = supplier;
    };

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

        obj.put("price", this.price);
        obj.put("number", this.number);
        obj.put("type", this.type);
        obj.put("supplier", this.supplier);

        return obj.toString();  
    }

    public String toString(){
        return ("{\nPrice: " + price + "\nNumber: " + number + "\nType: " + type + "\nSupplier: " + supplier + "\n}");
    }
}
