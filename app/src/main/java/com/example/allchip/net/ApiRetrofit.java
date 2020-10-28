package com.example.allchip.net;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.allchip.BuildConfig;
import com.example.allchip.data.model.Contract;
import com.example.allchip.data.model.Good;
import com.example.allchip.data.model.LoggedInUser;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * Created by lijing on 2017/7/19.
 *
 * @Description 使用Retrofit对网络请求进行配置
 */

public class ApiRetrofit {

    private volatile static ApiRetrofit mInstance;
    private Api api;
    private SharedPreferences preferences;

    private ApiRetrofit() {
        initRetrofit();
    }

    public static synchronized ApiRetrofit getInstance() {
        if (mInstance == null) {
            synchronized (ApiRetrofit.class) {
                if (mInstance == null) {
                    mInstance = new ApiRetrofit();
                }
            }
        }
        return mInstance;
    }

    private void initRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //添加拦截器，拦截器的添加顺序会影响运行结果
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new LoggingInterceptor());
        }

        //Cookie信息持久化
//        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(Utils.getContext()));

        builder.connectTimeout(Constants.CONNECT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(Constants.CONNECT_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(Constants.CONNECT_TIME_OUT, TimeUnit.SECONDS);
//        builder.cookieJar(cookieJar);
        builder.retryOnConnectionFailure(true);

        OkHttpClient client = builder.build();

        Gson gson = new GsonBuilder().setLenient().create();

        api = new Retrofit.Builder()
                .baseUrl(HostManager.INSTANCE.getHost())
                .addConverterFactory(GsonConverterFactory.create(gson)) //GSON Converter
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //支持RxJava2
                .client(client)
                .build()
                .create(Api.class);
    }

    private RequestBody getRequestBody(Object obj, String contentType) {
        String route = new Gson().toJson(obj);
        return RequestBody.create(okhttp3.MediaType.parse(contentType), route);
    }

    private RequestBody getTextPlainBody(Object obj) {
        return getRequestBody(obj, "text/plain; charset=UTF-8");
    }

    private RequestBody getRequestBody(Object obj) {
        return getRequestBody(obj, "application/json; charset=utf-8");
    }


    //登录
    public Observable<TModel<LoggedInUser>> login(String name, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("password", password);
        return api.login(getRequestBody(map));
    }

    //合同列表
    public Observable<TModel<List<Contract>>> getContractList() {
        return api.getContractList();
    }

    //合同详情
    public Observable<TModel<Contract>> getContractDetail(int id){
        Map<String, String> map = new HashMap<>();
        map.put("id", ""+id);
        return api.getContractDetail(getRequestBody(map));
    }

    public Observable<TModel> updatePackageGood(List<Good> goods){
        return api.updatePackageGood(getRequestBody(goods));
    }

}
