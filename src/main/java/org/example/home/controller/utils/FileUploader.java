package org.example.home.controller.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.File;

@Configuration
@PropertySource("classpath:FileUploader.properties")
public class FileUploader {

    @Value("${fileuploader.url}")
    private String url;

    @Value("${fileuploader.host}")
    private String host;

    @Value("${fileuploader.username}")
    private String username;

    @Value("${fileuploader.password}")
    private String password;

    @Value("${fileuploader.uploadUrl}")
    private String uploadUrl;
    @Value("${fileuploader.strategyId}")
    private int strategyId;

    static {
        // 在类加载时进行一次性配置
        Unirest.config().socketTimeout(0).connectTimeout(0);
    }

    public String getToken() {
        try {
            // 构建并发送请求
            HttpResponse<String> response = Unirest.post(url)
                    .header("Accept", "application/json")
                    .header("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                    .header("Host", host)
                    .header("Connection", "keep-alive")
                    .multiPartContent()
                    .field("email", username)
                    .field("password", password)
                    .asString();

            // 解析JSON响应
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            boolean status = rootNode.path("status").asBoolean();
            if (status) {
                String token = rootNode.path("data").path("token").asText();
                return token;
            } else {
                throw new RuntimeException("Failed to get token");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public UrlAndBollean uploadImage(String filepath) {
        String token = getToken();
        if (token == null) {
            System.err.println("Failed to get token");
            return new UrlAndBollean(null, false);
        }

        try {
            // 构建并发送文件上传请求
            HttpResponse<String> response = Unirest.post(uploadUrl)
                    .header("Authorization", "Bearer " + token)
                    .header("Accept", "application/json")
                    .header("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                    .header("Host", host)
                    .header("Connection", "keep-alive")
                    .field("file", new File(filepath), "application/octet-stream")
                    .field("strategy_id", String.valueOf(strategyId))
                    .asString();

            // 处理服务器响应
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            boolean status = rootNode.path("status").asBoolean();
            if (status) {
                JsonNode dataNode = rootNode.path("data");
                String key = dataNode.path("key").asText();
                String name = dataNode.path("name").asText();
                String pathname = dataNode.path("pathname").asText();
                String originName = dataNode.path("origin_name").asText();
                float size = dataNode.path("size").floatValue();
                String mimetype = dataNode.path("mimetype").asText();
                String extension = dataNode.path("extension").asText();
                String md5 = dataNode.path("md5").asText();
                String sha1 = dataNode.path("sha1").asText();
                String url = dataNode.path("links").path("url").asText();

                // 打印上传结果
                System.out.println("Upload successful.");
                System.out.println("Key: " + key);
                System.out.println("Name: " + name);
                System.out.println("Pathname: " + pathname);
                System.out.println("Origin Name: " + originName);
                System.out.println("Size: " + size + " KB");
                System.out.println("Mimetype: " + mimetype);
                System.out.println("Extension: " + extension);
                System.out.println("MD5: " + md5);
                System.out.println("SHA1: " + sha1);
                System.out.println("URL: " + url);
                clearToken();
                return new UrlAndBollean(url, true);
            } else {
                System.err.println("Upload failed. Response: " + response.getBody());
                return new UrlAndBollean(null, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new UrlAndBollean(null, false);
        }
    }

    private boolean clearToken() {
        try {
            HttpResponse<String> response = Unirest.delete(url)
                    .header("Accept", "application/json")
                    .header("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                    .header("Host", host)
                    .header("Connection", "keep-alive")
                    .asString();

            // 解析响应
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            boolean status = rootNode.path("status").asBoolean();

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
