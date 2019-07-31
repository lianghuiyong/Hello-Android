package com.xgw.androidkotlindemo.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

/**
 * @author: XieGuangwei
 * @description: activity抽象基类
 * @date: 2019/07/06
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
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

    protected fun showSnackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }
}