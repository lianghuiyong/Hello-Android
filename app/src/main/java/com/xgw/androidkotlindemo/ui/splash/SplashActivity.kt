package com.xgw.androidkotlindemo.ui.splash

import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.xgw.androidkotlindemo.R
import com.xgw.androidkotlindemo.base.BaseActivity
import com.xgw.androidkotlindemo.ui.main.MainActivity
import com.xgw.androidkotlindemo.utils.InjectorUtils
import com.xgw.androidkotlindemo.viewmodels.splash.SplashViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.cancel

/**
 * @author: XieGuangwei
 * @description: 启动页
 * @date: 2019/07/06
 */
class SplashActivity : BaseActivity() {
    private val TAG = "SplashActivity"
    private val delayViewModel by viewModels<SplashViewModel> {
        InjectorUtils.provideSplashViewModelFactory()
    }

    override fun getLayoutId() = R.layout.activity_splash

    override fun initView() {
        skip_btn.setOnClickListener {
            skip()
        }
        delayViewModel.delayTask.observe(this, Observer {
            Log.e(TAG, "执行操作：$it")
            skip_btn.text = "点我跳过${it}秒"
            if (it == 3) {
                with(skip_btn) {
                    visibility = View.VISIBLE
                }
            } else if (it == 1) {
                skip()
            }
        })
    }

    override fun initData() {
        delayViewModel.startDelay()
    }

    private fun skip() {
        delayViewModel.viewModelScope.cancel()
        nextActivity(MainActivity::class.java)
        finish()
    }
}