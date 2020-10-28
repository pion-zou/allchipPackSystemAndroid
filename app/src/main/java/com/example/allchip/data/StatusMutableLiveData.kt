package com.example.allchip.data

import androidx.lifecycle.MutableLiveData

class StatusMutableLiveData<T> : MutableLiveData<LiveStatusEvent<T>>()
