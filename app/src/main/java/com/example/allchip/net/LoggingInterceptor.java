package com.example.allchip.net;


import com.example.allchip.utils.LogUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * description: 打印request和response的拦截器
 * created by crx on 2019/1/8 14:27
 */
public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        //request
        Request request = chain.request();
        Buffer buffer = new Buffer();
        if(request.body() != null){
            request.body().writeTo(buffer);
        }

        //response
        long t1 = System.nanoTime();
        Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();

        String contentType = "";
        if (request.body() != null) {
            MediaType mediaType = request.body().contentType();
            if (mediaType != null) {
                contentType = mediaType.toString();
            }
        }
        //log out request/response together
        logInfo(String.format("OKHTTP SEND REQUEST to %s \non %s%n%sContent-Type:%s\n\n%s",
                request.url(), chain.connection(), request.headers(), contentType,buffer.readString(Charset.forName("utf-8"))));

        if(response.body() != null){
            logInfo(String.format(Locale.CHINA, "OKHTTP RECEIVE RESPONSE from %s in %.1fms%n%s\n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers(), readResponseBody(response.body())));
        }

        return response;
    }

    private String readResponseBody(ResponseBody body){
        try {
            BufferedSource source = body.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            Charset charset = Charset.forName("utf-8");
            return buffer.clone().readString(charset);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    private void logInfo(String str){
        LogUtils.d("OkHttp", str);
    }
}
