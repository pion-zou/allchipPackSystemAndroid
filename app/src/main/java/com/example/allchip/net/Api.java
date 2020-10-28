package com.example.allchip.net;

import com.example.allchip.data.model.Contract;
import com.example.allchip.data.model.LoggedInUser;
import com.sun.xml.bind.v2.schemagen.xmlschema.Any;

import org.checkerframework.framework.qual.PostconditionAnnotation;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by lijing on 2017/7/19.
 * IGO后台API定义
 */

public interface Api {

    /****************************无需登录可以调用的接口 - START******************************************/


    //登录
    @POST("/login")
    Observable<TModel<LoggedInUser>> login(@Body RequestBody body);

    //请求合同列表
    @GET("/getContractList")
    Observable<TModel<List<Contract>>> getContractList();

    @POST("/getContractDetail")
    Observable<TModel<Contract>> getContractDetail(@Body RequestBody body);

    @POST("/updatePackageGood")
    Observable<TModel> updatePackageGood(@Body RequestBody body);

    /****************************无需登录可以调用的接口 - END******************************************/

}