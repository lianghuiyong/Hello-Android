package com.xgw.androidkotlindemo.ui.web

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.TargetApi
import android.graphics.Bitmap
import android.os.Build
import android.os.Handler
import android.os.Message
import android.view.View
import androidx.annotation.RequiresApi
import com.tencent.smtt.export.external.interfaces.WebResourceError
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.xgw.androidkotlindemo.R
import com.xgw.androidkotlindemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.toolbar_layout.*

/**
 *  @author: XieGuangwei
 *  @description: webView页面
 *  @date: 2019/7/31 11:38
 */
class WebViewActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_web_view

    override fun initView() {
        setSupportActionBar(toolbar)
        //toolbar
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        toolbar.title = intent.getStringExtra("title")
        webView.loadUrl(intent.getStringExtra("url"))
    }


    private var mProgressHandler: ProgressHandler? = null
    private fun sendProgressMessage(progressType: Int, newProgress: Int, duration: Int) {
        val msg = Message()
        msg.what = progressType
        msg.arg1 = newProgress
        msg.arg2 = duration
        when (progressType) {
            PROGRESS_PROCESS ->
                mProgressHandler?.sendMessage(msg)

            PROGRESS_GONE ->
                mProgressHandler?.sendMessageDelayed(msg, 1000)
        }
    }

    private val PROGRESS_PROCESS = 0
    private val PROGRESS_GONE = 1
    private inner class ProgressHandler : Handler() {

        var mDstProgressIndex: Int = 0
        private var mDuration: Int = 0
        private var mAnimator: ObjectAnimator? = null


        override fun handleMessage(msg: Message) {
            when (msg.what) {
                PROGRESS_PROCESS -> {
                    mDstProgressIndex = msg.arg1
                    mDuration = msg.arg2
                    progress_bar?.visibility = View.VISIBLE
                    if (mAnimator != null && mAnimator!!.isRunning) {
                        mAnimator!!.cancel()
                    }
                    mAnimator = ObjectAnimator.ofInt(progress_bar, "progress", mDstProgressIndex)
                    mAnimator?.duration = mDuration.toLong()
                    mAnimator?.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            if (progress_bar.progress == 100) {
                                sendEmptyMessageDelayed(PROGRESS_GONE, 500)
                            }
                        }
                    })
                    mAnimator?.start()
                }
                PROGRESS_GONE -> {
                    mDstProgressIndex = 0
                    mDuration = 0
                    progress_bar?.progress = 0
                    progress_bar.visibility = View.GONE
                    if (mAnimator != null && mAnimator!!.isRunning) {
                        mAnimator!!.cancel()
                    }
                    mAnimator = ObjectAnimator.ofInt(progress_bar, "progress", 0)
                    mAnimator?.duration = 0
                    mAnimator?.removeAllListeners()
                }
                else -> {
                }
            }
        }
    }
    override fun initData() {
        val webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                //进度条
                if (mProgressHandler?.mDstProgressIndex == 0) {
                    sendProgressMessage(PROGRESS_PROCESS, 30, 500)
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                //进度条
                sendProgressMessage(PROGRESS_GONE, 100, 0)
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return webView.shouldOverrideUrlLoading(view, request?.url.toString())
            }

            //防止加载网页时调起系统浏览器
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return webView.shouldOverrideUrlLoading(view, url)
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
//                toolbar.titleContent = "无网络连接"
//                empty_layout.showNetWorkError("没有网络连接", View.OnClickListener { view?.reload() })
            }

            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
//                toolbar.titleContent = "无网络连接"
//                empty_layout.showNetWorkError("没有网络连接", View.OnClickListener { view?.reload() })
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onBackPressed() {
        webView.onBackPressed(this)
    }

    override fun onDestroy() {
        webView.onDestroy()
        super.onDestroy()
    }
}