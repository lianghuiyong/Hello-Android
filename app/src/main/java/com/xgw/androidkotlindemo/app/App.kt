package com.xgw.androidkotlindemo.app

import android.app.Application
import android.content.ComponentCallbacks2
import android.util.Log
import com.bumptech.glide.Glide
import com.tencent.smtt.sdk.QbSdk

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/19 15:24
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        val callback = object : QbSdk.PreInitCallback {
            override fun onViewInitFinished(arg: Boolean) {
                //x5內核初始化完成的回调，
                // true表示x5内核加载成功，
                // false表示x5内核加载失败，会自动切换到系统内核。
                val value = if (arg) "成功" else "失败"
                Log.e("====>", "X5内核加载状态：$value")
            }
            override fun onCoreInitFinished() {}
        }

        QbSdk.initX5Environment(this, callback)
    }

    companion object {
        lateinit var instance: App
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if(level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN){
            Glide.get(this).clearMemory()
        }
        Glide.get(this).trimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }
}