package com.xgw.common.widget

import android.app.Activity
import android.util.Log
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.AttrRes
import android.util.AttributeSet
import com.tencent.smtt.sdk.CookieManager
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView

class CommonWebView : WebView {
    constructor(context: Context) : super(context) {
        initSetting()
    }
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        initSetting()
    }
    constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        initSetting()
    }

    private fun initSetting() {
        //通过系统下载apk
        setDownloadListener { url, _, _, _, _ ->
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
        initWebViewSettings()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebViewSettings() {
        //垂直不显示滚动按钮
        isVerticalScrollBarEnabled = false
        x5WebViewExtension?.isVerticalScrollBarEnabled = false
        x5WebViewExtension?.setScrollBarFadingEnabled(false)

        val webSetting = settings
        webSetting.javaScriptEnabled = true

        webSetting.useWideViewPort = true  //将图片调整到适合webview的大小
        webSetting.loadWithOverviewMode = true // 缩放至屏幕的大小
        webSetting.setSupportZoom(false)  //支持缩放，默认为true。是下面那个的前提。
        webSetting.builtInZoomControls = true //设置内置的缩放控件。
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN //支持内容重新布局
        webSetting.allowFileAccess = true  //设置可以访问文件
        webSetting.setNeedInitialFocus(true) //当webview调用requestFocus时为webview设置节点
        webSetting.loadsImagesAutomatically = true  //支持自动加载图片
        webSetting.defaultTextEncodingName = "utf-8"//设置编码格式
        webSetting.setSupportMultipleWindows(true)
        webSetting.javaScriptCanOpenWindowsAutomatically = true //自动开启窗口 js:window.open()

        setCache(webSetting)
        setCookiesEnabled(true)
    }

    fun shouldOverrideUrlLoading(view: WebView?, jumpUrl: String?): Boolean {
        return false
    }

    private fun setCache(settings: WebSettings) {
        settings.cacheMode = WebSettings.LOAD_NO_CACHE//默认的缓存使用模式。在进行页面前进或后退的操作时，如果缓存可用并未过期就优先加载缓存，否则从网络上加载数据。这样可以减少页面的网络请求次数
        settings.domStorageEnabled = true //Dom Storage

        val cacheDir = context.cacheDir
        if (cacheDir != null) {
            val appCachePath = cacheDir.absolutePath + "app_cache/"
            settings.setAppCachePath(appCachePath)
            settings.databaseEnabled = true
            settings.setAppCacheEnabled(true)
        }
    }

    @SuppressLint("NewApi")
    fun setCookiesEnabled(enabled: Boolean) {
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, enabled)
        }
        CookieManager.getInstance().setAcceptCookie(enabled)
    }

    override fun loadUrl(url: String?) {
        Log.e("CommonWebView",url)
        super.loadUrl(url)
    }

    fun onBackPressed(activity: Activity){
        if (canGoBack()) {
            goBack()
        } else {
            activity.finish()
        }
    }

    fun onDestroy() {
        clearHistory()
        stopLoading()
        removeAllViews()
        destroy()
    }
}
