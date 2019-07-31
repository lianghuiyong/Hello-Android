package com.xgw.androidkotlindemo.app

import android.app.Application

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/19 15:24
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
    }
}