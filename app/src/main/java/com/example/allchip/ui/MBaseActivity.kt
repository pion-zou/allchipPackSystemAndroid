package com.example.allchip.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders


abstract class MBaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    protected var mBinding: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutResId(savedInstanceState))
        initData()
        initViewModel()
        initViewDataBinding()
        initView()
        bindListener()
        initViewObservable()

    }


    override fun setContentView(view: View?) {

    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun getLayoutResId(savedInstanceState: Bundle?): Int

    override fun onDestroy() {
        super.onDestroy()
        mBinding?.unbind()
    }

    /**
     * 初始化
     */
    open fun initData() {
    }


    /**
     * 初始化view
     */
    open fun initView() {
    }

    /**
     * 绑定事件
     */
    open fun bindListener() {

    }

    /**
     * View注入绑定
     */
    open fun initViewDataBinding() {
    }

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    open fun initViewModel() {
    }


    /**
     *初始化ViewObservable
     */
    open fun initViewObservable() {

    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    fun <T : ViewModel> createViewModel(cls: Class<T>): T {
        return ViewModelProviders.of(this).get(cls)
    }


}