package com.fastcampus.netplix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NetplixBatchApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(NetplixBatchApplication.class);
        SpringApplication.exit(run);
    }
}
