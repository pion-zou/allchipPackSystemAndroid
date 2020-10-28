package com.example.allchip.pack

import com.example.allchip.data.model.Contract
import com.example.allchip.data.model.Good
import com.example.allchip.net.ApiRetrofit
import com.example.allchip.net.TModel
import io.reactivex.Observable
import retrofit2.Retrofit

class PackRepository {
    fun uploadPackage(list:MutableList<Good>) :Observable<TModel<Any>>{
        return ApiRetrofit.getInstance().updatePackageGood(list)
    }
}