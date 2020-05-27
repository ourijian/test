package com.ourijian.startschool.util;

import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp网络请求工具类，提供Json,Param方式请求
 */
public class OkHttpUtil {

    private static OkHttpClient client = new OkHttpClient();

    private OkHttpUtil(){

    }

    /**
     * 同步get请求
     * @param url 请求的地址
     * @return 请求返回一个字符串
     * @throws IOException
     */
    public static String get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String result = response.body().string();
        return result;
    }

    /**
     * 同步的post请求 Json数据提交
     * @param url 请求的地址
     * @param object 请求的json参数
     * @return 返回的字符串
     * @throws IOException
     */
    public static String postForJson(String url, JsonObject object) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),object.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        return response.body().string();
    }

    /**
     * 同步的post请求 表单/params数据提交
     * @param url 请求的地址
     * @param body 请求的 表单/params参数
     * @return 返回请求结果
     * @throws IOException
     */
    public static String postForParams(String url, FormBody body) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        return response.body().string();
    }

}
