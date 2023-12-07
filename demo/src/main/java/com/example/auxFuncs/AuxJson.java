package com.example.auxFuncs;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.example.templates.Sale;
import com.example.templates.Purchase;
import com.example.templates.Sock;

public class AuxJson {

    public static JSONObject StringToJson(String data){
        JSONObject newObj = new JSONObject(data);
        return newObj;
    }

    public static Sale JsonToSale(JSONObject obj){

        String newReference = obj.get("reference").toString();
        BigDecimal temp = (BigDecimal) obj.get("price");
        double newPrice =  temp.doubleValue();
        int newNumber = (int) obj.get("number");
        int newSupplier = (int) obj.get("supplier");
        int newBuyer = (int) obj.get("buyer");

        Sale newSale = new Sale(newReference, newPrice, newNumber, newSupplier, newBuyer);
        return newSale;
    }

    public static Sale StringToSale(String data){
        JSONObject newObj = StringToJson(data);
        Sale newSale = JsonToSale(newObj);
        return newSale;
    }

    public static Purchase JsonToPurchase(JSONObject obj){
        String newReference = obj.get("reference").toString();
        BigDecimal temp = (BigDecimal) obj.get("price");
        double newPrice = temp.doubleValue();
        int newNumber = (int) obj.get("number");
        String newType = obj.get("type").toString();
        int newSupplier = (int) obj.get("supplier");

        Purchase newPurchase = new Purchase(newReference, newPrice, newNumber, newType, newSupplier);
        return newPurchase;
    }

    public static Sock JsonToSock(JSONObject obj){
        int newId = (int) obj.get("id");
        String newTipo = obj.get("tipo").toString();
        double newPreco = ((BigDecimal) obj.get("preco")).doubleValue();
        int newSupplier_id = (int) obj.get("supplier_id");

        Sock newSock = new Sock(newId, newTipo, newPreco, newSupplier_id);
        return newSock;
    }

    public static Purchase StringToPurchase(String data){
        JSONObject newObj = StringToJson(data);
        Purchase newPurchase = JsonToPurchase(newObj);
        return newPurchase;
    }

    public static Sock dbSockJSONToSock(String data){
        JSONObject newObj = StringToJson(data);
        Sock newSock = JsonToSock(newObj);
        return newSock;
    }
    
}
