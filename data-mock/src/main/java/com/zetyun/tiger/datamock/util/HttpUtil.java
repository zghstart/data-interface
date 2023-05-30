package com.zetyun.tiger.datamock.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    private static final OkHttpClient client = new OkHttpClient();
    private final String logUrl;

    public HttpUtil(String url) {
        this.logUrl = url;
    }

    public void get(String json, String name) throws UnsupportedEncodingException {
        String encodeJson = URLEncoder.encode(json, String.valueOf(StandardCharsets.UTF_8));
        String url = this.logUrl + "?" + name + "=" + encodeJson;
        Request request = (new Request.Builder()).url(url).get().build();
        Call call = client.newCall(request);
        long start = System.currentTimeMillis();

        try (Response response = call.execute()) {
            long end = System.currentTimeMillis();
            System.out.println(response.body().string() + " used:" + (end - start) + " ms");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("发送失败...检查网络地址...");
        }
    }

    public void post(String json) {
        System.out.println(json);
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), json);
        Request request = (new Request.Builder()).url(this.logUrl).post(requestBody).build();
        Call call = client.newCall(request);
        long start = System.currentTimeMillis();

        try (Response response = call.execute()) {
            long end = System.currentTimeMillis();
            System.out.println(response.body().string() + " used:" + (end - start) + " ms");
        } catch (IOException var10) {
            var10.printStackTrace();
            throw new RuntimeException("发送失败...检查网络地址...");
        }
    }

}
