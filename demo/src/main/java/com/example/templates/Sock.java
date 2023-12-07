package com.example.templates;

import org.json.JSONObject;

public class Sock {
    private int id;
    private String tipo;
    private double preco;
    private int supplier_id;

    public Sock(){};

    public Sock(int id, String tipo, double preco, int supplier_id){
        this.id = id;
        this.tipo = tipo;
        this.preco = preco;
        this.supplier_id = supplier_id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public String getTipo(){
        return tipo;
    }

    public void setPreco(double preco){
        this.preco = preco;
    }

    public double getPreco(){
        return preco;
    }

    public void setSupplier_id(int supplier_id){
        this.supplier_id = supplier_id;
    }

    public int getSupplier_id(){
        return supplier_id;
    }

    public String JsonToString(){
        JSONObject obj = new JSONObject();

        obj.put("id", id);
        obj.put("tipo", tipo);
        obj.put("preco", preco);
        obj.put("supplier_id", supplier_id);

        return obj.toString();
    }

    public String toString(){
        return("\n{ID: " + id + "\nTipo: " + tipo + "\nPreco: " + preco + "\nSupplier ID: " + supplier_id + "}\n");
    }

}
