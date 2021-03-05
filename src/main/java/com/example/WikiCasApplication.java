package com.example;

import net.unicon.cas.client.configuration.EnableCasClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@ComponentScan("com.example.controller")
//#启动CAS @EnableCasClient
@EnableCasClient
public class WikiCasApplication {

    public static void main(String[] args) {
        SpringApplication.run(WikiCasApplication.class, args);
    }


}
