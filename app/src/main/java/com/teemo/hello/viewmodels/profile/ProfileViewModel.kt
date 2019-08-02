package com.teemo.hello.viewmodels.profile

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.teemo.hello.api.RequestState
import com.teemo.common.base.BaseViewModel
import com.teemo.hello.bean.response.ProfileResponse
import com.teemo.hello.bean.send.ProfileSendBean
import com.teemo.hello.data.ProfileRepository
import com.teemo.hello.data.User

/**
 *  @author: XieGuangwei
 *  @description: 身份填写（登录、注册）ViewModel
 *  @date: 2019/7/16 20:12
 */
class ProfileViewModel internal constructor(private val profileRepository: ProfileRepository) :
    BaseViewModel<ProfileResponse>() {
    val profileType = MutableLiveData<Int>()
    fun setProfileType(profileType: Int) {
        this.profileType.value = profileType
    }

    fun startLogin(profileSendBean: ProfileSendBean) {
        val userName = profileSendBean.userName
        val password = profileSendBean.password
        when {
            TextUtils.isEmpty(userName) -> {
                setState(RequestState.ERROR, null, "请先输入用户名")
            }
            TextUtils.isEmpty(password) -> {
                setState(RequestState.ERROR, null, "请先输入密码")
            }
            else -> {
                requestHandleException({
                    it?.let {
                        val id = it.id
                        if (!TextUtils.isEmpty(id)) {
                            //先删除之前的用户信息
                            profileRepository.deleteAllUsers()
                            val user = User(id!!, it.username, it.icon)
                            profileRepository.insertUser(user)
                        }
                    }
                }) {
                    profileRepository.login(userName, password)
                }
            }
        }
    }

    fun startRegister(profileSendBean: ProfileSendBean) {
        val userName = profileSendBean.userName
        val password = profileSendBean.password
        val confirmPassword = profileSendBean.confirmPassword
        when {
            TextUtils.isEmpty(userName) -> {
                setState(RequestState.ERROR, null, "请先输入用户名")
            }
            TextUtils.isEmpty(password) -> {
                setState(RequestState.ERROR, null, "请先输入密码")
            }
            TextUtils.isEmpty(confirmPassword) -> {
                setState(RequestState.ERROR, null, "请先输入确认密码")
            }
            password != confirmPassword -> {
                setState(RequestState.ERROR, null, "两次密码不一致")
            }
            else -> {
                requestHandleException {
                    profileRepository.register(userName, password, confirmPassword)
                }
            }
        }
    }
}