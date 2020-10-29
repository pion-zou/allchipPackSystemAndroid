package com.example.allchip.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.allchip.R
import com.example.allchip.data.event.NavigateUpEvent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private var navigateUpAble = true
    private var backDialog: Dialog? = null

    private val BACK_KEY_DOWN_TIMEOUT = 2L * 1000 // 两次连续按下返回键的最大时间间隔
    private var mBackKeyDownTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //EventBus需在onParseIntent方法之后(解决AF回调太快优先弹窗的问题)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!navigateUpAble) {
            if (backDialog == null) {
                backDialog =
                    AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher).setTitle("新的货物")
                        .setMessage("你还有扫描的结果没有提交，是否确定退出").setPositiveButton("确定",
                            DialogInterface.OnClickListener { dialogInterface, _ ->
                                navigateUpAble = true
                                Toast.makeText(this@MainActivity, "再次点击退出", Toast.LENGTH_LONG)
                                    .show()
                                dialogInterface.dismiss()
                            }).setNegativeButton("取消",
                            DialogInterface.OnClickListener { dialogInterface, _ ->
                                dialogInterface.dismiss()
                            }).create()
            }
            backDialog?.show()
            return false
        }
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> startActivity(
                Intent(
                    MainActivity@ this,
                    SettingsActivity::class.java
                )
            )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onNavigateUpEvent(event: NavigateUpEvent) {
        this.navigateUpAble = event.navigateUpAble
    }

    @SuppressLint("ShowToast")
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            if (findNavController(R.id.nav_host_fragment).currentBackStackEntry != null) {
                return onSupportNavigateUp()
            }
            return if (System.currentTimeMillis() - mBackKeyDownTime < BACK_KEY_DOWN_TIMEOUT) {
                super.onKeyDown(keyCode, event)
            } else {
                Toast.makeText(this@MainActivity, "再次点击退出", Toast.LENGTH_SHORT).show()
                mBackKeyDownTime = System.currentTimeMillis()
                false
            }
        }
        return super.onKeyDown(keyCode, event)

    }
}