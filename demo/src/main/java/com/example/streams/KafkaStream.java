package com.example.streams;

import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.KeyValue;

import com.example.templates.Sale;
import com.example.templates.Purchase;
import com.example.auxFuncs.AuxJson;
import java.math.BigDecimal;

public class KafkaStream {
    public static void main(String[] args) throws InterruptedException, IOException {
    
/*
//String topicName = args[0].toString();
  String topicName = "bleh";
  String outtopicname = "resultstopicSales";

  java.util.Properties props = new Properties();
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, "exercises-application2"); //saves the state, thats why the count is so high
  props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "broker1:9092");
  props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
  props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
    
  StreamsBuilder builder = new StreamsBuilder();
  KStream<String, Long> lines = builder.stream(topicName);

  KTable<String, Long> outlines = lines.
    groupByKey().count();

  //outlines.toStream().to(outtopicname);
  outlines.mapValues(v -> "" + v).toStream().to(outtopicname, Produced.with(Serdes.String(), Serdes.String())); 


  KafkaStreams streams = new KafkaStreams(builder.build(), props);
  streams.start();
  
  System.out.println("Reading stream from topic " + topicName);
  */
 
    exercicio5();
    //exercicio6();
    //exercicio7();
    
    
    }
    /* 
    public static void exercicio5(){
      String topicName = "bleh7";
      String outtopicname = "resultstopicSales7";

      java.util.Properties props = new Properties();
      props.put(StreamsConfig.APPLICATION_ID_CONFIG, "exercises-application3"); //saves the state, thats why the count is so high
      props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "broker1:9092,broker2:9092,broker3:9092");
      props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
      props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        
      StreamsBuilder builder = new StreamsBuilder();
      KStream<String, String> lines = builder.stream(topicName, Consumed.with(Serdes.String(), Serdes.String()));

      lines
        .map((k,v) -> {
          Sale valores = new Sale();
          valores = AuxJson.StringToSale(v);
          double price = valores.getPrice();// mudar para o preço de venda do supplier
          int quant = valores.getNumber();
          double revenue = price * quant;
          String result = Double.toString(revenue) + "\n";    //aight think it was this, there was not \n so all values got aggregated in one line, find new outtopic later
          return new KeyValue<>(k,revenue);
        })
        .groupByKey(Grouped.with(Serdes.String(),Serdes.Double()))
        //.reduce((v1,v2) -> {})
        .reduce(Double::sum )    //due to being strings somewhere is where the values are fucked??
        .toStream()
        .map((k,v)->{
          String result = Double.toString(v) + "\n";
          return new KeyValue<>(k,v);
        })
        .to(outtopicname,Produced.with(Serdes.String(), Serdes.String()));
      
        
      //outlines.toStream().to(outtopicname);
      //outlines.mapValues(v -> "" + v).toStream().to(outtopicname, Produced.with(Serdes.String(), Serdes.String())); 


      KafkaStreams streams = new KafkaStreams(builder.build(), props);
      streams.start();
      
      System.out.println("Reading stream from topic " + topicName);
      
 }*/
    public static void exercicio5(){
      String topicName = "blehTest5";
      String outtopicname = "resultstopicSales9";

      java.util.Properties props = new Properties();
      props.put(StreamsConfig.APPLICATION_ID_CONFIG, "exercises-application5"); //saves the state, thats why the count is so high
      props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "broker1:9092,broker2:9092,broker3:9092");
      props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
      props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        
      StreamsBuilder builder = new StreamsBuilder();
      KStream<String, String> lines = builder.stream(topicName, Consumed.with(Serdes.String(), Serdes.String()));

      lines
      /* 
        .map((k,v) -> {
          Sale valores = new Sale();
          valores = AuxJson.StringToSale(v);
          double price = valores.getPrice();// mudar para o preço de venda do supplier
          int quant = valores.getNumber();
          double revenue = price * quant;
          System.out.println("at least got here lmao");
          return new KeyValue<>(k,revenue);
        })*/
        .mapValues(v -> {
          Sale valores = new Sale();
          valores = AuxJson.StringToSale(v);
          double price = valores.getPrice();// mudar para o preço de venda do supplier
          int quant = valores.getNumber();
          double revenue = price * quant;
          //System.out.println("at least got here lmao");
          return Double.toString(revenue);
        })
        .groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
        /* 
        .aggregate(
          () -> 0,
          (key, value, aggregate) -> {return value + aggregate;},
          Materialized.with(Serdes.String(), Serdes.Double())
        )*/
        //.reduce((v1, v2) -> v1+v2)
        .aggregate(
          () -> 0.0,
          (key, value, aggregate) -> aggregate + Double.parseDouble(value),
          Materialized.<String, Double, KeyValueStore<Bytes, byte[]>>as("sum-by-key-store")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(Serdes.Double())
        )
        .toStream()
        .map((k,v) -> {
          //String result = String.valueOf(v) + "\n";
          //System.out.println("key: " + k + " value: " + v + "\n");
          return new KeyValue<>(k,Double.toString(v));
        })
        .to(outtopicname,Produced.with(Serdes.String(), Serdes.String()));

      
        
      //outlines.toStream().to(outtopicname);
      //outlines.mapValues(v -> "" + v).toStream().to(outtopicname, Produced.with(Serdes.String(), Serdes.String())); 


      KafkaStreams streams = new KafkaStreams(builder.build(), props);
      streams.start();
      
      System.out.println("Reading stream from topic " + topicName);
      
 }


    /* 
    public static void exercicio6(){
      String topicName = "purchases2";
      String outtopicname = "resultstopicSales123";

      java.util.Properties props = new Properties();
      props.put(StreamsConfig.APPLICATION_ID_CONFIG, "exercises-application2"); //saves the state, thats why the count is so high
      props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "broker1:9092,broker2:9092,broker3:9092");
      props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
      props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        
      StreamsBuilder builder = new StreamsBuilder();
      KStream<String, String> lines = builder.stream(topicName, Consumed.with(Serdes.String(), Serdes.String()));

      
      lines
        .map((k,v) -> {
          Purchase valores = new Purchase();
          valores = AuxJson.StringToPurchase(v);
          double price = valores.getPrice();// mudar para o preço de compra dos suppliers
          int quant = valores.getNumber();
          double revenue = price * quant;
          String result = Double.toString(revenue);
          return new KeyValue<>(k,result);
        })
        .groupByKey()
        //.reduce((v1,v2) -> {})
        .reduce((v1,v2) -> v1 + v2 )
        .toStream()
        .to(outtopicname,Produced.with(Serdes.String(), Serdes.String()));
      
        
      //outlines.toStream().to(outtopicname);
      //outlines.mapValues(v -> "" + v).toStream().to(outtopicname, Produced.with(Serdes.String(), Serdes.String())); 


      KafkaStreams streams = new KafkaStreams(builder.build(), props);
      streams.start();
      
      System.out.println("Reading stream from topic " + topicName);
      
 }*/

 /*
    public static void exercicio7(){
      String topicName = "bleh3";
      String outtopicname = "resultstopicSales123";

      java.util.Properties props = new Properties();
      props.put(StreamsConfig.APPLICATION_ID_CONFIG, "exercises-application2"); //saves the state, thats why the count is so high
      props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "broker1:9092,broker2:9092,broker3:9092");
      props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
      props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        
      StreamsBuilder builder = new StreamsBuilder();
      KStream<String, String> lines = builder.stream(topicName, Consumed.with(Serdes.String(), Serdes.String()));

      
      lines
        .map((k,v) -> {
          Sale valores = new Sale();
          valores = AuxJson.StringToSale(v);
          double rev = valores.getPrice();// obter preço de venda do supplier
          double exp = valores.getPrice();// obter preço de compra do supplier
          double prof = rev - exp ;
          int quant = valores.getNumber();
          double profit = prof * quant;
          String result = Double.toString(profit);
          return new KeyValue<>(k,result);
        })
        .groupByKey()
        //.reduce((v1,v2) -> {})
        .reduce((v1,v2) -> v1 + v2 )
        .toStream()
        .to(outtopicname,Produced.with(Serdes.String(), Serdes.String()));
      
        
      //outlines.toStream().to(outtopicname);
      //outlines.mapValues(v -> "" + v).toStream().to(outtopicname, Produced.with(Serdes.String(), Serdes.String())); 


      KafkaStreams streams = new KafkaStreams(builder.build(), props);
      streams.start();
      
      System.out.println("Reading stream from topic " + topicName);
      
 } */


/* 
public static void exercicio8(){
      String topicName = "bleh3";
      String outtopicname = "resultstopicSales123";

      java.util.Properties props = new Properties();
      props.put(StreamsConfig.APPLICATION_ID_CONFIG, "exercises-application2"); //saves the state, thats why the count is so high
      props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "broker1:9092,broker2:9092,broker3:9092");
      props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
      props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        
      StreamsBuilder builder = new StreamsBuilder();
      KStream<String, String> lines = builder.stream(topicName, Consumed.with(Serdes.String(), Serdes.String()));

      
      lines
        .map((k,v) -> {
          Sale valores = new Sale();
          valores = AuxJson.StringToSale(v);
          double rev = valores.getPrice();// obter preço de venda do supplier
          double exp = valores.getPrice();// obter preço de compra do supplier
          double prof = rev - exp ;
          int quant = valores.getNumber();
          double profit = prof * quant;
          String result = Double.toString(profit);
          return new KeyValue<>(k,result);
        })
        .groupByKey()
        //.reduce((v1,v2) -> {})
        .reduce((v1,v2) -> v1 + v2 )
        .toStream()
        .to(outtopicname,Produced.with(Serdes.String(), Serdes.String()));
      
        
      //outlines.toStream().to(outtopicname);
      //outlines.mapValues(v -> "" + v).toStream().to(outtopicname, Produced.with(Serdes.String(), Serdes.String())); 


      KafkaStreams streams = new KafkaStreams(builder.build(), props);
      streams.start();
      
      System.out.println("Reading stream from topic " + topicName);
      
 }*/

}
