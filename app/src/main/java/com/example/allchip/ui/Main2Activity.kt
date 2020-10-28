package com.example.allchip.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.allchip.R
import com.google.zxing.integration.android.IntentIntegrator


class Main2Activity : AppCompatActivity(),View.OnClickListener {
    private var testResult:TextView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        requsetPermission()
        testResult = findViewById(R.id.text_result)
        findViewById<Button>(R.id.button1).setOnClickListener(this)
    }

    private fun requsetPermission() {
        if (Build.VERSION.SDK_INT > 22) {
            if (ContextCompat.checkSelfPermission(
                    this@Main2Activity,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                //先判断有没有权限 ，没有就在这里进行权限的申请
                ActivityCompat.requestPermissions(
                    this@Main2Activity,
                    arrayOf(Manifest.permission.CAMERA),
                    1
                )
            } else {
            }
        } else {
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //这里已经获取到了摄像头的权限，想干嘛干嘛了可以
            } else {
                //这里是拒绝给APP摄像头权限，给个提示什么的说明一下都可以。
                Toast.makeText(this@Main2Activity, "请手动打开相机权限", Toast.LENGTH_SHORT).show()
            }
            else -> {
            }
        }
    }

    override fun onClick(v: View) {
        when (v.getId()) {
            R.id.button1 -> {
                /*以下是启动我们自定义的扫描活动*/
                val intentIntegrator = IntentIntegrator(this@Main2Activity)
                intentIntegrator.setBeepEnabled(true)
                /*设置启动我们自定义的扫描活动，若不设置，将启动默认活动*/intentIntegrator.captureActivity =
                    ScanActivity::class.java
                intentIntegrator.initiateScan()
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
    ) {
        val result =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                testResult?.text = result.contents
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}