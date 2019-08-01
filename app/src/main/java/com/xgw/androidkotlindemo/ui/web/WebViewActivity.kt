package com.xgw.androidkotlindemo.ui.web

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

    override fun initData() {

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