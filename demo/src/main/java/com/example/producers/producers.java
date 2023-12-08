package com.example.producers;

import java.time.Duration;
import java.util.Arrays;
//import util.properties packages
import java.util.Properties;
import java.util.Random;

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

import com.example.templates.Sale;
import com.example.templates.Purchase;

import java.math.BigDecimal;



public class producers {
    public static void main(String[] args) throws Exception{

        //TODO can we just do the props for all and use a 
        
        //TODO make consumers part from bottom topics
        String topicDB = "DBInfo2";
        Properties propsDB = new Properties();
        propsDB.put("bootstrap.servers", "broker1:9092,broker2:9092,broker3:9092");   //TODO how do we do to accomodate multiple brokers??  
        propsDB.put("key.deserializer", 
            "org.apache.kafka.common.serialization.StringDeserializer");
        propsDB.put("value.deserializer", 
            "org.apache.kafka.common.serialization.StringDeserializer");
        propsDB.put(ConsumerConfig.GROUP_ID_CONFIG, "testConsumer");    //TODO important pick new one later


        //TODO make Purchase producer part
        String topicPurchase = "purchases611";
        Properties propsPurchase = new Properties();
        propsPurchase.put("bootstrap.servers", "broker1:9092,broker2:9092,broker3:9092");   //TODO how do we do to accomodate multiple brokers??  
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
        String topicSales = "sales511";
        Properties propsSales = new Properties();
        propsSales.put("bootstrap.servers", "broker1:9092,broker2:9092,broker3:9092");   //TODO how do we do to accomodate multiple brokers??  
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



        //TODO test stuff, remove before delivery or testing actual code
        int i = 0;
        //TODO cycle to produce and send all new info, maybe even read here
        //final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(propsDB);
        //consumer.subscribe(Arrays.asList(topicSales));
        while(i < 10){
            //It can send messages to topics
            /*
            producerSales.send(new ProducerRecord<String, String>(topicSales, Integer.toString(i), Integer.toString(i*2)));
            i++;
            System.out.println("it nr: " + i + "value: " + i*2);
            Thread.sleep(1000);
            */

            //It can read messages from topics
            /* 
            ConsumerRecords<String, String> records =
                            consumer.poll(Duration.ofMillis(100));

                    for (ConsumerRecord<String, String> record : records) {
                        System.out.println("Key: " + record.key() + " value: " + record.value());
                    }*/


            //String tempSale = randomSale(producerSales,topicSales);
            //String tempPurchase = randomPurchase(producerPurchase,topicPurchase);
            randomPurchaseSale(producerSales, producerPurchase, topicSales, topicPurchase);

           // producerSales.send(new ProducerRecord<String, String>(topicSales, tempREF, tempSale));
            //System.out.println(tempREF);
            //System.out.println(tempSale);
            //producerPurchase.send(new ProducerRecord<String, String>(topicPurchase, tempREF, randomPurchase()));
            i++;
            Thread.sleep(1000);
            
        }
        producerSales.close();
        producerPurchase.close();
    }

    static String[] referenceList = {"id123", "id456", "id789"};
    static String[] nameList = {"tomas", "alexandre", "joao"};
    static String[] typeList = {"low-cut", "knee-high", "invisible"};
    static double[] priceList = { 12.5, 6.99, 4.55};
    static int[] numberList = {5, 10, 15};
    static int[] supplierList = {1, 5, 10};
    static int[] buyerList = {156, 278, 923};
    static String tempREF = "";
    
    static double[] originalPrice = {20};

    public static String randomSale(Producer<String, String> producerSales, String topicSales){
        Random random = new Random();  

        String randomRef = referenceList[random.nextInt(referenceList.length)];
        double randomPrice = priceList[random.nextInt(priceList.length)];
        int randomNum = numberList[random.nextInt(numberList.length)];
        int randomSupplier = supplierList[random.nextInt(numberList.length)];
        int randomBuyer = buyerList[random.nextInt(buyerList.length)];

        double randomPrice2 = 5.1;
        int randomNum2 = 1;

        Sale tempSale = new Sale(randomRef, randomPrice2, randomNum2, randomSupplier, randomBuyer);
        System.out.println(randomRef + "\n");
        System.out.println(tempSale );
        producerSales.send(new ProducerRecord<String, String>(topicSales, randomRef, tempSale.JsonToString()));
        //tempREF = randomRef;
        return tempSale.JsonToString();
    }

    public static String randomPurchase(Producer<String, String> producerPurchase, String topicPurchase){
        Random random = new Random();

        double randomOriginPrice = originalPrice[random.nextInt(originalPrice.length)];
        int randomNum = numberList[random.nextInt(numberList.length)];
        String randomRef = referenceList[random.nextInt(referenceList.length)];
        int randomSupplier = supplierList[random.nextInt(numberList.length)];
        String randomType = typeList[random.nextInt(typeList.length)];

        double randomPrice2 = 5.1;
        int randomNum2 = 1;

        Purchase tempPurch = new Purchase(randomRef, randomPrice2,randomNum2,randomType,randomSupplier);
        producerPurchase.send(new ProducerRecord<String, String>(topicPurchase, randomRef, tempPurch.JsonToString()));
        //tempREF = randomRef;
        return tempPurch.JsonToString();
    
    }


    public static void randomPurchaseSale(Producer<String, String> producerSales,Producer<String, String> producerPurchase, String topicSales, String topicPurchase){
        Random random = new Random();  

        String randomRef = referenceList[random.nextInt(referenceList.length)];
        double randomPrice = priceList[random.nextInt(priceList.length)];
        int randomNum = numberList[random.nextInt(numberList.length)];
        int randomSupplier = supplierList[random.nextInt(numberList.length)];
        int randomBuyer = buyerList[random.nextInt(buyerList.length)];
        String randomType = typeList[random.nextInt(typeList.length)];

        double randomPrice2 = 5.1;
        int randomNum2 = 1;
        int randomNum3 = 2;

        Sale tempSale = new Sale(randomRef, randomPrice2, randomNum2, randomSupplier, randomBuyer);
        Purchase tempPurchase = new Purchase(randomRef, randomPrice2,randomNum3,randomType,randomSupplier);
        
        producerSales.send(new ProducerRecord<String, String>(topicSales, randomRef, tempSale.JsonToString()));
        producerPurchase.send(new ProducerRecord<String,String>(topicPurchase, randomRef, tempPurchase.JsonToString()));
        
        return ;
    }


}
