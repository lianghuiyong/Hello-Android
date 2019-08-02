package com.teemo.common.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.snackbar.Snackbar
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.teemo.hello.R

/**
 * @author: XieGuangwei
 * @description: activity抽象基类
 * @date: 2019/07/06
 */
abstract class BaseActivity : AppCompatActivity() {
    init {
        //兼容svg
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootView = layoutInflater.inflate(R.layout.common_base_activity, null)
        (rootView?.findViewById<View>(R.id.module_common_content_layout) as FrameLayout).run {
            setContentView(rootView)
            addView(layoutInflater.inflate(getLayoutId(), null))
        }

        //沉浸式
        QMUIStatusBarHelper.translucent(this)
        QMUIStatusBarHelper.setStatusBarDarkMode(this)

        initView()
        initData()
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun initView()
    protected abstract fun initData()
    protected fun nextActivity(
        clazz: Class<*>,
        bundle: Bundle? = null,
        forResultRequestCode: Int? = null
    ) {
        val intent = Intent()
        intent.setClass(this, clazz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        if (forResultRequestCode != null) {
            startActivityForResult(intent, forResultRequestCode)
        } else {
            startActivity(intent)
        }
    }

    //登录回调
    fun onLogin(){

    }

    //登出回调
    fun onLoginOut(){

    }

    protected fun showSnackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }
}