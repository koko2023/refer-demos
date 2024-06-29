package com.bear.hospital;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@MapperScan("com.bear.hospital.mapper")
@SpringBootApplication
@EnableConfigurationProperties
@EnableWebMvc
public class HospitalManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalManagerApplication.class, args);
    }

}
