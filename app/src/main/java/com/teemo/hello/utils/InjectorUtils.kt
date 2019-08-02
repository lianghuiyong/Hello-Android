package com.teemo.hello.utils

import android.content.Context
import com.teemo.hello.data.AppDatabase
import com.teemo.hello.data.MainRepository
import com.teemo.hello.data.ProfileRepository
import com.teemo.hello.viewmodels.main.MainViewModelFactory
import com.teemo.hello.viewmodels.profile.ProfileViewModelFactory
import com.teemo.hello.viewmodels.splash.SplashViewModelFactory

/**
 *  @author: XieGuangwei
 *  @description: viewmodel工厂注射器，生产的viewmodel工厂，注入到待使用处
 *  @date: 2019/7/31 16:06
 */
object InjectorUtils {
    /**
     * 提供启动页ViewModel工厂
     */
    fun provideSplashViewModelFactory(): SplashViewModelFactory {
        return SplashViewModelFactory()
    }

    /**
     * 提供身份填写ViewModel工厂
     */
    fun provideProfileViewModelFactory(context: Context): ProfileViewModelFactory {
        return ProfileViewModelFactory(ProfileRepository.getInstance(AppDatabase.getInstance(context).userDao()))
    }

    /**
     * 提供主页ViewModel工厂
     */
    fun provideMainViewModelFactory(context: Context): MainViewModelFactory {
        return MainViewModelFactory(
            MainRepository.getInstance(
                AppDatabase.getInstance(context).userDao(),
                AppDatabase.getInstance(context).cookieDao()
            ), ProfileRepository.getInstance(AppDatabase.getInstance(context).userDao())
        )
    }
}