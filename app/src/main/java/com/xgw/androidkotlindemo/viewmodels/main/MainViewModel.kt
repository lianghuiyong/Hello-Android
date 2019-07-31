package com.xgw.androidkotlindemo.viewmodels.main

import android.util.Log
import androidx.lifecycle.*
import com.xgw.androidkotlindemo.bean.response.BannerResponse
import com.xgw.androidkotlindemo.data.MainRepository
import com.xgw.androidkotlindemo.data.ProfileRepository
import com.xgw.androidkotlindemo.data.User
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/19 15:04
 */
class MainViewModel internal constructor(
    private val mainRepository: MainRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    //是否登录（cookie未过期+用户信息不为空）
    val hasLoginLiveData: MediatorLiveData<Boolean>
    val user: LiveData<User?>
    val banner: MutableLiveData<List<BannerResponse>> = MutableLiveData()

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

    fun getUserLiveData(): LiveData<User?> {
        return user
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun getBanner() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e("xgw", "请求banner出错：${throwable.message}")
        }
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
}