package com.xgw.androidkotlindemo.base

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/17 14:34
 */
data class BaseResponse<T>(var data: T?, var errorCode: Int?, var errorMsg: String?)