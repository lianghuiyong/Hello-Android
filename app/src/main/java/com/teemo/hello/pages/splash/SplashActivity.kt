package com.teemo.hello.pages.splash

import android.Manifest
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.teemo.hello.R
import com.teemo.common.base.BaseActivity
import com.teemo.hello.pages.main.MainActivity
import com.teemo.hello.utils.InjectorUtils
import com.teemo.hello.viewmodels.splash.SplashViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.cancel
import permissions.dispatcher.*

/**
 * @author: XieGuangwei
 * @description: 启动页
 * @date: 2019/07/06
 */

@RuntimePermissions
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
            } else if (it == 0) {
                showWRWithPermissionCheck()
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated function
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showWR() {
        skip()
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