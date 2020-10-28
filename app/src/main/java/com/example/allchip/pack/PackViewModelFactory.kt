package com.example.allchip.pack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PackViewModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PackViewModel::class.java)){
            return PackViewModel(PackRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}