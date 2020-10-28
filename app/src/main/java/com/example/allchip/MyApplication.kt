package com.example.allchip

import android.app.Application
import androidx.multidex.MultiDex
import com.example.allchip.net.HostManager

/**
 * @Description TODO
 * @Author zouzhihao
 * @Date 2020/10/21 2:59 PM
 * @Version 1.0
 */

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        var preferences = getSharedPreferences(
            BuildConfig.APPLICATION_ID + "_preferences",
            0)
        HostManager.host= "http://" + preferences.getString("ip", "") + ":" + preferences.getString("port", "")
    }

}