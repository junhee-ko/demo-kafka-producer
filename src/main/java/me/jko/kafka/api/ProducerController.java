package me.jko.kafka.api;

import lombok.RequiredArgsConstructor;
import me.jko.kafka.kafka.Producer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProducerController {

  private final Producer producer;

  @GetMapping("/produce")
  public ResponseEntity produce() {
    producer.produce();

    return ResponseEntity
        .ok()
        .build();
  }
}
