package com.teemo.hello.viewmodels.main

import android.util.Log
import androidx.lifecycle.*
import com.teemo.hello.api.RequestState
import com.teemo.hello.bean.response.BannerResponse
import com.teemo.hello.bean.response.WxAuthorResponse
import com.teemo.hello.data.MainRepository
import com.teemo.hello.data.ProfileRepository
import com.teemo.hello.data.User
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 *  @author: XieGuangwei
 *  @description: 主页ViewModel
 *  @date: 2019/7/19 15:04
 */
class MainViewModel internal constructor(
    private val mainRepository: MainRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    val requestState = MutableLiveData<RequestState>()//网络请求状态
    val wxResponses = MutableLiveData<List<WxAuthorResponse>>()//微信公众号请求列表返回
    //是否登录（cookie未过期+用户信息不为空）
    val hasLoginLiveData: MediatorLiveData<Boolean>
    val user: LiveData<User?>
    val banner: MutableLiveData<List<BannerResponse>> = MutableLiveData()//首页banner请求列表返回

    init {
        //获取cookie信息
        val latestCookie = mainRepository.getLatestExpireCookie()
        val isCookieNew = latestCookie.map {
            var isNew = false
            if (it != null) {
                isNew = it.expiresAt >= System.currentTimeMillis()
            }
            isNew
        }
        //获取用户信息
        user = profileRepository.getFirstUser()
        val hasUser = user.map {
            it != null
        }
        //合并cookie信息+用户信息（两个互相同时满足true时才说明用户是登录状态）
        hasLoginLiveData = MediatorLiveData()
        hasLoginLiveData.addSource(isCookieNew) {
            hasUser.value?.apply {
                hasLoginLiveData.value = it && this
            }
        }
        hasLoginLiveData.addSource(hasUser) {
            isCookieNew.value?.apply {
                hasLoginLiveData.value = it && this
            }
        }
    }

    /**
     * 更新用户名（登录之后）
     */
    fun updateUserName(name: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val user = User(
                "1",
                name,
                ""
            )
            Log.e("xgw", "手动插入用户数据：\n $user")
            profileRepository.insertUser(user)
        }
    }

    /**
     * 获取用户信息变化
     */
    fun getUserLiveData(): LiveData<User?> {
        return user
    }

    /**
     * 此处清理协程所有任务
     */
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    /**
     * 获取banner
     */
    fun getBanner() {
        //kotlin协程错误统一处理方式
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e("xgw", "请求banner出错：${throwable.message}")
        }
        //开启协程，请求banner
        viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO) {
            val baseResponse = mainRepository.getBanners()
            val code = baseResponse?.errorCode
            val msg = baseResponse?.errorMsg
            val data = baseResponse?.data
            if (code == 0) {
                viewModelScope.launch(coroutineContext + Dispatchers.Main) {
                    banner.value = data
                }
            } else {
                Log.e("xgw", "请求banner出错：$msg")
            }
        }
    }

    /**
     * 获取微信公众号作者列表
     */
    fun getWxAuthors() {
        //协程错误统一处理方式
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            viewModelScope.launch(Dispatchers.Main) {
                requestState.value = RequestState.ERROR
            }
        }
        //开启协程，获取微信公众号作者列表
        viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO) {
            val baseResponse = mainRepository.getWxAuthors()
            val code = baseResponse?.errorCode
            val msg = baseResponse?.errorMsg
            val data = baseResponse?.data
            if (code == 0) {
                viewModelScope.launch(Dispatchers.Main) {
                    wxResponses.value = data
                    requestState.value = RequestState.SUCCESS
                }
            } else {
                viewModelScope.launch(Dispatchers.Main) {
                    requestState.value = RequestState.ERROR
                }
            }
        }
    }
}