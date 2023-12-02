package com.example.streams;

import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.KeyValue;

import com.example.templates.Sale;
import com.example.aux.JsonAux;
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
    
    
    }

    public static void exercicio5(){
      String topicName = "bleh3";
      String outtopicname = "resultstopicSales123";

      java.util.Properties props = new Properties();
      props.put(StreamsConfig.APPLICATION_ID_CONFIG, "exercises-application2"); //saves the state, thats why the count is so high
      props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "broker1:9092");
      props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
      props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        
      StreamsBuilder builder = new StreamsBuilder();
      KStream<String, String> lines = builder.stream(topicName, Consumed.with(Serdes.String(), Serdes.String()));

      
      lines
        .map((k,v) -> {
          Sale valores = new Sale();
          valores = JsonAux.StringToSale(v);
          double price = valores.getPrice();
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
      
 }
}
