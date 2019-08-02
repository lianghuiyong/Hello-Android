package com.teemo.hello.api

import android.text.TextUtils
import com.teemo.hello.base.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/17 17:49
 */
class NetworkHandler {
    companion object {
        fun <T> handleRequest(call: Call<BaseResponse<T>>, success: (data: T?) -> Unit, error: (msg: String?) -> Unit) {
            call.enqueue(object : Callback<BaseResponse<T>> {
                override fun onFailure(call: Call<BaseResponse<T>>, t: Throwable) {
                    when (t) {
                        is UnknownHostException -> {
                            error("请检查网络")
                        }
                        is SocketTimeoutException -> {
                            error("连接超时")
                        }
                        else -> {
                            error("请求失败")
                        }
                    }
                }

                override fun onResponse(call: Call<BaseResponse<T>>, response: Response<BaseResponse<T>>) {
                    if (response.isSuccessful) {
                        val data = response.body()?.data
                        val code = response.body()?.errorCode
                        val msg = response.body()?.errorMsg
                        if (code != 0) {
                            if (TextUtils.isEmpty(msg)) {
                                error("请求失败")
                            } else {
                                error(msg)
                            }
                        } else {
                            success(data)
                        }
                    } else {
                        error("请求失败")
                    }
                }

            })
        }
    }
}