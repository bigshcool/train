package com.rl.train.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.rl")
public class GatewayApplication {
    private final static Logger logger = LoggerFactory.getLogger(GatewayApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        logger.info("启动成功");
    }
}
