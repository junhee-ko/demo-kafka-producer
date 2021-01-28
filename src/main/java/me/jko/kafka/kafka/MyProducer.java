package me.jko.kafka.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyProducer {

  private final KafkaTemplate<Integer, String> template;
  private static final String TOPIC = "this-is-test";

  public void produce() {
    template
        .send(TOPIC, "message")
        .addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
          @Override
          public void onSuccess(SendResult<Integer, String> result) {
            log.info("Produce Success, Result : {}", result);
          }

          @Override
          public void onFailure(Throwable ex) {
            log.error("Produce Fail, Ex : {}", ex.getMessage());
          }
        });
  }
}
