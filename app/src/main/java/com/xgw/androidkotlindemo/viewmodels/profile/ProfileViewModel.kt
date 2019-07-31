package com.xgw.androidkotlindemo.viewmodels.profile

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.xgw.androidkotlindemo.api.RequestState
import com.xgw.androidkotlindemo.base.BaseViewModel
import com.xgw.androidkotlindemo.bean.response.ProfileResponse
import com.xgw.androidkotlindemo.bean.send.ProfileSendBean
import com.xgw.androidkotlindemo.data.ProfileRepository
import com.xgw.androidkotlindemo.data.User

/**
 *  @author: XieGuangwei
 *  @description:
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