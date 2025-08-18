package com.zagdev.devicemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DeviceManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeviceManagerApplication.class, args);
    }

}
