package com.example.controller;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class CasTest1 {

    @RequestMapping("/test1")
    public String test1(){
        return "test1....";
    }
}