package com.swjd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.swjd.modules.*.dao")
public class MkBootLayuiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MkBootLayuiApplication.class, args);
    }

}
