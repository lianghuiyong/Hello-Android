package com.xgw.androidkotlindemo.viewmodels.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
/**
 *  @author: XieGuangwei
 *  @description: 启动页ViewModel生产工厂
 *  @date: 2019/7/16 20:16
 */
class SplashViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashViewModel() as T
    }
}