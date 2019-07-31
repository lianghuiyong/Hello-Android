package com.xgw.androidkotlindemo.ui.web

import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
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
        val webSettings = webView.settings
        //支持缩放，默认为true。
        webSettings.setSupportZoom(false)
        //调整图片至适合webview的大小
        webSettings.useWideViewPort = true
        // 缩放至屏幕的大小
        webSettings.loadWithOverviewMode = true
        //设置默认编码
        webSettings.defaultTextEncodingName = "utf-8"
        //设置自动加载图片
        webSettings.loadsImagesAutomatically = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                Log.e("xgw", view?.url)
                return false
            }
        }
        webView.loadUrl(intent.getStringExtra("url"))
    }

    override fun initData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}