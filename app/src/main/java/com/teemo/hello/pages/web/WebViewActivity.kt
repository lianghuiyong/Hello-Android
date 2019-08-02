package com.teemo.hello.pages.web

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.teemo.common.base.BaseActivity
import com.teemo.hello.R
import com.tencent.smtt.export.external.interfaces.WebResourceError
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*

/**
 *  @author: XieGuangwei
 *  @description: webView页面
 *  @date: 2019/7/31 11:38
 */
class WebViewActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_web_view

    override fun initView() {
        QMUIStatusBarHelper.setStatusBarLightMode(this)
        tool_bar.addOnBackListener { onBackPressed() }
        webView.loadUrl(intent.getStringExtra("url"))
    }

    override fun initData() {
        val mChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(p0: WebView?, newProgress: Int) {
                // 修改进度条
                progress_bar.upProgress(newProgress)
            }

            //获取页面标题
            override fun onReceivedTitle(p0: WebView?, title: String?) {
                super.onReceivedTitle(p0, title)
                tool_bar.title = title
            }
        }

        val mWebViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                //进度条
                progress_bar.upProgress(30)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                //进度条
                progress_bar.upProgress(100)
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

        webView.apply {
            webViewClient = mWebViewClient
            webChromeClient = mChromeClient
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