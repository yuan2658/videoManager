package com.yuan.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.wf.jwtp.configuration.EnableJwtPermission;

/**
 * @author yuanyuan
 */
@EnableJwtPermission
@SpringBootApplication
public class EasyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyWebApplication.class, args);
    }
}
