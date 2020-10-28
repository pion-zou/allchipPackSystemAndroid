package com.example.allchip.contract

import com.example.allchip.data.model.Contract
import com.example.allchip.net.ApiRetrofit
import com.example.allchip.net.TModel
import io.reactivex.Observable

class ContractRepository {
    fun getContractList(): Observable<TModel<MutableList<Contract>>> = ApiRetrofit.getInstance().getContractList()

}