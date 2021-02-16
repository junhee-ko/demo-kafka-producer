package me.jko.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyKafkaProducer {

  private final KafkaTemplate<String, MyMessage> template;
  private static final String TOPIC = "my-test-topic";

  public void produce(MyMessage message) {
    template
        .send(TOPIC, message)
        .addCallback(new ListenableFutureCallback<SendResult<String, MyMessage>>() {
          @Override
          public void onSuccess(SendResult<String, MyMessage> result) {
            log.info("Produce Success, Result : {}", result);
          }

          @Override
          public void onFailure(Throwable ex) {
            log.error("Produce Fail, Ex : {}", ex.getMessage());
          }
        });
  }
}
