package com.xgw.androidkotlindemo.data

import com.xgw.androidkotlindemo.api.NetworkApi

/**
 *  @author: XieGuangwei
 *  @description: 微信文章列表获取
 *  @date: 2019/7/31 18:15
 */
class WxArticalRepository private constructor() {
    companion object {
        @Volatile
        private var instance: WxArticalRepository? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: WxArticalRepository().also {
                instance = it
            }
        }
    }

    /**
     * 获取微信文章列表
     */
    suspend fun getWxArticles(pageNo: Int, wxid: String) = NetworkApi.create().getWxArticales(pageNo, wxid)
}