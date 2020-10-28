package com.example.allchip.contract

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.allchip.data.LiveStatusEvent
import com.example.allchip.data.LoadData
import com.example.allchip.data.LoadStatus
import com.example.allchip.data.StatusMutableLiveData
import com.example.allchip.data.model.Contract
import com.example.allchip.viewmodel.BaseViewModel
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GoodViewModel(private val repository: GoodRepository) : BaseViewModel() {
    private val _contractDetail = StatusMutableLiveData<LoadData<Contract>>()
    var contractDetail:StatusMutableLiveData<LoadData<Contract>> = _contractDetail

    fun getContractDetail(@LoadStatus state:Int , id:Int){
        repository.getContractDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(this)
            .subscribe({
                _contractDetail.value = LiveStatusEvent.success(LoadData.success(state , it.data))
            },{
                _contractDetail.value = LiveStatusEvent.error(it.message)
            })
    }
}