package com.rl.train.member.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


// Generated by https://start.springboot.io
// 优质的 spring/boot/data/security/cloud 框架中文文档尽在 => https://springdoc.cn

@SpringBootApplication
@ComponentScan("com.rl")
@MapperScan("com.rl.train.member.mapper")
public class MemberApplication {
    private final static Logger logger = LoggerFactory.getLogger(MemberApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
        logger.info("启动成功");
    }
    
}
