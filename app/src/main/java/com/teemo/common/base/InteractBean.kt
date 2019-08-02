package com.teemo.common.base

import com.teemo.hello.api.RequestState

/**
 *  @author: XieGuangwei
 *  @description: 交互实体，记录网络状态和服务器返回实体
 *  @date: 2019/7/18 16:53
 */
data class InteractBean<T>(val state: RequestState, val data: T?, val msg: String?)