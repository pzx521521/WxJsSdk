package com.para.wxjssdk.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class WxDownLoad {
    /**
     * 根据文件id下载文件
     * @param accessToken
     * @param mediaId
     * @return 文件流对象
     */
    private static InputStream getInputStream(String accessToken, String mediaId) {
        InputStream is = null;
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+ accessToken + "&media_id=" + mediaId;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            // 获取文件转化为byte流
            is = http.getInputStream();
        } catch (Exception e) {
            log.info("Failed to convert inputStream from weixin server,accessToken:{},mediaId:{}",accessToken,mediaId);
        }
        return is;

    }

    public static void saveImageToDisk(String mediaId, String picName, String picPath) throws Exception {
        String accessToken = TokenThread.accessToken.getAccessToken();
        InputStream inputStream = getInputStream(accessToken, mediaId);
        // 循环取出流中的数据
        byte[] data = new byte[1024];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(picPath+picName);
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
            log.info("Write the fileInputStream is successful");
        } catch (IOException e) {
            log.error("Write the fileInputStream is error");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("Close the fileInputStream is error");
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error("Close the fileOutputStream is error");
                }
            }
        }
    }
}
