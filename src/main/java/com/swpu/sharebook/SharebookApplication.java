package com.swpu.sharebook;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SharebookApplication {
    public static void main(String[] args) {
        SpringApplication.run(SharebookApplication.class, args);
    }
}
