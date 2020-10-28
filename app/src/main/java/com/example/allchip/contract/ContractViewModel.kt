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

class ContractViewModel(private val repository: ContractRepository) : BaseViewModel() {
    private val _contractList = StatusMutableLiveData<LoadData<MutableList<Contract>>>()
    var contractList:StatusMutableLiveData<LoadData<MutableList<Contract>>> = _contractList

    fun getContractList(@LoadStatus state:Int){
        repository.getContractList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(this)
            .subscribe({
                _contractList.value = LiveStatusEvent.success(LoadData.success(state , it.data))
            },{
                _contractList.value = LiveStatusEvent.error(it.message)
            })
    }
}