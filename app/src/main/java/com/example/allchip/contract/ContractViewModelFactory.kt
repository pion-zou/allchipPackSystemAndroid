package com.example.allchip.contract

import android.widget.ViewSwitcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ContractViewModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ContractViewModel::class.java)){
            return ContractViewModel(ContractRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}