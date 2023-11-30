Tutorial (create topic)
kafka-topics.sh --bootstrap-server localhost:9092 --topic demo_java --create --partitions 3 --replication-factor 1

Our:
kafka-topics.sh --bootstrap-server broker1:9092 --topic demo_java --create --partitions 3 --replication-factor 1

Tutorial(enter consumer cli)
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic demo_java

Our:
kafka-console-consumer.sh --bootstrap-server broker1:9092 --topic demo_java


## TODO:
- Make Customers (Producer) to "Sock Sales" topic
- Make Purchase Orders (producer) to "Sock Purchases" Topic
- Make Kafka Streams (where all processing will be done) that will post messages to "Results" and "DBInfo" Topic
- Make class to pass info from "Results" Topic to database

BDInfo: 
- Sock supplier ID
    - ID
    - Name
- Sock characteristics
    - Price
    - Type
    - supplier_id

Purchases:
    In: 
        - Socks
    Out: 
        - Price
        - Number of pairs
        - Type
        - Sock supplier

Sales:
    In:
        - Socks
    Out:
        - Sock
        - Price
        - Number of pairs
        - sock supplier
        - Buyer ID

Results:
    In:
        - Purchases
        - Sales
    Out:
        - computated results (also go to the database)


Database:
- Supplier
    - ID
    - Name

- Sock
    - ID
    - Price
    - Type
    - supplier_id (fk)