package com.example.producers;

//import util.properties packages
import java.util.Properties;

//import simple producer packages
import org.apache.kafka.clients.producer.Producer;

//import KafkaProducer packages
import org.apache.kafka.clients.producer.KafkaProducer;

//import ProducerRecord packages
import org.apache.kafka.clients.producer.ProducerRecord;

public class producers {

    public static void main(String[] args) throws Exception{
    //TODO make consumers part from bottom topics
    String topicDB = "DBInfo";
    Properties propsDB = new Properties();
    propsDB.put("bootstrap.servers", "broker1:9092");   //TODO how do we do to accomodate multiple brokers??  
    propsDB.put("acks", "all");
    propsDB.put("retries", 0);
    propsDB.put("batch.size", 16384);
    propsDB.put("linger.ms", 1);   
    propsDB.put("buffer.memory", 33554432);
    propsDB.put("key.serializer", 
        "org.apache.kafka.common.serialization.StringSerializer");
    propsDB.put("value.serializer", 
        "org.apache.kafka.common.serialization.LongSerializer");


    //TODO make Purchase producer part
    String topicPurchase = "purchases";
    Properties propsPurchase = new Properties();
    propsPurchase.put("bootstrap.servers", "broker1:9092");   //TODO how do we do to accomodate multiple brokers??  
    propsPurchase.put("acks", "all");
    propsPurchase.put("retries", 0);
    propsPurchase.put("batch.size", 16384);
    propsPurchase.put("linger.ms", 1);   
    propsPurchase.put("buffer.memory", 33554432);
    propsPurchase.put("key.serializer", 
     "org.apache.kafka.common.serialization.StringSerializer");
    propsPurchase.put("value.serializer", 
     "org.apache.kafka.common.serialization.LongSerializer");

    Producer<String, Long> producerPurchase = new KafkaProducer<>(propsPurchase);


    //TODO make Sales producer part
    String topicSales = "bleh";
    Properties propsSales = new Properties();
    propsSales.put("bootstrap.servers", "broker1:9092");   //TODO how do we do to accomodate multiple brokers??  
    propsSales.put("acks", "all");
    propsSales.put("retries", 0);
    propsSales.put("batch.size", 16384);
    propsSales.put("linger.ms", 1);   
    propsSales.put("buffer.memory", 33554432);
    propsSales.put("key.serializer", 
     "org.apache.kafka.common.serialization.StringSerializer");
    propsSales.put("value.serializer", 
     "org.apache.kafka.common.serialization.LongSerializer");

    Producer<String, Long> producerSales = new KafkaProducer<>(propsSales);


     int i = 0;
    //TODO cycle to produce and send all new info, maybe even read here
    while(true){
        producerSales.send(new ProducerRecord<String, Long>(topicSales, Integer.toString(i%5), (long) i));
        i++;
        System.out.println("it nr: " + i + "value: " + i%5);
        Thread.sleep(1000);
    }



    }
}
