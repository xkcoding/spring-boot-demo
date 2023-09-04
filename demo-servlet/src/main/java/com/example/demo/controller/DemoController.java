package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/controller")
public class DemoController {

  @GetMapping("/demo1")
  public String demo1() {
    return "demo1";
  }

}
