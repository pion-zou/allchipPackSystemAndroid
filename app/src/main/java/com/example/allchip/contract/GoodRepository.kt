package com.example.allchip.contract

import com.example.allchip.data.model.Contract
import com.example.allchip.net.ApiRetrofit
import com.example.allchip.net.TModel
import io.reactivex.Observable

class GoodRepository {
    fun getContractDetail(id:Int): Observable<TModel<Contract>> = ApiRetrofit.getInstance().getContractDetail(id)
}