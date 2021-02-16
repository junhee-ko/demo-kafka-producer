package me.jko.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EmbeddedKafka
@ActiveProfiles("test")
class MyKafkaProducerTest {

  @Autowired
  private EmbeddedKafkaBroker embeddedKafka;

  @Autowired
  private MyKafkaProducer myKafkaProducer;

  private BlockingQueue<ConsumerRecord<String, MyMessage>> records;
  private KafkaMessageListenerContainer<String, MyMessage> container;
  private static final String topic = "my-test-topic";


  @BeforeEach
  void setUp() {
    records = new LinkedBlockingQueue<>();
    container = getKafkaMessageListenerContainer();
    container.setupMessageListener((MessageListener<String, MyMessage>) records::add);
    container.start();

    ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
  }

  @AfterEach
  void tearDown() {
    container.stop();
  }

  @Test
  @DisplayName("Kafka Produce JSON Record Test")
  void givenMyMessage_whenProduceToKafka_thenSuccess() throws InterruptedException {
    // Given
    MyMessage myMessage = MyMessage.builder()
        .number(1)
        .data("test data")
        .build();

    // When
    myKafkaProducer.produce(myMessage);

    // Then
    ConsumerRecord<String, MyMessage> record = records.poll(2, SECONDS);
    assertNotNull(record);
    assertEquals(topic, record.topic());
    assertEquals(myMessage.getData(), record.value().getData());
  }

  private KafkaMessageListenerContainer<String, MyMessage> getKafkaMessageListenerContainer() {
    JsonDeserializer<MyMessage> deserializer = new JsonDeserializer<>(MyMessage.class);
    deserializer.addTrustedPackages("me.jko.kafka.producer");

    Map<String, Object> consumerConfigs = new HashMap<>(
        KafkaTestUtils.consumerProps("test-consumer-group", "false", embeddedKafka)
    );

    return new KafkaMessageListenerContainer<>(
        new DefaultKafkaConsumerFactory<>(consumerConfigs, new StringDeserializer(), deserializer),
        new ContainerProperties(topic)
    );
  }
}