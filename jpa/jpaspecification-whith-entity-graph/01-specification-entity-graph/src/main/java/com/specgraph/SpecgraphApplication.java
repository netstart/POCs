package com.specgraph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.specgraph")
public class SpecgraphApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpecgraphApplication.class, args);
    }

}
