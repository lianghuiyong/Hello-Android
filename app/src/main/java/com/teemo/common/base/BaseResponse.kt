package com.teemo.common.base

/**
 *  @author: XieGuangwei
 *  @description: 服务器返回实体基类
 *  @date: 2019/7/17 14:34
 */
data class BaseResponse<T>(var data: T?, var errorCode: Int?, var errorMsg: String?)