Tutorial (create topic)
kafka-topics.sh --bootstrap-server localhost:9092 --topic demo_java --create --partitions 3 --replication-factor 1

Our:
kafka-topics.sh --bootstrap-server broker1:9092 --topic demo_java --create --partitions 3 --replication-factor 1

Tutorial(enter consumer cli)
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic demo_java

Our:
kafka-console-consumer.sh --bootstrap-server broker1:9092 --topic resultstopic2

