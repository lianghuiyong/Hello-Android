package com.xgw.androidkotlindemo.viewmodels.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xgw.androidkotlindemo.data.MainRepository
import com.xgw.androidkotlindemo.data.ProfileRepository

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/19 18:17
 */
class MainViewModelFactory(private val mainRepository: MainRepository, private val profileRepository: ProfileRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(mainRepository,profileRepository) as T
    }
}