package com.example.producers;

import java.time.Duration;
import java.util.Arrays;
//import util.properties packages
import java.util.Properties;

//import simple producer packages
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
//import KafkaProducer packages
import org.apache.kafka.clients.producer.KafkaProducer;

//import ProducerRecord packages
import org.apache.kafka.clients.producer.ProducerRecord;

public class producerstest {

    public static void main(String[] args) throws Exception{

    //TODO can we just do the props for all and use a 
    
    //TODO make consumers part from bottom topics
    String topicDB = "DBInfo";
    Properties propsDB = new Properties();
    propsDB.put("bootstrap.servers", "broker1:9092");   //TODO how do we do to accomodate multiple brokers??  
    propsDB.put("key.deserializer", 
        "org.apache.kafka.common.serialization.StringDeserializer");
    propsDB.put("value.deserializer", 
        "org.apache.kafka.common.serialization.StringDeserializer");
    propsDB.put(ConsumerConfig.GROUP_ID_CONFIG, "testConsumer");


    //TODO make Purchase producer part
    String topicPurchase = "purchases";
    Properties propsPurchase = new Properties();
    propsPurchase.put("bootstrap.servers", "broker1:9092, broker2:9093");   //TODO how do we do to accomodate multiple brokers??  
    propsPurchase.put("acks", "all");
    propsPurchase.put("retries", 0);
    propsPurchase.put("batch.size", 16384);
    propsPurchase.put("linger.ms", 1);   
    propsPurchase.put("buffer.memory", 33554432);
    propsPurchase.put("key.serializer", 
     "org.apache.kafka.common.serialization.StringSerializer");
    propsPurchase.put("value.serializer", 
     "org.apache.kafka.common.serialization.StringSerializer");

    Producer<String, String> producerPurchase = new KafkaProducer<>(propsPurchase);


    //TODO make Sales producer part
    String topicSales = "bleh2";
    Properties propsSales = new Properties();
    propsSales.put("bootstrap.servers", "broker1:9092, broker2:9093");   //TODO how do we do to accomodate multiple brokers??  
    propsSales.put("acks", "all");
    propsSales.put("retries", 0);
    propsSales.put("batch.size", 16384);
    propsSales.put("linger.ms", 1);   
    propsSales.put("buffer.memory", 33554432);
    propsSales.put("key.serializer", 
     "org.apache.kafka.common.serialization.StringSerializer");
    propsSales.put("value.serializer", 
     "org.apache.kafka.common.serialization.StringSerializer");

    Producer<String, String> producerSales = new KafkaProducer<>(propsSales);


     int i = 0;
    //TODO cycle to produce and send all new info, maybe even read here
    final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(propsDB);
    consumer.subscribe(Arrays.asList(topicSales));
    while(true){
        
        producerSales.send(new ProducerRecord<String, String>(topicSales, Integer.toString(i), Integer.toString(i*2)));
        i++;
        System.out.println("it nr: " + i + "value: " + i*2);
        Thread.sleep(1000);
        
        /* 
        ConsumerRecords<String, String> records =
                        consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("Key: " + record.key() + " value: " + record.value());
                }*/
    }



    }
}
