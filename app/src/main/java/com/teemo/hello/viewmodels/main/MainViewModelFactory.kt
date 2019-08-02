package com.teemo.hello.viewmodels.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.teemo.hello.data.MainRepository
import com.teemo.hello.data.ProfileRepository

/**
 *  @author: XieGuangwei
 *  @description: 主页ViewModel生产工厂
 *  @date: 2019/7/19 18:17
 */
class MainViewModelFactory(
    private val mainRepository: MainRepository,
    private val profileRepository: ProfileRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(mainRepository, profileRepository) as T
    }
}