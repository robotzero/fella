package com.queen.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {
  @GetMapping("/test")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Product> test() {
    return getProduct();
  }

  private Mono<Product> getProduct() {
    return Mono.fromSupplier(() -> {
      final var product = new Product();
      product.setId(14);
      return product;
    });
  }
}
