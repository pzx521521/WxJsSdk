package com.para.wxjssdk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "wechat")
@PropertySource("application.yml")
@Data
public class WxConfig {
    private String appId;
    private String appSecret;
    private String domin;
    private String savePath;
}
