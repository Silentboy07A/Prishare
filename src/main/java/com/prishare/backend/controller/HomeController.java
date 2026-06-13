package com.prishare.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "PriShare Running";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello PriShare";
    }
}