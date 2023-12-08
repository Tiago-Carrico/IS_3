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
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.KeyValue;

import com.example.templates.Sale;
import com.example.templates.Purchase;
import com.example.auxFuncs.AuxJson;
import java.math.BigDecimal;
import java.time.Duration;

public class KafkaStream {

  static public KTable<String,Double> salesTable;
  static public KTable<String,Double> purchasesTable;

  static public KTable<Windowed<String>,Double> salesTableWindow;
  static public KTable<Windowed<String>,Double> purchasesTableWindow;


  static public KTable<String,Double> totalsalesTable;
  static public KTable<String,Double> totalpurchaseTable;
  static public KTable<String,Double> totalprofitTable;
    public static void main(String[] args) throws InterruptedException, IOException {

    String topicName1 = "sales511";
    String topicName2 = "purchases611";
    
    java.util.Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "exercises-application111"); //saves the state, thats why the count is so high
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "broker1:9092,broker2:9092,broker3:9092");
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
      
    StreamsBuilder builder = new StreamsBuilder();

    KStream<String, String> lines = builder.stream(topicName1, Consumed.with(Serdes.String(), Serdes.String()));
    KStream<String, String> lines2 = builder.stream(topicName2, Consumed.with(Serdes.String(), Serdes.String()));
 
    exercicio5(lines);
    exercicio6(lines2);
    exercicio7();
    exercicio8(lines);
    exercicio9(lines2);
    exercicio10();
    exercicio14(lines);
    exercicio15(lines2);
    exercicio16();

    
    
    KafkaStreams streams = new KafkaStreams(builder.build(), props);
    streams.start();
    
    }
    

    public static void exercicio5(KStream<String,String> build ){
      String outtopicname = "resultstopicSales987";

     salesTable = build
        .map((k,v) -> {
          Sale valores = new Sale();
          valores = AuxJson.StringToSale(v);
          double price = valores.getPrice();
          int quant = valores.getNumber();
          double revenue = price * quant;
          System.out.println("REVENUES:  " + revenue  + "\n\n\n");
          System.out.println("ToString : " + Double.toString(revenue) + " \n" );
          System.out.println("key : " + k + " \n" );
          //System.out.println("at least got here lmao");
          return new KeyValue<>(k,Double.toString(revenue));
        })
        .groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
        .aggregate(
          () -> 0.0,
          (key, value, aggregate) -> aggregate + Double.parseDouble(value),
          Materialized.with(Serdes.String(), Serdes.Double())
        );

        salesTable.toStream()
        .map((k,v) -> {
          return new KeyValue<>(k,"Exercicio 5 " + k + " -> " + Double.toString(v));
        })
        .to(outtopicname,Produced.with(Serdes.String(), Serdes.String()));
        
      
      
      System.out.println("Reading stream from topic Sales503");
      
 }


    
    public static void exercicio6(KStream<String,String> build  ){
      String outtopicname = "resultstopicSales987";

      purchasesTable = build
        .map((k,v) -> {
          Purchase valores = new Purchase();
          valores = AuxJson.StringToPurchase(v);
          double price = valores.getPrice();
          int quant = valores.getNumber();
          double revenue = price * quant;
          System.out.println("REVENUES:  " + revenue  + "\n\n\n");
          System.out.println("ToString : " + Double.toString(revenue) + " \n" );
          System.out.println("key : " + k + " \n" );
          return new KeyValue<>(k,Double.toString(revenue));
        })
        .groupByKey(Grouped.with(Serdes.String(), Serdes.String()))        
        .aggregate(
          () -> 0.0,
          (key, value, aggregate) -> aggregate + Double.parseDouble(value),
          Materialized.with(Serdes.String(), Serdes.Double())
        );

        purchasesTable.toStream()
        .map((k,v) -> {
          return new KeyValue<>(k,"Exercicio 6 " + k + " -> " + Double.toString(v));
        })
        
        .to(outtopicname,Produced.with(Serdes.String(), Serdes.String()));
      
      System.out.println("Reading stream from topic Purchases603" );
      
 }

 
    public static void exercicio7( ){

      String outtopicname = "resultstopicSales987";

      KTable<String,Double> profitTable = purchasesTable.join(salesTable,(left,right) -> left - right);
/*
      profitTable.toStream().groupBy((key, value) -> KeyValue.pair(key, value))
      .reduce((value1, value2) -> value2, Materialized.as("latest-value-store"))
      .toStream()
      .foreach((key, value) -> {
          // Print only the latest value for each key
          System.out.println("Latest Key: " + key + ", Latest Value: " + value);
      });

      */

      profitTable.toStream().map((k,v) -> {
          return new KeyValue<>(k,"Exercicio 7 " + k + " -> " + Double.toString(v));
        })
        .to(outtopicname,Produced.with(Serdes.String(), Serdes.String()));

        System.out.println("Doing stream for 7" );
      
 } 


 
public static void exercicio8(KStream<String,String> build){
   
  String outtopicname = "resultstopicSales987";

      totalsalesTable =build
        .mapValues(v -> {
          Sale valores = new Sale();
          valores = AuxJson.StringToSale(v);
          double price = valores.getPrice();
          int quant = valores.getNumber();
          double revenue = price * quant;
          return Double.toString(revenue);
        })
        .map((k, v) -> new KeyValue<>("sum", v))
        .groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
        .aggregate(
          () -> 0.0,
          (key, value, aggregate) -> aggregate + Double.parseDouble(value),
          Materialized.with(Serdes.String(), Serdes.Double())
        );


        totalsalesTable.toStream()
        .map((k,v) -> {
          System.out.println("current sum: " + v);
          return new KeyValue<>(k," Exercicio 8 " + k + " -> " + Double.toString(v));
        })
        .to(outtopicname,Produced.with(Serdes.String(), Serdes.String()));

  
      
      System.out.println("Reading stream from topic Sales");
      
 }

 public static void exercicio9(KStream<String,String> build){

      String outtopicname = "resultstopicSales987";

      totalpurchaseTable = build
        .mapValues(v -> {
          Purchase valores = new Purchase();
          valores = AuxJson.StringToPurchase(v);
          double price = valores.getPrice();
          int quant = valores.getNumber();
          double revenue = price * quant;
          return Double.toString(revenue);
        })
        .map((k, v) -> new KeyValue<>("sum", v))
        .groupByKey(Grouped.with(Serdes.String(), Serdes.String()))
        .aggregate(
          () -> 0.0,
          (key, value, aggregate) -> aggregate + Double.parseDouble(value),
          Materialized.with(Serdes.String(),Serdes.Double())
        );

        totalpurchaseTable.toStream()
        .map((k,v) -> {
          System.out.println("current sum: " + v);
          return new KeyValue<>(k," Exercicio 9 " + k + " -> " + Double.toString(v));
        })
        .to(outtopicname,Produced.with(Serdes.String(), Serdes.String()));

      
      
      System.out.println("Reading stream from topic Purchases");
      
 }

 public static void exercicio10( ){

      String outtopicname = "resultstopicSales987";

      KTable<String,Double> totalprofitTable = totalpurchaseTable.join(totalsalesTable,(left,right) -> left - right);


      totalprofitTable.toStream().map((k,v) -> {
          return new KeyValue<>(k,"Exercicio 10 " + k + " -> " + Double.toString(v));
        })
        
        .to(outtopicname,Produced.with(Serdes.String(), Serdes.String()));

        System.out.println("Doing stream for 10" );
      
 } 


 public static void exercicio14(KStream<String,String> build){
      
      String outtopicname = "resultstopicSales987";

      TimeWindows tumblingWindow = TimeWindows.of(Duration.ofMinutes(60));

      salesTableWindow = build
        .mapValues(v -> {
          Sale valores = new Sale();
          valores = AuxJson.StringToSale(v);
          double price = valores.getPrice();// mudar para o preço de venda do supplier
          int quant = valores.getNumber();
          double revenue = price * quant;
          //System.out.println("at least got here lmao");
          return revenue;
        })
        .map((k, v) -> new KeyValue<>("sum", v))
        .groupByKey(Grouped.with(Serdes.String(), Serdes.Double()))
        .windowedBy(tumblingWindow)
        .reduce(Double::sum);

        salesTableWindow
        .toStream()
        .map((windowedKey, sum) -> KeyValue.pair(windowedKey.key(), " Exercicio 14 -> " + Double.toString(sum)))
        .to(outtopicname,Produced.with(Serdes.String(), Serdes.String()));

      System.out.println("Reading stream from topic Sales");
      
 }

 public static void exercicio15(KStream<String,String> build){
       String outtopicname = "resultstopicSales987";

      
      TimeWindows tumblingWindow = TimeWindows.of(Duration.ofMinutes(60));

      purchasesTableWindow = build
        .mapValues(v -> {
          Purchase valores = new Purchase();
          valores = AuxJson.StringToPurchase(v);
          double price = valores.getPrice();
          int quant = valores.getNumber();
          double revenue = price * quant;
          return revenue;
        })
        .map((k, v) -> new KeyValue<>("sum", v))
        .groupByKey(Grouped.with(Serdes.String(), Serdes.Double()))
        .windowedBy(tumblingWindow)
        .reduce(Double::sum);

        purchasesTableWindow
        .toStream()
        .map((windowedKey, sum) -> KeyValue.pair(windowedKey.key(), " Exercicio 15 -> " + Double.toString(sum)))
        .to(outtopicname,Produced.with(Serdes.String(), Serdes.String()));

      
      System.out.println("Reading stream from topic Purchases");
      
 }

public static void exercicio16( ){

      String outtopicname = "resultstopicSales987";

      KTable<Windowed<String>,Double> totalprofitTableWindow = purchasesTableWindow.join(salesTableWindow,(left,right) -> left - right);

  KTable<String, Double> transformedTable = totalprofitTableWindow
    .toStream()
    .map((keyValue, value) -> KeyValue.pair(keyValue.key(), value)) // Extract key from Windowed and create new KeyValue
    .groupByKey(Grouped.with(Serdes.String(), Serdes.Double())) // Group by extracted String key
    .reduce((value1, value2) -> value1);


      transformedTable.toStream().map((k,v) -> {
          return new KeyValue<>(k,"Exercicio 16 " + k + " -> " + Double.toString(v));
        })
        .to(outtopicname,Produced.with(Serdes.String(), Serdes.String()));

        System.out.println("Doing stream for 16" );
      
 } 


}
