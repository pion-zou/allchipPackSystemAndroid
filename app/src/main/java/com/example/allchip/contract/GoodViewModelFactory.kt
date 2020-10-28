package com.example.allchip.contract

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GoodViewModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GoodViewModel::class.java)){
            return GoodViewModel(GoodRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}