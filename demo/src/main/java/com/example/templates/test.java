package com.example.templates;

import java.math.BigDecimal;

import org.json.*;
import com.example.aux.JsonAux;

public class test {

    public static void main(String[] args){

        Sale testSale = new Sale("123", 17.5, 5, 456, 789);
        String jsonify = testSale.JsonToString();

        //System.out.println(jsonify);

        JSONObject json2nd = new JSONObject();
        json2nd = JsonAux.StringToJson(jsonify);

        Sale lastSale = new Sale();
        lastSale = JsonAux.JsonToSale(json2nd);

        System.out.println(lastSale);



        /* 
        //receives string well
        String testString = testSale.jsonTest();
        System.out.println(testString);

        //puts it back to json object well
        JSONObject newObj = new JSONObject(testString);

        System.out.println("new post jsonobject: " + newObj);

        //also works lezz gooo
        System.out.println(newObj.get("price"));
        */




    }
    
}
