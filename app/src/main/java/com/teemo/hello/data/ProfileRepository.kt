package com.teemo.hello.data

import com.teemo.hello.api.NetworkApi
import com.teemo.common.base.BaseResponse
import com.teemo.hello.bean.response.ProfileResponse
import retrofit2.http.Field

/**
 *  @author: XieGuangwei
 *  @description:
 *  @date: 2019/7/17 14:22
 */
class ProfileRepository private constructor(
    private val userDao: UserDao
) {
    companion object {
        @Volatile
        private var instance: ProfileRepository? = null

        fun getInstance(userDao: UserDao): ProfileRepository =
            instance ?: synchronized(this) {
                instance ?: ProfileRepository(userDao).also {
                    instance = it
                }
            }
    }

    suspend fun login(userName: String, password: String): BaseResponse<ProfileResponse>? {
        return NetworkApi.create().login(userName, password)
    }

    suspend fun register(
        userName: String,
        password: String, @Field("repassword") confirmPassword: String
    ): BaseResponse<ProfileResponse>? {
        return NetworkApi.create().register(userName, password, confirmPassword)
    }

    fun insertUser(user: User) = userDao.insertUser(user)

    fun deleteAllUsers() = userDao.deleteAllUsers()

    fun getFirstUser() = userDao.getFirstUser()

}