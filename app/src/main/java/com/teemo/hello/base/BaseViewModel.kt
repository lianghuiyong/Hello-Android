package com.teemo.hello.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teemo.hello.api.RequestState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 *  @author: XieGuangwei
 *  @description: viewmodel基类，统一处理网络请求状态变化、网络请求错误处理
 *  @date: 2019/7/18 14:18
 */
abstract class BaseViewModel<T> : ViewModel() {
    val interaction = MutableLiveData<InteractBean<T>>()

    init {
        setState(RequestState.DEFAULT, null, null)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    protected fun requestHandleException(
        response: ((T?) -> Unit)? = null,
        request: suspend () -> BaseResponse<T>?
    ) {
        setState(RequestState.LOADING, null, null)
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            val errorMsg = when (throwable) {
                is UnknownHostException -> "请检查网络"
                is SocketTimeoutException -> "连接超时"
                else -> "请求失败"
            }
            setState(RequestState.ERROR, null, errorMsg)
        }
        viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO) {
            val baseResponse = request()
            val code = baseResponse?.errorCode
            val msg = baseResponse?.errorMsg
            val data = baseResponse?.data
            if (code == 0) {
                if (response != null) {
                    response(data)
                    setState(RequestState.SUCCESS, data, null)
                }
            } else {
                setState(RequestState.ERROR, data, msg ?: "请求失败")
            }
        }
    }

    protected fun setState(state: RequestState, data: T?, msg: String?) {
        viewModelScope.launch(Dispatchers.Main) {
            interaction.value = InteractBean(state, data, msg)
        }
    }
}