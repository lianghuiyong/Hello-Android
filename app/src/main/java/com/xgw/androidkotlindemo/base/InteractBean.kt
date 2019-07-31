package com.xgw.androidkotlindemo.base

import com.xgw.androidkotlindemo.api.RequestState

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/18 16:53
 */
data class InteractBean<T>(val state: RequestState, val data: T?, val msg: String?)