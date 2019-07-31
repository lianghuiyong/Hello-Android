package com.xgw.androidkotlindemo.viewmodels.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xgw.androidkotlindemo.data.ProfileRepository

/**
 *  @author: XieGuangwei
 *  @description: 身份填写（登录、注册）ViewModel生产工厂
 *  @date: 2019/7/16 20:16
 */
class ProfileViewModelFactory(private val repository: ProfileRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(repository) as T
    }
}