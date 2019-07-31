package com.xgw.androidkotlindemo.bean.response

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/18 18:48
 */
data class ProfileResponse(
    val admin: Boolean?,
    val chapterTops: List<String>?,
    val email: String?,
    val collectIds: List<String>?,
    val icon: String?,
    var id: String?,
    var nickname: String?,
    var password: String?,
    var token: String?,
    var type: String?,
    var username: String?
)