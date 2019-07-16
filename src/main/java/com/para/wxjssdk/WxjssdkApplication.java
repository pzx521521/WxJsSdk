package com.para.wxjssdk;

import com.para.wxjssdk.config.WxConfig;
import com.para.wxjssdk.util.TokenThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WxjssdkApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxjssdkApplication.class, args);
    }

}
