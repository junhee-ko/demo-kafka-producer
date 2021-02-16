//package me.jko.kafka.direct;
//
//import org.apache.kafka.common.serialization.StringSerializer;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.apache.kafka.clients.producer.ProducerConfig.*;
//
//@Configuration
//public class ProducerConfig {
//
//  private static final String BOOTSTRAP_SERVERS = "localhost:9091,localhost:9092,localhost:9093";
//
//  @Bean
//  public ProducerFactory<Integer, String> producerFactory() {
//    return new DefaultKafkaProducerFactory<>(producerConfigs());
//  }
//
//  @Bean
//  public Map<String, Object> producerConfigs() {
//    Map<String, Object> props = new HashMap<>();
//
//    props.put(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
//    props.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//    props.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//
//    return props;
//  }
//
//  @Bean
//  public KafkaTemplate<Integer, String> kafkaTemplate() {
//    return new KafkaTemplate<>(producerFactory());
//  }
//}
