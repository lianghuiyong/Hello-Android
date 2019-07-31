package com.xgw.androidkotlindemo.viewmodels.wxarticle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xgw.androidkotlindemo.data.WxArticalRepository

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/31 18:20
 */
class WxArticleViewModelFactory(private val repository: WxArticalRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WxArticaleViewModel(repository) as T
    }
}