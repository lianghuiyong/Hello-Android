package com.teemo.hello.bean.response

/**
 *  @author: XieGuangwei
 *  @description: 微信公众号文章返回实体
 *  @date: 2019/7/31 18:06
 */
data class WxArticleResponse(
    val author: String,
    val link: String,
    val niceDate: String,
    val title: String
)