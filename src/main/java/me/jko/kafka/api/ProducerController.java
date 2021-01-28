package me.jko.kafka.api;

import lombok.RequiredArgsConstructor;
import me.jko.kafka.kafka.MyProducer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProducerController {

  private final MyProducer myProducer;

  @GetMapping("/produce")
  public ResponseEntity produce() {
    myProducer.produce();

    return ResponseEntity
        .ok()
        .build();
  }
}
