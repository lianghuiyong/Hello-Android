package com.xgw.androidkotlindemo.viewmodels.wxarticle

import com.xgw.androidkotlindemo.base.BaseViewModel
import com.xgw.androidkotlindemo.bean.response.WxArticleResponse
import com.xgw.androidkotlindemo.data.WxArticalRepository

/**
 *  @author: XieGuangwei
 *  @description: 微信文章列表获取ViewModel
 *  @date: 2019/7/31 18:13
 */
class WxArticaleViewModel internal constructor(private val wxArticalRepository: WxArticalRepository) :
    BaseViewModel<List<WxArticleResponse>>() {
    fun getWxArticles() {
//        requestHandleException {
////            wxArticalRepository.getWxArticles()
//        }
    }
}