package com.teemo.hello.bean.response

/**
 *  @author: XieGuangwei
 *  @description: 微信公众号号主返回实体
 *  @date: 2019/7/31 16:06
 */
data class WxAuthorResponse(
    val courseId: String,
    val id: String,
    val name: String,
    val parentChapterId: String
) {
    override fun equals(other: Any?): Boolean {
        return other is WxAuthorResponse && other.name == name
    }

    override fun hashCode(): Int {
        var result = courseId.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + parentChapterId.hashCode()
        return result
    }
}