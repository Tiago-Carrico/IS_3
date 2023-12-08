package com.example.producers;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
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
import org.apache.kafka.common.errors.WakeupException;
import org.json.JSONObject;
import com.example.auxFuncs.AuxJson;
import com.example.templates.Sock;

public class producerstest {

    public static void main(String[] args) throws Exception{

    //TODO can we just do the props for all and use a 
    
    //TODO make consumers part from bottom topics
    String topicDB = "DBInfo_sock";
    Properties propsDB = new Properties();
    propsDB.put("bootstrap.servers", "broker1:9092");   //TODO how do we do to accomodate multiple brokers??  
    propsDB.put("key.deserializer", 
        "org.apache.kafka.common.serialization.StringDeserializer");
    propsDB.put("value.deserializer", 
        "org.apache.kafka.common.serialization.StringDeserializer");
    propsDB.put(ConsumerConfig.GROUP_ID_CONFIG, "testConsumer");

    final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(propsDB);

    // get a reference to the current thread
    final Thread mainThread = Thread.currentThread();

    // adding the shutdown hook
    Runtime.getRuntime().addShutdownHook(new Thread() {
        public void run() {
            //log.info("Detected a shutdown, let's exit by calling consumer.wakeup()...");
            consumer.wakeup();

            // join the main thread to allow the execution of the code in the main thread
            try {
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });


    try {

            // subscribe consumer to our topic(s)
            consumer.subscribe(Arrays.asList(topicDB));


            ArrayList<Sock> arrSocks = new ArrayList<Sock>();
            // poll for new data
            while (true) {
                ConsumerRecords<String, String> records =
                        consumer.poll(Duration.ofMillis(100));

                

                for (ConsumerRecord<String, String> record : records) {
                    //log.info("Key: " + record.key() + ", Value: " + record.value());
                    //log.info("Partition: " + record.partition() + ", Offset:" + record.offset());
                    //JSONObject jsonData = StringToJson(record);
                    
                    //System.out.println(record.value() + "\n\n");
                    JSONObject jsonData = StringToJson(record.value());
                    //System.out.println(record.value().getClass());
                    System.out.println(jsonData.get("payload").getClass());  //works good, gets only socks in json
                    //System.out.println(jsonData);
                    System.out.println(JsonToSock(jsonData.get("payload")));
                    //arrSocks.add(JsonToSock(jsonData));
                }
                
            }
            //System.out.println(arrSocks);

        } catch (WakeupException e) {
            //log.info("Wake up exception!");
            // we ignore this as this is an expected exception when closing a consumer
        } catch (Exception e) {
            //log.error("Unexpected exception", e);
        } finally {
            consumer.close(); // this will also commit the offsets if need be.
            //log.info("The consumer is now gracefully closed.");
        }




    }

    private static Sock JsonToSock(JSONObject obj){
        int newId = (int) obj.get("id");
        String newTipo = obj.get("tipo").toString();
        double newPreco = ((BigDecimal) obj.get("preco")).doubleValue();
        int newSupplier_id = (int) obj.get("supplier_id");

        Sock newSock = new Sock(newId, newTipo, newPreco, newSupplier_id);
        return newSock;
    }

    private static JSONObject StringToJson(String data) {
        JSONObject newObj = new JSONObject(data);
        return newObj;
    }
}
