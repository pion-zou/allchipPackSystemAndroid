package com.example.allchip.pack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.allchip.data.LiveStatusEvent
import com.example.allchip.data.LoadData
import com.example.allchip.data.LoadStatus
import com.example.allchip.data.StatusMutableLiveData
import com.example.allchip.data.model.Contract
import com.example.allchip.data.model.Good
import com.example.allchip.net.TModel
import com.example.allchip.viewmodel.BaseViewModel
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PackViewModel(private val repository: PackRepository) : BaseViewModel() {
    private val _uploadPackageLiveDate = MutableLiveData<TModel<Any>>()
    var uploadPackageLiveDate:MutableLiveData<TModel<Any>> = _uploadPackageLiveDate

    fun uploadPackage(list: MutableList<Good>){
        repository.uploadPackage(list)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(this)
            .subscribe({
                _uploadPackageLiveDate.value = it
            },{
                _uploadPackageLiveDate.value = TModel.error(it.message)
            })
    }
}