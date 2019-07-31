package com.xgw.androidkotlindemo.utils

import android.content.Context
import com.xgw.androidkotlindemo.data.AppDatabase
import com.xgw.androidkotlindemo.data.MainRepository
import com.xgw.androidkotlindemo.data.ProfileRepository
import com.xgw.androidkotlindemo.viewmodels.main.MainViewModelFactory
import com.xgw.androidkotlindemo.viewmodels.profile.ProfileViewModelFactory
import com.xgw.androidkotlindemo.viewmodels.splash.SplashViewModelFactory


object InjectorUtils {
    fun provideSplashViewModelFactory(): SplashViewModelFactory {
        return SplashViewModelFactory()
    }

    fun provideProfileViewModelFactory(context: Context): ProfileViewModelFactory {
        return ProfileViewModelFactory(ProfileRepository.getInstance(AppDatabase.getInstance(context).userDao()))
    }

    fun provideMainViewModelFactory(context: Context): MainViewModelFactory {
        return MainViewModelFactory(
            MainRepository.getInstance(
                AppDatabase.getInstance(context).userDao(),
                AppDatabase.getInstance(context).cookieDao()
            ), ProfileRepository.getInstance(AppDatabase.getInstance(context).userDao())
        )
    }
}