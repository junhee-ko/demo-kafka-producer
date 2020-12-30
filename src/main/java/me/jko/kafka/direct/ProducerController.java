package me.jko.kafka.direct;

import lombok.RequiredArgsConstructor;

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
