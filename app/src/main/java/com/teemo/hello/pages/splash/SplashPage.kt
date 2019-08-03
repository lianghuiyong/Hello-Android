package com.teemo.hello.pages.splash

import android.Manifest
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.teemo.common.base.BaseActivity
import com.teemo.hello.R
import com.teemo.hello.pages.main.MainPage
import com.teemo.hello.utils.InjectorUtils
import com.teemo.hello.viewmodels.splash.SplashViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.cancel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions

/**
 * @author: XieGuangwei
 * @description: 启动页
 * @date: 2019/07/06
 */

@RuntimePermissions
class SplashPage : BaseActivity() {
    private val delayViewModel by viewModels<SplashViewModel> {
        InjectorUtils.provideSplashViewModelFactory()
    }

    override fun canDragBack(): Boolean {
        return false
    }

    override fun getLayoutId() = R.layout.activity_splash

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showWR() {
        skip()
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onWRDenied() {
        skip()
    }

    override fun initData() {
        skip_btn.setOnClickListener {
            skip()
        }
        delayViewModel.delayTask.observe(this, Observer {
            skip_btn.text = "跳过$it"
            if (it == 3) {
                skip_btn.visibility = View.VISIBLE
            } else if (it == 0) {
                showWRWithPermissionCheck()
            }
        })

        startAnim()
//        home_view_pager.apply {
//            val mAdapter = SplashAdapter()
//            val list = ArrayList<Int>()
//            list.add(R.drawable.app_splash_1)
//            list.add(R.drawable.app_splash_2)
//            list.add(R.drawable.app_splash_3)
//            mAdapter.setNewData(list)
//            adapter = mAdapter
//            offscreenPageLimit = 1
//
//            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//                override fun onPageSelected(position: Int) {
//                    if (position == list.size - 1) {
//                        startAnim()
//                    }
//                }
//            })
//        }
//
//        val viewAnim = ObjectAnimator.ofFloat(home_view_pager, "alpha", 0.3f, 1f)
//        viewAnim.startDelay = 300
//        viewAnim.start()
    }

    private fun startAnim() {
        bottom_layout.postDelayed({
            delayViewModel.startDelay()

            val viewAnim = ObjectAnimator.ofFloat(bottom_layout, "translationY", bottom_layout.height.toFloat(), 0f)
            viewAnim.interpolator = DecelerateInterpolator()
            viewAnim.start()
        },200)
    }

    private fun skip() {
        delayViewModel.viewModelScope.cancel()
        nextActivity(MainPage::class.java)
        finish()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.fade_out)
    }
}