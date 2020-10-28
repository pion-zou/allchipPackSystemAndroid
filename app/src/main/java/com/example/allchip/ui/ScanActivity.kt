package com.example.allchip.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.allchip.R
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DecoratedBarcodeView.TorchListener


class ScanActivity : AppCompatActivity() {
    private var capture: CaptureManager? = null
    private var buttonLed: ImageButton? = null
    private var barcodeScannerView: DecoratedBarcodeView? = null
    private var bTorch = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        barcodeScannerView = initializeContent()
        buttonLed = findViewById(R.id.button_led)
        /*根据闪光灯状态设置imagebutton*/barcodeScannerView!!.setTorchListener(object : TorchListener {
            override fun onTorchOn() {
                buttonLed?.background = resources.getDrawable(R.drawable.image_bg_on)
                bTorch = true
            }

            override fun onTorchOff() {
                buttonLed?.background = resources.getDrawable(R.drawable.image_bg_off)
                bTorch = false
            }
        })

        /*开关闪光灯*/buttonLed?.setOnClickListener {
            if (bTorch) {
                barcodeScannerView!!.setTorchOff()
            } else {
                barcodeScannerView!!.setTorchOn()
            }
        }
        capture = CaptureManager(this, barcodeScannerView)
        capture!!.initializeFromIntent(intent, savedInstanceState)
        capture!!.decode()
    }

    /**
     * Override to use a different layout.
     *
     * @return the DecoratedBarcodeView
     */
    protected fun initializeContent(): DecoratedBarcodeView {
        setContentView(R.layout.activity_scan)
        return findViewById<View>(R.id.dbv) as DecoratedBarcodeView
    }

    override fun onResume() {
        super.onResume()
        capture!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture!!.onPause()
        barcodeScannerView!!.setTorchOff()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture!!.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture!!.onSaveInstanceState(outState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        capture!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return barcodeScannerView!!.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }
}