package com.example.teachfiles;

//import util.properties packages
import java.util.Properties;

//import simple producer packages
import org.apache.kafka.clients.producer.Producer;

//import KafkaProducer packages
import org.apache.kafka.clients.producer.KafkaProducer;

//import ProducerRecord packages
import org.apache.kafka.clients.producer.ProducerRecord;

//Create java class named “SimpleProducer”
public class SimpleProducer {

 public static void main(String[] args) throws Exception{

  //Assign topicName to string variable
   //Assign topicName to string variable
   String topicName = "demo__java3";

   // create instance for properties to access producer configs   
   
   Properties props = new Properties();
 
   //Assign localhost id
   props.put("bootstrap.servers", "broker1:9092");
 
   //Set acknowledgements for producer requests.      
   props.put("acks", "all");
 
   //If the request fails, the producer can automatically retry,
   props.put("retries", 0);
 
   //Specify buffer size in config
   props.put("batch.size", 16384);
 
   //Reduce the no of requests less than 0   
   props.put("linger.ms", 1);
 
   //The buffer.memory controls the total amount of memory available to the producer for buffering.   
   props.put("buffer.memory", 33554432);
 
   props.put("key.serializer", 
     "org.apache.kafka.common.serialization.StringSerializer");
 
   props.put("value.serializer", 
     "org.apache.kafka.common.serialization.LongSerializer");
 
   Producer<String, Long> producer = new KafkaProducer<>(props);
 
   for(int i = 0; i < 1000; i++)
    producer.send(new ProducerRecord<String, Long>(topicName, Integer.toString(i), (long) i));


/* 
  Properties props2 = new Properties();
 
   //Assign localhost id
   props2.put("bootstrap.servers", "broker1:9092");
 
   //Set acknowledgements for producer requests.      
   props2.put("acks", "all");
 
   //If the request fails, the producer can automatically retry,
   props2.put("retries", 0);
 
   //Specify buffer size in config
   props2.put("batch.size", 16384);
 
   //Reduce the no of requests less than 0   
   props2.put("linger.ms", 1);
 
   //The buffer.memory controls the total amount of memory available to the producer for buffering.   
   props2.put("buffer.memory", 33554432);
 
   props2.put("key.serializer", 
     "org.apache.kafka.common.serialization.StringSerializer");
 
   props2.put("value.serializer", 
     "org.apache.kafka.common.serialization.StringSerializer");

  Producer<String, String> producer2 = new KafkaProducer<>(props2);

  for(int i = 0; i < 1000; i++){
    producer2.send(new ProducerRecord<String, String>(topicName, Integer.toString(i), Integer.toString(i)));
    System.out.println("key:" + Integer.toString(i) + " value: " + Integer.toString(i));
  }*/

   System.out.println("Message sent successfully to topic " + topicName);
   producer.close();
   //producer2.close();
 }
}