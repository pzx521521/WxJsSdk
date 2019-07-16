package com.para.wxjssdk.controller;


import com.alibaba.fastjson.JSON;
import com.para.wxjssdk.config.WxConfig;
import com.para.wxjssdk.util.Sign;
import com.para.wxjssdk.util.TokenThread;
import com.para.wxjssdk.util.WxDownLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/wechat")
public class AccessTokenController {
    @Autowired
    private WxConfig wxConfig;
    @RequestMapping("/uploadPic")
    public Map<String, String> uploadPic(String imgid){
        Map<String, String> ret = new HashMap<String, String>();
        String photoName = new Date().getTime() + UUID.randomUUID().toString() +  ".jpg";

        try {
            WxDownLoad.saveImageToDisk(imgid, photoName, wxConfig.getSavePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ret.put("success", imgid);
        return ret;
    }
    @RequestMapping("/getConfig")
    public Map<String, String> getConfig(@RequestParam(required = false) String url){
        if(TokenThread.appId == null || TokenThread.appId == "" ){
            //获取servlet初始参数appid和appsecret
            TokenThread.appId = wxConfig.getAppId();
            TokenThread.appSecret = wxConfig.getAppSecret();
            System.out.println("appid:"+TokenThread.appId);
            System.out.println("appSecret:"+TokenThread.appSecret);
            new Thread(new TokenThread()).start(); //启动进程
        }
        String jsapi_tickec = TokenThread.jsapiTicket.getJsapiTicket();
        Map<String, String> jssdk = Sign.main(jsapi_tickec,url);
        return jssdk;
    }
}
